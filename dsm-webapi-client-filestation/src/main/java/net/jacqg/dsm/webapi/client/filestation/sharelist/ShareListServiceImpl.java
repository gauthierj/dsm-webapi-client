package net.jacqg.dsm.webapi.client.filestation.sharelist;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.jacqg.dsm.webapi.client.AbstractDsmServiceImpl;
import net.jacqg.dsm.webapi.client.DsmWebApiResponseError;
import net.jacqg.dsm.webapi.client.DsmWebapiRequest;
import net.jacqg.dsm.webapi.client.DsmWebapiResponse;
import net.jacqg.dsm.webapi.client.filestation.common.PaginationAndSorting;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ShareListServiceImpl extends AbstractDsmServiceImpl implements ShareListService {

    public ShareListServiceImpl() {
        super("SYNO.FileStation.List");
    }

    @Override
    public Share.ShareList list(PaginationAndSorting paginationAndSorting, Optional<Boolean> onlyWritable) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiInfo().getApi(), "1", getApiInfo().getPath(), "list_share")
                .parameter("offset", Integer.toString(paginationAndSorting.getOffset()))
                .parameter("limit", Integer.toString(paginationAndSorting.getLimit()))
                .parameter("sort_by", paginationAndSorting.getSortBy().getRepresentation())
                .parameter("sort_direction", paginationAndSorting.getSortDirection().getRepresentation())
                .parameter("onlywritable", onlyWritable.orElse(false).toString())
                .parameter("additional", "real_path,owner,time,perm,mount_point_type,sync_share,volume_status");
        ShareListResponse call = getDsmWebapiClient().call(request, ShareListResponse.class);
        return call.getData();
    }

    @Override
    public List<Share> list(boolean onlyWritable) {
        return list(PaginationAndSorting.DEFAULT_PAGINATION_AND_SORTING, Optional.of(onlyWritable)).getElements();
    }

    @Override
    public List<Share> list() {
        return list(false);
    }

    private static class ShareListResponse extends DsmWebapiResponse<Share.ShareList> {

        public ShareListResponse(@JsonProperty("success") boolean success, @JsonProperty("data") Share.ShareList data, @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }
}
