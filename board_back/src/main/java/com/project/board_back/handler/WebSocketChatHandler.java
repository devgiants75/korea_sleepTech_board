package com.project.board_back.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.board_back.dto.chat.ChatMessageDto;
import com.project.board_back.redis.RedisPublisher;
import com.project.board_back.repository.ChatRoomRepository;
import com.project.board_back.session.WebSocketSessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final RedisPublisher redisPublisher;
    private final WebSocketSessionManager sessionManager;
    private final ChatRoomRepository chatRoomRepository;

    /**
     * 클라이언트가 WebSocket 연결을 맺으면 호출됨
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("✅ WebSocket 연결 성공: " + session.getId());
        Long roomId = getRoomId(session);
        sessionManager.registerSession(roomId, session);
    }

    /**
     * 클라이언트가 WebSocket 연결을 끊으면 호출됨
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessionManager.unregisterSession(session);
    }

    /**
     * 클라이언트로부터 텍스트 메시지를 수신하면 호출됨
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessageDto chatMessage = objectMapper.readValue(message.getPayload(), ChatMessageDto.class);
        // 1. 방 ID 유효성 확인
        if (!chatRoomRepository.existsById(chatMessage.getRoomId())) {
            // 방이 없으면 그냥 무시하거나, 에러 메시지 전송
            session.sendMessage(new TextMessage("❌ 존재하지 않는 채팅방입니다."));
            return;
        }

        // 2. Redis 발행
        redisPublisher.publish(chatMessage);
    }

    /**
     * WebSocket 요청 URL에서 roomId 파라미터 추출
     * 예: ws://localhost:8080/ws/chat?roomId=1
     */
    private Long getRoomId(WebSocketSession session) {
        String uri = String.valueOf(session.getUri());
        if (uri.contains("roomId=")) {
            return Long.parseLong(uri.substring(uri.indexOf("roomId=") + 7));
        }
        return -1L;
    }
}

