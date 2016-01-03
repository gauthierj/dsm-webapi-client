package net.jacqg.dsm.webapi.client.exception;

import net.jacqg.dsm.webapi.client.DsmWebApiResponseError;

public class BadRequestException extends DsmWebApiErrorException {

    public BadRequestException(String message, DsmWebApiResponseError error) {
        super(message, error);
    }
}
