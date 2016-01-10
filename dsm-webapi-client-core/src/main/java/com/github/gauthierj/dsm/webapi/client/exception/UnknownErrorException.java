package com.github.gauthierj.dsm.webapi.client.exception;

import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;

public class UnknownErrorException extends DsmWebApiErrorException {

    public UnknownErrorException(DsmWebApiResponseError error) {
        super("Unknown error", error);
    }
}
