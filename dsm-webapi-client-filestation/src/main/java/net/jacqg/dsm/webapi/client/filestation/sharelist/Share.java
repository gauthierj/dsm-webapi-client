package net.jacqg.dsm.webapi.client.filestation.sharelist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.jacqg.dsm.webapi.client.filestation.common.PaginatedList;

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

    public static class ShareList extends PaginatedList<Share> {

        @JsonCreator
        public ShareList(@JsonProperty("total") int total,
                         @JsonProperty("offset") int offset,
                         @JsonProperty("shares") List<Share> shares) {
            super(total, offset, shares);
        }
    }
}
