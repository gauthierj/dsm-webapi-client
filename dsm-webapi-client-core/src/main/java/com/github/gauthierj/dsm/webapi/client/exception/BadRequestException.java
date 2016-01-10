package com.github.gauthierj.dsm.webapi.client.exception;

import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;

public class BadRequestException extends DsmWebApiErrorException {

    public BadRequestException(String message, DsmWebApiResponseError error) {
        super(message, error);
    }
}
