package com.github.gauthierj.dsm.webapi.client.filestation.exception;

import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;
import com.github.gauthierj.dsm.webapi.client.exception.DsmWebApiErrorException;

public class NoSuchTaskException extends DsmWebApiErrorException {

    public NoSuchTaskException(DsmWebApiResponseError error) {
        super("No such task of the file operation", error);
    }
}
