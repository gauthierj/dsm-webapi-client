package net.jacqg.dsm.webapi.client.filestation.exception;

import net.jacqg.dsm.webapi.client.exception.DsmWebApiErrorException;
import net.jacqg.dsm.webapi.client.filestation.common.ErrorCodes;

public class FileNotFoundException extends DsmWebApiErrorException {

    private final String path;

    public FileNotFoundException(String path) {
        super("No such file or directory", ErrorCodes.ERROR_CODE_NO_SUCH_FILE_OR_DIRECTORY);
        this.path = path;
    }

    public FileNotFoundException(Throwable cause, String path) {
        super("No such file or directory", cause, ErrorCodes.ERROR_CODE_NO_SUCH_FILE_OR_DIRECTORY);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
