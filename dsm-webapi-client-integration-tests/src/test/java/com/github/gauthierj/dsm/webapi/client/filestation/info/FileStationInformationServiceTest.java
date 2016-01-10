package com.github.gauthierj.dsm.webapi.client.filestation.info;

import com.github.gauthierj.dsm.webapi.client.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FileStationInformationServiceTest extends AbstractTest {

    @Autowired
    private FileStationInformationService fileStationInformationService;

    @Test
    public void testName() throws Exception {
        FileStationInformation fileStationInformation = fileStationInformationService.getFileStationInformation();
        Assert.assertNotNull(fileStationInformation);
    }
}
