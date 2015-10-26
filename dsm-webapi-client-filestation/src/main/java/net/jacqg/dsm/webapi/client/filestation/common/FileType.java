package net.jacqg.dsm.webapi.client.filestation.common;

public enum FileType {
    FILE("file"), DIRECTORY("directory"), ALL("all");

    private final String representation;

    FileType(String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }
}
