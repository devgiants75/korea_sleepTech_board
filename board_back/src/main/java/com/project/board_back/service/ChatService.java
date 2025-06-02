package com.project.board_back.service;

import com.project.board_back.dto.chat.ChatMessageDto;
import com.project.board_back.entity.ChatRoom;

import java.util.List;

public interface ChatService {

    /**
     * 채팅 메시지 저장
     */
    void saveMessage(ChatMessageDto dto);

    /**
     * 채팅방 생성
     */
    ChatRoom createRoom(String name);

    /**
     * 전체 채팅방 조회
     */
    List<ChatRoom> getAllRooms();
}