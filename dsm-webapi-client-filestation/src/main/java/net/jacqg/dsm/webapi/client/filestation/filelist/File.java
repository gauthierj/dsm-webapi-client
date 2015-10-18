package net.jacqg.dsm.webapi.client.filestation.filelist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
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
            this.children.addAll(children.getFiles());
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
        return children;
    }

    public static class FileList {

        private final int total;
        private final int offset;
        private final List<File> files = new ArrayList<>();

        public FileList(@JsonProperty("total") int total,
                        @JsonProperty("offset") int offset,
                        @JsonProperty("files") List<File> files) {
            this.total = total;
            this.offset = offset;
            if(files != null) {
                this.files.addAll(files);
            }
        }

        public int getTotal() {
            return total;
        }

        public int getOffset() {
            return offset;
        }

        public List<File> getFiles() {
            return files;
        }
    }
}
