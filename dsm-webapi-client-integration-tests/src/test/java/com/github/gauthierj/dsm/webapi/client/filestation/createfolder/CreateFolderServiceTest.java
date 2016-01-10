package com.github.gauthierj.dsm.webapi.client.filestation.createfolder;

import com.google.common.base.Strings;
import com.github.gauthierj.dsm.webapi.client.AbstractTest;
import com.github.gauthierj.dsm.webapi.client.filestation.exception.CouldNotCreateFolderException;
import com.github.gauthierj.dsm.webapi.client.filestation.common.File;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class CreateFolderServiceTest extends AbstractTest {

    @Value("${dsm.webapi.username}")
    private String connectedUser;

    @Autowired
    private CreateFolderService createFolderService;

    @Test
    public void testCreateFolder() throws Exception {
        LocalDateTime beforeCreation = LocalDateTime.now();
        String folderPath = "/CreateFolder" + System.currentTimeMillis() + "/subFolder";
        File newFolderCreated = createFolderService.createFolder(getShareRoot() + folderPath, "newFolderCreated", true);
        LocalDateTime afterCreation = LocalDateTime.now();
        Assert.assertNotNull(newFolderCreated);
        Assert.assertTrue(newFolderCreated.isDirectory());
        Assert.assertEquals("newFolderCreated", newFolderCreated.getName());
        Assert.assertEquals(getShareRoot() + folderPath + "/newFolderCreated", newFolderCreated.getPath());
        Assert.assertTrue(newFolderCreated.getProperties().getRealPath().endsWith(getShareRoot() + folderPath + "/newFolderCreated"));
        Assert.assertTrue(newFolderCreated.getProperties().getSize() > 0);
        Assert.assertEquals(connectedUser, newFolderCreated.getProperties().getOwner().getUsername());
        Assert.assertFalse(Strings.isNullOrEmpty(newFolderCreated.getProperties().getOwner().getGroup()));
        Assert.assertTrue(newFolderCreated.getProperties().getOwner().getGid() > 0);
        Assert.assertTrue(newFolderCreated.getProperties().getOwner().getUid() > 0);
        Assert.assertTrue(newFolderCreated.getProperties().getTimeInformation().getCreationTime().isAfter(beforeCreation));
        Assert.assertTrue(newFolderCreated.getProperties().getTimeInformation().getCreationTime().isBefore(afterCreation));
        Assert.assertTrue(newFolderCreated.getProperties().getTimeInformation().getLastAccessTime().isAfter(beforeCreation));
        Assert.assertTrue(newFolderCreated.getProperties().getTimeInformation().getLastAccessTime().isBefore(afterCreation));
        Assert.assertTrue(newFolderCreated.getProperties().getTimeInformation().getLastChangeTime().isAfter(beforeCreation));
        Assert.assertTrue(newFolderCreated.getProperties().getTimeInformation().getLastChangeTime().isBefore(afterCreation));
        Assert.assertTrue(newFolderCreated.getProperties().getTimeInformation().getLastModificationTime().isAfter(beforeCreation));
        Assert.assertTrue(newFolderCreated.getProperties().getTimeInformation().getLastModificationTime().isBefore(afterCreation));
        Assert.assertEquals(777, newFolderCreated.getProperties().getFilePermission().getPosix());
        Assert.assertTrue(newFolderCreated.getProperties().getFilePermission().isAclMode());
        Assert.assertTrue(newFolderCreated.getProperties().getFilePermission().getAcl().isAppend());
        Assert.assertTrue(newFolderCreated.getProperties().getFilePermission().getAcl().isDelete());
        Assert.assertTrue(newFolderCreated.getProperties().getFilePermission().getAcl().isExecute());
        Assert.assertTrue(newFolderCreated.getProperties().getFilePermission().getAcl().isRead());
        Assert.assertTrue(newFolderCreated.getProperties().getFilePermission().getAcl().isWrite());

        // Check folder through mount point
        Path createdFolder = Paths.get(getShareMountPoint().toString(), folderPath);
        Assert.assertTrue(Files.exists(createdFolder));
        Assert.assertTrue(Files.isDirectory(createdFolder));
    }

    @Test(expected = CouldNotCreateFolderException.class)
    public void testCreateFolderWithoutCreatingParents() throws Exception {
        File newFolderCreate = createFolderService.createFolder("/dsm-webapi-it/CreateFolder2" + System.currentTimeMillis() + "/subFolder", "newFolderCreated", false);
    }
}
