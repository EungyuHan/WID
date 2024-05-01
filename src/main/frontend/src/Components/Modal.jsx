import styled,{keyframes} from 'styled-components';
import React,{ useState } from 'react';

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
    background-color: White;
    border-radius: 10px;
    box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
     
`
const changeOpen = (isModalOpen) => {
    isModalOpen = false;
} 

function Modal(props) {
    return(
        
        <Modals>

            <ModalContent>
                <button onClick={props.toggleModal}></button>
            </ModalContent>
        </Modals>
        
    )
}

export default Modal;