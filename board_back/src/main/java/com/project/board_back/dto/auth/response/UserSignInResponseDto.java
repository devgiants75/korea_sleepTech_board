package com.project.board_back.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserSignInResponseDto {
    private String token;
    private Long userId;
    private String email;
    private String role;
    private String profileImageUrl;
}