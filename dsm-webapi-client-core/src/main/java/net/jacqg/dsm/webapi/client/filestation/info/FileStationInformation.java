package net.jacqg.dsm.webapi.client.filestation.info;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileStationInformation {

    private boolean manager;
    private List<String> supportedVirtualFileSystems = new ArrayList<>();
    private boolean supportSharing;
    private String hostname;

    @JsonCreator
    public FileStationInformation(
            @JsonProperty("is_manager") boolean manager,
            @JsonProperty("support_virtual") String supportedVirtualFileSystems,
            @JsonProperty("support_sharing") boolean supportSharing,
            @JsonProperty("hostname") String hostname) {
        this.manager = manager;
        if(Strings.isNullOrEmpty(supportedVirtualFileSystems)) {
            this.supportedVirtualFileSystems.addAll(Splitter.on(',').splitToList(supportedVirtualFileSystems));
        }
        this.supportSharing = supportSharing;
        this.hostname = hostname;
    }

    public boolean isManager() {
        return manager;
    }

    public List<String> getSupportedVirtualFileSystems() {
        return Collections.unmodifiableList(supportedVirtualFileSystems);
    }

    public boolean isSupportSharing() {
        return supportSharing;
    }

    public String getHostname() {
        return hostname;
    }
}
