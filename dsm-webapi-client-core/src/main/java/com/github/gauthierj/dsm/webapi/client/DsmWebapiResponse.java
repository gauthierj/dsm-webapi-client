package com.github.gauthierj.dsm.webapi.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DsmWebapiResponse<T> {

    private boolean success;
    private T data;
    private DsmWebApiResponseError error;

    @JsonCreator
    public DsmWebapiResponse(@JsonProperty("success") boolean success,
                             @JsonProperty("data") T data,
                             @JsonProperty("error") DsmWebApiResponseError error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public DsmWebApiResponseError getError() {
        return error;
    }

}
