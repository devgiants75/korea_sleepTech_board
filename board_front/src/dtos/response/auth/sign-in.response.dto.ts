// sign-in.response.dto.ts

interface User {
  id: bigint;
  email: string;
}

export default interface SignInResponseDto {
  token: string; // 토큰 정보
  user: User;
  exprTime: number; // 만료 시간
}