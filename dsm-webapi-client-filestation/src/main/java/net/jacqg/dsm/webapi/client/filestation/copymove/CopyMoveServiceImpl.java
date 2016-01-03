package net.jacqg.dsm.webapi.client.filestation.copymove;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.jacqg.dsm.webapi.client.AbstractDsmServiceImpl;
import net.jacqg.dsm.webapi.client.DsmWebApiResponseError;
import net.jacqg.dsm.webapi.client.DsmWebapiRequest;
import net.jacqg.dsm.webapi.client.DsmWebapiResponse;
import net.jacqg.dsm.webapi.client.ErrorHandler;
import net.jacqg.dsm.webapi.client.filestation.common.OverwriteBehavior;
import net.jacqg.dsm.webapi.client.filestation.common.TaskId;
import net.jacqg.dsm.webapi.client.filestation.dirsize.DirSizeResult;
import net.jacqg.dsm.webapi.client.filestation.exception.CouldNotCopyOrMoveFiles;
import net.jacqg.dsm.webapi.client.filestation.exception.TaskTimeOutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CopyMoveServiceImpl extends AbstractDsmServiceImpl implements CopyMoveService {

    // API Infos
    public static final String API_ID = "SYNO.FileStation.CopyMove";
    public static final String API_VERSION = "1";

    // API Methods
    public static final String METHOD_START = "start";
    public static final String METHOD_STATUS = "status";
    public static final String METHOD_STOP = "stop";

    // Parameters
    public static final String PARAMETER_ACCURATE_PROGRESS = "accurate_progress";
    public static final String PARAMETER_DEST_FOLDER_PATH = "dest_folder_path";
    public static final String PARAMETER_OVERWRITE = "overwrite";
    public static final String PARAMETER_PATH = "path";
    public static final String PARAMETER_REMOVE_SRC = "remove_src";
    public static final String PARAMETER_SEARCH_TASKID = "search_taskid";
    public static final String PARAMETER_TASKID = "taskid";

    // Parameters values
    public static final String PARAMETER_VALUE_FALSE = "false";
    public static final String PARAMETER_VALUE_TRUE = "true";

    @Value("${dsm.webapi.synchronousCopyMove.timeout:30}")
    private int synchronousCopyMoveTimeout;

    public CopyMoveServiceImpl() {
        super(API_ID);
    }

    @Override
    public String startCopy(String path, String destinationFolderPath, OverwriteBehavior overwriteBehavior) {
        return start(path, destinationFolderPath, overwriteBehavior, false, true, Optional.empty());
    }

    @Override
    public String startMove(String path, String destinationFolderPath, OverwriteBehavior overwriteBehavior) {
        return start(path, destinationFolderPath, overwriteBehavior, true, true, Optional.empty());
    }

    @Override
    public String start(String path, String destinationFolderPath, OverwriteBehavior overwriteBehavior, boolean removeSource, boolean accurateProgress, Optional<String> searchTaskId) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), API_VERSION, getApiInfo().getPath(), METHOD_START)
                .parameter(PARAMETER_PATH, path)
                .parameter(PARAMETER_DEST_FOLDER_PATH, destinationFolderPath)
                .optionalParameter(PARAMETER_OVERWRITE, getOverwriteParameterValue(overwriteBehavior))
                .parameter(PARAMETER_REMOVE_SRC, removeSource ? PARAMETER_VALUE_TRUE : PARAMETER_VALUE_FALSE)
                .parameter(PARAMETER_ACCURATE_PROGRESS, accurateProgress ? PARAMETER_VALUE_TRUE : PARAMETER_VALUE_FALSE)
                .optionalParameter(PARAMETER_SEARCH_TASKID, searchTaskId);
        StartCopyMoveResponse response = getDsmWebapiClient().call(request, StartCopyMoveResponse.class, new CopyMoveErrorHandler());
        return response.getData().getTaskId();
    }

    @Override
    public CopyMoveStatus status(String taskId) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), API_VERSION, getApiInfo().getPath(), METHOD_STATUS)
                .parameter(PARAMETER_TASKID, taskId);
        CopyMoveStatusResponse response = getDsmWebapiClient().call(request, CopyMoveStatusResponse.class, new CopyMoveErrorHandler());
        return response.getData();
    }

    @Override
    public void stop(String taskId) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), API_VERSION, getApiInfo().getPath(), METHOD_STOP)
                .parameter(PARAMETER_TASKID, taskId);
        getDsmWebapiClient().call(request, DsmWebapiResponse.class, new CopyMoveErrorHandler());
    }

    @Override
    public void synchronousCopy(String path, String destinationFolderPath, OverwriteBehavior overwriteBehavior) {
        waitForCompletion(startCopy(path, destinationFolderPath, overwriteBehavior));
    }

    @Override
    public void synchronousMove(String path, String destinationFolderPath, OverwriteBehavior overwriteBehavior) {
        waitForCompletion(startMove(path, destinationFolderPath, overwriteBehavior));
    }

    private Optional<String> getOverwriteParameterValue(OverwriteBehavior overwriteBehavior) {
        switch (overwriteBehavior) {
            case OVERWRITE:
                return Optional.of(PARAMETER_VALUE_TRUE);
            case SKIP:
                return Optional.of(PARAMETER_VALUE_FALSE);
        }
        return Optional.empty();
    }

    private void waitForCompletion(String taskId) {
        try {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start <= synchronousCopyMoveTimeout * 1000) {
                CopyMoveStatus status = this.status(taskId);
                if(status.isFinished()) {
                    return;
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            throw new IllegalStateException("Thread interrupted", e);
        } finally {
            stop(taskId);
        }
        throw new TaskTimeOutException();
    }

    private static class StartCopyMoveResponse extends DsmWebapiResponse<net.jacqg.dsm.webapi.client.filestation.common.TaskId> {

        public StartCopyMoveResponse(@JsonProperty("success") boolean success, @JsonProperty("data") TaskId data, @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }

    private static class CopyMoveStatusResponse extends DsmWebapiResponse<CopyMoveStatus> {

        public CopyMoveStatusResponse(@JsonProperty("success") boolean success, @JsonProperty("data") CopyMoveStatus data, @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }

    private static class CopyMoveErrorHandler implements ErrorHandler {

        @Override
        public void handleError(DsmWebapiRequest request, DsmWebApiResponseError error) {
            switch (error.getCode()) {
                case 1000:
                    throw new CouldNotCopyOrMoveFiles("Failed to copy files/folders. More information in <errors> object.", error);
                case 1001:
                    throw new CouldNotCopyOrMoveFiles("Failed to move files/folders. More information in <errors> object.", error);
                case 1002:
                    throw new CouldNotCopyOrMoveFiles("An error occurred at the destination. More information in <errors> object.", error);
                case 1003:
                    throw new CouldNotCopyOrMoveFiles("Cannot overwrite or skip the existing file because no overwrite parameter is given.", error);
                case 1004:
                    throw new CouldNotCopyOrMoveFiles("File cannot overwrite a folder with the same name, or folder cannot overwrite a file with the same name.", error);
                case 1006:
                    throw new CouldNotCopyOrMoveFiles("Cannot copy/move file/folder with special characters to a FAT32 file system.", error);
                case 1007:
                    throw new CouldNotCopyOrMoveFiles("Cannot copy/move a file bigger than 4G to a FAT32 file system.", error);
            }
        }
    }
}
