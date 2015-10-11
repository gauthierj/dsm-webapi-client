package net.jacqg.dsm.webapi.client.exception;

public class DsmWebApiErrorException extends DsmWebApiClientException {

    private final int errorCode;

    public DsmWebApiErrorException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public DsmWebApiErrorException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public DsmWebApiErrorException(Throwable cause, int errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
