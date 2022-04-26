package com.example.websocket.config;

import com.example.websocket.model.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
                                                                       RedisSubscriber redisSubscriber) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(redisSubscriber, createTopic());

        return redisMessageListenerContainer;
    }

    @Bean
    public RedisTemplate<String, ChatMessage> redisTemplate(RedisConnectionFactory redisConnectionFactory, ObjectMapper objectMapper) {
        Jackson2JsonRedisSerializer<ChatMessage> serializer = new Jackson2JsonRedisSerializer<>(ChatMessage.class);
        RedisTemplate redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(serializer);

        return redisTemplate;
    }

    private Topic createTopic() {
        return new PatternTopic("/topic/**");
    }

}
