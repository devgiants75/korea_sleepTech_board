package com.project.board_back.controller;

import com.project.board_back.dto.ResponseDto;
import com.project.board_back.dto.file.PostRequestDto;
import com.project.board_back.dto.file.PostResponseDto;
import com.project.board_back.service.PostDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostDataService postDataService;

    // 게시글 등록 (파일 업로드 포함)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseDto<PostResponseDto>> createPost(
            @RequestPart("data") @Valid PostRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        ResponseDto<PostResponseDto> response = postDataService.createPost(dto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 목록 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<PostResponseDto>>> getPosts() {
        return ResponseEntity.ok(postDataService.getAllPosts());
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<PostResponseDto>> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postDataService.getPostById(id));
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<PostResponseDto>> updatePost(
            @PathVariable Long id,
            @RequestPart("data") @Valid PostRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        return ResponseEntity.ok(postDataService.updatePost(id, dto, file));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> deletePost(@PathVariable Long id) {
        return ResponseEntity.ok(postDataService.deletePost(id));
    }
}