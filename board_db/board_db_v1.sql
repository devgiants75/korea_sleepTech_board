CREATE DATABASE IF NOT EXISTS project_board_db
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_unicode_ci;

USE project_board_db;

-- 타임존을 UTC로 설정 (한국이면 Asia/Seoul도 가능)
SET time_zone = '+00:00';

-- 1. 역할 테이블
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. 카테고리 테이블
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. 채팅방 테이블
CREATE TABLE chat_rooms (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. 채팅 메시지 (채팅방 참조)
CREATE TABLE chat_messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id BIGINT,
    sender VARCHAR(100),
    message TEXT,
    message_type VARCHAR(10) NOT NULL,
    CONSTRAINT chk_message_type CHECK (message_type IN ('JOIN', 'TALK', 'LEAVE')),
    FOREIGN KEY (room_id) REFERENCES chat_rooms(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. users 테이블 (upload_files는 나중 생성 → profile_image_id는 제약 없이 nullable 설정)
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL,
    role_id BIGINT NOT NULL,
    profile_image_id BIGINT, -- FK 나중에 추가
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT chk_user_status CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    FOREIGN KEY (role_id) REFERENCES roles(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. 게시글 테이블
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    author_id BIGINT,
    category_id BIGINT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. 댓글 테이블
CREATE TABLE comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    author_id BIGINT,
    post_id BIGINT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (post_id) REFERENCES posts(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. 업로드 파일 테이블 (이 시점에는 users, posts, chat_messages가 모두 생성됨)
CREATE TABLE upload_files (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_name VARCHAR(255),
    file_name VARCHAR(255),
    file_path VARCHAR(255),
    file_type VARCHAR(50),
    file_size BIGINT,
    target_type VARCHAR(20) NOT NULL,
    post_id BIGINT,
    chat_message_id BIGINT,
    CONSTRAINT chk_upload_target_type CHECK (target_type IN ('POST', 'CHAT', 'PROFILE')),
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE SET NULL,
    FOREIGN KEY (chat_message_id) REFERENCES chat_messages(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 9. users.profile_image_id FK 추가 (지금은 upload_files 테이블이 존재하므로 FK 가능)
ALTER TABLE users
ADD CONSTRAINT fk_users_profile_image
FOREIGN KEY (profile_image_id) REFERENCES upload_files(id);

-- 10. 사용자 변경 로그
CREATE TABLE user_change_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    email VARCHAR(100),
    prev_status VARCHAR(20),
    new_status VARCHAR(20),
    prev_role VARCHAR(50),
    new_role VARCHAR(50),
    changed_by VARCHAR(100),
    change_reason VARCHAR(255),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 11. 게시글 변경 로그
CREATE TABLE post_change_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    prev_title VARCHAR(255),
    new_title VARCHAR(255),
    prev_content TEXT,
    new_content TEXT,
    prev_category VARCHAR(50),
    new_category VARCHAR(50),
    changed_by VARCHAR(100),
    change_reason VARCHAR(255),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES posts(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 관리자/사용자 역할 추가
INSERT INTO roles (role_name) VALUES ('ADMIN'), ('USER');

-- 기본 카테고리 추가
INSERT INTO categories (category_name) VALUES ('공지사항'), ('자유게시판'), ('Q&A');