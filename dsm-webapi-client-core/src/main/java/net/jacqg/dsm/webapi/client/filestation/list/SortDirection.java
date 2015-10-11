package net.jacqg.dsm.webapi.client.filestation.list;

public enum SortDirection {

    ASC("asc"), DESC("desc");

    private final String representation;

    SortDirection(String representation) {
        this.representation = representation;
    }

    public String getRepresentation() {
        return representation;
    }
}
