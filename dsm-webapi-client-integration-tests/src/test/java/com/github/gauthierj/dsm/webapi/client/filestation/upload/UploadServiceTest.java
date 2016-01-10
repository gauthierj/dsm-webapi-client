package com.github.gauthierj.dsm.webapi.client.filestation.upload;

import com.github.gauthierj.dsm.webapi.client.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
