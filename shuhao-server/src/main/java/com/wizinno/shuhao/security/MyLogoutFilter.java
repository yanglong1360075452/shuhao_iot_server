package com.wizinno.shuhao.security;

import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.PostConstruct;

public class MyLogoutFilter extends LogoutFilter {

    private RequestMatcher logoutRequestMatcher;

    @PostConstruct
    public void init(){
        super.setLogoutRequestMatcher(logoutRequestMatcher);
    }

    public MyLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler... handlers){
        super(logoutSuccessHandler, handlers);
    }

    public RequestMatcher getLogoutRequestMatcher() {
        return logoutRequestMatcher;
    }

    @Override
    public void setLogoutRequestMatcher(RequestMatcher logoutRequestMatcher) {
        this.logoutRequestMatcher = logoutRequestMatcher;
    }
}
