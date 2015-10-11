package net.jacqg.dsm.webapi.client.filestation.list;

public enum Sort {

    NAME("name"), USER("user"), GROUP("group"), MTIME("mtime"), ATIME("atime"), CTIME("ctime"), CRTIME("crtime"), POSIX("posix");

    private final String representation;

    Sort(String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }
}
