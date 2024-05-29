import React,{ useState } from 'react';
import styled,{keyframes} from 'styled-components';
import Waves from '../Components/Waves';


function UserSendPage() {


    return (
      <BackGround>
        

        <Waves/>
      </BackGround>
        
      
    );
  }
  

  const BackGround = styled.div`
  position: fixed;
  background: linear-gradient(to right, #FFFFFF, #0083b0);
  width: 100%;
  height: 100vh;
  padding = 0px;
  display: flex;
`

const Button = styled.button`
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
`

const SideNavBar = styled.div`
  position: relative;
  background : black;
  
`

const PreviewDiv = styled.div`
  postion: relative;
  width: 
`




export default UserSendPage;
