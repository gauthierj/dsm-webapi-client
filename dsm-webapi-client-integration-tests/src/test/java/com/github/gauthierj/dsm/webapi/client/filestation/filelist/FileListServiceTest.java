package com.github.gauthierj.dsm.webapi.client.filestation.filelist;

import com.github.gauthierj.dsm.webapi.client.filestation.common.File;
import com.github.gauthierj.dsm.webapi.client.AbstractTest;
import com.github.gauthierj.dsm.webapi.client.filestation.exception.FileNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

public class FileListServiceTest extends AbstractTest{

    @Autowired
    FileListService fileListService;

    @Test
    public void testList() throws Exception {
        List<File> list = fileListService.list("/dsm-webapi-it");
        Assert.assertEquals(3, list.size());
    }

    @Test
    public void testGetFiles() {
        List<File> files = fileListService.getFiles(Arrays.asList("/dsm-webapi-it/test-1", "/dsm-webapi-it/test-text-file.txt"));
        Assert.assertEquals(2, files.size());
    }

    @Test(expected = FileNotFoundException.class)
    public void testListWrongName() throws Exception {
        List<File> list = fileListService.list("/dsm-webapi-it/brol");
    }

    @Test
    public void testGetFilesWrongName() {
        List<File> files = fileListService.getFiles(Arrays.asList("/dsm-webapi-it/test-1", "/dsm-webapi-it/brol"));
        Assert.assertEquals(1, files.size());
    }
}
