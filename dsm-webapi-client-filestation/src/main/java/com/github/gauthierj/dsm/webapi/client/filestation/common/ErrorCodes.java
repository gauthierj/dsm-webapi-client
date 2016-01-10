package com.github.gauthierj.dsm.webapi.client.filestation.common;

public final class ErrorCodes {

    public static final int ERROR_CODE_NO_SUCH_FILE_OR_DIRECTORY = 408;
    public static final int ERROR_CODE_NO_SUCH_TASK = 599;
    public static final int ERROR_CODE_COULD_NOT_RENAME = 1200;

    private ErrorCodes() {
        throw new UnsupportedOperationException("Cannot instantiate");
    }
}
