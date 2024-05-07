import styled,{keyframes} from 'styled-components';
import axios from 'axios';
import Button from '../Components/Button';
import React,{ useState } from 'react';
import Waves from '../Components/Waves';




function CheckPrivateModal(props) {
    const [privateKey, setPrivateKey] = useState('')

    return(
        <Modals>
            <ModalContent>
                <Instruction>발급받은 개인키를 입력해주세요</Instruction>
                <div>
                    <PrivateInput type={'password'} value={privateKey} size={30}placeholder={"개인키를 입력해주세요"} 
                    onChange={(e)=>{
                        setPrivateKey(e.target.value)
                    }}>
                    </PrivateInput>
                </div>
                <div>
                <Button name={"확인"}></Button>
                </div>
                
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
    position: absolute;
    display: block;
    top: 50%;
    left: 50%;
    width: 30%;
    height: 20%;
    padding: 40px;
    margin: auto;
    text-align: center;
    background: linear-gradient(to top, #FFFFFF, #0083b0);
    border-radius: 10px;
    box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
    transform: translate(-50%, -50%);

`
const Instruction = styled.h3`
    color : White;
`

const PrivateInput = styled.input`
    padding: 10px 25px;
    margin: 5px 10px;
    border-radius: 5px;
    border: none;   
`

export default CheckPrivateModal;