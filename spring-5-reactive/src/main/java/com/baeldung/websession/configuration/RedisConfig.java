package com.baeldung.websession.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

// https://1upnote.me/post/2018/06/install-config-redis-on-mac-homebrew/
// brew update
// brew upgrade
// brew install redis
// brew services start redis
// brew services stop redis
// If you don’t want/need a background service you can just run: redis-server
// vim /usr/local/etc/redis.conf
// And start Redis with the configuration file as: redis-server /usr/local/etc/redis.conf
// Test if Redis is running: redis-cli ping

@Configuration
@EnableRedisWebSession
public class RedisConfig {

    @Value("${spring.redis.host}")
    String redisHost;

    @Value("${spring.redis.port:6379}")
    Integer redisPort;

    @Value("${spring.redis.password}")
    String redisPassword;

    @Value("${spring.redis.database}")
    Integer redisDatabase;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisStandaloneConfiguration());
    }

    @Bean
    RedisConnectionFactory redisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration)
    {
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    ReactiveRedisConnection reactiveRedisConnection(final ReactiveRedisConnectionFactory reactiveRedisConnectionFactory)
    {
        return reactiveRedisConnectionFactory.getReactiveConnection();
    }

    @Bean
    RedisStandaloneConfiguration redisStandaloneConfiguration()
    {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(redisDatabase);
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisPassword));

        return redisStandaloneConfiguration;
    }

}