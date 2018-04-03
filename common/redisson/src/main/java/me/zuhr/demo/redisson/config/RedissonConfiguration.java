package me.zuhr.demo.redisson.config;

import me.zuhr.demo.redisson.lock.DistributedLockTemplate;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author ZuRun
 * @date 2018/03/31 12:03:59
 */
@Configuration
public class RedissonConfiguration {
//    @Value("classpath:/redisson-conf.yml")
//    Resource configFile;

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;

    /**
     * 是否启用集群
     */
    @Value("${spring.redis.iscluster}")
    private Boolean isluster;
    /**
     * Redis 集群的节点
     */
    @Value("${spring.redis.cluster.nodes}")
    private List<String> nodesList;

    /**
     * 单机redis
     *
     * @return
     */
    @Bean
    public RedissonClient redissonSingle() {
        Config config = new Config();


        if (isluster) {
            // redis集群模式
            ClusterServersConfig clusterServersConfig = config.useClusterServers();
            for (String nodes : nodesList) {
                String[] node = nodes.split(",");
                for (int i = 0, length = node.length; i < length; i++) {
                    clusterServersConfig.addNodeAddress("redis://".concat(node[i]));
                }

            }
        } else {
            // 单机模式
            config.useSingleServer().setAddress("redis://".concat(host.concat(":") + port));
        }
        return Redisson.create(config);
    }

    @Bean
    public DistributedLockTemplate distributedLockTemplate(@Qualifier("redissonSingle") RedissonClient redissonSingle) {
        return new DistributedLockTemplate(redissonSingle);
    }
}
