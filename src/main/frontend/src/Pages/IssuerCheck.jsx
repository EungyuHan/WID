import Button from '../Components/Button';
import React,{ useState , useEffect } from 'react';
import styled from 'styled-components';
import axios from 'axios';
import Waves from '../Components/Waves';
import CheckCertificationModal from '../Components/CheckCertificationModal';
import {Link , useLocation} from 'react-router-dom';
import { pdfjs } from 'react-pdf';
import PDFpreviewer from '../Components/PDFpreviewer';
import ConfirmCertificationModal from '../Components/ConfirmCertificationModal';
pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.js`;


function IssuerCheck() {
    const location = useLocation();
    const [isCheckClicked, setCheck] = useState(false); 
    const [isConfirmClicked, setConfirm] = useState(false);

    const data = location.state;


    const handleDownload = () => {
        // PDF 파일 다운로드 로직 구현
        // 예: window.open(pdfUrl, '_blank');
        console.log(location.state);
    };

    const confirmVC = () => {
        console.log(typeof(data.id));
        const formData = {certificateId: data.id};
        axios.post('http://localhost:8080/certificate/issuer/sign', formData ,{
            headers: {
                Authorization: `${localStorage.getItem('authToken')}`,
            }
        })
    }


    const confirmCheck = () => {
        setConfirm(!isConfirmClicked);
    }


    return(
        <BackGround>
    <div div style={{ zIndex: 1 }}>
        
            <LogoContainer>
            <img src='img/logo.png' width={`80px`} height={`80px`} alt='Logo'></img>
            </LogoContainer>
            <HomeContainer>
            <Link to="/AdminPage">
                <img src='img/Home.png' width={`60px`} height={`60px`} alt='Logo'></img>
                </Link>
            </HomeContainer>
            <HelpContainer>
                <Button name = "인증하기" onClick = {()=> {
                    confirmVC();
                    setCheck(!isCheckClicked);
                }}></Button>
            </HelpContainer>
            
    
    <ContentContainer>
        <FormContainer>
            <FormH4>신청인  <FormInput value={data.name}/></FormH4>
            <FormH4>학  번  <FormInput value={data.studentId}/></FormH4>
            <FormH4>제 목 <FormInput value={data.subject}/></FormH4>
            <FormH4>과  목 <FormInput value={data.subject}/></FormH4>
            <FormH4>분류<FormInput value={data.type}/></FormH4>
            <FormH4>작업기간 <FormInput value={data.term}/></FormH4>
        </FormContainer>

        <DescriptionContainer>
            <FormH4>설명</FormH4>
            <DescriptionTextarea value={data.summary}></DescriptionTextarea>
        </DescriptionContainer>
    
        
        <RightContainer>
        <div style={{ textAlign: 'right' }}>
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
    { isCheckClicked && <CheckCertificationModal confirmdata = {data} confirmCheck= {confirmCheck}></CheckCertificationModal> }
    { isCheckClicked && isConfirmClicked && <ConfirmCertificationModal></ConfirmCertificationModal>}
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
    display: flex;
    justify-content: center;
    
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
    top: 57%;
    left: 4%;
`
const DescriptionTextarea = styled.textarea`
    width: 100%;  
    height: 200px;
    border-radius: 10px;
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