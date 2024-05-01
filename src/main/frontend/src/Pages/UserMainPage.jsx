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
            


            <Waves></Waves>
        </BackGround>
    )
}

export default UserMainPage;