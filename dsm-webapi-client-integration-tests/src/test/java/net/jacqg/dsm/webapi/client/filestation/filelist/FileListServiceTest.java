package net.jacqg.dsm.webapi.client.filestation.filelist;

import net.jacqg.dsm.webapi.client.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FileListServiceTest extends AbstractTest{

    @Autowired
    FileListService fileListService;

    @Test
    public void testList() throws Exception {
        List<File> list = fileListService.list("/dsm-webapi-it");
        // TODO Add assertions
    }
}
