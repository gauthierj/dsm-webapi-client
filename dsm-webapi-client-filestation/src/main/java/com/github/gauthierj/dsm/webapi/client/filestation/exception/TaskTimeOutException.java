package com.github.gauthierj.dsm.webapi.client.filestation.exception;

import com.github.gauthierj.dsm.webapi.client.exception.DsmWebApiClientException;

public class TaskTimeOutException extends DsmWebApiClientException {

    public TaskTimeOutException(Throwable cause) {
        super("Task did not complete within allowed delay", cause);
    }

    public TaskTimeOutException() {
        super("Task did not complete within allowed delay");
    }
}
