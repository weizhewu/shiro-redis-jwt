package com.waltz.springshirostudy.constant;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 鉴权常量
 * @createDate: 2023/4/6 14:40
 **/
public class AuthConstant {
    //用于验证使用：Header中的名字
    public static final String DEFAULT_TOKEN_NAME = "Authorization";

    //验证用户账号
    public static final String CLIENT_PARAM_ACCOUNT = "account";

    //验证用户标识
    public static final String CLIENT_PARAM_USERID ="userId";

    //验证token保存时间戳
    public static final String CURRENT_TIME_MILLS = "currentTimeMills";

    //私有秘钥secretKey（可以对其base64加密）
    public static final String SECRET_KEY = "esddfdJiYmNjY2evasdlZWhhhhdga";

    // 盐
    public static final String SALT = "esddfdJiYmNjY2evasdlZWhhhhdga";

    // 盐加密次数
    public static final Integer SALT_TIMES = 3;

    // 加密方式
    public static final String MD5 = "MD5";


    // 永久封禁数量，数量到达10的时候，账号将被封禁
    public static final Integer DISABLE_COUNT = 10;

}
