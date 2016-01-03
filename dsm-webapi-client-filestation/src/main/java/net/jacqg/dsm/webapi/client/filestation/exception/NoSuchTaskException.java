package net.jacqg.dsm.webapi.client.filestation.exception;

import net.jacqg.dsm.webapi.client.DsmWebApiResponseError;
import net.jacqg.dsm.webapi.client.exception.DsmWebApiErrorException;
import net.jacqg.dsm.webapi.client.filestation.common.ErrorCodes;

public class NoSuchTaskException extends DsmWebApiErrorException {

    public NoSuchTaskException(DsmWebApiResponseError error) {
        super("No such task of the file operation", error);
    }
}
