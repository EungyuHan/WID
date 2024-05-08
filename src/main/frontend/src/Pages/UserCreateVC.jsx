import Button from '../Components/Button';
import React,{ useState } from 'react';
import styled,{keyframes} from 'styled-components';
import Waves from '../Components/Waves';
import {Link} from 'react-router-dom';
import { Document, Page } from 'react-pdf';

function UserCreateVC() {
    return(
        <BackGround>
            <div div style={{ zIndex: 1 }}>
                
                    <LogoContainer>
                    <img src='img/logo.png' width={`80px`} height={`80px`} alt='Logo'></img>
                    </LogoContainer>
                    <HomeContainer>
                    <Link to="/MainPage">
                        <img src='img/Home.png' width={`60px`} height={`60px`} alt='Logo'></img>
                        </Link>
                    </HomeContainer>
                    <HelpContainer>
                        <Link to="/Help">
                        <img src='img/Help.png' width={`75x`} height={`75px`} alt='Logo'></img>
                        </Link>
                    </HelpContainer>
                    
            
            <ContentContainer>
                <FormContainer>
                    <FormH4>신청인  <FormInput></FormInput></FormH4>
                    <FormH4>학교    <FormInput></FormInput></FormH4>
                    <FormH4>전공 <FormInput></FormInput></FormH4>
                    <FormH4>과목 <FormInput></FormInput></FormH4>
                    <FormH4>요청대상 <FormInput></FormInput></FormH4>
                    <FormH4>신청대상 <FormInput></FormInput></FormH4>
                    <FormH4>작업기간 <FormInput></FormInput></FormH4>
                </FormContainer>
    
                <DescriptionContainer>
                    <FormH4>설명</FormH4>
                    <textarea size={200}></textarea>
                </DescriptionContainer>
                
                <PreviewContainer>
                <Button name={"제출하기"}></Button>
                    미리보기 창 ddddddddddddddd
                </PreviewContainer>
                
            </ContentContainer>










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
const LogoContainer = styled.div`
    position: fixed; 
    width: 60%;
    height: 12%;
    left: 5%;
    background-color: transparent;
`

const HomeContainer = styled.div`
    position: fixed;
    width: 10%;
    height: 12%;
    top:1%;
    right:15%;
`
const HelpContainer = styled.div`
    position: fixed;
    width: 10%;
    height: 12%;
    right:5%;
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
const FormContainer = styled.div`
    position: absolute;
    top: 2%;
    left: 4%;
    width: 45%;
    height: 65%;
`

const FormH4 = styled.h4`
    size: 5;
    margin: auto;
    padding: 5px 10px;
    color: white;
`

const FormInput = styled.input`
    padding: 6px 25px;
    margin: 2px 2px;
    border-radius: 5px;
    border: none;
`

const DescriptionContainer = styled.div`
    position: absolute;
    width: 45%;
    top: 67%;
    left: 4%;
`

const PreviewContainer = styled.div`
    position: absolute;
    top: 2%;
    right: 4%;
    width: 45%
    height: 80%;
    background-color: white;
`





export default UserCreateVC;