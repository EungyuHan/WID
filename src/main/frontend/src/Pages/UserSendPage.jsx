import React,{ useState } from 'react';
import styled,{keyframes} from 'styled-components';
import Waves from '../Components/Waves';


function UserSendPage() {
    //유저 전송페이지에서는 전송하고자하는 회사를 선택하고 보낼 수 있어야한다.
    //되도록 간단하게 작업해두자
    //이 파트 그냥 일단 모달창으로 작업해두는게 나을 것 같다.

    return (
      <Modals>


      </Modals>
    );
  }
  

  const Modals = styled.div`
  width: 100%;
  height: 100%;
  position: absolute;
  display: block;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.4);
  z-index:2;
`








export default UserSendPage;
