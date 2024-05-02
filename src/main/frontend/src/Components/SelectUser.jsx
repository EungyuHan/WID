import styled,{keyframes} from 'styled-components';
import React,{ useState } from 'react';
import Button from './Button';


function SelectUser(props) {
    const [selectedValue, setSelectedValue] = useState('');

    const handleRadioChange = (event) => {
        setSelectedValue(event.target.value);
    };
    return(
        <Modals>
            <ModalContent>
                <input 
                    type="radio" 
                    id="User" 
                    name="gender" 
                    value="User"
                    onChange={handleRadioChange}>
                </input> 
                <label for="User"><h3>유저</h3></label>
                <input 
                    type="radio" 
                    id="Issuer" 
                    name="gender" 
                    value="Issuer"
                    onChange={handleRadioChange}>
                </input> 
                <label for="Issuer"><h3>교수</h3></label>
                <input 
                    type="radio" 
                    id="Verifier" 
                    name="gender" 
                    value="Verifier"
                    onChange={handleRadioChange}>
                </input> 
                <label for="Verifier"><h3>회사</h3></label>
                <h1>{selectedValue}</h1>
                <Button name="선택하기" onClick={props.isSelected}></Button>
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
    background-color: #cacfd3;
    border-radius: 10px;
    box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
    
`

export default SelectUser;