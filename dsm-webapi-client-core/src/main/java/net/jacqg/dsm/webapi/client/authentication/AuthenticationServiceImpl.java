package net.jacqg.dsm.webapi.client.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.jacqg.dsm.webapi.client.DsmWebapiClient;
import net.jacqg.dsm.webapi.client.DsmWebapiRequest;
import net.jacqg.dsm.webapi.client.DsmWebapiResponse;
import net.jacqg.dsm.webapi.client.apiinfo.ApiInfo;
import net.jacqg.dsm.webapi.client.apiinfo.ApiInfoService;
import net.jacqg.dsm.webapi.client.authentication.exception.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    @Qualifier("unauthenticated")
    private DsmWebapiClient restClient;

    @Autowired
    private ApiInfoService apiInfoService;

    private ApiInfo apiInfo;

    @PostConstruct
    public void init() {
        this.apiInfo = apiInfoService.findOne("SYNO.API.Auth");
    }

    @Override
    public LoginInformation login(String username, String password, String session) throws InvalidLoginException {
        DsmWebapiRequest request = new DsmWebapiRequest(apiInfo.getApi(), apiInfo.getMaxVersion(), apiInfo.getPath(), "login")
                .parameter("account", username)
                .parameter("passwd", password)
                .parameter("session", session)
                .parameter("format", "sid");
        LoginWebapiResponse response = restClient.call(request, LoginWebapiResponse.class);
        if(response.isSuccess()) {
            return new LoginInformation(username, session, response.getData().getSid());
        } else {
            throw new InvalidLoginException(response.getError().getCode());
        }
    }

    @Override
    public void logout(LoginInformation loginInformation) {
        DsmWebapiRequest request = new DsmWebapiRequest(apiInfo.getApi(), apiInfo.getMaxVersion(), apiInfo.getPath(), "logout")
                .parameter("session", loginInformation.getSession());
        DsmWebapiResponse response = restClient.call(request, DsmWebapiResponse.class);
        if(!response.isSuccess()) {
            throw new AssertionError("Cannot happen");
        }
    }

    private static class LoginWebapiResponse extends DsmWebapiResponse<LoginInformation> {

        public LoginWebapiResponse(@JsonProperty("success") boolean success, @JsonProperty("data") LoginInformation data, @JsonProperty("error") Error error) {
            super(success, data, error);
        }
    }

}
