package com.waltz.springshirostudy.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;
import java.util.Iterator;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: 自定义Realm策略，解决在多个 Realm 下，其中一个 Realm 抛异常后，继续验证其它 Realm，导致原 Realm 异常被新的 AuthenticationException 覆盖问题，无法识别原始异常类型
 * @createDate: 2023/4/10 11:15
 **/
@Slf4j
public class MyModularRealmAuthenticator extends ModularRealmAuthenticator {

    /**
     *  扩展父类原方法，对异常立即进行外抛
     * @param realms realms
     * @param token token
     * @return AuthenticationInfo
     */
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        AuthenticationStrategy strategy = this.getAuthenticationStrategy();
        AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);
        if (log.isTraceEnabled()) {
            log.trace("Iterating through {} realms for PAM authentication", realms.size());
        }
        Iterator var5 = realms.iterator();
        AuthenticationException authenticationException = null;
        while(var5.hasNext()) {
            Realm realm = (Realm)var5.next();
            aggregate = strategy.beforeAttempt(realm, token, aggregate);
            if (realm.supports(token)) {
                log.trace("Attempting to authenticate token [{}] using realm [{}]", token, realm);
                AuthenticationInfo info = null;
                Throwable t = null;
                try {
                    info = realm.getAuthenticationInfo(token);
                } catch (Throwable var11) {
                    t = var11;
                    authenticationException = (AuthenticationException) var11;
                    if (log.isDebugEnabled()) {
                        String msg = "Realm [" + realm + "] threw an exception during a multi-realm authentication attempt:";
                        log.debug(msg, var11);
                    }
                }
                aggregate = strategy.afterAttempt(realm, token, info, aggregate, t);
                //增加此逻辑，只有authenticationException不为null，则表示有Realm较验到了异常，则立即中断后续Realm验证直接外抛
                if (authenticationException != null){
                    throw authenticationException;
                }
            } else {
                log.debug("Realm [{}] does not support token {}.  Skipping realm.", realm, token);
            }
        }

        aggregate = strategy.afterAllAttempts(token, aggregate);
        return aggregate;
    }
}
