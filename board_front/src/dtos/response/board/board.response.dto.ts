// board.response.dto.ts

export interface BoardResponseDto {
  id: number;
  title: string;
  content: string;
  author: string;
  createdAt: string; 
  updatedAt?: string; 
  //# JS는 일반적으로 날짜(DATE)를 string 형태로 응답받는 것이 표준
  // : JSON 표준 내에 날짜 타입이 없음 
  // >> string으로 전달받은 날짜를 new Date()를 사용하여 변환

  // 서버(백엔드) > 프론트엔드 전달: 문자열
}