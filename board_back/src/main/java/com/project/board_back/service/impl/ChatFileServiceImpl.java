package com.project.board_back.service.impl;

import com.project.board_back.dto.chat.ChatMessageDto;
import com.project.board_back.service.ChatFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ChatFileServiceImpl implements ChatFileService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public ChatMessageDto saveChatFile(MultipartFile file, Long roomId, String sender) throws IOException {
        // 업로드 디렉토리 생성
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        // 원본 및 UUID 파일명 생성
        String original = file.getOriginalFilename();
        String uuidName = UUID.randomUUID() + "_" + original;
        String fullPath = uploadDir + "/" + uuidName;
        file.transferTo(new File(fullPath));

        // 클라이언트 전송용 DTO 생성
        String fileUrl = "/files/" + uuidName;
        return new ChatMessageDto(
                ChatMessageDto.MessageType.FILE,
                roomId,
                sender,
                "[파일] " + original,
                original,
                fileUrl
        );
    }
}
