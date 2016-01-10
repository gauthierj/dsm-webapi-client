package com.github.gauthierj.dsm.webapi.client.filestation.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

public class TimeInformation {

    private final LocalDateTime lastAccessTime;
    private final LocalDateTime lastModificationTime;
    private final LocalDateTime lastChangeTime;
    private final LocalDateTime creationTime;

    @JsonCreator
    public TimeInformation(@JsonDeserialize(using = UnixTimeStampDeserializer.class) @JsonProperty("atime") LocalDateTime lastAccessTime,
                           @JsonDeserialize(using = UnixTimeStampDeserializer.class) @JsonProperty("mtime") LocalDateTime lastModificationTime,
                           @JsonDeserialize(using = UnixTimeStampDeserializer.class) @JsonProperty("ctime") LocalDateTime lastChangeTime,
                           @JsonDeserialize(using = UnixTimeStampDeserializer.class) @JsonProperty("crtime") LocalDateTime creationTime) {
        this.lastAccessTime = lastAccessTime;
        this.lastModificationTime = lastModificationTime;
        this.lastChangeTime = lastChangeTime;
        this.creationTime = creationTime;
    }

    public LocalDateTime getLastAccessTime() {
        return lastAccessTime;
    }

    public LocalDateTime getLastModificationTime() {
        return lastModificationTime;
    }

    public LocalDateTime getLastChangeTime() {
        return lastChangeTime;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }
}
