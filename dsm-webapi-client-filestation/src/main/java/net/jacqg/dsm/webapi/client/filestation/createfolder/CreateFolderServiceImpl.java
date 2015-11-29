package net.jacqg.dsm.webapi.client.filestation.createfolder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.jacqg.dsm.webapi.client.AbstractDsmServiceImpl;
import net.jacqg.dsm.webapi.client.DsmWebapiRequest;
import net.jacqg.dsm.webapi.client.DsmWebapiResponse;
import net.jacqg.dsm.webapi.client.filestation.exception.CouldNotCreateFolderException;
import net.jacqg.dsm.webapi.client.filestation.filelist.File;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateFolderServiceImpl extends AbstractDsmServiceImpl implements CreateFolderService {

    public CreateFolderServiceImpl() {
        super("SYNO.FileStation.CreateFolder");
    }

    @Override
    public void createFolder(String parentPath, String name) {
        createFolder(parentPath, name, false);
    }

    @Override
    public File createFolder(String parentPath, String name, boolean createParents) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiInfo().getApi(), "1", getApiInfo().getPath(), "create")
                .parameter("folder_path", parentPath)
                .parameter("name", name)
                .parameter("force_parent", createParents)
                .parameter("additional", "real_path,size,owner,time,perm,type");
        CreateFolderResponse response = getDsmWebapiClient().call(request, CreateFolderResponse.class);
        handleErrors(parentPath, name, createParents, response);
        return response.getData().getFolders().get(0);
    }

    private void handleErrors(String parentPath, String name, boolean createParents, CreateFolderResponse response) {
        if(!response.isSuccess()) {
            int errorCode = response.getError().getCode();
            throw new CouldNotCreateFolderException(parentPath, name, createParents, errorCode);
        }
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
            return folders;
        }
    }

    public static class CreateFolderResponse extends DsmWebapiResponse<FolderList> {

        public CreateFolderResponse(@JsonProperty("success") boolean success, @JsonProperty("data") FolderList data, @JsonProperty("error") Error error) {
            super(success, data, error);
        }
    }
}
