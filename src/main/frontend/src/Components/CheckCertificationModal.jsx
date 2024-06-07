import styled, { keyframes } from 'styled-components';
import React, { useState } from 'react';
import Button from './Button';
import { Link } from 'react-router-dom';
import CheckPrivateModal from '../Components/CheckPrivateModal';

function CheckCertificationModal(props) {
  const [checkedItems, setCheckedItems] = useState([true, true, true, true]);

  const handleCheckboxChange = (index) => {
    const newCheckedItems = [...checkedItems];
    newCheckedItems[index] = !newCheckedItems[index];
    setCheckedItems(newCheckedItems);
  };

  const [PKchecked, setPKchecked] = useState(false);
  
  
  return (
    <Modals>
      <ModalContent>
        <h2 style={{ color: 'white' }}>귀하가 인증하고자 하는 내역입니다.</h2>
        <InformDiv>
          <InformItem>
            <input
              type="checkbox"
              checked={checkedItems[0]}
              onChange={() => handleCheckboxChange(0)}
            />
            <InformText>
              신청인: 이용준, 인증명: 소프트웨어공학캡스톤프로젝트(블록체인 네트워크를 활용한 활동내역 증명 및 관리 시스템)
            </InformText>
          </InformItem>
          <InformItem>
            <input
              type="checkbox"
              checked={checkedItems[1]}
              onChange={() => handleCheckboxChange(1)}
            />
            <InformText>
              신청인: 김서연, 인증명: 데이터베이스(자동차 유지비 관리 시스템)
            </InformText>
          </InformItem>
          <InformItem>
            <input
              type="checkbox"
              checked={checkedItems[2]}
              onChange={() => handleCheckboxChange(2)}
            />
            <InformText>
              신청인: 남준성, 인증명: 학부연구생
            </InformText>
          </InformItem>
          <InformItem>
            <input
              type="checkbox"
              checked={checkedItems[3]}
              onChange={() => handleCheckboxChange(3)}
            />
            <InformText>
              신청인: 손영빈, 인증명: AM:PM 학술동아리(해커톤 대회)
            </InformText>
          </InformItem>
        </InformDiv>
        <div>
          <h4>상기의 내용을 인증하시겠습니까?</h4>
        </div>
        <Button onClick={()=>{setPKchecked(true)}} name="인증하기"/>
      </ModalContent>
      { PKchecked && (<CheckPrivateModal onClose={props.onClose}/>)}
      
    </Modals>
  );
}

const Modals = styled.div`
  width: 100%;
  height: 100%;
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 2;
`;

const ModalContent = styled.div`
  position: relative;
  top: 10%;
  display: block;
  width: 50%;
  height: 70%;
  padding: 40px;
  margin: auto;
  text-align: center;
  background: linear-gradient(to top, #ffffff, #0083b0);
  border-radius: 10px;
  box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);
`;

const InformDiv = styled.div`
  position: relative;
  width: 80%;
  height: 50%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: left;
  margin: auto;
  text-align: left;
  background-color: white;
  border-radius: 10px;
`;

const InformItem = styled.div`
  display: flex;
  align-items: center;
  margin: 10px;
  border-bottom: 2px solid black;
`;

const InformText = styled.h4`
  margin: 0 10px;
  text-align: left;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

`;

export default CheckCertificationModal;
