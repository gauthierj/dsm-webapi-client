package net.jacqg.dsm.webapi.client.exception;

import net.jacqg.dsm.webapi.client.DsmWebApiResponseError;
import net.jacqg.dsm.webapi.client.GenericErrorCodes;

public class UnknownErrorException extends DsmWebApiErrorException {

    public UnknownErrorException(DsmWebApiResponseError error) {
        super("Unknown error", error);
    }
}
