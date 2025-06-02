package com.project.board_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "post_change_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostChangeLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    private String prevTitle;
    private String newTitle;

    @Lob
    private String prevContent;
    @Lob
    private String newContent;

    private String prevCategory;
    private String newCategory;

    private String changedBy;
    private String changeReason;

    @Builder
    public PostChangeLog(Long postId, String prevTitle, String newTitle,
                         String prevContent, String newContent,
                         String prevCategory, String newCategory,
                         String changedBy, String changeReason) {
        this.postId = postId;
        this.prevTitle = prevTitle;
        this.newTitle = newTitle;
        this.prevContent = prevContent;
        this.newContent = newContent;
        this.prevCategory = prevCategory;
        this.newCategory = newCategory;
        this.changedBy = changedBy;
        this.changeReason = changeReason;
    }
}

