import { Route, Routes } from 'react-router-dom'
import './App.css'
import SignUp from './views/auth/SignUp'
import SignIn from './views/auth/SignIn'
import { useEffect } from 'react'
import { useCookies } from 'react-cookie'
import { useUserStore } from './stores/user.store'
import LogoutButton from './components/LogoutButton'
import FileUpload from './views/file/FileUpload'

//! 프로젝트 기초 환경 설정
// 1. 외부 라이브러리 설치 (의존성 설치)
// : axios
// : react-router-dom
// : zustand
// >> npm i + 각 라이브러리명 사용

// 2. 공통 상수 공통 함수, 공통 타입 명시

// 3. root 경로의 index.tsx에서 BrowserRouter 등록

// 4. 폴더 구조 생성
// assets, apis(ts), components(tsx), constants(ts), layouts(tsx)
// , stores(ts), styles(ts), types(ts), views(tsx)

// : ts - export const tmp = '';
// : tsx - rfce(함수형 컴포넌트 생성)

function App() {
  //& === HOOK === //
  const [cookies ] = useCookies(["accessToken"]);

  // (state) => state.setLogin
  // : Zustand 내부의 상태(속성1, 메서드2)에서 setLogin만 꺼내옴 
  const setLogin = useUserStore((state) => state.setLogin); 
  const isLogin = useUserStore((state) => state.isLogin);

  useEffect(() => {
    const accessToken = cookies.accessToken;
    if (accessToken) {
      setLogin();
    }
  }, [cookies]);

  return (
    <>
      <header>
        {isLogin ? (
          <>
            <span>환영합니다!</span>
            <LogoutButton />
          </>
        ) : (
          <>
            <a href="/auth/sign-up">회원가입</a>
            <a href="/auth/sign-in">로그인</a>
          </>
        )}
      </header>
      <FileUpload />
      <Routes>
        <Route path='/auth/sign-up' element={<SignUp />} />
        <Route path='/auth/sign-in' element={<SignIn />} />
      </Routes>
    </>
  )
}

export default App
