package net.jacqg.dsm.webapi.client.filestation.sharelist;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.jacqg.dsm.webapi.client.filestation.common.PaginatedList;

import java.util.List;

public class ShareList extends PaginatedList<Share> {

    @JsonCreator
    public ShareList(@JsonProperty("total") int total,
                     @JsonProperty("offset") int offset,
                     @JsonProperty("shares") List<Share> shares) {
        super(total, offset, shares);
    }
}
