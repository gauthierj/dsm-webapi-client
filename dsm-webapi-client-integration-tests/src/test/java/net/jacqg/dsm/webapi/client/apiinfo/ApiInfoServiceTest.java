package net.jacqg.dsm.webapi.client.apiinfo;

import com.google.common.base.Strings;
import net.jacqg.dsm.webapi.client.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ApiInfoServiceTest extends AbstractTest {

    @Autowired
    private ApiInfoService apiInfoService;

    @Test
    public void testFindAll() {
        List<ApiInfo> all = apiInfoService.findAll();
        Assert.assertNotNull(all);
        Assert.assertFalse(all.isEmpty());
    }

    @Test
    public void testFindOne() {
        ApiInfo one = apiInfoService.findOne("SYNO.Backup.App");
        Assert.assertNotNull(one);
        Assert.assertEquals("SYNO.Backup.App", one.getApi());
        Assert.assertEquals("1", one.getMinVersion());
        Assert.assertFalse(Strings.isNullOrEmpty(one.getMaxVersion()));
        Assert.assertEquals("entry.cgi", one.getPath());
        Assert.assertEquals("JSON", one.getRequestFormat());
    }

    @Test(expected = ApiNotFoundException.class)
    public void testFindOneNotExisting() {
        apiInfoService.findOne("not-existing");
    }
}
