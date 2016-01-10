package com.github.gauthierj.dsm.webapi.client.filestation.exception;

import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;
import com.github.gauthierj.dsm.webapi.client.exception.DsmWebApiErrorException;

public class FileNotFoundException extends DsmWebApiErrorException {

    private final String path;

    public FileNotFoundException(String path, DsmWebApiResponseError error) {
        super("No such file or directory", error);
        this.path = path;
    }

    public FileNotFoundException(Throwable cause, String path) {
        super("No such file or directory", cause, null);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
