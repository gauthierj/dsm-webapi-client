package net.jacqg.dsm.webapi.client.authentication.exception;

import net.jacqg.dsm.webapi.client.DsmWebApiResponseError;
import net.jacqg.dsm.webapi.client.exception.DsmWebApiErrorException;

public class InvalidLoginException extends DsmWebApiErrorException {

    public static final int ERROR_CODE_NO_SUCH_ACCOUNT = 400;
    public static final int ERROR_CODE_ACCOUNT_DISABLED = 401;
    public static final int ERROR_CODE_PERMISSION_DENIED = 402;
    public static final int ERROR_CODE_2_STEP_VERIFICATION_CODE_REQUIRED = 403;
    public static final int ERROR_CODE_2_STEP_VERIFICATION_CODE_AUTHENTICATION_FAILED = 404;

    public InvalidLoginException(DsmWebApiResponseError error) {
        super(error.getCode()+ ": " + getMessage(error.getCode()), error);
    }

    private static String getMessage(int errorCode) {
        switch (errorCode) {
            case ERROR_CODE_NO_SUCH_ACCOUNT:
                return "No such account or incorrect password";
            case ERROR_CODE_ACCOUNT_DISABLED:
                return "Account disabled";
            case ERROR_CODE_PERMISSION_DENIED:
                return "Permission denied";
            case ERROR_CODE_2_STEP_VERIFICATION_CODE_REQUIRED:
                return "2-step verification code required";
            case ERROR_CODE_2_STEP_VERIFICATION_CODE_AUTHENTICATION_FAILED:
                return "Failed to authenticate 2-step verification code";
            default:
                throw new IllegalStateException(String.format("Wrong Error Code : %s", errorCode));
        }
    }
}
