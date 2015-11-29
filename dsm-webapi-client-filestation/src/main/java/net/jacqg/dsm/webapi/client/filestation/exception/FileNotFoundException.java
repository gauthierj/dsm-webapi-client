package net.jacqg.dsm.webapi.client.filestation.exception;

import net.jacqg.dsm.webapi.client.exception.DsmWebApiErrorException;

public class FileNotFoundException extends DsmWebApiErrorException {

    private final String path;

    public FileNotFoundException(String path) {
        super("No such file or directory", 408);
        this.path = path;
    }

    public FileNotFoundException(Throwable cause, String path) {
        super("No such file or directory", cause, 408);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
