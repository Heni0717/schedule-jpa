package com.example.app.common.config;

import com.example.app.userinfo.auth.AuthService;
import com.example.app.userinfo.auth.SessionStorage;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebConfig {

    private final SessionStorage sessionStorage;
    private final AuthService authService;

    @Bean
    public FilterRegistrationBean<Filter> sessionAuthFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionAuthFilter(sessionStorage));
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<Filter> ownerCheckFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new OwnerCheckFilter(authService, sessionStorage));
        registrationBean.setOrder(2);
        registrationBean.addUrlPatterns("/schedule/*");
        return registrationBean;
    }
}
