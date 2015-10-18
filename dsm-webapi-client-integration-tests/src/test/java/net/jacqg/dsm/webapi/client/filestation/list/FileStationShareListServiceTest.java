package net.jacqg.dsm.webapi.client.filestation.list;

import net.jacqg.dsm.webapi.client.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class FileStationShareListServiceTest extends AbstractTest {

    @Autowired
    private FileStationShareListService fileStationShareListService;

    @Test
    public void testList() throws Exception {
        List<Share> list = fileStationShareListService.list();
        Assert.assertEquals(2, list.size());
        List<String> shareNames = list.stream().map(Share::getName).collect(Collectors.toList());
        Assert.assertTrue(shareNames.contains("home"));
        Assert.assertTrue(shareNames.contains("dsm-webapi-it"));
    }

}
