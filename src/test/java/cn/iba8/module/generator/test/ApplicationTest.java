package cn.iba8.module.generator.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/*
 * @Author sc.wan
 * @Description 单元测试
 * @Date 10:37 2019/6/28
 * @Param
 * @return
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class ApplicationTest {

   @Before
   public void init() {
       System.out.println("开始测试a");
   }

   @After
   public void after() {
       System.out.println("结束测试");
   }

}
