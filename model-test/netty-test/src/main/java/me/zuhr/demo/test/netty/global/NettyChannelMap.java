package me.zuhr.demo.test.netty.global;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zurun
 * @date 2018/6/5 10:13:11
 */
public class NettyChannelMap {

    private static Map<String, Channel> map = new ConcurrentHashMap<String, Channel>();

    public static void add(String clientId, Channel channel) {
        map.put(clientId, channel);

    }

    public static Channel get(String clientId) {
        return map.get(clientId);
    }

    public static Map getAll() {
        return map;
    }

    public static boolean remove(Channel channel) {
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getValue().equals(channel)) {
                map.remove(entry.getKey());
                return true;
            }
        }
        return false;
    }
}
