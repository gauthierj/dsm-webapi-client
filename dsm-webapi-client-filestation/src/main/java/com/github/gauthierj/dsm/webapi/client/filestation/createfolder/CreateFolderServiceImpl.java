package com.github.gauthierj.dsm.webapi.client.filestation.createfolder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gauthierj.dsm.webapi.client.AbstractDsmServiceImpl;
import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiRequest;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiResponse;
import com.github.gauthierj.dsm.webapi.client.ErrorHandler;
import com.github.gauthierj.dsm.webapi.client.filestation.common.File;
import com.github.gauthierj.dsm.webapi.client.filestation.exception.CouldNotCreateFolderException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class CreateFolderServiceImpl extends AbstractDsmServiceImpl implements CreateFolderService {

    // API Infos
    private static final String API_ID = "SYNO.FileStation.CreateFolder";
    private static final String API_VERSION = "1";

    // API Methods
    private static final String METHOD_CREATE = "create";

    // Parameters
    private static final String PARAMETER_ADDITIONAL = "additional";
    private static final String PARAMETER_FOLDER_PATH = "folder_path";
    private static final String PARAMETER_FORCE_PARENT = "force_parent";
    private static final String PARAMETER_NAME = "name";

    // Parameters values
    private static final String PARAMETER_VALUE_ADDITIONAL = "real_path,size,owner,time,perm,type";

    public CreateFolderServiceImpl() {
        super(API_ID);
    }

    @Override
    public File createFolder(String parentPath, String name) {
        return createFolder(parentPath, name, false);
    }

    @Override
    public File createFolder(String parentPath, String name, boolean createParents) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiInfo().getApi(), API_VERSION, getApiInfo().getPath(), METHOD_CREATE)
                .parameter(PARAMETER_FOLDER_PATH, parentPath)
                .parameter(PARAMETER_NAME, name)
                .parameter(PARAMETER_FORCE_PARENT, createParents)
                .parameter(PARAMETER_ADDITIONAL, PARAMETER_VALUE_ADDITIONAL);
        CreateFolderResponse response = getDsmWebapiClient().call(request, CreateFolderResponse.class, new CreateFolderErrorHandler());
        return response.getData().getFolders().get(0);
    }

    public static class FolderList {

        private final List<File> folders = new ArrayList<>();

        @JsonCreator
        public FolderList(@JsonProperty("folders") List<File> folders) {
            if (folders != null) {
                this.folders.addAll(folders);
            }
        }

        public List<File> getFolders() {
            return Collections.unmodifiableList(folders);
        }
    }

    public static class CreateFolderResponse extends DsmWebapiResponse<FolderList> {

        public CreateFolderResponse(@JsonProperty("success") boolean success, @JsonProperty("data") FolderList data, @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }

    public static class CreateFolderErrorHandler implements ErrorHandler {

        @Override
        public void handleError(DsmWebapiRequest request, DsmWebApiResponseError error) {
            throw new CouldNotCreateFolderException(
                    request.getParameters().get(PARAMETER_FOLDER_PATH),
                    request.getParameters().get(PARAMETER_NAME),
                    Boolean.valueOf(request.getParameters().get(PARAMETER_FORCE_PARENT)),
                    error);
        }
    }
}
