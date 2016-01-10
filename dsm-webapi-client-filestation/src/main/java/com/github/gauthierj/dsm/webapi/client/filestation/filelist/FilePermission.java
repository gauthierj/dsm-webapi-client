package com.github.gauthierj.dsm.webapi.client.filestation.filelist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gauthierj.dsm.webapi.client.filestation.common.Acl;

public class FilePermission {

    private final int posix;
    private final boolean aclMode;
    private final Acl acl;

    @JsonCreator
    public FilePermission(
            @JsonProperty("posix") int posix,
            @JsonProperty("is_acl_mode") boolean aclMode,
            @JsonProperty("acl") Acl acl) {
        this.posix = posix;
        this.aclMode = aclMode;
        this.acl = acl;
    }

    public int getPosix() {
        return posix;
    }

    public boolean isAclMode() {
        return aclMode;
    }

    public Acl getAcl() {
        return acl;
    }
}
