package com.github.gauthierj.dsm.webapi.client.filestation.rename;

import com.github.gauthierj.dsm.webapi.client.filestation.common.File;
import com.github.gauthierj.dsm.webapi.client.AbstractTest;
import com.github.gauthierj.dsm.webapi.client.filestation.exception.CouldNotRenameException;
import com.github.gauthierj.dsm.webapi.client.filestation.filelist.FileListService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RenameServiceTest extends AbstractTest {

    @Autowired
    private RenameService renameService;

    @Autowired
    private FileListService fileListService;

    @Test
    public void testRenameFile() {
        File renamedFile = renameService.rename("/dsm-webapi-it/test-1/test-text-file2.txt", "new-text-file.txt");
        Assert.assertEquals("new-text-file.txt", renamedFile.getName());
        Assert.assertNull(fileListService.getFile("/dsm-webapi-it/test-1/test-text-file2.txt"));
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-1/new-text-file.txt"));
    }

    @Test
    public void testRenameFolder() {
        File renamedFile = renameService.rename("/dsm-webapi-it/test-2", "new-folder");
        Assert.assertEquals("new-folder", renamedFile.getName());
        Assert.assertNull(fileListService.getFile("/dsm-webapi-it/test-2"));
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/new-folder"));
    }

    @Test(expected = CouldNotRenameException.class)
    public void testRenameNotExisting() {
        File renamedFile = renameService.rename("/dsm-webapi-it/test-1/not-existing.txt", "new-text-file.txt");
    }
}
