package com.github.gauthierj.dsm.webapi.client.filestation.exception;

import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;
import com.github.gauthierj.dsm.webapi.client.exception.DsmWebApiErrorException;

public class CouldNotCreateFolderException extends DsmWebApiErrorException {

    private final String parentPath;
    private final String name;
    private final boolean createParents;

    public CouldNotCreateFolderException(String parentPath, String name, boolean createParents, DsmWebApiResponseError error) {
        this(parentPath, name, createParents, error, String.format("Could not create folder. Parent path: %s, Name: %s, Create parents: %s", parentPath, name, createParents));
    }

    public CouldNotCreateFolderException(String parentPath, String name, boolean createParents, DsmWebApiResponseError error, String message) {
        super(message, error);
        this.parentPath = parentPath;
        this.name = name;
        this.createParents = createParents;
    }

    public String getParentPath() {
        return parentPath;
    }

    public String getName() {
        return name;
    }

    public boolean isCreateParents() {
        return createParents;
    }
}
