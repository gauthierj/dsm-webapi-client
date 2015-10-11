package net.jacqg.dsm.webapi.client;

public interface DsmWebapiClient {
    <T extends DsmWebapiResponse<?>> T call(DsmWebapiRequest request, Class<T> responseType);
}
