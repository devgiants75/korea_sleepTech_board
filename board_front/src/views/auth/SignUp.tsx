/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { signUpRequest } from '@/apis';
import { SignUpRequestDto } from '@/dtos/request/auth/sign-up.request.dto';1
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';

//* === Style === //
const containerStyle = css`
  max-width: 400px;
  margin: 60px auto;
  padding: 40px;
  border: 1px solid #e0e0e0;
  border-radius: 16px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.06);
  background-color: #ffffff;
`;

const titleStyle = css`
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 30px;
`;

const inputStyle = css`
  width: 100%;
  padding: 12px;
  margin-bottom: 16px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-sizing: border-box;
  transition: border 0.3s;
  &:focus {
    outline: none;
    border-color: #4caf50;
  }
`;

const buttonStyle = css`
  width: 100%;
  padding: 12px;
  font-size: 16px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  color: white;
  background-color: #4caf50;
  transition: background-color 0.3s;
  &:hover {
    background-color: #43a047;
  }
`;

const errorMessageStyle = css`
  color: red;
  text-align: center;
  margin-top: 10px;
  font-size: 14px;
`;

function SignUp() { // rfce: 함수형 컴포넌트 생성
  //& === Hook === //
  const navigate = useNavigate();

  //& === State === //
  const [form, setForm] = useState({
    email: "",
    password: "",
    passwordCheck: ""
  });

  const [message, setMessage] = useState('');

  //& === Handler === //
  const onInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  }

  const onSignUpClick = async () => {
    const { email, password, passwordCheck } = form;

    if (!email || !password || !passwordCheck) {
      setMessage('모든 항목을 입력해주세요.');
      return;
    }

    if (password !== passwordCheck) {
      setMessage('비밀번호가 일치하지 않습니다.');
      return;
    }

    const requestBody: SignUpRequestDto = {
      email,
      password,
      confirmPassword: passwordCheck
    };

    const response = await signUpRequest(requestBody);
    const { result, message } = response;

    if (!result) {
      // 실패 응답 반환의 경우
      setMessage(message);
      return;
    }

    alert("회원가입을 성공하였습니다.");
    navigate('/auth/sign-in');
  }

  return (
    <div css={containerStyle}>
      <h2 css={titleStyle}>회원가입</h2>
      <input 
        type="email"
        placeholder='이메일'
        name='email'
        value={form.email}
        onChange={onInputChange} 
        css={inputStyle}
      />
      <input 
        type="password"
        placeholder='비밀번호'
        name='password'
        value={form.password}
        onChange={onInputChange} 
        css={inputStyle}
      />
      <input 
        type="password"
        placeholder='비밀번호 확인'
        name='passwordCheck'
        value={form.passwordCheck}
        onChange={onInputChange} 
        css={inputStyle}
      />
      <button onClick={onSignUpClick} css={buttonStyle}>
        회원가입
      </button>
      {message && <p css={errorMessageStyle}>{message}</p>}
    </div>
  )
}

export default SignUp