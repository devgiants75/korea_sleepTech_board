package com.project.board_back.service.impl;


import com.project.board_back.dto.chat.ChatMessageDto;
import com.project.board_back.entity.ChatMessage;
import com.project.board_back.entity.ChatRoom;
import com.project.board_back.repository.ChatMessageRepository;
import com.project.board_back.repository.ChatRoomRepository;
import com.project.board_back.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository messageRepo;
    private final ChatRoomRepository roomRepo;

    @Override
    public void saveMessage(ChatMessageDto dto) {
        ChatMessage msg = new ChatMessage(
                dto.getRoomId(),
                dto.getSender(),
                dto.getMessage(),
                ChatMessage.MessageType.valueOf(dto.getMessageType().name())
        );
        messageRepo.save(msg);
    }

    @Override
    public ChatRoom createRoom(String name) {
        return roomRepo.save(new ChatRoom(name));
    }

    @Override
    public List<ChatRoom> getAllRooms() {
        return roomRepo.findAll();
    }
}
