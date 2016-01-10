package com.github.gauthierj.dsm.webapi.client.filestation.delete;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiResponse;
import com.github.gauthierj.dsm.webapi.client.AbstractDsmServiceImpl;
import com.github.gauthierj.dsm.webapi.client.DsmWebApiResponseError;
import com.github.gauthierj.dsm.webapi.client.DsmWebapiRequest;
import com.github.gauthierj.dsm.webapi.client.ErrorHandler;
import com.github.gauthierj.dsm.webapi.client.filestation.common.TaskId;
import com.github.gauthierj.dsm.webapi.client.filestation.exception.FileNotFoundException;
import com.github.gauthierj.dsm.webapi.client.filestation.exception.TaskTimeOutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DeleteServiceImpl extends AbstractDsmServiceImpl implements DeleteService {

    public static final String API_ID = "SYNO.FileStation.Delete";
    public static final String API_VERSION = "1";
    public static final String PARAMETER_PATH = "path";

    @Value("${dsm.webapi.synchronousDelete.timeout:30}")
    private int synchronousDeleteTimeout;

    public DeleteServiceImpl() {
        super(API_ID);
    }

    @Override
    public String start(String path, boolean recursive, boolean accurateProgress, Optional<String> searchTaskId) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), API_VERSION, getApiInfo().getPath(), "start")
                .parameter(PARAMETER_PATH, path)
                .parameter("accurate_progress", accurateProgress)
                .parameter("recursive", recursive)
                .optionalParameter("search_taskid", searchTaskId);
        return getDsmWebapiClient().call(request, StartDeleteResponse.class, new DeleteErrorHandler()).getData().getTaskId();
    }

    @Override
    public DeleteStatus status(String taskId) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), API_VERSION, getApiInfo().getPath(), "status")
                .parameter("taskid", taskId);
        return getDsmWebapiClient().call(request, DeleteStatusResponse.class, new DeleteErrorHandler()).getData();
    }

    @Override
    public void stop(String taskId) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiId(), API_VERSION, getApiInfo().getPath(), "stop")
                .parameter("taskid", taskId);
        getDsmWebapiClient().call(request, DsmWebapiResponse.class, new DeleteErrorHandler());
    }

    @Override
    public boolean synchronousDelete(String path, boolean recursive, Optional<String> searchTaskId) {
        String taskId = start(path, recursive, false, searchTaskId);
        DeleteStatus deleteStatus = waitForCompletion(taskId);
        return deleteStatus.getProcessedFiles() > 0;
    }

    private DeleteStatus waitForCompletion(String taskId) {
        try {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start <= synchronousDeleteTimeout * 1000) {
                DeleteStatus status = this.status(taskId);
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

    private static class StartDeleteResponse extends DsmWebapiResponse<TaskId> {

        public StartDeleteResponse(@JsonProperty("success") boolean success, @JsonProperty("data") TaskId data, @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }

    private static class DeleteStatusResponse extends DsmWebapiResponse<DeleteStatus> {

        public DeleteStatusResponse(@JsonProperty("success") boolean success, @JsonProperty("data") DeleteStatus data, @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }

    private static class DeleteErrorHandler implements ErrorHandler {

        @Override
        public void handleError(DsmWebapiRequest request, DsmWebApiResponseError error) {
            switch (error.getCode()) {
                case 900:
                    if(error.getErrors().size() == 1) {
                        DsmWebApiResponseError innerError = error.getErrors().get(0);
                        switch (innerError.getCode()) {
                            case 408:
                                throw new FileNotFoundException(request.getParameters().get(PARAMETER_PATH), innerError);
                        }
                    }
            }
        }
    }
}
