package com.project.board_back.redis;

import com.project.board_back.dto.chat.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

/**
 * Redis Pub 역할 - 메시지를 발행
 */
@Component
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;

    public void publish(ChatMessageDto message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}

