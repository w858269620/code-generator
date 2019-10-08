package cn.iba8.module.generator.test.service;

import cn.iba8.module.generator.common.MetaDatabaseTableDefinition;
import cn.iba8.module.generator.service.MetaDatabaseService;
import cn.iba8.module.generator.test.ApplicationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MetaDatabaseServiceTest extends ApplicationTest {

    @Autowired
    private MetaDatabaseService metaDatabaseService;

    @Test
    public void metaTables() {
        List<MetaDatabaseTableDefinition> definitions = metaDatabaseService.metaTables("10.133.230.62:3306:code_generator");
//        Assert.assertNotNull(definitions);
    }
}
