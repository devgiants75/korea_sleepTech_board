package com.project.board_back.controller;

import com.project.board_back.dto.ResponseDto;
import com.project.board_back.dto.chat.ChatMessageDto;
import com.project.board_back.redis.RedisPublisher;
import com.project.board_back.service.ChatFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/chat/files")
@RequiredArgsConstructor
public class ChatFileController {

    private final ChatFileService chatFileService;
    private final RedisPublisher redisPublisher;

    /**
     * 채팅 파일 업로드 및 실시간 전송 처리
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<ChatMessageDto>> uploadChatFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("roomId") Long roomId,
            @RequestParam("sender") String sender
    ) throws IOException {
        ChatMessageDto messageDto = chatFileService.saveChatFile(file, roomId, sender);
        redisPublisher.publish(messageDto); // Redis로 메시지 발행
        return ResponseEntity.ok(ResponseDto.success("파일 전송 성공","", messageDto));
    }
}