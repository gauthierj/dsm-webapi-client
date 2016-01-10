package com.github.gauthierj.dsm.webapi.client.filestation.dirsize;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiResponse;
import com.github.gauthierj.dsm.webapi.client.filestation.common.ErrorCodes;
import com.github.gauthierj.dsm.webapi.client.AbstractDsmServiceImpl;
import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiRequest;
import com.github.gauthierj.dsm.webapi.client.ErrorHandler;
import com.github.gauthierj.dsm.webapi.client.filestation.common.TaskId;
import com.github.gauthierj.dsm.webapi.client.filestation.exception.NoSuchTaskException;
import com.github.gauthierj.dsm.webapi.client.filestation.exception.TaskTimeOutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.jayway.awaitility.Awaitility.await;

@Service
public class DirSizeServiceImpl extends AbstractDsmServiceImpl implements DirSizeService {

    // API Infos
    private static final String API_ID = "SYNO.FileStation.DirSize";
    private static final String API_VERSION = "1";

    // API Methods
    private static final String METHOD_START = "start";
    private static final String METHOD_STATUS = "status";
    private static final String METHOD_STOP = "stop";

    // Parameters
    private static final String PARAMETER_PATH = "path";
    private static final String PARAMETER_TASKID = "taskid";

    @Value("${dsm.webapi.synchronousDirSize.timeout:30}")
    private int synchronousDirSizeTimeout;

    public DirSizeServiceImpl() {
        super(API_ID);
    }

    @Override
    public String start(String path) {
        DsmWebapiRequest request = new DsmWebapiRequest(this.getApiId(), API_VERSION, getApiInfo().getPath(), METHOD_START)
                .parameter(PARAMETER_PATH, path);
        StartDirSizeResponse response = getDsmWebapiClient().call(request, StartDirSizeResponse.class);
        return response.getData().getTaskId();
    }

    @Override
    public DirSizeResult status(String taskId) {
        DsmWebapiRequest request = new DsmWebapiRequest(this.getApiId(), API_VERSION, getApiInfo().getPath(), METHOD_STATUS)
                .parameter(PARAMETER_TASKID, taskId);
        DirSizeStatusResponse response = getDsmWebapiClient().call(request, DirSizeStatusResponse.class, new DirSizeStatusErrorHandler());
        return response.getData();
    }

    @Override
    public void stop(String taskId) {
        DsmWebapiRequest request = new DsmWebapiRequest(this.getApiId(), API_VERSION, getApiInfo().getPath(), METHOD_STOP)
                .parameter(PARAMETER_TASKID, taskId);
        DsmWebapiResponse response = getDsmWebapiClient().call(request, DsmWebapiResponse.class);
    }

    @Override
    public DirSizeResult synchronousDirSize(String path) {
        String taskId = start(path);
        try {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start <= synchronousDirSizeTimeout * 1000) {
                DirSizeResult status = this.status(taskId);
                if(status.isFinished()) {
                    return status;
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

    public static class StartDirSizeResponse extends DsmWebapiResponse<TaskId> {

        public StartDirSizeResponse(@JsonProperty("success") boolean success, @JsonProperty("data") TaskId data, @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }

    public static class DirSizeStatusResponse extends DsmWebapiResponse<DirSizeResult> {

        public DirSizeStatusResponse(@JsonProperty("success") boolean success, @JsonProperty("data") DirSizeResult data, @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }

    public static class DirSizeStatusErrorHandler implements ErrorHandler {
        @Override
        public void handleError(DsmWebapiRequest request, DsmWebApiResponseError error) {
            if(error.getCode() == ErrorCodes.ERROR_CODE_NO_SUCH_TASK) {
                throw new NoSuchTaskException(error);
            }
        }
    }
}
