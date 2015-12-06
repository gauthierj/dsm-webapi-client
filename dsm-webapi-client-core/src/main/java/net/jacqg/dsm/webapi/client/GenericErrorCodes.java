package net.jacqg.dsm.webapi.client;

public final class GenericErrorCodes {

    public static final int ERROR_CODE_UNKNOWN_ERROR = 100;
    public static final int ERROR_CODE_NO_PARAMETER = 101;
    public static final int ERROR_CODE_API_DOES_NOT_EXISTS = 102;
    public static final int ERROR_CODE_METHOD_DOES_NOT_EXISTS = 103;
    public static final int ERROR_CODE_VERSION_DOES_NOT_SUPPORT_FEATURE = 104;
    public static final int ERROR_CODE_PERMISSION_DENIED = 105;
    public static final int ERROR_CODE_SESSION_TIMEOUT = 106;
    public static final int ERROR_CODE_DUPLICATE_LOGIN = 107;

    private GenericErrorCodes() {
        throw new UnsupportedOperationException("Cannot instantiate");
    }

}
