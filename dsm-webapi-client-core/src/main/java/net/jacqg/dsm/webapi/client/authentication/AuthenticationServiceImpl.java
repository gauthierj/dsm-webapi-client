package net.jacqg.dsm.webapi.client.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.jacqg.dsm.webapi.client.DsmWebApiResponseError;
import net.jacqg.dsm.webapi.client.DsmWebapiClient;
import net.jacqg.dsm.webapi.client.DsmWebapiRequest;
import net.jacqg.dsm.webapi.client.DsmWebapiResponse;
import net.jacqg.dsm.webapi.client.ErrorHandler;
import net.jacqg.dsm.webapi.client.apiinfo.ApiInfo;
import net.jacqg.dsm.webapi.client.apiinfo.ApiInfoService;
import net.jacqg.dsm.webapi.client.authentication.exception.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    // API Infos
    private static final String API_ID = "SYNO.API.Auth";

    // API Methods
    private static final String METHOD_LOGIN = "login";

    // Parameters
    private static final String PARAMETER_ACCOUNT = "account";
    private static final String PARAMETER_FORMAT = "format";
    private static final String PARAMETER_LOGOUT = "logout";
    private static final String PARAMETER_PASSWD = "passwd";
    private static final String PARAMETER_SESSION = "session";

    // Parameters values
    private static final String PARAMETER_VALUE_SID = "sid";

    @Autowired
    @Qualifier("unauthenticated")
    private DsmWebapiClient restClient;

    @Autowired
    private ApiInfoService apiInfoService;

    private ApiInfo apiInfo;

    @PostConstruct
    public void init() {
        this.apiInfo = apiInfoService.findOne(API_ID);
    }

    @Override
    public LoginInformation login(String username, String password, String session) throws InvalidLoginException {
        DsmWebapiRequest request = new DsmWebapiRequest(apiInfo.getApi(), apiInfo.getMaxVersion(), apiInfo.getPath(), METHOD_LOGIN)
                .parameter(PARAMETER_ACCOUNT, username)
                .parameter(PARAMETER_PASSWD, password)
                .parameter(PARAMETER_SESSION, session)
                .parameter(PARAMETER_FORMAT, PARAMETER_VALUE_SID);
        LoginWebapiResponse response = restClient.call(request, LoginWebapiResponse.class, new LoginErrorHandler());
        return new LoginInformation(username, session, response.getData().getSid());
    }

    @Override
    public void logout(LoginInformation loginInformation) {
        DsmWebapiRequest request = new DsmWebapiRequest(apiInfo.getApi(), apiInfo.getMaxVersion(), apiInfo.getPath(), PARAMETER_LOGOUT)
                .parameter(PARAMETER_SESSION, loginInformation.getSession());
        DsmWebapiResponse response = restClient.call(request, DsmWebapiResponse.class);
    }

    private static class LoginWebapiResponse extends DsmWebapiResponse<LoginInformation> {

        public LoginWebapiResponse(@JsonProperty("success") boolean success, @JsonProperty("data") LoginInformation data, @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }

    private static class LoginErrorHandler implements ErrorHandler {
        @Override
        public void handleError(DsmWebapiRequest request, DsmWebApiResponseError error) {
            throw new InvalidLoginException(error.getCode());
        }
    }
}
