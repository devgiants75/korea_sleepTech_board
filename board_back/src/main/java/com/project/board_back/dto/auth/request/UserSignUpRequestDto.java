package com.project.board_back.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserSignUpRequestDto {
    @NotBlank(message = "아이디는 필수입니다.")
    @Size(min = 6, message = "아이디는 최소 6자 이상이어야 합니다.")
    private String username; // 로그인 ID

    @Email(message = "이메일 형식을 확인해주세요.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    private Long profileImageId; // 프로필 이미지 선택 시 사용 (선택적)
}
