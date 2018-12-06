package com.banry.pscm.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import com.banry.pscm.account.CustomAccessDecisionManager;
import com.banry.pscm.account.CustomFilterSecurityInterceptor;
import com.banry.pscm.account.CustomUserDetailsService;
import com.banry.pscm.account.CustomerAuthenticationSecurityConfig;

/**
 * 
 * @author Administrator
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomFilterSecurityInterceptor myFilterSecurityInterceptor;
    @Autowired
    private CustomAccessDecisionManager accessDecisionManager;
    
    @Autowired
    @Qualifier("dynamicDataSource")
    private DataSource dataSource;
    
    @Autowired
    private CustomerAuthenticationSecurityConfig customerAuthenticationSecurityConfig;
    
    @Bean
    UserDetailsService customUserService(){ //注册UserDetailsService 的bean
        return new CustomUserDetailsService();
    }
    
    @Bean
    AuthenticationSuccessHandler successHandler() {
    	return new SimpleUrlAuthenticationSuccessHandler("/index");
    }
    
    @Bean
    AuthenticationFailureHandler failureHandler() {
    	return new SimpleUrlAuthenticationFailureHandler("/login?error");
    }
    
    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){        
        JdbcTokenRepositoryImpl j=new JdbcTokenRepositoryImpl();
        j.setDataSource(dataSource);
        return j;
    }

    @Bean
    public TokenBasedRememberMeServices tokenBasedRememberMeServices() {
        TokenBasedRememberMeServices tbrms = new TokenBasedRememberMeServices("railway", customUserService());
        // 设置cookie过期时间为2天
        tbrms.setTokenValiditySeconds(60 * 60 * 24 * 2);
        // 设置checkbox的参数名为rememberMe（默认为remember-me），注意如果是ajax请求，参数名不是checkbox的name而是在ajax的data里
//        tbrms.setParameter("rememberMe");
        return tbrms;
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//user Details Service验证
        auth.userDetailsService(customUserService()).passwordEncoder(passwordEncoder()); //user Details Service验证
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.apply(customerAuthenticationSecurityConfig);
        http.csrf().disable()
        		.authorizeRequests()
        		.antMatchers("/login","/**/*.bmp","/**/*.jpeg","/**/*.jpg",
        				"/**/*.png","/**/*.gif","/**/*.css","/**/*.js","/**/iconfont.woff","/**/iconfont.ttf","/**/iconfont.eot",
        				"/**/*.apk","/**/*.png","/discloseContent","/confirmDisclose","/wokerDiscloseList","/tenderPlan/getTotalReleaseTenderPlan").permitAll()
                .anyRequest().authenticated() //任何请求,登录后可以访问
//                .and()
//                .authorizeRequests()
//                .antMatchers("/**").hasRole("admin")
//                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()  
                .and()
                .formLogin()
                .loginPage("/login")//.loginProcessingUrl("/login.htm")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/index")
                .permitAll() //登录页面用户任意访问
                .and()
                .logout().permitAll() //注销行为任意访问
                .and()
                .rememberMe()
//                .tokenValiditySeconds(1209600)
                //指定记住登录信息所使用的数据源
                .tokenRepository(tokenRepository());//code4
        myFilterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager);
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }
}