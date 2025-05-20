export const tmp = '';

//# ===== API 관련 기능을 관리하는 폴더 ===== //

// 스프링부트(백엔드)의 응답 구조
// : ResponseEntity<ResponseDto<T>> 형식으로 반환

/*
  ? ResponseDto<T> 구조
  public class ResponseDto<T> {
    private boolean result; // 성공 여부
    private String message; // 응답 메시지
    private T data; // 실제 응답 데이터
  }
*/ 

//& 1. URL 상수 정의: constants.ts 파일 생성
//& 2. Axios 인스턴스 및 공통 설정: axiosConfig.ts