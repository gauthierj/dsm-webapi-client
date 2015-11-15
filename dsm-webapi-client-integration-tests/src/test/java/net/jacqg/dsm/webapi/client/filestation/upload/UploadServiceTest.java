package net.jacqg.dsm.webapi.client.filestation.upload;

import net.jacqg.dsm.webapi.client.AbstractTest;
import net.jacqg.dsm.webapi.client.authentication.AuthenticationHolder;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UploadServiceTest extends AbstractTest {

    @Autowired
    private UploadService uploadService;

    @Test
    public void testUpload() throws Exception {
        UploadRequest uploadRequest = UploadRequest.createBuilder("/dsm-webapi-it/createFiles" + System.currentTimeMillis(), "test-file.txt", "this is a test file with strsing content\nHelloWorld!\n".getBytes("UTF-8"))
                .createParents(true)
                .creationTime(LocalDateTime.of(1984, 3, 9, 10, 0))
                .lastAccessTime(LocalDateTime.of(1984, 3, 9, 10, 0))
                .lastModificationTime(LocalDateTime.of(1984, 3, 9, 10, 0))
                .build();
        uploadService.uploadFile(uploadRequest);
    }
}
