package net.jacqg.dsm.webapi.client;

import net.jacqg.dsm.webapi.client.exception.BadRequestException;
import net.jacqg.dsm.webapi.client.exception.DsmWebApiErrorException;
import net.jacqg.dsm.webapi.client.exception.PermissionDeniedException;
import net.jacqg.dsm.webapi.client.exception.SessionExpiredException;
import net.jacqg.dsm.webapi.client.exception.UnknownErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class DsmWebapiClientImpl implements DsmWebapiClient {

    @Autowired
    @Qualifier("dsmWebapiClientRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private DsmUrlProvider dsmUrlProvider;

    @Override
    public <T extends DsmWebapiResponse<?>> T call(DsmWebapiRequest request, Class<T> responseType) {
        return call(request, responseType, null);
    }

    @Override
    public <T extends DsmWebapiResponse<?>> T call(DsmWebapiRequest request, Class<T> responseType, ErrorHandler errorHandler) {
        T response = restTemplate.getForObject(buildUri(request), responseType);
        handleFailure(request, errorHandler, response);
        return response;
    }

    public void customizeUri(UriComponentsBuilder uriComponentsBuilder) {
        // Template method
    }

    @Override
    public URI buildUri(DsmWebapiRequest request) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(dsmUrlProvider.getDsmUrl())
                .path("webapi/")
                .path(request.getPath())
                .queryParam("api", request.getApi())
                .queryParam("version", request.getVersion())
                .queryParam("method", request.getMethod());
        for (Map.Entry<String, String> entry : request.getParameters().entrySet()) {
            uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue());
        }
        customizeUri(uriComponentsBuilder);
        String url = uriComponentsBuilder.toUriString();
        int queryStart = url.indexOf("?");
        return createUriQuietly(url, queryStart);
    }

    private <T extends DsmWebapiResponse<?>> void handleFailure(DsmWebapiRequest request, ErrorHandler errorHandler, T response) {
        if(!response.isSuccess()) {
            int code = response.getError().getCode();
            handleGenericErrors(response);
            handleSpecificErrorsIfNeeded(request, errorHandler, response);
            throw new DsmWebApiErrorException(String.format("An unexpected error has occured: %s", code), response.getError());
        }
    }

    private <T extends DsmWebapiResponse<?>> void handleGenericErrors(T response) {
        if(!response.isSuccess()) {
            DsmWebApiResponseError error = response.getError();
            switch (error.getCode()) {
                case GenericErrorCodes.ERROR_CODE_UNKNOWN_ERROR:
                    throw new UnknownErrorException(error);
                case GenericErrorCodes.ERROR_CODE_NO_PARAMETER:
                    throw new BadRequestException("No parameter of API, method or version", error);
                case GenericErrorCodes.ERROR_CODE_API_DOES_NOT_EXISTS:
                    throw new BadRequestException("The requested API does not exist", error);
                case GenericErrorCodes.ERROR_CODE_METHOD_DOES_NOT_EXISTS:
                    throw new BadRequestException("The requested method does not exist", error);
                case GenericErrorCodes.ERROR_CODE_VERSION_DOES_NOT_SUPPORT_FEATURE:
                    throw new BadRequestException("The requested version does not support the functionality", error);
                case GenericErrorCodes.ERROR_CODE_PERMISSION_DENIED:
                    throw new PermissionDeniedException(error);
                case GenericErrorCodes.ERROR_CODE_SESSION_TIMEOUT:
                    throw new SessionExpiredException("Session timeout", error);
                case GenericErrorCodes.ERROR_CODE_DUPLICATE_LOGIN:
                    throw new SessionExpiredException("Session interrupted by duplicate login", error);
                default:
                    //skip
            }
        }
    }

    private <T extends DsmWebapiResponse<?>> void handleSpecificErrorsIfNeeded(DsmWebapiRequest request, ErrorHandler errorHandler, T response) {
        if(errorHandler != null) {
            errorHandler.handleError(request, response.getError());
        }
    }

    private URI createUriQuietly(String url, int queryStart) {
        try {
            return new URI(url.substring(0, queryStart) + url.substring(queryStart).replace("/", "%2F"));
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Could not build uri form url: " + url, e);
        }
    }
}
