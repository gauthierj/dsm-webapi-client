package net.jacqg.dsm.webapi.client.filestation.sharelist;

import net.jacqg.dsm.webapi.client.filestation.common.PaginationAndSorting;

import java.util.List;
import java.util.Optional;

public interface ShareListService {

    Share.ShareList list(PaginationAndSorting paginationAndSorting, Optional<Boolean> onlyWritable);

    List<Share> list(boolean onlyWritable);

    List<Share> list();
}
