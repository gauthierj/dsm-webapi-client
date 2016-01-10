package com.github.gauthierj.dsm.webapi.client.filestation.exception;

import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;
import com.github.gauthierj.dsm.webapi.client.exception.DsmWebApiErrorException;

public class CouldNotRenameException extends DsmWebApiErrorException {

    private final String path;
    private final String name;

    public CouldNotRenameException(String path, String name, DsmWebApiResponseError error) {
        super(String.format("Could not rename. Path: %s, name: %s", path, name), error);
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }
}
