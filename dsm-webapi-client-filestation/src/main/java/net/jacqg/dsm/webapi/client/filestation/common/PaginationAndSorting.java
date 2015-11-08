package net.jacqg.dsm.webapi.client.filestation.common;

public class PaginationAndSorting {

    public static final PaginationAndSorting DEFAULT_PAGINATION_AND_SORTING = new PaginationAndSorting(0, -1, Sort.NAME, SortDirection.ASC);

    public enum Sort {

        NAME("name"), USER("user"), GROUP("group"), MTIME("mtime"), ATIME("atime"), CTIME("ctime"), CRTIME("crtime"), POSIX("posix"), SIZE("size"), TYPE("type");

        private final String representation;

        Sort(String representation) {
            this.representation = representation;
        }

        public String getRepresentation() {
            return representation;
        }
    }

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

    private final int offset;
    private final int limit;
    private final Sort sortBy;
    private final SortDirection sortDirection;

    public PaginationAndSorting(int offset, int limit, Sort sortBy, SortDirection sortDirection) {
        this.offset = offset;
        this.limit = limit;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    public static PaginationAndSorting getDefaultPaginationAndSorting() {
        return DEFAULT_PAGINATION_AND_SORTING;
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public Sort getSortBy() {
        return sortBy;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }
}
