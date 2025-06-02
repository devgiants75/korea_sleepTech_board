package com.project.board_back.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserSignUpResponseDto {
    private Long userId;
    private String email;
    private String status; // 예: PENDING
    private String role;   // 예: USER
    private String profileImageUrl; // null 허용
}