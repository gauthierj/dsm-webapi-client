package com.github.gauthierj.dsm.webapi.client.filestation.exception;

import com.github.gauthierj.dsm.webapi.client.exception.DsmWebApiErrorException;
import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;

public class CouldNotCopyOrMoveFilesException extends DsmWebApiErrorException {

    public CouldNotCopyOrMoveFilesException(String message, DsmWebApiResponseError error) {
        super(message, error);
    }
}
