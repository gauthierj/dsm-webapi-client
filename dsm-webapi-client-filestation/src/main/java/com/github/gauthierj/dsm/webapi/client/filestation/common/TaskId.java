package com.github.gauthierj.dsm.webapi.client.filestation.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskId {

    private final String taskId;

    @JsonCreator
    public TaskId(@JsonProperty("taskid") String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }
}
