package com.project.board_back.service;

import com.project.board_back.dto.ResponseDto;
import com.project.board_back.dto.auth.request.PasswordResetRequestDto;
import com.project.board_back.dto.auth.request.UserSignInRequestDto;
import com.project.board_back.dto.auth.request.UserSignUpRequestDto;
import com.project.board_back.dto.auth.response.UserSignInResponseDto;
import com.project.board_back.dto.auth.response.UserSignUpResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface AuthService {
    ResponseDto<UserSignUpResponseDto> signup(@Valid UserSignUpRequestDto dto);
    ResponseDto<UserSignInResponseDto> login(@Valid UserSignInRequestDto dto);
    Mono<ResponseEntity<String>> resetPassword(@Valid PasswordResetRequestDto dto);
}
