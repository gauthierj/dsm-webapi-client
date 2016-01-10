package com.github.gauthierj.dsm.webapi.client.filestation.exception;

import com.github.gauthierj.dsm.webapi.client.exception.DsmWebApiErrorException;
import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;

public class FileAlreadyExistsException extends DsmWebApiErrorException {

    private final String parentFolderPath;
    private final String fileName;

    public FileAlreadyExistsException(DsmWebApiResponseError error, String parentPath, String fileName) {
        super(String.format("File already exists: %s/%s", parentPath, fileName), error);
        this.parentFolderPath = parentPath;
        this.fileName = fileName;
    }

    public String getParentFolderPath() {
        return parentFolderPath;
    }

    public String getFileName() {
        return fileName;
    }
}
