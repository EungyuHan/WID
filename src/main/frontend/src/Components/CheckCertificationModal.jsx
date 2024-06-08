import styled from 'styled-components';
import React, { useState } from 'react';
import Button from './Button';

function CheckCertificationModal(props) {
  

  return (
    <Modals>
      <ModalContent>
        <h2 style={{ color: 'white' }}>귀하가 인증하고자 하는 내역입니다.</h2>
        <InformDiv>
          <InformItem>
            <InformText>
              {/* 과목명: {props.selectedRequest[0].subject} */}
            </InformText>
          </InformItem>
          <InformItem>
            <InformText>
              {/* 제목: {props.selectedRequest[0].sender} */}
            </InformText>
          </InformItem>
          <InformItem>
            <InformText>
              {/* 신청인: {props.selectedRequest[0].date} */}
            </InformText>
          </InformItem>
          <InformItem>
            <InformText>
              {/* 일자: {props.selectedRequest[0].date} */}
            </InformText>
          </InformItem>
        </InformDiv>
        <div>
          <h4>상기의 내용을 인증하시겠습니까?</h4>
        </div>
        <Button onClick = {props.confirmCheck} name="인증하기"/>
      </ModalContent>99
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
  height: 50vh;
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
