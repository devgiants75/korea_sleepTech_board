//# 폴더명은 소문자 사용 권장 + 이어지는 단어는 -(하이픈)으로 연결
// post-board.request.dto.ts

export interface PostBoardRequestDto {
  title: string;
  content: string;
}