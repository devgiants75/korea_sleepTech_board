/** @jsxImportSource @emotion/react */
import React, { useState } from 'react'
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom'
import {
  containerStyle,
  titleStyle,
  inputStyle,
  buttonStyle,
  errorMessageStyle
} from '@/views/auth/auth.style'; // 구조 분해 할당
import { SignInRequestDto } from '@/dtos/request/auth/sign-in.request.dto';
import { signInRequest } from '@/apis';
import { useUserStore } from '@/stores/user.store';

// 클라이언트에서 토큰 저장 (쿠키 사용)
// : react-cookie
// : npm i react-cookie

function SignIn() {
  //& === Hook === //
  const navigate = useNavigate();
  const [cookies, setCookie] = useCookies(["accessToken"]);
  const setLogin = useUserStore((state) => state.setLogin);

  //& === State === //
  const [form, setForm] = useState({
    email: '',
    password: ''
  });

  const [message, setMessage] = useState('');

  //& === Handler === //
  const onInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  }

  const onSignInClick = async () => {
    const { email, password } = form;

    if (!email || !password) {
      setMessage('이메일과 비밀번호를 입력해주세요.');
      return;
    }

    const requestBody: SignInRequestDto = { email, password };
    const response = await signInRequest(requestBody);
    const { result, message, data } = response;

    if (!result || !data) {
      setMessage(message);
      return;
    }

    const { token, exprTime } = data;
    const expireDate = new Date();
    expireDate.setMilliseconds(expireDate.getMilliseconds() + exprTime);

    setCookie("accessToken", token, {
      path: '/',
      expires: expireDate,
      sameSite: 'strict',
    });

    setLogin();

    alert('로그인 성공');
    navigate('/');
  }

  return (
    <div css={containerStyle}>
      <h2 css={titleStyle}>로그인</h2>
      <input 
        type="email"
        name='email'
        value={form.email}
        placeholder='이메일'
        onChange={onInputChange}
        css={inputStyle} 
      />
      <input 
        type="password"
        name='password'
        value={form.password}
        placeholder='비밀번호'
        onChange={onInputChange}
        css={inputStyle} 
      />
      <button onClick={onSignInClick} css={buttonStyle}>
        로그인
      </button>
      {message && <p css={errorMessageStyle}>{message}</p>}
    </div>
  )
}

export default SignIn