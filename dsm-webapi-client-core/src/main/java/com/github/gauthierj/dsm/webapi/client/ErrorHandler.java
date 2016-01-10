package com.github.gauthierj.dsm.webapi.client;

public interface ErrorHandler {

    void handleError(DsmWebapiRequest request, DsmWebApiResponseError error);
}
