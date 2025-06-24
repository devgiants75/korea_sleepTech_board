package com.project.board_back.service.impl;

import com.project.board_back.common.constants.ResponseMessage;
import com.project.board_back.dto.ResponseDto;
import com.project.board_back.dto.file.PostRequestDto;
import com.project.board_back.dto.file.PostResponseDto;
import com.project.board_back.entity.Post;
import com.project.board_back.entity.UploadFile;
import com.project.board_back.repository.PostDataRepository;
import com.project.board_back.repository.UploadFileRepository;
import com.project.board_back.service.PostDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostDataServiceImpl implements PostDataService {

    private final PostDataRepository postRepo;
    private final UploadFileRepository fileRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    @Transactional
    public ResponseDto<PostResponseDto> createPost(PostRequestDto dto, MultipartFile file) throws IOException {
        // 게시글 저장
        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        post = postRepo.save(post);

        // 첨부파일 저장
        if (file != null && !file.isEmpty()) {
            saveFile(file, post.getId(), "POST");
        }

        PostResponseDto data = toDto(post);
        return ResponseDto.success(ResponseMessage.SUCCESS, "", data);
    }

    @Override
    public ResponseDto<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> list = postRepo.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseDto.success(ResponseMessage.SUCCESS, "", list);
    }

    @Override
    public ResponseDto<PostResponseDto> getPostById(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return ResponseDto.success(ResponseMessage.SUCCESS, "", toDto(post));
    }

    @Override
    @Transactional
    public ResponseDto<PostResponseDto> updatePost(Long id, PostRequestDto dto, MultipartFile file) throws IOException {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        postRepo.save(post);

        // 기존 파일 삭제
        List<UploadFile> existing = fileRepo.findByTargetTypeAndPost_Id("POST", id);
        for (UploadFile uf : existing) {
            new File(uploadDir + "/" + uf.getFileName()).delete();
            fileRepo.delete(uf);
        }
        // 새 파일 저장
        if (file != null && !file.isEmpty()) {
            saveFile(file, id, "POST");
        }

        return ResponseDto.success(ResponseMessage.SUCCESS, "", toDto(post));
    }

    @Override
    @Transactional
    public ResponseDto<?> deletePost(Long id) {
        // 게시글 삭제 전 파일 삭제
        List<UploadFile> files = fileRepo.findByTargetTypeAndPost_Id("POST", id);
        for (UploadFile uf : files) {
            new File(uploadDir + "/" + uf.getFileName()).delete();
            fileRepo.delete(uf);
        }
        postRepo.deleteById(id);
        return ResponseDto.success(ResponseMessage.SUCCESS, null);
    }

    /** Post → DTO 변환 */
    private PostResponseDto toDto(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt().format(FORMAT))
                .build();
    }

    /** 실제 파일 저장 및 메타데이터 기록 */
    private void saveFile(MultipartFile file, Long targetId, String type) throws IOException {
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String original = file.getOriginalFilename();
        String uuid = UUID.randomUUID() + "_" + original;
        file.transferTo(new File(uploadDir + "/" + uuid));

        UploadFile uf = UploadFile.builder()
                .originalName(original)
                .fileName(uuid)
                .filePath("/files/" + uuid)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
//                .targetType(UploadFile.TargetType.valueOf(type)) // -=========== 원래 (type)
                .targetType(UploadFile.builder().build().getTargetType())
                .build();
        fileRepo.save(uf);
    }
}