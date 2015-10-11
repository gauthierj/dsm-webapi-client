package net.jacqg.dsm.webapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

public class DsmWebapiClientImpl implements DsmWebapiClient {

    @Autowired
    @Qualifier("dsmWebapiClientRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private DsmUrlProvider dsmUrlProvider;

    @Override
    public <T extends DsmWebapiResponse<?>> T call(DsmWebapiRequest request, Class<T> responseType) {
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
        return restTemplate.getForObject(uriComponentsBuilder.toUriString(), responseType);
    }

    public void customizeUri(UriComponentsBuilder uriComponentsBuilder) {
        // Template method
    }
}
