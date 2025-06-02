package com.project.board_back.service;

import com.project.board_back.dto.chat.ChatMessageDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ChatFileService {
    /**
     * 채팅용 파일 저장 후 ChatMessageDto 생성
     */
    ChatMessageDto saveChatFile(MultipartFile file, Long roomId, String sender) throws IOException;
}
