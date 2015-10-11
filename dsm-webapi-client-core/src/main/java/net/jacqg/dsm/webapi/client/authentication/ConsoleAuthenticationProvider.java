package net.jacqg.dsm.webapi.client.authentication;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

public class ConsoleAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public LoginInformation getLoginInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("username: ");
        String username = scanner.nextLine();
        System.out.print("password: ");
        String password = scanner.nextLine();
        return authenticationService.login(username, password, username);
    }
}
