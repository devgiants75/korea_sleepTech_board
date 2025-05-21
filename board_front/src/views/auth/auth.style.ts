// auth.style.ts
/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';

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

export { containerStyle, titleStyle, inputStyle, buttonStyle, errorMessageStyle };