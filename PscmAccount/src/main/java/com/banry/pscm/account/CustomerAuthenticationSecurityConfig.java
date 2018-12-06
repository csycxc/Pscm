package com.banry.pscm.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.banry.pscm.service.account.SysUsersService;
import com.banry.pscm.service.tender.SupplierService;

@Component
public class CustomerAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;
    
    @Autowired
    private RememberMeServices rememberMeServices;
    
    @Autowired
    private SysUsersService sysUsersService;
    @Autowired  
    private RestTemplate restTemplate;
    @Autowired
	private SupplierService supplierService;
    @Autowired
	private Environment ev;	

//    @Autowired
//    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {

    	DisUsernamePasswordAuthenticationFilter authenticationFilter = new DisUsernamePasswordAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        authenticationFilter.setFilterProcessesUrl("/checkLogin.htm");
        authenticationFilter.setRememberMeServices(rememberMeServices);
        authenticationFilter.setSysUsersService(sysUsersService);
        authenticationFilter.setSupplierService(supplierService);
        authenticationFilter.setRestTemplate(restTemplate);
        authenticationFilter.setEv(ev);
//        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
//        smsCodeAuthenticationProvider.setUserDetailsService(userDetailsService);

        http//.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
