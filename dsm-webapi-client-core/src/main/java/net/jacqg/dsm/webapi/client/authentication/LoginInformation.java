package net.jacqg.dsm.webapi.client.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginInformation {

    private final String username;
    private final String session;
    private final String sid;

    public LoginInformation(String username, String session, String sid) {
        this.username = username;
        this.session = session;
        this.sid = sid;
    }

    @JsonCreator
    private LoginInformation(@JsonProperty("sid") String sid) {
        this(null, null, sid);
    }

    public String getUsername() {
        return username;
    }

    public String getSession() {
        return session;
    }

    public String getSid() {
        return sid;
    }
}
