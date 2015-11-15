package net.jacqg.dsm.webapi.client.filestation.exception;

import net.jacqg.dsm.webapi.client.exception.DsmWebApiErrorException;

public class CouldNotCreateFolderException extends DsmWebApiErrorException {

    private final String parentPath;
    private final String name;
    private final boolean createParents;

    public CouldNotCreateFolderException(String parentPath, String name, boolean createParents, int errorCode) {
        this(parentPath, name, createParents, errorCode, String.format("Could not create folder. Parent path: %s, Name: %s, Create parents: %s", parentPath, name, createParents));
    }

    public CouldNotCreateFolderException(String parentPath, String name, boolean createParents, int errorCode, String message) {
        super(message, errorCode);
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
