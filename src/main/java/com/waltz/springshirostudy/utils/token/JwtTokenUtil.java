package com.waltz.springshirostudy.utils.token;


import com.waltz.springshirostudy.constant.AuthConstant;
import com.waltz.springshirostudy.constant.JwtToken;
import com.waltz.springshirostudy.constant.RedisConstant;
import com.waltz.springshirostudy.service.RedisService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.Objects;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: token工具
 * @createDate: 2023/4/6 14:17
 **/
@Component
@Slf4j
public class JwtTokenUtil {
    private static RedisService redisService;

    @Autowired
    public void setRedisService(RedisService redisService) {
        JwtTokenUtil.redisService = redisService;
    }


    /**
     * 根据秘钥生成token
     * @param account 账号
     * @param userName 用户名称
     * @return 生成的秘钥
     */
    public static String createToken(String account,String userName){
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        // 生成jwtBuilder并构造参数信息
        JwtBuilder builder = Jwts.builder()
                // 不重要的数据放在claim中
                .claim("currentTimeMillis",currentTimeMillis)
                .claim("userName",userName)
                .setSubject(account) // 设置主题（声明信息）
                .setExpiration(new Date(System.currentTimeMillis()+ RedisConstant.SHIRO_CACHE_EXPIRATION_TIME)) // 过期时间
                .setIssuedAt(new Date(System.currentTimeMillis()))//当前时间
                .signWith(SignatureAlgorithm.HS256, AuthConstant.SECRET_KEY);// 设置安全密钥（生成签名所需的密钥和算法）
        // 生成Jwt Token(1.编码 Header 和 Payload 2.生成签名 3.拼接字符串)
        return builder.compact();
    }

    /**
     * 根据秘钥解析token
     * @param token token
     * @return Claims
     */
    public static Claims parseToken(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(AuthConstant.SECRET_KEY))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e){
            log.error("parseToken异常",e);
        }
        return claims;
    }


    /**
     * 获得token中的信息
     * @param token token
     * @param claimsParam claims中的参数
     * @return token中的信息
     */
    public static String getClaim(String token,String claimsParam){
        Claims claims = parseToken(token);
        if (Objects.isNull(claims)) {
            return null;
        }
        Object o = claims.get(claimsParam);
        if (o instanceof String){
            return (String) o;
        }
        return null;
    }


    /**
     * 身份验证
     * @param authToken authToken
     * @return JwtToken
     */
    public static JwtToken validateAndGetToken(String authToken){
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser()
                    .setSigningKey(AuthConstant.SECRET_KEY)
                    .parseClaimsJws(authToken);
            String account = claimsJws.getBody().getSubject();
            System.out.println("redisKey:"+RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account);
            if (redisService.existsKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
                return new JwtToken(account, authToken);
            }
        } catch (SignatureException e) {
            log.info("JWT验签失败:", e);
            throw new SignatureException(e.getMessage());
        } catch (MalformedJwtException e) {
            log.info("JWT构造异常:", e);
            throw new MalformedJwtException(e.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("JWT过期异常:", e);
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.info("JWT预期格式不正确:", e);
            throw new UnsupportedJwtException(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.info("方法参数异常:", e);
            throw new RuntimeException(e);
        }
        return null;
    }
}
