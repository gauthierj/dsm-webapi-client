package net.jacqg.dsm.webapi.client.filestation;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.jacqg.dsm.webapi.client.DsmWebapiRequest;
import net.jacqg.dsm.webapi.client.DsmWebapiResponse;
import net.jacqg.dsm.webapi.client.filestation.list.Share;
import net.jacqg.dsm.webapi.client.filestation.list.Sort;
import net.jacqg.dsm.webapi.client.filestation.list.SortDirection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileStationListServiceImpl extends AbstractDsmServiceImpl implements FileStationListService {

    public FileStationListServiceImpl() {
        super("SYNO.FileStation.List");
    }

    @Override
    public List<Share> list(int offset, int limit, Sort sortBy, SortDirection sortDirection, boolean onlyWritable) {
        DsmWebapiRequest request = new DsmWebapiRequest(getApiInfo().getApi(), "1", getApiInfo().getPath(), "list_share")
                .parameter("offset", Integer.toString(offset))
                .parameter("limit", Integer.toString(limit))
                .parameter("sort_by", sortBy.getRepresentation())
                .parameter("sort_direction", sortDirection.getRepresentation())
                .parameter("onlywritable", Boolean.toString(onlyWritable))
                .parameter("additional", "real_path,owner,time,perm,mount_point_type,sync_share,volume_status");
        ShareListResponse call = getDsmWebapiClient().call(request, ShareListResponse.class);
        return call.getData().getShares();
    }

    @Override
    public List<Share> list(boolean onlyWritable) {
        return list(0, Integer.MAX_VALUE, Sort.NAME, SortDirection.ASC, onlyWritable);
    }

    @Override
    public List<Share> list() {
        return list(0, Integer.MAX_VALUE, Sort.NAME, SortDirection.ASC, false);
    }

    private static class ShareListResponse extends DsmWebapiResponse<Share.ShareList> {

        public ShareListResponse(@JsonProperty("success") boolean success, @JsonProperty("data") Share.ShareList data, @JsonProperty("error") Error error) {
            super(success, data, error);
        }
    }
}
