// constants.ts
// # URL 상수 정의
//& variable: URL 상수 //

// const API_DOMAIN = 'http://localhost:8080';
const API_DOMAIN = import.meta.env.REACT_APP_API_DOMAIN || "http://localhost:8080";

//! 1. 인증 관련 요청 베이스 URL
const AUTH_MODULE_URL = `${API_DOMAIN}/api/v1/auth`;
export const SIGN_UP_URL = `${AUTH_MODULE_URL}/signup`;
export const SIGN_IN_URL = `${AUTH_MODULE_URL}/login`;

//! 2. 게시글 관련 요청 베이스 URL
const BOARD_MODULE_URL = `${API_DOMAIN}/api/v1/boards`;
// 게시글 생성
export const POST_BOARD_URL = `${BOARD_MODULE_URL}`;
// '나의' 게시글 전체 조회
export const GET_MY_BOARD_URL = `${BOARD_MODULE_URL}/me`;
// 게시글 단건 조회
export const GET_BOARD_URL = (boardId: number | string) => `${BOARD_MODULE_URL}/${boardId}`;
// 게시글 수정
export const PUT_BOARD_URL = (boardId: number | string) => `${BOARD_MODULE_URL}/${boardId}`;
// 게시글 삭제
export const DELETE_BOARD_URL = (boardId: number | string) => `${BOARD_MODULE_URL}/${boardId}`;
// 댓글 생성
export const POST_COMMENT_URL = (boardId: number | string) => `${BOARD_MODULE_URL}/${boardId}/comments`;
// 댓글 조회 (전체: 게시글 ID)
export const GET_COMMENT_URL = (boardId: number | string) => `${BOARD_MODULE_URL}/${boardId}/comments`;

//! 3. 파일 시스템 관련 요청 베이스 URL
const FILE_MODULE_URL = `${API_DOMAIN}/api/v1/files`;
export const UPLOAD_FILE_URL = `${FILE_MODULE_URL}/upload`;