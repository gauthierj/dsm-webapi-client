package net.jacqg.dsm.webapi.client.filestation.copymove;

import net.jacqg.dsm.webapi.client.AbstractTest;
import net.jacqg.dsm.webapi.client.filestation.common.OverwriteBehavior;
import net.jacqg.dsm.webapi.client.filestation.filelist.FileListService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CopyMoveServiceTest extends AbstractTest {

    @Autowired
    private CopyMoveService copyMoveService;

    @Autowired
    private FileListService fileListService;

    @Test
    public void testSynchronousMoveFile() {
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-1/test-text-file2.txt"));
        copyMoveService.synchronousMove("/dsm-webapi-it/test-1/test-text-file2.txt", "/dsm-webapi-it/test-2", OverwriteBehavior.OVERWRITE);
        Assert.assertNull(fileListService.getFile("/dsm-webapi-it/test-1/test-text-file2.txt"));
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-2/test-text-file2.txt"));
    }

    @Test
    public void testSynchronousCopyFile() {
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-1/test-text-file2.txt"));
        copyMoveService.synchronousCopy("/dsm-webapi-it/test-1/test-text-file2.txt", "/dsm-webapi-it/test-2", OverwriteBehavior.OVERWRITE);
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-1/test-text-file2.txt"));
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-2/test-text-file2.txt"));
    }

    @Test
    public void testSynchronousMoveDirectory() {
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-1"));
        copyMoveService.synchronousMove("/dsm-webapi-it/test-1", "/dsm-webapi-it/test-2", OverwriteBehavior.OVERWRITE);
        Assert.assertNull(fileListService.getFile("/dsm-webapi-it/test-1"));
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-2/test-1"));
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-2/test-1/test-text-file2.txt"));
    }

    @Test
    public void testSynchronousCopyDirectory() {
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-1"));
        copyMoveService.synchronousCopy("/dsm-webapi-it/test-1", "/dsm-webapi-it/test-2", OverwriteBehavior.OVERWRITE);
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-1"));
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-1/test-text-file2.txt"));
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-2/test-1"));
        Assert.assertNotNull(fileListService.getFile("/dsm-webapi-it/test-2/test-1/test-text-file2.txt"));
    }
}
