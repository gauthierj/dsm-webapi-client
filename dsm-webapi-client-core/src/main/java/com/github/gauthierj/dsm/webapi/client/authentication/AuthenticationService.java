package com.github.gauthierj.dsm.webapi.client.authentication;

public interface AuthenticationService {

    LoginInformation login(String username, String password, String session);

    void logout(LoginInformation loginInformation);
}
