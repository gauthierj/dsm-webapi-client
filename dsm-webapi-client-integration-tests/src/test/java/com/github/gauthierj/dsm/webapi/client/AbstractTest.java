package com.github.gauthierj.dsm.webapi.client;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class AbstractTest {

    private static final String FILE_RESOURCES_ROOT = "/file-resources";

    @Value("${dsm.share}")
    private String share;

    @Value("${dsm.share.mountPoint}")
    private String shareMountPointPath;

    private Path shareMountPoint;

    @Before
    public void setUp() throws URISyntaxException, IOException, InterruptedException {
        shareMountPoint = Paths.get(shareMountPointPath);
        Assert.assertTrue(Files.exists(shareMountPoint));
        Assert.assertTrue(Files.isWritable(shareMountPoint));
        createFileStructure();
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.cleanDirectory(shareMountPoint.toFile());
    }

    public String getShare() {
        return share;
    }

    public String getShareRoot() {
        return "/" + share;
    }

    public Path getShareMountPoint() {
        return shareMountPoint;
    }

    private void createFileStructure() throws URISyntaxException, IOException, InterruptedException {
        URL resource = AbstractTest.class.getResource(FILE_RESOURCES_ROOT);
        Path fileResourcesRoot = Paths.get(resource.toURI());
        int i = 0;
        while (i < 5) {
            try {
                FileUtils.copyDirectory(fileResourcesRoot.toFile(), shareMountPoint.toFile());
                i = 5;
            } catch (IOException e) {
                if (i < 4) {
                    Thread.sleep(1000 + i * i * 1000);
                    i++;
                } else {
                    throw new IllegalStateException("Could not create file structure", e);
                }
            }
        }
    }

}
