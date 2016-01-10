package com.github.gauthierj.dsm.webapi.client.exception;

import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;

public class PermissionDeniedException extends DsmWebApiErrorException {

    public PermissionDeniedException(DsmWebApiResponseError error) {
        super("The logged in session does not have permission", error);
    }
}
