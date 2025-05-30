import { ResponseDto } from "@/dtos/response";
import { axiosInstance, bearerAuthorization, responseErrorHandler, responseSuccessHandler } from "./axiosConfig";
import { AxiosError } from "axios";
import { DELETE_BOARD_URL, GET_BOARD_URL, GET_MY_BOARD_URL, POST_BOARD_URL, PUT_BOARD_URL } from "./constants";

// 생성(CREATE)
export const postBoard = async (dto: PostBoardRequestDto, accessToken: string): Promise<ResponseDto<BoardResponseDto>> => {
  try {
    const response = await axiosInstance.post(POST_BOARD_URL, dto, bearerAuthorization(accessToken));
    return responseSuccessHandler(response);
  } catch (error) {
    return responseErrorHandler(error as AxiosError<ResponseDto>); 
  }
}

// 조회(READ) - 나의 게시글 전체 조회
export const getMyBoard = async (accessToken: string): Promise<ResponseDto<BoardResponseDto[]>> => {
  try {
    const response = await axiosInstance.get(GET_MY_BOARD_URL, bearerAuthorization(accessToken));
    return responseSuccessHandler(response);
  } catch (error) {
    return responseErrorHandler(error as AxiosError<ResponseDto>); 
  }
}

// 조회(READ) - 게시글 단건 조회
export const getBoard = async (boardId: number): Promise<ResponseDto<BoardResponseDto>> => {
  try {
    const response = await axiosInstance.get(GET_BOARD_URL(boardId));
    return responseSuccessHandler(response);
  } catch (error) {
    return responseErrorHandler(error as AxiosError<ResponseDto>); 
  }
}

// 수정(Update)
export const updateBoard = async (boardId: number, dto: UpdateBoardRequestDto, accessToken: string): Promise<ResponseDto<BoardResponseDto>> => {
  try {
    const response = await axiosInstance.put(PUT_BOARD_URL(boardId), dto, bearerAuthorization(accessToken));
    return responseSuccessHandler(response);
  } catch (error) {
    return responseErrorHandler(error as AxiosError<ResponseDto>); 
  }
}

// 삭제(Delete)
export const deleteBoard = async (boardId: number, accessToken: string): Promise<ResponseDto<null>> => {
  try {
    const response = await axiosInstance.delete(DELETE_BOARD_URL(boardId), bearerAuthorization(accessToken));
    return responseSuccessHandler(response);
  } catch (error) {
    return responseErrorHandler(error as AxiosError<ResponseDto>); 
  }
}

