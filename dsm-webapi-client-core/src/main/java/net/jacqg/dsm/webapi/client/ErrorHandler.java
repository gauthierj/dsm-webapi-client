package net.jacqg.dsm.webapi.client;

public interface ErrorHandler {

    void handleError(DsmWebapiRequest request, DsmWebApiResponseError error);
}
