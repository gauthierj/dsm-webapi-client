package net.jacqg.dsm.webapi.client.filestation.rename;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.jacqg.dsm.webapi.client.AbstractDsmServiceImpl;
import net.jacqg.dsm.webapi.client.DsmWebApiResponseError;
import net.jacqg.dsm.webapi.client.DsmWebapiRequest;
import net.jacqg.dsm.webapi.client.DsmWebapiResponse;
import net.jacqg.dsm.webapi.client.ErrorHandler;
import net.jacqg.dsm.webapi.client.filestation.common.ErrorCodes;
import net.jacqg.dsm.webapi.client.filestation.common.File;
import net.jacqg.dsm.webapi.client.filestation.exception.CouldNotRenameException;
import org.springframework.stereotype.Service;

@Service
public class RenameServiceImpl extends AbstractDsmServiceImpl implements RenameService {

    // API Infos
    public static final String API_ID = "SYNO.FileStation.Rename";
    public static final String API_VERSION = "1";

    // API Methods
    public static final String METHOD_RENAME = "rename";

    // Parameters
    public static final String PARAMETER_ADDITIONAL = "additional";
    public static final String PARAMETER_NAME = "name";
    public static final String PARAMETER_PATH = "path";
    public static final String PARAMETER_SEARCH_TASKID = "search_taskid";

    // Parameters values
    public static final String PARAMETER_VALUE_ADDITIONAL = "real_path,size,owner,time,perm,type";

    public RenameServiceImpl() {
        super(API_ID);
    }

    @Override
    public File rename(String path, String name) {
        DsmWebapiRequest request = buildRequest(path, name);
        RenameResponse response = getDsmWebapiClient().call(request, RenameResponse.class, new RenameErrorHandler());
        return response.getData().getElements().get(0);
    }

    private DsmWebapiRequest buildRequest(String path, String name) {
        return new DsmWebapiRequest(getApiId(), API_VERSION, getApiInfo().getPath(), METHOD_RENAME)
                .parameter(PARAMETER_PATH, path)
                .parameter(PARAMETER_NAME, name)
                .parameter(PARAMETER_ADDITIONAL, PARAMETER_VALUE_ADDITIONAL);
    }

    @Override
    public File rename(String path, String name, String searchTaskId) {
        DsmWebapiRequest request = buildRequest(path, name)
                .parameter(PARAMETER_SEARCH_TASKID, searchTaskId);
        RenameResponse response = getDsmWebapiClient().call(request, RenameResponse.class, new RenameErrorHandler());
        return response.getData().getElements().get(0);
    }

    private static class RenameResponse extends DsmWebapiResponse<File.FileList> {

        @JsonCreator
        public RenameResponse(
                @JsonProperty("success") boolean success,
                @JsonProperty("data") File.FileList data,
                @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }

    private static class RenameErrorHandler implements ErrorHandler {

        @Override
        public void handleError(DsmWebapiRequest request, DsmWebApiResponseError error) {

            if(error.getCode() == ErrorCodes.ERROR_CODE_COULD_NOT_RENAME) {
                throw new CouldNotRenameException(
                        request.getParameters().get(PARAMETER_PATH),
                        request.getParameters().get(PARAMETER_NAME),
                        error
                );
            }
        }
    }
}
