package com.project.board_back.repository;

import com.project.board_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);       // 이메일 중복 체크용
    Optional<User> findByUsername(String username); // 로그인용
}