package cn.iba8.module.generator.test.cron;

import cn.iba8.module.generator.cron.DatabaseChangeMonitor;
import cn.iba8.module.generator.test.ApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class DatabaseChangeMonitorTest extends ApplicationTest {

    @Autowired
    private DatabaseChangeMonitor databaseChangeMonitor;

    @Test
    @Transactional
    public void monitor() {
        databaseChangeMonitor.monitor();
    }
}
