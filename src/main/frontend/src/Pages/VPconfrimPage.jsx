import React, { useState , useEffect } from 'react';
import styled,{keyframes} from 'styled-components';
import axios from 'axios';
import Waves from '../Components/Waves';

function VPconfirmPage() {
    // 회사측에서는 회사측으로 넘어온 VP 목록을 확인하고 해당 VP에 대한 정보를 볼 수 있어야 한다.
    // 회사측 웹페이지는 되도록 일단 간단하게 작업해두자.
    return(
        <BackGround>
            

            <Waves/>
        </BackGround>
    )
}



const BackGround = styled.div`
position: fixed;
background: linear-gradient(to right, #FFFFFF, #0083b0);
width: 100%;
height: 100vh;
padding = 0px;
display: flex;
`

export default VPconfirmPage;