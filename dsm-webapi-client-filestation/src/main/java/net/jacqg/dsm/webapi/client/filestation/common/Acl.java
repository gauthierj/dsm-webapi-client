package net.jacqg.dsm.webapi.client.filestation.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Acl {

    private final boolean append;
    private final boolean delete;
    private final boolean execute;
    private final boolean read;
    private final boolean write;

    @JsonCreator
    public Acl(@JsonProperty("append") boolean append,
               @JsonProperty("del") boolean delete,
               @JsonProperty("exec") boolean execute,
               @JsonProperty("read") boolean read,
               @JsonProperty("write") boolean write) {
        this.append = append;
        this.delete = delete;
        this.execute = execute;
        this.read = read;
        this.write = write;
    }

    public boolean isAppend() {
        return append;
    }

    public boolean isDelete() {
        return delete;
    }

    public boolean isExecute() {
        return execute;
    }

    public boolean isRead() {
        return read;
    }

    public boolean isWrite() {
        return write;
    }
}
