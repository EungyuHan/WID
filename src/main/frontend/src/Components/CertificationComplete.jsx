import styled,{keyframes} from 'styled-components';
import React,{ useState } from 'react';
import Button from './Button';
import {Link} from 'react-router-dom';


function CertificationComplete() {
    const [isChecked,setIsChecked] = useState(false);

    



    return(
        <Modals>
            <ModalContent>
                <h2 style={{color:'white'}}>인증 완료 내역</h2>
                <InformDiv>
                    <InformText> 1. 신청인: 이용준, 인증명: 소프트웨어공학캡스톤프로젝트(블록체인 네트워크를 활용한 활동내역 증명 및 관리 시스템)</InformText>
                    <InformText> 2. 신청인: 김서연, 인증명: 데이터베이스(자동차 유지비 관리 시스템) </InformText>
                    <InformText> 3. 신청인: 남준성, 인증명: 학부연구생 </InformText>
                    <InformText> 4. 신청인: 손영빈, 인증명: AM:PM 학술동아리(해커톤 대회) </InformText>
                    {/* <h5>해당 내용은 일정기간 이후 자동으로 파기되며 
                    새롭게 신청서를 작성해주셔야 합니다.</h5> */}
                </InformDiv>
                <div>
                <h4>상기의 내용을 인증하였습니다.</h4>
                </div>
                
                <Link to="/MainPage"><Button name={'확인'}></Button></Link>

            </ModalContent>
        </Modals>
    )
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

const ModalContent = styled.div`
    position: relative;
    top: 10%;
    display: block;
    width: 50%;
    height: 70%;
    padding: 40px;
    margin: auto;
    text-align: center;
    background: linear-gradient(to top, #FFFFFF, #0083b0);
    border-radius: 10px;
    box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
`


const InformDiv = styled.div`
    position: relative;
    width:80%;
    height: 50%;
    display: flex;
    flex-direction: column; 
    justify-content: center; 
    align-items: left; 
    margin:auto;
    text-align: left;
    background-color:white;
    border-radius:10px;
`
const InformText = styled.h4`
  margin: 10px;
  text-align: left;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  border-bottom: 2px solid black;
`;



export default CertificationComplete;