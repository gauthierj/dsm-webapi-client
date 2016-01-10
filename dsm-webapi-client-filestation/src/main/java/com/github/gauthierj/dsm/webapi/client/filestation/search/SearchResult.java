package com.github.gauthierj.dsm.webapi.client.filestation.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gauthierj.dsm.webapi.client.filestation.common.File;

import java.util.List;

public class SearchResult extends File.FileList {

    private final boolean finished;

    public SearchResult(@JsonProperty("finished") boolean finished, @JsonProperty("total") int total, @JsonProperty("offset") int offset, @JsonProperty("files") List<File> files) {
        super(total, offset, files);
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }
}
