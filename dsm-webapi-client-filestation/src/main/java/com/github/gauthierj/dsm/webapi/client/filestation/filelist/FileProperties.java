package com.github.gauthierj.dsm.webapi.client.filestation.filelist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gauthierj.dsm.webapi.client.filestation.common.TimeInformation;
import com.github.gauthierj.dsm.webapi.client.filestation.common.User;

public class FileProperties {

    private final String realPath;
    private final int size;
    private final User owner;
    private final TimeInformation timeInformation;
    private final FilePermission filePermission;
    private final String mountPointType;
    private final String type;

    @JsonCreator
    public FileProperties(
            @JsonProperty("real_path") String realPath,
            @JsonProperty("size") int size,
            @JsonProperty("owner") User owner,
            @JsonProperty("time") TimeInformation timeInformation,
            @JsonProperty("perm") FilePermission filePermission,
            @JsonProperty("mount_point_type") String mountPointType,
            @JsonProperty("type") String type) {
        this.realPath = realPath;
        this.size = size;
        this.owner = owner;
        this.timeInformation = timeInformation;
        this.filePermission = filePermission;
        this.mountPointType = mountPointType;
        this.type = type;
    }

    public String getRealPath() {
        return realPath;
    }

    public int getSize() {
        return size;
    }

    public User getOwner() {
        return owner;
    }

    public TimeInformation getTimeInformation() {
        return timeInformation;
    }

    public FilePermission getFilePermission() {
        return filePermission;
    }

    public String getMountPointType() {
        return mountPointType;
    }

    public String getType() {
        return type;
    }
}
