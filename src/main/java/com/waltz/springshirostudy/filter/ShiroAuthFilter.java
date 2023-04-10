package com.waltz.springshirostudy.filter;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.waltz.springshirostudy.entity.JwtToken;
import com.waltz.springshirostudy.utils.token.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 自定义shiro过滤器
 * @createDate: 2023/4/7 11:16
 **/
@Slf4j
public class ShiroAuthFilter extends BasicHttpAuthenticationFilter {
    // 需要过滤拦截的请求地址，多个地址用","分割
    @Value("${system.config.filterExclude}")
    private String excludeUrl;

    // 默认需要放行的请求地址
    private final String[] defaultExcludeUrl = new String[]{
            "/logout", "/login", "/userLogin/index" , "/authenticate","/exception/authenticationException"
    };

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        return executeLogin(request, response);
    }


    /**
     * 执行登录的操作
     * 检测header里面是否包含CpaToken字段即可
     * @param request 请求
     * @param response 响应
     * @return boolean
     * @throws AuthenticationException 异常
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        String accessToken = this.getAuthzHeader(request);
        if (StringUtils.isBlank(accessToken)) {
            log.error("请求header中accessToken为空");
            response401(response);
            return false;
        }
        JwtToken token = null;
        try {
            token = JwtTokenUtil.validateAndGetToken(accessToken);
        } catch (Exception e){
            log.error("异常：{}",e.getMessage());
            response401(response);
            return false;
        }

        if (Objects.nonNull(token)) {
            // 提交给realm进行登入，如果错误他会抛出异常并被捕获
            getSubject(request, response).login(token);
        } else {
            log.error("token为空");
            response401(response);
            return false;
        }
        return true;
    }

    /**
     * 请求接口url果过滤
     * @param request 请求
     * @param response 响应
     * @param mappedValue 请求头中的值
     * @return boolean
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
       log.info("处理需过滤的请求");
        String u = ((HttpServletRequest) request).getRequestURI();
        String contextPath=((HttpServletRequest)request).getContextPath();
        //去除contextPath
        if(StringUtils.isNotBlank(u)&&!contextPath.equals("/") && u.startsWith(contextPath)){
            u=u.substring(contextPath.length());
        }

        for (String e : defaultExcludeUrl) {
            if (u.endsWith(e)) {
                return true;
            }
        }
        if (StringUtils.isNotBlank(excludeUrl)) {
            String[] customExcludes = excludeUrl.split(",");
            for (String e : customExcludes) {
                if (e.contains("*")) {
                    e = e.replace("*", "");
                    if (u.startsWith(e)) {
                        return true;
                    }
                }
                if (u.endsWith(e)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 异常跳转到处理类
     * @param response 响应
     */
    private void response401(ServletResponse response) {
        try {
            ((HttpServletResponse) response).sendRedirect("/exception/authenticationException");
        } catch (IOException e) {
            log.error("异常跳转", e);
        }

    }
}
