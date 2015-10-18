package net.jacqg.dsm.webapi.client.exception;

import net.jacqg.dsm.webapi.client.GenericErrorCodes;

public class PermissionDeniedException extends DsmWebApiErrorException {

    public PermissionDeniedException() {
        super("The logged in session does not have permission", GenericErrorCodes.ERROR_CODE_PERMISSION_DENIED);
    }
}
