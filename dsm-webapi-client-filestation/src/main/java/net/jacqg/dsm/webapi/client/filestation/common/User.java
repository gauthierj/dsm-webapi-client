package net.jacqg.dsm.webapi.client.filestation.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private final int gid;
    private final String group;
    private final int uid;
    private final String username;

    @JsonCreator
    public User(@JsonProperty("gid") int gid,
                @JsonProperty("group") String group,
                @JsonProperty("uid") int uid,
                @JsonProperty("user") String username) {
        this.gid = gid;
        this.group = group;
        this.uid = uid;
        this.username = username;
    }

    public int getGid() {
        return gid;
    }

    public String getGroup() {
        return group;
    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }
}
