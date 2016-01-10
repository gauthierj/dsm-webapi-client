package com.github.gauthierj.dsm.webapi.client.filestation.copymove;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CopyMoveStatus {

    private final boolean finished;
    private final long processedBytes;
    private final long totalBytes;
    private final double progress;
    private final String path;
    private final String destinationPath;

    @JsonCreator
    public CopyMoveStatus(
            @JsonProperty("finished") boolean finished,
            @JsonProperty("processed_size") long processedBytes,
            @JsonProperty("total") long totalBytes,
            @JsonProperty("progress") double progress,
            @JsonProperty("path") String path,
            @JsonProperty("dest_folder_path") String destinationPath) {
        this.finished = finished;
        this.processedBytes = processedBytes;
        this.totalBytes = totalBytes;
        this.progress = progress;
        this.path = path;
        this.destinationPath = destinationPath;
    }

    public boolean isFinished() {
        return finished;
    }

    public long getProcessedBytes() {
        return processedBytes;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public double getProgress() {
        return progress;
    }

    public String getPath() {
        return path;
    }

    public String getDestinationPath() {
        return destinationPath;
    }
}
