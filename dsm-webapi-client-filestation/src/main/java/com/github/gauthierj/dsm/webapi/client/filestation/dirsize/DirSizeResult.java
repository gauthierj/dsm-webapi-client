package com.github.gauthierj.dsm.webapi.client.filestation.dirsize;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DirSizeResult {

    private final boolean finished;
    private final int numberOfDirectories;
    private final int numberOfFiles;
    private final long totalByteSize;

    @JsonCreator
    public DirSizeResult(@JsonProperty("finished") boolean finished,
                         @JsonProperty("num_dir") int numberOfDirectories,
                         @JsonProperty("num_file") int numberOfFiles,
                         @JsonProperty("total_size") long totalByteSize) {
        this.finished = finished;
        this.numberOfDirectories = numberOfDirectories;
        this.numberOfFiles = numberOfFiles;
        this.totalByteSize = totalByteSize;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getNumberOfDirectories() {
        return numberOfDirectories;
    }

    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    public long getTotalByteSize() {
        return totalByteSize;
    }
}
