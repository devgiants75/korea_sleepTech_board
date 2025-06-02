package com.project.board_back.service;

import com.project.board_back.dto.ResponseDto;
import com.project.board_back.dto.file.PostRequestDto;
import com.project.board_back.dto.file.PostResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostDataService {

    /**
     * 게시글 등록 (파일 업로드 포함)
     */
    ResponseDto<PostResponseDto> createPost(PostRequestDto dto, MultipartFile file) throws IOException;

    /**
     * 전체 게시글 조회
     */
    ResponseDto<List<PostResponseDto>> getAllPosts();

    /**
     * 단건 게시글 조회
     */
    ResponseDto<PostResponseDto> getPostById(Long id);

    /**
     * 게시글 수정 (파일 교체 가능)
     */
    ResponseDto<PostResponseDto> updatePost(Long id, PostRequestDto dto, MultipartFile file) throws IOException;

    /**
     * 게시글 삭제
     */
    ResponseDto<?> deletePost(Long id);
}