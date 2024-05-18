import React, { useState } from 'react';
import styled,{keyframes} from 'styled-components';


function VCviewer() {

    const [search, setSearch] = useState("");


    return(
        <VCcontents>
            <VCSearch>
            <img src='img/SearchIcon.png' width={`20x`} height={`20px`} alt='Logo'></img>
                <Input type={'text'} placeholder='검색하실 활동내역을 입력해주세요' ></Input>
            </VCSearch>
            <VC_List_Div>
                VC 리스트가 담길 div칸 입니다.
            </VC_List_Div>
        </VCcontents>
        
    )
}


const VCcontents = styled.div`
    position: relative;
    background-color: transparent;
    width:100%;
    height:100%;
    margin : auto;
    border-radius:10px;
`
const VCSearch = styled.div`
    width:35%;
    height:15%;
    color: black;
    background-color: #ffffffa6;
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    
`
const VC_List_Div = styled.div`
    width: 99%;
    height: 80%;
    margin : auto;
    border-radius: 10px;
    background-color: #ffffff19;
    box-shadow: 0.5px 0.5px 2px black;
`
const Input = styled.input`
padding: 8px 20px;
margin: 5px 10px;
border-radius: 5px;
border: none;
box-shadow: 0.5px 0.5px 2px black;
`
export default VCviewer;