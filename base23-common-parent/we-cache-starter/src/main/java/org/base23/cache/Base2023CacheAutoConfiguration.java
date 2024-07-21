package org.base23.cache;

import java.time.Duration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class Base2023CacheAutoConfiguration {

  /**
   * 使用Redis作为缓存数据库
   */
  @Bean
  @ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis") // 只有在配置文件指定是redis才创建redis相关的Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofHours(1)) // 设置缓存的过期时间
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

    // 使用一个冒号进行拼接RedisKey前缀。默认是使用两个冒号，太难看了！
    config = config.computePrefixWith(cacheName -> cacheName + ":");

    return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config).build();
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(connectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    return template;
  }

  // 如果没有配置没有Redis时使用Caffeine Cache
  @Bean
  @ConditionalOnProperty(name = "spring.cache.type", havingValue = "caffeine", matchIfMissing = true)
  public CaffeineCacheManager caffeineCacheManager() {
    return new CaffeineCacheManager();
  }

}
