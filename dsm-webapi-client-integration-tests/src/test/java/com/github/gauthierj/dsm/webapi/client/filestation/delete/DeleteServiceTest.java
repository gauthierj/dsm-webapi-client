package com.github.gauthierj.dsm.webapi.client.filestation.delete;

import com.github.gauthierj.dsm.webapi.client.AbstractTest;
import com.github.gauthierj.dsm.webapi.client.filestation.exception.FileNotFoundException;
import com.github.gauthierj.dsm.webapi.client.filestation.filelist.FileListService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.*;

public class DeleteServiceTest extends AbstractTest {

    @Autowired
    private FileListService fileListService;

    @Autowired
    private DeleteServiceImpl deleteService;

    @Test
    public void testSynchronousDelete() {
        assertTrue(deleteService.synchronousDelete("/dsm-webapi-it/test-1", true, Optional.empty()));
        assertNull(fileListService.getFile("/dsm-webapi-it/test-1"));
    }

    @Test
    public void testDeleteNonRecursive() {
        assertFalse(deleteService.synchronousDelete("/dsm-webapi-it/test-1", false, Optional.empty()));
        assertNotNull(fileListService.getFile("/dsm-webapi-it/test-1"));
    }

    @Test(expected = FileNotFoundException.class)
    public void testNotExistingShare() {
        deleteService.synchronousDelete("/not-existing/test-brol", false, Optional.empty());
    }

    @Test
    public void testNotExistingFile() {
        assertFalse(deleteService.synchronousDelete("/dsm-webapi-it/test-brol", false, Optional.empty()));
    }
}
