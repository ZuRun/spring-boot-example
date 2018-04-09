package me.zuhr.demo.mybatis;

import me.zuhr.demo.mybatis.entity.User;
import me.zuhr.demo.mybatis.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 单元测试可能需要修改的内容:
 * 1.pom中被注释的flyway,用来生成表
 * 2.application.yml和generatorConfig.xml中的数据库连接信息
 *
 * @author zurun
 * @date 2018/4/9 17:41:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MybatisApplication.class)
public class MybatisApplicationTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void mybatisTest() {
        User user = new User();
        user.setPassword("pwd");
        user.setPhone("0535-8888888");
        user.setUserName("用户名A");
        user.setAge(1);
        userMapper.insertSelective(user);
        user.setUserName("B");
        userMapper.insertSelective(user);

        User user2 = userMapper.selectByPrimaryKey(1000L);
        User user3 = userMapper.selectByPrimaryKey(1001L);

        System.out.println(user.getId());
        System.out.println(user.getUserName());
        System.out.println(user2.getUserName());
        System.out.println(user3.getUserName());
    }
}