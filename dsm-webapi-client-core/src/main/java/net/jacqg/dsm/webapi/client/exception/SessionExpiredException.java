package net.jacqg.dsm.webapi.client.exception;

public class SessionExpiredException extends DsmWebApiErrorException {

    public SessionExpiredException(String message, int errorCode) {
        super(message, errorCode);
    }
}
