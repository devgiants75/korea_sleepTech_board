package com.project.board_back.repository;

import com.project.board_back.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    List<UploadFile> findByTargetTypeAndPost_Id(String targetType, Long postId);
    List<UploadFile> findByTargetTypeAndChatMessage_Id(String targetType, Long chatMessageId);
    // List<UploadFile> findByTargetTypeAndPost_Id(UploadFile.TargetType targetType, Long postId);
}