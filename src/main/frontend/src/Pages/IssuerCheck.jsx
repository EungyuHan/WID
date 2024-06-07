import Button from '../Components/Button';
import React,{ useState } from 'react';
import styled,{keyframes} from 'styled-components';
import Waves from '../Components/Waves';
import CheckPrivateModal from '../Components/CheckPrivateModal';

import {Link} from 'react-router-dom';
import { pdfjs } from 'react-pdf';
import PDFpreviewer from '../Components/PDFpreviewer';
pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;


function IssuerCheck() {
    const [PKchecked, setPKchecked] = useState(false);
    const handleDownload = () => {
        // PDF 파일 다운로드 로직 구현
        // 예: window.open(pdfUrl, '_blank');
      };
    

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
                    <FormH4>신청인  <FormInput value='qwer1216914@gmail.com'/></FormH4>
                    <FormH4>학  교  <FormInput value='전북대학교'/></FormH4>
                    <FormH4>전  공 <FormInput value='소프트웨어공학과'/></FormH4>
                    <FormH4>과  목 <FormInput value='소프트웨어공학캡스톤프로젝트'/></FormH4>
                    <FormH4>요청대상 <FormInput value='김순태 교수님'/></FormH4>
                    <FormH4>작업기간 <FormInput value='2023.03 ~ 2023.12'/></FormH4>
                </FormContainer>
    
                <DescriptionContainer>
                    <FormH4>설명</FormH4>
                    <textarea value="소프트웨어공학캡스톤 프로젝트 과목에서 팀프로젝트로 논문을 작성하였습니다" />
                </DescriptionContainer>
               
                
                <RightContainer>
                <div style={{ textAlign: 'right' }}>
                  <Button onClick={()=>{setPKchecked(true)}} name="인증하기"/>
                </div>
                <PreviewContainer>
                <RightTopContainer>
                <FormH4>증명서번호 <FormInput value='12255'/></FormH4>
                <button onClick={handleDownload}>다운로드</button>  
                </RightTopContainer>  
                    <PDFpreviewer></PDFpreviewer>
                </PreviewContainer>
                </RightContainer>
                
            </ContentContainer>
            { PKchecked && (<CheckPrivateModal></CheckPrivateModal>)}
            </div>
            <Waves></Waves>
        </BackGround>
   
        
    )
}
const BackGround = styled.div`
    position: fixed;
    background: linear-gradient(to right, #FFFFFF, #0083b0);
    width: 100%;
    height: 100vh;
    padding: 0px;
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

const RightContainer = styled.div`
    position: absolute;
    width: 45%;
    height: 100%;
    right: 4%;
    background-color: transparent;
    `

const RightTopContainer = styled.div`
  display: flex;
  justify-content: left;
  align-items: center;
  margin-right: 250px;
`
const PreviewContainer = styled.div`
    position: relative;
    top: 1%;
    height: 90%;
    overflow: auto;
    border-radius: 10px;
    background-color: gray;
`







export default IssuerCheck;