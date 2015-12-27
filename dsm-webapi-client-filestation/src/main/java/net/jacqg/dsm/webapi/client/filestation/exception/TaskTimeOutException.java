package net.jacqg.dsm.webapi.client.filestation.exception;

import net.jacqg.dsm.webapi.client.exception.DsmWebApiClientException;

public class TaskTimeOutException extends DsmWebApiClientException {

    public TaskTimeOutException(Throwable cause) {
        super("Task did not complete within allowed delay", cause);
    }

    public TaskTimeOutException() {
        super("Task did not complete within allowed delay");
    }
}
