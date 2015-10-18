package net.jacqg.dsm.webapi.client.exception;

import net.jacqg.dsm.webapi.client.GenericErrorCodes;

public class UnknownErrorException extends DsmWebApiErrorException {

    public UnknownErrorException() {
        super("Unknown error", GenericErrorCodes.ERROR_CODE_UNKNOWN_ERROR);
    }
}
