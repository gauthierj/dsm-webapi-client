package net.jacqg.dsm.webapi.client.filestation.list;

import java.util.List;

public interface FileStationShareListService {

    List<Share> list(int offset, int limit, Sort sortBy, SortDirection sortDirection, boolean onlyWritable);

    List<Share> list(boolean onlyWritable);

    List<Share> list();
}
