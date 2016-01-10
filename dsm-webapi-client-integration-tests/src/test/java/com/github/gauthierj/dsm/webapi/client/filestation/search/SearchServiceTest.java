package com.github.gauthierj.dsm.webapi.client.filestation.search;

import com.github.gauthierj.dsm.webapi.client.filestation.common.File;
import com.github.gauthierj.dsm.webapi.client.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SearchServiceTest extends AbstractTest {

    @Autowired
    private SearchService searchService;

    @Test
    public void testSearch() {
        SearchCriteria build = SearchCriteria.SearchCriteriaBuilder
                .create()
                .pattern("test")
                .build();
        List<File> files = searchService.synchronousSearch("/dsm-webapi-it", true, build);
        Assert.assertTrue(files.size() > 0);
        // TODO need more assertions
    }
}
