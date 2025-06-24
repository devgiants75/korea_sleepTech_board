package com.project.board_back.entity;

import com.project.board_back.common.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username; // 사용자 로그인 ID

    @Column(nullable = false, unique = true, length = 100)
    private String email; // 이메일

    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_image_id")
    private UploadFile profileImage;

    @Builder
    public User(String username, String email, String password, UserStatus status, Role role, UploadFile profileImage) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = status;
        this.role = role;
        this.profileImage = profileImage;
    }
}
