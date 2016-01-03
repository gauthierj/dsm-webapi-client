package net.jacqg.dsm.webapi.client.exception;

import net.jacqg.dsm.webapi.client.DsmWebApiResponseError;
import net.jacqg.dsm.webapi.client.GenericErrorCodes;

public class PermissionDeniedException extends DsmWebApiErrorException {

    public PermissionDeniedException(DsmWebApiResponseError error) {
        super("The logged in session does not have permission", error);
    }
}
