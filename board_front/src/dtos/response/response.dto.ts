// 공통 응답 타입: ResponseDto //

export default interface ResponseDto<T = any> {
  result: boolean; 
  message: string;
  data?: T; // 응답 데이터의 data는 제네릭 처리
}