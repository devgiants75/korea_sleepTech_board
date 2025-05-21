import { create } from "zustand";

interface UserState {
  isLogin: boolean; 
  setLogin: () => void;
  setLogout: () => void;
}

export const useUserStore = create<UserState>((set) => ({
  // 저장소는 객체를 반환
  // : 객체는 ()소괄호에 묶여 반환
  isLogin: false,
  setLogin: () => set({ isLogin: true }),
  setLogout: () => set({ isLogin: false })
}));