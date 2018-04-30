package me.zuhr.demo.rocketmq.common;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zurun
 * @date 2018/3/19 00:59:39
 */
public class AbstractRocketMqConsumerTest {

    public static void main(String[] args){
            Set set=new HashSet();
            set.add("sss");
            set.add(1);
            set.add("xxx");
        System.out.println(StringUtils.join(set, " || "));
    }
}