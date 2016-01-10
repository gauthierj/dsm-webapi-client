package com.github.gauthierj.dsm.webapi.client.filestation.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gauthierj.dsm.webapi.client.filestation.filelist.FileProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class File {

    private final String path;
    private final String name;
    private final boolean directory;
    private final FileProperties properties;
    private final List<File> children = new ArrayList<>();

    @JsonCreator
    public File(
            @JsonProperty("path") String path,
            @JsonProperty("name") String name,
            @JsonProperty("isdir") boolean directory,
            @JsonProperty("additional") FileProperties properties,
            @JsonProperty("children") FileList children) {
        this.path = path;
        this.name = name;
        this.directory = directory;
        this.properties = properties;
        if(children != null) {
            this.children.addAll(children.getElements());
        }
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public boolean isDirectory() {
        return directory;
    }

    public FileProperties getProperties() {
        return properties;
    }

    public List<File> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public static class FileList extends PaginatedList<File> {

        public FileList(@JsonProperty("total") int total,
                        @JsonProperty("offset") int offset,
                        @JsonProperty("files") List<File> files) {
            super(total, offset, files);
        }
    }
}
