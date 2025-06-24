import React, { ChangeEvent, useState } from "react";
import DaumPostcodeEmbed, { Address, State } from "react-daum-postcode";

//# react-daum-postcode
// 설치: npm install react-daum-postcode

function PostCode() {
  const [zonecode, setZonecode] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [detailedAddress, setDetailedAddress] = useState<string>("");
  const [isOpen, setIsOpen] = useState<boolean>(false);

  const themeObj = {
    bgColor: "", // 바탕 배경색
    searchBgColor: "", // 검색창 배경색
    contentBgColor: "", // 본문 배경색
    pageBgColor: "", // 페이지 배경색
    textColor: "", // 기본 글자색
    queryTextColor: "", // 검색창 글자색
    postcodeTextColor: "#FA7142", // 우편번호 글자색
    emphTextColor: "#ff0404", // 강조 글자색
    outlineColor: "", // 테두리
  };

  const postCodeStyle = {
    width: "400px",
    height: "500px",
    border: "1.4px solid #333333",
  };

  const completeHandler = (data: Address) => {
    const { address, zonecode } = data;
    setZonecode(zonecode);
    setAddress(address);
    setIsOpen(false); // 완료되면 창 닫기
  };

  const closeHandler = (state: State) => {
    if (state === "FORCE_CLOSE" || state === "COMPLETE_CLOSE") {
      setIsOpen(false);
    }
  };

  const toggleHandler = () => {
    setIsOpen((prev) => !prev);
  };

  const inputChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
    setDetailedAddress(event.target.value);
  };

  return (
    <div>
      <div>
        <strong>주소</strong>
      </div>
      <div>
        <div>
          <div>{zonecode}</div>
          <button type="button" onClick={toggleHandler}>
            주소 찾기
          </button>
        </div>
        {isOpen && (
          <div>
            <DaumPostcodeEmbed
              theme={themeObj}
              style={postCodeStyle}
              onComplete={completeHandler}
              onClose={closeHandler}
            />
          </div>
        )}
        <div>{address}</div>
        <input
          type="text"
          placeholder="상세 주소 입력"
          value={detailedAddress}
          onChange={inputChangeHandler}
        />
      </div>
    </div>
  );
}

export default PostCode;
