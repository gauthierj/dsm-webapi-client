package com.github.gauthierj.dsm.webapi.client.exception;

import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;

public class SessionExpiredException extends DsmWebApiErrorException {

    public SessionExpiredException(String message, DsmWebApiResponseError error) {
        super(message, error);
    }
}
