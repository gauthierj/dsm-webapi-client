package net.jacqg.dsm.webapi.client.authentication;

import com.google.common.base.Strings;
import net.jacqg.dsm.webapi.client.AbstractTest;
import net.jacqg.dsm.webapi.client.authentication.exception.InvalidLoginException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class AuthenticationServiceTest extends AbstractTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Value("${dsm.webapi.username}")
    private String username;
    @Value("${dsm.webapi.password}")
    private String password;
    @Value("${dsm.webapi.session}")
    private String session;

    @Test(expected = InvalidLoginException.class)
    public void testWrongCredentials() {
        authenticationService.login("azer", "azer", "azer");
    }

    @Test
    public void testGoodCredentials() {
        LoginInformation login = authenticationService.login(username, password, session);
        Assert.assertFalse(Strings.isNullOrEmpty(login.getSid()));
        Assert.assertEquals(username, login.getUsername());
        Assert.assertEquals(session, login.getSession());

    }

    @Test
    public void testLogout() {
        LoginInformation login = authenticationService.login(username, password, session);
        authenticationService.logout(login);
    }
}
