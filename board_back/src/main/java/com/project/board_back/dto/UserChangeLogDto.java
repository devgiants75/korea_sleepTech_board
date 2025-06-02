package com.project.board_back.dto;

import lombok.Builder;
import lombok.Getter;

// controller에서 쓰이지 않는 DTO
@Getter
@Builder
public class UserChangeLogDto {
    private Long userId;
    private String email;
    private String prevStatus;
    private String newStatus;
    private String prevRole;
    private String newRole;
    private String changedBy;      // 관리자의 email 또는 ID
    private String changeReason;   // 예: "관리자 승인"
}

//@Transactional
//public void approveUser(Long userId, String adminEmail) {
//    User user = userRepository.findById(userId)
//            .orElseThrow(() -> new RuntimeException("사용자 없음"));
//
//    String prevStatus = user.getStatus().name();
//    String prevRole = user.getRole().getRoleName();
//
//    // 상태 변경
//    user.updateStatus(UserStatus.APPROVED);
//
//    // 변경 이력 저장
//    UserChangeLogDto logDto = UserChangeLogDto.builder()
//            .userId(user.getId())
//            .email(user.getEmail())
//            .prevStatus(prevStatus)
//            .newStatus(user.getStatus().name())
//            .prevRole(prevRole)
//            .newRole(prevRole) // 역할 안 바꿨다면 동일하게
//            .changedBy(adminEmail)
//            .changeReason("관리자가 승인함")
//            .build();
//
//    UserChangeLog changeLog = UserChangeLog.builder()
//            .userId(logDto.getUserId())
//            .email(logDto.getEmail())
//            .prevStatus(logDto.getPrevStatus())
//            .newStatus(logDto.getNewStatus())
//            .prevRole(logDto.getPrevRole())
//            .newRole(logDto.getNewRole())
//            .changedBy(logDto.getChangedBy())
//            .changeReason(logDto.getChangeReason())
//            .build();
//
//    userChangeLogRepository.save(changeLog);
//}
