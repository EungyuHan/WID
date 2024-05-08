import Button from '../Components/Button';
import React,{ useState } from 'react';
import styled,{keyframes} from 'styled-components';
import Waves from '../Components/Waves';

function UserCreateVC() {
    return(
        <BackGround>
            <div div style={{ zIndex: 1 }}>
                
                    <LogoContainer>
                    <img src='img/logo.png' width={`80px`} height={`80px`} alt='Logo'></img>
                    </LogoContainer>
                    <NavContainer>
                        
                    </NavContainer>
                    
               
            <ContentContainer></ContentContainer>










            </div>
            <Waves></Waves>
        </BackGround>
    )
}



const BackGround = styled.div`
    position: relative;
    background: linear-gradient(to right, #FFFFFF, #0083b0);
    width: 100%;
    height: 100vh;
    padding = 0px;
    display: flex;
`
const NavContainer = styled.div`
    
`


const ContentContainer = styled.div`
    position: fixed;
    top: 12%;
    left: 50%;
    width: 90%;
    height: 87%;
    background-color: rgba(0, 0, 0, 0.4);
    transform: translate(-50%);
    border-radius: 15px;
`


const LogoContainer = styled.div`
    position: fixed; 
    width: 70%;
    height: 18%;
    left: 5%;
    background-color: transparent;
`


export default UserCreateVC;