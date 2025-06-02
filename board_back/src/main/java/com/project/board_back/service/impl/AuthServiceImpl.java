package com.project.board_back.service.impl;

import com.project.board_back.dto.ResponseDto;
import com.project.board_back.dto.auth.request.PasswordResetRequestDto;
import com.project.board_back.dto.auth.request.UserSignInRequestDto;
import com.project.board_back.dto.auth.request.UserSignUpRequestDto;
import com.project.board_back.dto.auth.response.UserSignInResponseDto;
import com.project.board_back.dto.auth.response.UserSignUpResponseDto;
import com.project.board_back.entity.Role;
import com.project.board_back.entity.User;
import com.project.board_back.repository.RoleRepository;
import com.project.board_back.repository.UserRepository;
import com.project.board_back.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.expiration}")
    private String jwtExpirationMs;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final Map<String, String> verificationTokens = new ConcurrentHashMap<>();

    private AuthenticationManager authenticationManager;

    @Lazy
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseDto<UserSignUpResponseDto> signup(UserSignUpRequestDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseDto.fail("", "이미 사용 중인 이메일입니다.");
        }

        Role userRole = roleRepository.findByRoleName("USER")
                .orElseGet(() -> roleRepository.save(Role.builder().roleName("USER").build()));

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        userRepository.save(user);

        UserSignUpResponseDto data = UserSignUpResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .build();
        return ResponseDto.success("회원가입 성공", "", data);
    }

    @Override
    public ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        User user = (User) auth.getPrincipal();

        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(jwtExpirationMs)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();

        UserSignInResponseDto data = UserSignInResponseDto.builder()
                .token(token)
                .email(user.getEmail())
                .build();
        return ResponseDto.success("로그인 성공", "", data);
    }

    @Override
    public Mono<ResponseEntity<String>> resetPassword(PasswordResetRequestDto dto) {
        return Mono.fromSupplier(() -> userRepository.findByEmail(dto.getEmail())
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(dto.getEmail()));
                    userRepository.save(user);
                    return ResponseEntity.ok("비밀번호 재설정 완료");
                })
                .orElseGet(() -> ResponseEntity.badRequest().body("존재하지 않는 이메일입니다.")));
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}
