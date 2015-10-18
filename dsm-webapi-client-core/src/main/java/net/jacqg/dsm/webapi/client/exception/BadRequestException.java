package net.jacqg.dsm.webapi.client.exception;

public class BadRequestException extends DsmWebApiErrorException {

    public BadRequestException(String message, int errorCode) {
        super(message, errorCode);
    }
}
