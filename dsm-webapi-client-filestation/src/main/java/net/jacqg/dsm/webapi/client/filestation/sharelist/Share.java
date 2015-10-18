package net.jacqg.dsm.webapi.client.filestation.sharelist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Share {

    private final String path;
    private final String name;
    private final boolean directory;
    private final ShareProperties properties;

    @JsonCreator
    public Share(@JsonProperty("path") String path,
                 @JsonProperty("name") String name,
                 @JsonProperty("isdir") boolean directory,
                 @JsonProperty("additional") ShareProperties properties) {
        this.path = path;
        this.name = name;
        this.directory = directory;
        this.properties = properties;
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

    public ShareProperties getProperties() {
        return properties;
    }

    public static class ShareList {

        private final int total;
        private final int offset;
        private final List<Share> shares = new ArrayList<>();

        @JsonCreator
        public ShareList(@JsonProperty("total") int total,
                         @JsonProperty("offset") int offset,
                         @JsonProperty("shares") List<Share> shares) {
            this.total = total;
            this.offset = offset;
            if(shares != null) {
                this.shares.addAll(shares);
            }
        }

        public int getTotal() {
            return total;
        }

        public int getOffset() {
            return offset;
        }

        public List<Share> getShares() {
            return Collections.unmodifiableList(shares);
        }
    }
}
