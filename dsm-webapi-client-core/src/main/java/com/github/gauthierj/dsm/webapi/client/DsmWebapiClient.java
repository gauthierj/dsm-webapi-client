package com.github.gauthierj.dsm.webapi.client;

import java.net.URI;

public interface DsmWebapiClient {

    <T extends DsmWebapiResponse<?>> T call(DsmWebapiRequest request, Class<T> responseType);

    <T extends DsmWebapiResponse<?>> T call(DsmWebapiRequest request, Class<T> responseType, ErrorHandler errorHandler);

    URI buildUri(DsmWebapiRequest request);
}
