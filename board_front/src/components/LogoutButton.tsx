import { useUserStore } from '@/stores/user.store';
import React from 'react'
import { useCookies } from 'react-cookie'
import { useNavigate } from 'react-router-dom';

function LogoutButton() {
  //& === HOOK === //
  const [_, __, removeCookie] = useCookies(["accessToken"]);
  const setLogout = useUserStore((state) => state.setLogout);
  const navigate = useNavigate();

  const onLogout = () => {
    removeCookie("accessToken", { path: '/' });
    setLogout(); // 전역 상태 초기화
    alert('로그아웃 되었습니다.');
    navigate('/auth/sign-in');
  }

  return (
    <button onClick={onLogout} style={{
      padding: '10px',
      marginTop: '10px'
    }}>
      로그아웃
    </button>
  )
}

export default LogoutButton