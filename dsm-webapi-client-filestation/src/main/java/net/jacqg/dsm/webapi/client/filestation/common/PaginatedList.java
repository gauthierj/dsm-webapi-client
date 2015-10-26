package net.jacqg.dsm.webapi.client.filestation.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaginatedList<T> {

    private final int total;
    private final int offset;
    private final List<T> elements = new ArrayList<>();

    public PaginatedList(int total, int offset, List<T> elements) {
        this.total = total;
        this.offset = offset;
        if(elements != null) {
            this.elements.addAll(elements);

        }
    }

    public int getTotal() {
        return total;
    }

    public int getOffset() {
        return offset;
    }

    public List<T> getElements() {
        return Collections.unmodifiableList(elements);
    }
}
