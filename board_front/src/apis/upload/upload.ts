// uploadApi.ts
import { AxiosError } from "axios";
import { axiosInstance, responseErrorHandler, responseSuccessHandler } from "../axiosConfig";
import ResponseDto from "@/dtos/response.dto";

export const uploadFile = async (formData: FormData, uploadUrl: string): Promise<ResponseDto<string>> => {
  try {
    const response = await axiosInstance.post(uploadUrl, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      }
    });

    return responseSuccessHandler(response);
  } catch (error) {
    return responseErrorHandler(error as AxiosError<ResponseDto>);
  }
}

// await uploadFile(formData, UPLOAD_PROFILE_URL)
// await uploadFile(formData, UPLOAD_BOARD_IMAGE_URL)