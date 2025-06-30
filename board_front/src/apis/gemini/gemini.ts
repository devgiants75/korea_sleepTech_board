import ResponseDto from '@/dtos/response.dto';
import { AxiosError } from 'axios';
import { axiosInstance, bearerAuthorization, responseErrorHandler, responseSuccessHandler } from '../axiosConfig';


// Gemini API 요청 함수
export const askGemini = async (
  input: string,
  accessToken: string
): Promise<ResponseDto<string>> => {
  try {
    const response = await axiosInstance.post(
      '/api/gemini/ask',
      { input },
      bearerAuthorization(accessToken)
    );
    return responseSuccessHandler<string>(response);
  } catch (error) {
    return responseErrorHandler(error as AxiosError<ResponseDto>);
  }
};