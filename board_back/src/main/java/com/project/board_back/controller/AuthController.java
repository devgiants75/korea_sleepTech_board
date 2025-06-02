package com.project.board_back.controller;

import com.project.board_back.common.constants.ApiMappingPattern;
import com.project.board_back.dto.ResponseDto;
import com.project.board_back.dto.auth.request.EmailSendRequestDto;
import com.project.board_back.dto.auth.request.PasswordResetRequestDto;
import com.project.board_back.dto.auth.request.UserSignInRequestDto;
import com.project.board_back.dto.auth.request.UserSignUpRequestDto;
import com.project.board_back.dto.auth.response.UserSignInResponseDto;
import com.project.board_back.dto.auth.response.UserSignUpResponseDto;
import com.project.board_back.service.AuthService;
import com.project.board_back.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiMappingPattern.AUTH_API)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MailService mailService;

    private static final String POST_SIGN_UP = "/signup";
    private static final String POST_SIGN_IN = "/login";

    // 1) 회원가입
    @PostMapping(POST_SIGN_UP)
    public ResponseEntity<ResponseDto<UserSignUpResponseDto>> signup(@Valid @RequestBody UserSignUpRequestDto dto) {
        ResponseDto<UserSignUpResponseDto> response = authService.signup(dto);
        return ResponseDto.toResponseEntity(HttpStatus.CREATED, response);
    }

    // 2) 로그인
    @PostMapping(POST_SIGN_IN)
    public ResponseEntity<ResponseDto<UserSignInResponseDto>> login(@Valid @RequestBody UserSignInRequestDto dto) {
        ResponseDto<UserSignInResponseDto> response = authService.login(dto);
        return ResponseDto.toResponseEntity(HttpStatus.OK, response);
    }

    @PostMapping("/send-email")
    public Mono<ResponseEntity<String>> sendEmail(@Valid @RequestBody EmailSendRequestDto dto) {
        return mailService.sendSimpleMessage(dto.getEmail());
    }

    @GetMapping("/verify")
    public Mono<ResponseEntity<String>> verifyEmail(@RequestParam String token) {
        return mailService.verifyEmail(token);
    }

    @PostMapping("/reset-password")
    public Mono<ResponseEntity<String>> resetPassword(@Valid @RequestBody PasswordResetRequestDto dto) {
        return authService.resetPassword(dto);
    }
}