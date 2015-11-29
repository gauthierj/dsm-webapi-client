package net.jacqg.dsm.webapi.client.filestation.download;

import net.jacqg.dsm.webapi.client.AbstractTest;
import net.jacqg.dsm.webapi.client.filestation.exception.FileNotFoundException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;

import java.io.IOException;

public class DownloadServiceTest extends AbstractTest {

    @Autowired
    private DownloadService downloadService;

    @Test
    public void testDownloadTextFile() {
        byte[] download = downloadService.download("/dsm-webapi-it/test-1/test-text-file2.txt");
        Assert.assertEquals("This is a test file", new String(download));
    }

    @Test
    public void testDownloadTextPdfFile() throws IOException {
        byte[] downloaded = downloadService.download("/dsm-webapi-it/test-2/Test_document_PDF.pdf");
        byte[] expected = StreamUtils.copyToByteArray(DownloadServiceTest.class.getResourceAsStream("/file-resources/test-2/Test_document_PDF.pdf"));
        Assert.assertEquals(expected.length, downloaded.length);
        for (int i = 0; i < downloaded.length; i++) {
            Assert.assertEquals(expected[i], downloaded[i]);
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void testDownloadInexistingFile() {
        downloadService.download("/dsm-webapi-it/test-1/inexisting.txt");
    }
}
