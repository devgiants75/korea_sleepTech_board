// sign-in.response.dto.ts

export default interface SignInResponseDto {
  accessToken: string; // 토큰 정보
  expiration: number; // 만료 시간
}