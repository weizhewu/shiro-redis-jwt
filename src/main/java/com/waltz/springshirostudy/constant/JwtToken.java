package com.waltz.springshirostudy.constant;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: JwtToken
 * @createDate: 2023/4/6 14:54
 **/
public class JwtToken implements AuthenticationToken {
    private String account;

    private String authToken;

    public JwtToken(String account, String authToken) {
        this.account = account;
        this.authToken = authToken;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public Object getPrincipal() {
        return account;
    }

    @Override
    public Object getCredentials() {
        return authToken;
    }
}
