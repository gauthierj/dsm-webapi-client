package com.github.gauthierj.dsm.webapi.client.filestation.delete;

import java.util.Optional;

public interface DeleteService {

    String start(String path, boolean recursive, boolean accurateProgress, Optional<String> searchTaskId);

    DeleteStatus status(String taskId);

    void stop(String taskId);

    boolean synchronousDelete(String path, boolean recursive, Optional<String> searchTaskId);
}
