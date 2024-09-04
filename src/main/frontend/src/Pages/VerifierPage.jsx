import React, { useState , useEffect } from 'react';
import styled from 'styled-components';
import axios from 'axios';
import Waves from '../Components/Waves';
import VPverifiyModal from '../Components/VPverifiyModal';

function VerifierPage() {
    const [mail, setMail] = useState(null);
    const [data, setData] = useState(null);
    const [isModalOpen , setModal] = useState(false);
    
    const mails = [
        { id: 1, sender: 'qwer1216914@gmail.com', subject: '[소프트웨어공학캡스톤프로젝트] 소프트웨어공학과 프로젝트 인증 요청합니다.', date: '2022-05-01' },
    ];
    
    let 샘플데이터 = { "id" : 1, "내용": "오라 달콤한 죽음이여"};

    useEffect(()=> {
        //서버로부터 신청된 mail들 axios로 가져오기;
        
    })
    
    const getModalContent = (보내야하는것) => {
        // 해당 ID에서 보낸 내용을 모두 불러오기 위한 Axios 코드 
        
        setData(보내야하는것);
        modalOpenClose();
        
    }

    const modalOpenClose = () => {
        setModal(!isModalOpen);
        console.log(isModalOpen);
    }

    const MailList = (props) => {
            return (
            <MailListContainer>
                {props.mails.map((mail) => (
                <MailItem key={mail.id} onClick={()=> {getModalContent(샘플데이터)}}>
                    <MailInfo>
                        <Sender>{mail.sender}</Sender>
                    </MailInfo>
                    <MailInfo>
                        <Subject>{mail.subject}</Subject>
                    </MailInfo>
                    <MailInfo>
                        <Date>{mail.date}</Date>
                    </MailInfo>
                </MailItem>
                )
                )}
            </MailListContainer>
            )
        };
    


        return (
            <BackGround>
                <div style={{ zIndex: 1 }}>
                <LogoBar>
                    <img src='img/logo.png' width={`102px`} height={`81px`} alt='Logo'></img>
                </LogoBar>
                <UpNavBar>
                    <UpNavBarTop>
                    <UpNavButton>자사 신규 이력서 내역 열람</UpNavButton>
                    
                    <div style={{ display: 'flex', justifyContent: 'flex-end', alignItems: 'center', marginRight: '80px', flex: 1 }}>
                    </div>
                    </UpNavBarTop>
                    <Text>현재 <span style={{ borderBottom: '1px solid black' }}>정승 네트워크</span>에 신청된 신청자 리스트입니다.</Text>
                    <UpNavBarBottom>
            
                <Container>
                <Text> <span style={{ borderBottom: '1px solid white', color: 'white' }}>전체 요청 <span style={{ color: '#cf242a' }}>{mails.length}</span></span></Text>
                <div style={{ display: 'flex', justifyContent: 'flex-end', marginRight: '20px', marginTop: '20px' }}>
                    </div>
                    <ContentsContainer>
                        <InformationDiv>
                        
                        <MailList mails={mails} />
                        
                        </InformationDiv>
                    </ContentsContainer>
                    
                    </Container>
                    </UpNavBarBottom>
                </UpNavBar>
                </div>
            { isModalOpen && <VPverifiyModal onClose = {modalOpenClose} data = {data} />}
            <Waves/>
            </BackGround>
        );
    
}



const BackGround = styled.div`
    position: fixed;
    background: linear-gradient(to right, #FFFFFF, #0083b0);
    width: 100%;
    height: 100vh;
    display: flex;
    background-attachment: fixed;
`


const Text = styled.div`
    margin-top: 30px;
    margin-left: 50px;
    font-size: 18px;
    font-weight: bold;

`

const LogoBar = styled.div`
    position: relative;
    width: auto;
    height: 10%;
    background-color: transparent;
    margin: 20px;
    margin-left: 10px;
`

const UpNavBar = styled.div`
    position:fixed;
    width: 80%;
    height: 100%;
    background-color: transparent;
    right: 0;
    top: 0;
`

const UpNavBarTop = styled.div`
    position:relative;
    top: 0; 
    width: 100%;
    height: 10%;
    background-color: transparent;
    display: flex;
    border-bottom: 3px solid black;
    right: 0;
`

const UpNavBarBottom = styled.div`
    position:fixed;
    width: 100%;
    height: 70%;
    background-color: transparent;
    display: flex;
    flex-direction: column;
`

const UpNavButton = styled.button`
    position: relative;
    width: 25%;
    height: 100%; 
    border: none; 
    box-shadow: none;
    background-color: transparent;
    color: #383838;
    text-align: center;
    font-size: 22px;
    font-family: 'Arial', sans-serif;
    transition: background-color 1s ease;
    box-shadow: none;
    &:hover {
        background-color: #FFFFFF;
        color:black;
    } 
    
` 

const Container = styled.div`
    position: relative;
    top: 5%;
    left: 2%;
    background-color: #383838;
    width: 75%;
    height: 100%;
    border-radius: 5px;
    `
const ContentsContainer = styled.div`
    position: relative;
    background-color: rgba(0, 0, 0, 0.25);
    transform: translate(-50%);
    top:5%;
    left: 50%;
    width:100%;
    height:60%;
    justify-content: center;
    border-radius: 10px;
    overflow: auto;
`

const InformationDiv = styled.div`
    width: 100%;
    height: auto;
    background-color: transparent;
    margin: auto;
    overflow: hidden;
    border-radius: 5px;
    margin-top:10px;
    margin-bottom: 10px;
`;

const MailListContainer = styled.div`
    width: 100%;
    display: grid;
    grid-template-columns: auto;
    grid-gap: 10px;
`;

const MailItem = styled.div`
    display: flex; 
        align-items: flex-start;
        grid-template-columns: auto auto auto auto;
        gap: 10px;
    position:auto;
    width: 100%;
    height: 100%;
    background-color: transparent;
    border-bottom: 1px solid white;
    &:hover {
        background-color: #ffffff40;
        color: black;
    }
`;

const MailInfo = styled.div`
    white-space: nowrap;
    overflow: hidden; 
    text-overflow: ellipsis;
`;


const Subject = styled.div`
    margin: 0;
    font-size: 14px;
    font-weight: bold;
    color: white;
    white-space: nowrap;
    overflow: hidden; 
    text-overflow: ellipsis;
    width: 500px; 
`;

const Date = styled.div`
    margin: 0;
    margin-left: 60px;
    font-size: 14px;
    color: white;
`;

const Sender = styled.div`
    margin: 0;
    font-size: 14px;
    color: white;
    margin-right: 5px;
    width: 200px; 
`;

export default VerifierPage;