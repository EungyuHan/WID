import styled,{keyframes} from 'styled-components';
import React,{ useState } from 'react';
import Button from './Button';

function ConfirmSubmitModal() {
    const [isChecked,setIsChecked] = useState(false);



    return(
        <Modals>
            <ModalContent>
                <h2>인증신청이 완료되었습니다.</h2>
                <InformDiv>
                    <InformText>제출된 내용은 담당자의 사실여부 확인 후에 성공적으로 저장이됩니다.</InformText>
                    <InformText>1.사실이 아닌경우</InformText>
                    <InformText>2.내용에 대한 사실 여부 확인이 어려운 경우</InformText>
                    <InformText>3.담당자가 확인이 불가능한 경우</InformText>
                    <h5>해당 내용은 일정기간 이후 자동으로 파기되며 
                    새롭게 신청서를 작성해주셔야 합니다.</h5>
                </InformDiv>
                <div>
                <h4>상기의 내용을 확인하였습니다.</h4>
                <input type={'checkbox'} onClick={()=>{setIsChecked(!isChecked)}}></input>
                </div>
                
                <Button name={'제출하기'} disabled={!isChecked}></Button>
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
    align-items: center; 
    margin:auto;
    text-align: center;
    background-color:white;
    border-radius:10px;
`
const InformText = styled.h4`
    margin:10px;
`
export default ConfirmSubmitModal;