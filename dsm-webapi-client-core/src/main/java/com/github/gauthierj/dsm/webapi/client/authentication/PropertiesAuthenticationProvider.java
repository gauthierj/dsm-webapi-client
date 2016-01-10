package com.github.gauthierj.dsm.webapi.client.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class PropertiesAuthenticationProvider implements AuthenticationProvider {

    @Value("${dsm.webapi.username}")
    private String username;
    @Value("${dsm.webapi.password}")
    private String password;
    @Value("${dsm.webapi.session:default}")
    private String session;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public LoginInformation getLoginInformation() {
        return authenticationService.login(username, password, session);
    }
}
