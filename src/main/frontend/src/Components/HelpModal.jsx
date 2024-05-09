import styled,{keyframes} from 'styled-components';
import React,{ useState } from 'react';
import Button from './Button';



function HelpModal(props) {
    return(
        <Modals>
            <button onClick={props.onClose}>뒤로가기</button>
            <Instruct>안녕하세요 WID가 처음이시군요</Instruct>
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

const Instruct = styled.h2`
    color: White;
`



export default HelpModal;