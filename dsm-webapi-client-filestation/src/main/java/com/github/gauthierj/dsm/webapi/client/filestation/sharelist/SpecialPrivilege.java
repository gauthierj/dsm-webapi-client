package com.github.gauthierj.dsm.webapi.client.filestation.sharelist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SpecialPrivilege {

    private final boolean disableDownload;
    private final boolean disableList;
    private final boolean disableModify;

    @JsonCreator
    public SpecialPrivilege(@JsonProperty("disable_download") boolean disableDownload,
                            @JsonProperty("disable_list") boolean disableList,
                            @JsonProperty("disable_modify") boolean disableModify) {
        this.disableDownload = disableDownload;
        this.disableList = disableList;
        this.disableModify = disableModify;
    }

    public boolean isDisableDownload() {
        return disableDownload;
    }

    public boolean isDisableList() {
        return disableList;
    }

    public boolean isDisableModify() {
        return disableModify;
    }
}
