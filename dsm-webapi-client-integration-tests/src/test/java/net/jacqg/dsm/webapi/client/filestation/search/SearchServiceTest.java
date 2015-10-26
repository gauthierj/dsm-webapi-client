package net.jacqg.dsm.webapi.client.filestation.search;

import net.jacqg.dsm.webapi.client.AbstractTest;
import net.jacqg.dsm.webapi.client.filestation.filelist.File;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static net.jacqg.dsm.webapi.client.filestation.search.SearchCriteria.*;

public class SearchServiceTest extends AbstractTest {

    @Autowired
    private SearchService searchService;

    @Test
    public void testSearch() {
        List<File> files = searchService.synchronousSearch("/dsm-webapi-it", true, SearchCriteriaBuilder.create().build());
        System.out.println(files);
    }
}
