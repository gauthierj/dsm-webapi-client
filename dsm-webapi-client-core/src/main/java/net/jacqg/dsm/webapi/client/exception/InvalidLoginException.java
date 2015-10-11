package net.jacqg.dsm.webapi.client.exception;

public class InvalidLoginException extends DsmWebApiErrorException {

    public InvalidLoginException(int errorCode) {
        super(getMessage(errorCode), errorCode);
    }

    private static String getMessage(int errorCode) {
        switch (errorCode) {
            case 400:
                return "No such account or incorrect password";
            case 401:
                return "Account disabled";
            case 402:
                return "Permission denied";
            case 403:
                return "2-step verification code required";
            case 404:
                return "Failed to authenticate 2-step verification code";
            default:
                throw new IllegalStateException(String.format("Wrong Error Code : %s", errorCode));
        }
    }
}
