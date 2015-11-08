package net.jacqg.dsm.webapi.client.filestation.download;

import net.jacqg.dsm.webapi.client.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DownloadServiceTest extends AbstractTest {

    @Autowired
    private DownloadService downloadService;

    @Test
    public void testDownload() throws Exception {
        byte[] download = downloadService.download("/dsm-webapi-it/test-1/test-text-file2.txt");
        Assert.assertEquals("This is a test file", new String(download));
    }
}
