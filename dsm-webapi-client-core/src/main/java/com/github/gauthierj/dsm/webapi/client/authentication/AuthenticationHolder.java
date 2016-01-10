package com.github.gauthierj.dsm.webapi.client.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class AuthenticationHolder {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AuthenticationService authenticationService;

    private LoginInformation loginInformation;

    public synchronized LoginInformation getLoginInformation() {
        if(loginInformation == null) {
            loginInformation = authenticationProvider.getLoginInformation();
        }
        return loginInformation;
    }

    @PreDestroy
    public void destroy() {
        authenticationService.logout(loginInformation);
    }
}
