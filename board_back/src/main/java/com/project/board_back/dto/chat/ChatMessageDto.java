package com.project.board_back.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 채팅 메시지 전송에 사용되는 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {

    public enum MessageType {
        JOIN, TALK, LEAVE, FILE
    }

    private MessageType messageType;
    private Long roomId;
    private String sender;
    private String message;

    private String fileName; // FILE 메시지일 경우 원본 파일명
    private String fileUrl;  // FILE 메시지일 경우 접근 가능한 URL
}