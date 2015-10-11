package net.jacqg.dsm.webapi.client.filestation.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShareProperties {

    private final String mountPointType;
    private final User owner;
    private final Permission permission;
    private final String realPath;
    private final boolean syncShare;
    private final TimeInformation timeInformation;
    private final VolumeStatus volumeStatus;

    @JsonCreator
    public ShareProperties(@JsonProperty("mount_point_type") String mountPointType,
                           @JsonProperty("owner") User owner,
                           @JsonProperty("perm") Permission permission,
                           @JsonProperty("real_path") String realPath,
                           @JsonProperty("sync_share") boolean syncShare,
                           @JsonProperty("time") TimeInformation timeInformation,
                           @JsonProperty("volume_status") VolumeStatus volumeStatus) {
        this.mountPointType = mountPointType;
        this.owner = owner;
        this.permission = permission;
        this.realPath = realPath;
        this.syncShare = syncShare;
        this.timeInformation = timeInformation;
        this.volumeStatus = volumeStatus;
    }

    public String getMountPointType() {
        return mountPointType;
    }

    public User getOwner() {
        return owner;
    }

    public Permission getPermission() {
        return permission;
    }

    public String getRealPath() {
        return realPath;
    }

    public boolean isSyncShare() {
        return syncShare;
    }

    public TimeInformation getTimeInformation() {
        return timeInformation;
    }

    public VolumeStatus getVolumeStatus() {
        return volumeStatus;
    }
}
