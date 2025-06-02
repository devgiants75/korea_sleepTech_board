package com.project.board_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_change_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserChangeLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // 변경된 사용자

    private String email; // 변경 당시 이메일
    private String prevStatus;
    private String newStatus;
    private String prevRole;
    private String newRole;

    private String changedBy;
    private String changeReason;

    @Builder
    public UserChangeLog(Long userId, String email, String prevStatus, String newStatus,
                         String prevRole, String newRole, String changedBy, String changeReason) {
        this.userId = userId;
        this.email = email;
        this.prevStatus = prevStatus;
        this.newStatus = newStatus;
        this.prevRole = prevRole;
        this.newRole = newRole;
        this.changedBy = changedBy;
        this.changeReason = changeReason;
    }
}
