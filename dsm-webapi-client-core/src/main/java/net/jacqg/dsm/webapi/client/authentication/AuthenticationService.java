package net.jacqg.dsm.webapi.client.authentication;

public interface AuthenticationService {

    LoginInformation login(String username, String password, String session);

    void logout(LoginInformation loginInformation);
}
