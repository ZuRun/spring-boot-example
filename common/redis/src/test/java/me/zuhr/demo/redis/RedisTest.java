package me.zuhr.demo.redis;

import me.zuhr.demo.redis.enuma.SexEnum;
import me.zuhr.demo.redis.utils.RedisUtils;
import me.zuhr.demo.redis.vo.ClassVo;
import me.zuhr.demo.redis.vo.UserVo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author zurun
 * @date 2018/2/22 18:06:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedisApplicationTest.class)
public class RedisTest {
    @Autowired
    RedisUtils redisUtils;

    private final Long TIMEOUT_SECOND = 10L;

    /**
     * 测试保存对象,使用get和set方法
     *
     * @throws IOException
     */
    @Test
    public void getSetTest() {
        String key = "userVo";
        UserVo userVo = createUserVo();
        go("get一个没有的key", () -> redisUtils.get("------===!!"));

        go("OLD", () -> userVo);

        // 写入缓存
        redisUtils.set(key, userVo, TIMEOUT_SECOND);

        // 通过get方法获取对象
        go("get", () -> {
            Object object = redisUtils.get(key);
            return object;
        });

        // 通过get方法获取对象并且反序列化为对象
        UserVo newUserVo = (UserVo) redisUtils.get(key, UserVo.class);
        go("newUserVo", () -> newUserVo);

        // userVo中虽然classVo和list中的classVo是同一个对象,但是反序列化后是不同的对象
        go("改变classVo", () -> {
            newUserVo.getClassVo().setNum(11);
            return newUserVo;
        });

        // 直接获取字符串
        go("getString", () -> redisUtils.getString(key));

        // getAndSet
        go("getAndSet", () -> {
            userVo.setAge(26);
            userVo.setSexEnum(SexEnum.MAN);
            return redisUtils.getAndSet(key, userVo, TIMEOUT_SECOND);
        });

        // 检查getAndSet是否生效
        go("getString", () -> redisUtils.get(key));

    }

    /**
     * 对象使用hash存储
     */
    @Test
    public void hashTest() {
        String key = "userVo2";
        UserVo userVo = createUserVo();
        go("OLD", () -> userVo);

        // 写入缓存
        redisUtils.hashSet(key, userVo, TIMEOUT_SECOND);
        go("获取name", () -> redisUtils.hashGet(key, "name"));
        go("获取对象", () -> redisUtils.hashGet(key, UserVo.class));
        go("获取map对象", () -> redisUtils.hashGet(key));
        go("hashKey数量", () -> redisUtils.hashSize(key));
        go("是否有某个Key", () -> redisUtils.hasKey(key));
        go("是否有某个hashKey", () -> redisUtils.hasKey(key,"age"));
        go("是否有某个hashKey", () -> redisUtils.hasKey(key,"age2"));
        go("hashKey集合", () -> redisUtils.hashKeys(key));
        go("hash value集合", () -> redisUtils.hashValues(key));
    }

    /**
     * 输出的内容
     *
     * @param name
     * @param todo
     */
    private void go(String name, MyTodo todo) {
        System.out.println("\r\n" + name.toUpperCase() + " :");
        System.out.println(todo.execute());
    }

    private interface MyTodo {
        Object execute();
    }


    private UserVo createUserVo() {
        UserVo userVo = new UserVo();
        userVo.setAge(25);
        userVo.setName("姓名name");
        userVo.setAmount(999988888777776555L);
        userVo.setWeight(99.0911);
        userVo.setMoney(BigDecimal.valueOf(500000000.11));
        userVo.setSexEnum(SexEnum.WOMAN);

        ClassVo classVo = new ClassVo();
        classVo.setName("课程A");
        classVo.setNum(12);
        userVo.setClassVo(classVo);

        userVo.addClass(classVo);
        userVo.addClass(classVo);
        return userVo;
    }

    @Before
    public void start() {
        System.out.println("\r\n==== ==== ==== START ==== ==== ====\r\n");
    }

    @After
    public void end() {
        System.out.println("\r\n==== ==== ==== END ==== ==== ====\r\n");
    }

}
