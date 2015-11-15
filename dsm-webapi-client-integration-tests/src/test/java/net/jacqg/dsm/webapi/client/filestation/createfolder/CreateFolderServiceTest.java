package net.jacqg.dsm.webapi.client.filestation.createfolder;

import net.jacqg.dsm.webapi.client.AbstractTest;
import net.jacqg.dsm.webapi.client.filestation.exception.CouldNotCreateFolderException;
import net.jacqg.dsm.webapi.client.filestation.filelist.File;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CreateFolderServiceTest extends AbstractTest {

    @Autowired
    private CreateFolderService createFolderService;

    @Test
    public void testCreateFolder() throws Exception {
        File newFolderCreate = createFolderService.createFolder("/dsm-webapi-it/CreateFolder" + System.currentTimeMillis() + "/subFolder", "newFolderCreated", true);
        Assert.assertNotNull(newFolderCreate);
        // TODO more assertions
    }

    @Test(expected = CouldNotCreateFolderException.class)
    public void testCreateFolderWithoutCreatingParents() throws Exception {
        File newFolderCreate = createFolderService.createFolder("/dsm-webapi-it/CreateFolder2" + System.currentTimeMillis() + "/subFolder", "newFolderCreated", false);
    }
}
