package net.jacqg.dsm.webapi.client.filestation.exception;

import net.jacqg.dsm.webapi.client.exception.DsmWebApiErrorException;
import net.jacqg.dsm.webapi.client.filestation.common.ErrorCodes;

public class NoSuchTaskException extends DsmWebApiErrorException {

    public NoSuchTaskException() {
        super("No such task of the file operation", ErrorCodes.ERROR_CODE_NO_SUCH_TASK);
    }
}
