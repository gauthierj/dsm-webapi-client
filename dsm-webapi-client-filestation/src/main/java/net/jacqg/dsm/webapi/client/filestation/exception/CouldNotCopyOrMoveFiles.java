package net.jacqg.dsm.webapi.client.filestation.exception;

import net.jacqg.dsm.webapi.client.DsmWebApiResponseError;
import net.jacqg.dsm.webapi.client.exception.DsmWebApiErrorException;

public class CouldNotCopyOrMoveFiles extends DsmWebApiErrorException {

    public CouldNotCopyOrMoveFiles(String message, DsmWebApiResponseError error) {
        super(message, error);
    }
}
