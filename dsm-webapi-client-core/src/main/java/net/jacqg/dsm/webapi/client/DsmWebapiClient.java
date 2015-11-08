package net.jacqg.dsm.webapi.client;

import java.net.URI;

public interface DsmWebapiClient {
    <T extends DsmWebapiResponse<?>> T call(DsmWebapiRequest request, Class<T> responseType);

    URI buildUri(DsmWebapiRequest request);
}
