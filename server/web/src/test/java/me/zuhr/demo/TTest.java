package me.zuhr.demo;

import me.zuhr.demo.redis.config.RedisConfig;
import me.zuhr.demo.redis.utils.RedisUtils;
import me.zuhr.demo.vo.ClassVo;
import me.zuhr.demo.vo.UserVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zurun
 * @date 2018/2/11 16:31:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class TTest {

    @Autowired
    RedisUtils redisUtils;

//    private StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    RedisConfig redisConfig;
    @Test
    public void d(){
        redisUtils.set("t1","-----------");
        System.out.println(redisUtils.get("t1"));
        redisUtils.get("test");
        System.out.println(redisUtils.get("test"));
// 保存字符串
//        stringRedisTemplate.opsForValue().set("aaa", "111");
//        System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
//        System.out.println(stringRedisTemplate.opsForValue().get("test"));
//        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));

    }

    @Test
    public void config() throws IOException {
        String key="userVo";
        UserVo vo=new UserVo();
        vo.setAge(25);
        vo.setName("小祖");
        vo.setWeight(71.1);
        vo.setAmount(12345678902345678L);
        vo.setMoney(BigDecimal.valueOf(123456123456123456.123321));
        ClassVo classVo=new ClassVo();
        classVo.setName("课程A");
        classVo.setNum(12);
        vo.setClassVo(classVo);
        for(int i=0;i<3;i++){
            Map map=new HashMap();
            map.put("name","name_"+i);
            map.put("xx","xx_"+i);
            vo.addList(map);
            classVo.setNum(99);
            vo.addClass(classVo);
        }


        System.out.println(vo);
        System.out.println("---------------");


        redisUtils.set(key,vo);
        UserVo newVo= (UserVo) redisUtils.get(key,UserVo.class);
        newVo.getClassVo().setNum(1);
        System.out.println(newVo);

    }
}
