/** @jsxImportSource @emotion/react */
import { signUpRequest } from '@/apis';
import { SignUpRequestDto } from '@/dtos/request/auth/sign-up.request.dto';1
import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import * as authStyle from '@/views/auth/auth.style';

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
    <div css={authStyle.containerStyle}>
      <h2 css={authStyle.titleStyle}>회원가입</h2>
      <input 
        type="email"
        placeholder='이메일'
        name='email'
        value={form.email}
        onChange={onInputChange} 
        css={authStyle.inputStyle}
      />
      <input 
        type="password"
        placeholder='비밀번호'
        name='password'
        value={form.password}
        onChange={onInputChange} 
        css={authStyle.inputStyle}
      />
      <input 
        type="password"
        placeholder='비밀번호 확인'
        name='passwordCheck'
        value={form.passwordCheck}
        onChange={onInputChange} 
        css={authStyle.inputStyle}
      />
      <button onClick={onSignUpClick} css={authStyle.buttonStyle}>
        회원가입
      </button>
      {message && <p css={authStyle.errorMessageStyle}>{message}</p>}
    </div>
  )
}

export default SignUp