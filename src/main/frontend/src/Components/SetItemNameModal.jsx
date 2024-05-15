import styled,{keyframes} from 'styled-components';
import React,{ useState } from 'react';
import Button from '../Components/Button';



function SetItemNameModal(props) {
    const [itemName, setName] = useState("");
    

    return (  
        <Modals>
            <ModalContent>
                <h3>항목명을 입력해주세요.</h3>
                <ItemNameInput type='text' value={itemName} placeholder='항목명' onChange={(e)=>{
                            setName(e.target.value)
                        }}></ItemNameInput>
                <Button name="확인" onClick={ ()=>{
                    props.getString(itemName);
                    setName("");
                    props.onClick();
                }} ></Button>
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

const ItemNameInput = styled.input`
    padding: 10px 25px;
    margin: 5px 10px;
    border-radius: 5px;
    border: none;   
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

export default SetItemNameModal;