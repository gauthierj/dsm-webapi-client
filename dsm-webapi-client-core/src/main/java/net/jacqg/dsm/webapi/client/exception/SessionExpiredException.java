package net.jacqg.dsm.webapi.client.exception;

import net.jacqg.dsm.webapi.client.DsmWebApiResponseError;

public class SessionExpiredException extends DsmWebApiErrorException {

    public SessionExpiredException(String message, DsmWebApiResponseError error) {
        super(message, error);
    }
}
