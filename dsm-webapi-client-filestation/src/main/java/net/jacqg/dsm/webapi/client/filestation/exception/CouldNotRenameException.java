package net.jacqg.dsm.webapi.client.filestation.exception;

import net.jacqg.dsm.webapi.client.DsmWebApiResponseError;
import net.jacqg.dsm.webapi.client.exception.DsmWebApiErrorException;
import net.jacqg.dsm.webapi.client.filestation.common.ErrorCodes;

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
