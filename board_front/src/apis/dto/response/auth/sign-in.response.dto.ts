// sign-in.response.dto.ts

import { ResponseDto } from '@/apis/dto/response';

export default interface SignInResponseDto extends ResponseDto {
  accessToken: string; // 토큰 정보
  expiration: number; // 만료 시간
}