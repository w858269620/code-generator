package cn.iba8.module.generator.test.cron;

import cn.iba8.module.generator.cron.DatabaseChangePush;
import cn.iba8.module.generator.test.ApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class DatabaseChangePushTest extends ApplicationTest {

    @Autowired
    private DatabaseChangePush databaseChangePush;

    @Test
    @Transactional
    public void pushDatabase() {
        databaseChangePush.pushDatabase();
    }

    @Test
    @Transactional
    public void pushTableAndField() {
        databaseChangePush.pushTableAndField();
    }

}
