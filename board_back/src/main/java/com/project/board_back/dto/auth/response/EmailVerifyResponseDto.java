package com.project.board_back.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailVerifyResponseDto {
    private boolean verified;
    private String message;
}
