package net.jacqg.dsm.webapi.client.filestation.exception;

import net.jacqg.dsm.webapi.client.exception.DsmWebApiClientException;

public class SearchTimeOutException extends DsmWebApiClientException {

    public SearchTimeOutException(Throwable cause) {
        super("Search did not complete within allowed delay", cause);
    }
}
