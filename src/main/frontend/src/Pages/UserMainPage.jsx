import styled,{keyframes} from 'styled-components';
import React,{ useState } from 'react';
import Button from '../Components/Button';
import Waves from '../Components/Waves';


const BackGround = styled.div`
    position: relative;
    background: linear-gradient(to right, #FFFFFF, #0083b0);
    width: 100%;
    height: 100vh;
    padding = 0px;
    display: flex;
    justify-content: center;
    align-items: center;
`


function UserMainPage(){
    return(
        <BackGround>
            <h1>메인페이지 입니다.</h1>
            <Waves></Waves>
            <button>글자</button>
        </BackGround>
        
    )
}

export default UserMainPage;