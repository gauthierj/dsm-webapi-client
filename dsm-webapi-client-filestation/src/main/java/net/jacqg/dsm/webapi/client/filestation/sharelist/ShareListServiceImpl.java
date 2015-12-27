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

    // API Infos
    private static final String API_ID = "SYNO.FileStation.List";
    private static final String API_VERSION = "1";

    // API Methods
    private static final String METHOD_LIST_SHARE = "list_share";

    // Parameters
    private static final String PARAMETER_ADDITIONAL = "additional";
    private static final String PARAMETER_OFFSET = "offset";
    private static final String PARAMETER_ONLYWRITABLE = "onlywritable";
    private static final String PARAMETER_LIMIT = "limit";
    private static final String PARAMETER_SORT_BY = "sort_by";
    private static final String PARAMETER_SORT_DIRECTION = "sort_direction";

    // Parameters values
    private static final String PARAMETER_VALUE_ADDITIONAL = "real_path,owner,time,perm,mount_point_type,sync_share,volume_status";

    public ShareListServiceImpl() {
        super(API_ID);
    }

    @Override
    public ShareList list(PaginationAndSorting paginationAndSorting, Optional<Boolean> onlyWritable) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiInfo().getApi(), API_VERSION, getApiInfo().getPath(), METHOD_LIST_SHARE)
                .parameter(PARAMETER_OFFSET, Integer.toString(paginationAndSorting.getOffset()))
                .parameter(PARAMETER_LIMIT, Integer.toString(paginationAndSorting.getLimit()))
                .parameter(PARAMETER_SORT_BY, paginationAndSorting.getSortBy().getRepresentation())
                .parameter(PARAMETER_SORT_DIRECTION, paginationAndSorting.getSortDirection().getRepresentation())
                .parameter(PARAMETER_ONLYWRITABLE, onlyWritable.orElse(false).toString())
                .parameter(PARAMETER_ADDITIONAL, PARAMETER_VALUE_ADDITIONAL);
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

    private static class ShareListResponse extends DsmWebapiResponse<ShareList> {

        public ShareListResponse(@JsonProperty("success") boolean success, @JsonProperty("data") ShareList data, @JsonProperty("error") DsmWebApiResponseError error) {
            super(success, data, error);
        }
    }
}
