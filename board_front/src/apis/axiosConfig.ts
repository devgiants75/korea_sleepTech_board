//# Axios 인스턴스 및 공통 설정

import axios, { AxiosError, AxiosResponse } from "axios";
import { ResponseDto } from "./dto/response";

// Axios 인스턴스 생성
export const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_DOMAIN || "http://localhost:8080",
});

// 공통 응답 처리
// 1) 성공
export const responseSuccessHandler = <T = any>(response: AxiosResponse<ResponseDto>) => {
  return response.data;
}

// 2) 실패
export const responseErrorHandler = (error: AxiosError<ResponseDto>) => {
  if (!error.response) return { result: false, message: '네트워크 오류' };
  return error.response.data;
}

//& function: Authorization Bearer 헤더 //
// >> 인증이 필요한 모든 요청에 JWT access token을 붙이기 위해 사용
const bearerAuthorization = (accessToken: string) => ({
  headers: { 'Authorization': `Bearer ${accessToken}` }
}); // 객체 단일 반환 불가: 소괄호로 감싸서 전달

//? EX) axios.get(URL, bearerAuthorization(token));