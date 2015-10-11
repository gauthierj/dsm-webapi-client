package net.jacqg.dsm.webapi.client.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHolder {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    private LoginInformation loginInformation;

    public synchronized LoginInformation getLoginInformation() {
        if(loginInformation == null) {
            loginInformation = authenticationProvider.getLoginInformation();
        }
        return loginInformation;
    }
}
