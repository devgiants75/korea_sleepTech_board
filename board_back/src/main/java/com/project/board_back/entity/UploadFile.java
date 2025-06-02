package com.project.board_back.entity;

import com.project.board_back.common.enums.TargetType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "upload_files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalName;
    private String fileName;
    private String filePath;
    private String fileType;
    private long fileSize;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id")
    private ChatMessage chatMessage;

    @Builder
    public UploadFile(String originalName, String fileName, String filePath,
                      String fileType, long fileSize, TargetType targetType,
                      Post post, ChatMessage chatMessage) {
        this.originalName = originalName;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.targetType = targetType;
        this.post = post;
        this.chatMessage = chatMessage;
    }

    public void assignToPost(Post post) {
        this.post = post;
        post.getImages().add(this);
    }
}
