package com.waltz.springshirostudy.config;

import com.waltz.springshirostudy.filter.ShiroAuthFilter;
import com.waltz.springshirostudy.filter.StatelessDefaultSubjectFactory;
import com.waltz.springshirostudy.filter.StatelessRealm;
import com.waltz.springshirostudy.filter.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * @version 1.0.0
 * @author: wei-zhe-wu
 * @description: shiro配置类
 * @createDate: 2023/4/7 11:41
 **/
@Configuration
@Slf4j
public class ShiroConfig {
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);


        log.info("配置访问权限,拦截策略以键值对存入map");
        Map<String, String> filterChainDefinitions = new LinkedHashMap<>();
        filterChainDefinitions.put("/static/**", "anon");
        filterChainDefinitions.put("/userLogin/login", "anon");

        log.info("设置拦截器");
        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();

        // 无状态过滤
        filters.put("statelessAuth", shiroAuthFilter());
        shiroFilterFactoryBean.setFilters(filters);
        // 拦截自定义设置
        filterChainDefinitions.put("/**", "statelessAuth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitions);

        return shiroFilterFactoryBean;
    }

    @Bean(name = "securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //设置不创建session
        securityManager.setSubjectFactory(subjectFactory());

        log.info("设置sessionManager禁用掉会话调度器");
        securityManager.setSessionManager(sessionManager());

        log.info("注入缓存管理器");
        // 这个如果执行多次，也是同样的一个对象
        securityManager.setCacheManager(new CustomCacheManager());
        /*
         * 无状态需要设置不创建session，禁用使用Sessions 作为存储策略的实现，
         * 但它没有完全地禁用Sessions，
         * 所以需要配合context.setSessionCreationEnabled(false);
         */

        ((DefaultSessionStorageEvaluator) ((DefaultSubjectDAO) securityManager.getSubjectDAO()).getSessionStorageEvaluator()).setSessionStorageEnabled(false);
        log.info("配置realm");
//        03 设置SecurityManager对象的认证策略
        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
//        modularRealmAuthenticator.setAuthenticationStrategy(new AllSuccessfulStrategy());
        securityManager.setAuthenticator(modularRealmAuthenticator);
        //设置两个Realm，一个用于用户登录验证和访问权限获取；一个用于jwt token的认证
        Collection<Realm> realms = new ArrayList<>(Arrays.asList(userRealm(),statelessRealm()));
        securityManager.setRealms(realms);
        return securityManager;
    }


    /*
     * subject工厂管理器.
     * 用于禁用session
     */
    @Bean
    public DefaultWebSubjectFactory subjectFactory() {
        return new StatelessDefaultSubjectFactory();
    }

    /**
     * session管理器
     * sessionManager通过sessionValidationSchedulerEnabled禁用掉会话调度器，
     * 因为我们禁用掉了会话，所以没必要再定期过期会话了。
     * @return DefaultSessionManager
     */
    @Bean
    public DefaultSessionManager sessionManager() {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(false);
        return sessionManager;
    }

    /**
     * token验证
     * @return StatelessRealm
     */
    @Bean
    public StatelessRealm statelessRealm() {
        StatelessRealm statelessRealm = new StatelessRealm();
        statelessRealm.setCachingEnabled(false);
        return statelessRealm;
    }

    /**
     * 账号密码验证
     * @return UserRealm
     */
    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
        userRealm.setCachingEnabled(false);
        return userRealm;
    }


    /**
     * 无状态权限验证
     * @return ShiroAuthFilter
     */
    @Bean
    public ShiroAuthFilter shiroAuthFilter(){
        return new ShiroAuthFilter();
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 必须加否则shiro注解无法使用
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
