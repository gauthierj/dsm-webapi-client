package com.github.gauthierj.dsm.webapi.client.filestation.sharelist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VolumeStatus {

    private final long freeSpace;
    private final long totalSpace;
    private final boolean readOnly;

    @JsonCreator
    public VolumeStatus(@JsonProperty("freespace") long freeSpace,
                        @JsonProperty("totalspace") long totalSpace,
                        @JsonProperty("readonly") boolean readOnly) {
        this.freeSpace = freeSpace;
        this.totalSpace = totalSpace;
        this.readOnly = readOnly;
    }

    public long getFreeSpace() {
        return freeSpace;
    }

    public long getTotalSpace() {
        return totalSpace;
    }

    public boolean isReadOnly() {
        return readOnly;
    }
}
