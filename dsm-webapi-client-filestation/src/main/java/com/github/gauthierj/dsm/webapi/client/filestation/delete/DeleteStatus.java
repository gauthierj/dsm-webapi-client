package com.github.gauthierj.dsm.webapi.client.filestation.delete;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteStatus {

    private final boolean finished;
    private final long processedFiles;
    private final long totalFiles;
    private final double progress;
    private final String path;
    private final String processingPath;

    @JsonCreator
    public DeleteStatus(
            @JsonProperty("finished") boolean finished,
            @JsonProperty("processed_num") long processedFiles,
            @JsonProperty("total") long totalFiles,
            @JsonProperty("progress") double progress,
            @JsonProperty("path") String path,
            @JsonProperty("processing_path") String processingPath) {
        this.finished = finished;
        this.processedFiles = processedFiles;
        this.totalFiles = totalFiles;
        this.progress = progress;
        this.path = path;
        this.processingPath = processingPath;
    }

    public boolean isFinished() {
        return finished;
    }

    public long getProcessedFiles() {
        return processedFiles;
    }

    public long getTotalFiles() {
        return totalFiles;
    }

    public double getProgress() {
        return progress;
    }

    public String getPath() {
        return path;
    }

    public String getProcessingPath() {
        return processingPath;
    }
}
