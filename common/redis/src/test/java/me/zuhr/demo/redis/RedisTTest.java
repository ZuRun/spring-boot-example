package me.zuhr.demo.redis;

import me.zuhr.demo.redis.utils.RedisUtils;
import me.zuhr.demo.redis.vo.ClassVo;
import me.zuhr.demo.redis.vo.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zurun
 * @date 2018/2/11 16:31:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedisApplicationTest.class)
public class RedisTTest {

    @Autowired
    RedisUtils redisUtils;

//    private StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    RedisConfig redisConfig;
    @Test
    public void config() {
        System.out.println("-------setValue--------");
        redisUtils.jackson2.set("string","-string");
        redisUtils.jackson2.set("int",999);
        redisUtils.jackson2.set("double",12.340);
        redisUtils.jackson2.set("long",999999999999999L);
        redisUtils.jackson2.set("boolean",false);
        System.out.println(redisUtils.jackson2.get("string"));
        System.out.println(redisUtils.jackson2.get("int"));
        System.out.println(redisUtils.jackson2.get("double"));
        System.out.println(redisUtils.jackson2.get("long"));
        System.out.println(redisUtils.jackson2.get("boolean"));
        System.out.println("---------------");
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
            classVo.setNum(11111111);
            vo.addClass(classVo);
        }


        System.out.println(vo);
        System.out.println("---------------");

        redisUtils.jackson2.hashSet(key,vo);
        UserVo newVo= (UserVo) redisUtils.jackson2.hashGet(key,UserVo.class);
        newVo.getClassVo().setNum(1);
        System.out.println(newVo);

        System.out.println("---------------");

        int age= (int) redisUtils.jackson2.hashGet(key,"age");
        System.out.println(age);

        System.out.println("---------------");

        String name= (String) redisUtils.jackson2.hashGet(key,"name");
        System.out.println(name);

        System.out.println("---------------");

        ClassVo newClassVo= (ClassVo) redisUtils.jackson2.hashGet(key,"classVo",ClassVo.class);
        System.out.println(newClassVo);

//
//        redisUtils.set(key,vo);
//        UserVo newVo= (UserVo) redisUtils.get(key,UserVo.class);
//        newVo.getClassVo().setNum(1);
//        System.out.println(newVo);

    }
}
