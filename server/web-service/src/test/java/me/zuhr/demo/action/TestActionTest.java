package me.zuhr.demo.action;

import me.zuhr.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zurun
 * @date 2018/2/13 15:32:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class TestActionTest {

    @Autowired
    TestAction testAction;

    @Test
    public void test(){
        System.out.println(testAction.redis());
    }
}