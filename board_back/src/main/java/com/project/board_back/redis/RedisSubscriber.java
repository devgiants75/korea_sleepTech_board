package com.project.board_back.redis;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.board_back.dto.chat.ChatMessageDto;
import com.project.board_back.service.ChatService;
import com.project.board_back.session.WebSocketSessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Redis Sub 역할 - 메시지를 수신하고 세션에 전달
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final WebSocketSessionManager sessionManager;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // Redis에서 수신한 JSON 문자열을 ChatMessageDto로 역직렬화
            ChatMessageDto dto = objectMapper.readValue(message.getBody(), ChatMessageDto.class);

            // DB에 메시지 저장
            chatService.saveMessage(dto);

            // 연결된 WebSocket 세션들에게 전송
            sessionManager.sendToRoom(dto.getRoomId(), dto);

        } catch (Exception e) {
            log.error("Redis 메시지 처리 실패", e);
        }
    }
}
