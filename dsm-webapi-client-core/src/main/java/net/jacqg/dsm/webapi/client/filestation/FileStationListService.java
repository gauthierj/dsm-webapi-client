package net.jacqg.dsm.webapi.client.filestation;

import net.jacqg.dsm.webapi.client.filestation.list.Share;
import net.jacqg.dsm.webapi.client.filestation.list.Sort;
import net.jacqg.dsm.webapi.client.filestation.list.SortDirection;

import java.util.List;

public interface FileStationListService {

    public List<Share> list(int offset, int limit, Sort sortBy, SortDirection sortDirection, boolean onlyWritable);

    List<Share> list(boolean onlyWritable);

    List<Share> list();
}
