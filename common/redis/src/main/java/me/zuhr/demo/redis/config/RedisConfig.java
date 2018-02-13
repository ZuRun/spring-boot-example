package me.zuhr.demo.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.zuhr.demo.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author zurun
 * @date 2018/2/9 17:51:33
 */
//@Configuration
//@ConfigurationProperties(prefix = "spring.redis")
//@PropertySource("classpath:redis.properties")
//@EnableCaching
@EnableConfigurationProperties
@Configuration
@PropertySource(value = "classpath:/config/redis.properties")
//@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;


    /**
     * 连接redis的工厂类
     *
     * @return
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
//        factory.setTimeout(timeout);
        factory.setPassword(password);
//        factory.setDatabase(database);
        return factory;
    }

    /**
     * 配置RedisTemplate
     * 设置添加序列化器
     * key 使用string序列化器
     * value 使用Json序列化器
     * //TODO-zurun 还有一种简答的设置方式，改变defaultSerializer对象的实现。
     *
     * @return
     */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate() {
//        //StringRedisTemplate的构造方法中默认设置了stringSerializer
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        //set key serializer
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        template.setKeySerializer(stringRedisSerializer);
//        template.setHashKeySerializer(stringRedisSerializer);
//
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//        //set value serializer
//        template.setDefaultSerializer(jackson2JsonRedisSerializer);
//
//        template.setConnectionFactory(jedisConnectionFactory());
//        template.afterPropertiesSet();
//        return template;
//    }
    @Bean
    RedisTemplate<String, Object> objRedisTemplate(@Qualifier("jedisConnectionFactory") JedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setDefaultSerializer(new Jackson2JsonRedisSerializer(Object.class));
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
//        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        return redisTemplate;
    }


    /**
     * 注:Qualifier注解用来指定bean,消除重复bean提示
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RedisUtils redisUtils(@Qualifier("jedisConnectionFactory") JedisConnectionFactory connectionFactory) {
        return new RedisUtils(connectionFactory);
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
