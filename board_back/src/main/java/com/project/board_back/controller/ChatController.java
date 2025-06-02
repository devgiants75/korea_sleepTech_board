package com.project.board_back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {

    // 현재는 WebSocket 연결이 클라이언트에서 직접 이루어지고
    // WebSocket 핸들러(WebSocketChatHandler)에서 처리되므로
    // ChatController는 채팅방 목록 조회나 REST 기반 확장용으로 사용 가능
}
