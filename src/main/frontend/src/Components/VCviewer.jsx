import React, { useState , useEffect } from 'react';
import styled from 'styled-components';
import axios from 'axios';

function VCviewer(props) {
    const [vcdata, setVCdata] = useState([]);
    const [search, setSearch] = useState("");
    const [loading, setLoading] = useState(true);
    
    useEffect(() => {
        axios.get('http://localhost:3001/vccontent')
        .then(response => {
            setVCdata(response.data);
            console.log(vcdata);
            setLoading(false);
        })
    },[])



    const addVC = (name,index,) => {
        const newList = [...props.content, {id: index,  content: name}];
        props.setContent(newList);
        console.log(newList);
    }
    
    const deleteVC = (name) => {
        const newList = props.content.filter(item => item.content.summary !== name);
        props.setContent(newList);
    }

    const renderContentList = (search) => {
        if(!search){
            return vcdata.map((data)=>(
                <VC_List_Container>
                    <VCcontentDiv width={'80%'}>{data.summary}</VCcontentDiv>
                    <VCcontentDiv width={'20%'}>
                        <AddVCbutton onClick={()=>{addVC(data , props.focusIndex)}}>추가</AddVCbutton>
                        <DeleteVCbutton onClick={()=>{deleteVC(data.summary)}}>삭제</DeleteVCbutton>
                        
                    </VCcontentDiv>
                </VC_List_Container>
        ))
        }
        else{
            const searchList = vcdata.filter((data) => data.summary.includes(search));
            return searchList.map((data)=> (
                <VC_List_Container>
                <VCcontentDiv width={'80%'}>{data.summary}</VCcontentDiv>
                <VCcontentDiv width={'20%'}>
                    <AddVCbutton onClick={()=>{addVC(data , props.focusIndex)}}>추가</AddVCbutton>
                    <DeleteVCbutton onClick={()=>{deleteVC(data.summary)}}>삭제</DeleteVCbutton>
                    
                </VCcontentDiv>
                </VC_List_Container>
            ))
        }
            
    }

    if(loading) {
        return <div>loading</div>
    }
    

    return(
        <VCcontents>
            <VCSearch>
            <img src='img/SearchIcon.png' width={`20x`} height={`20px`} alt='Logo'></img>
                <Input type={'text'} placeholder='검색하실 활동내역을 입력해주세요' onChange={(e)=>{setSearch(e.target.value); console.log(search)}}></Input>
            </VCSearch>
            <VC_List_Div>
                {renderContentList(search)}
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
    const Input = styled.input`
    padding: 8px 20px;
    margin: 5px 10px;
    border-radius: 5px;
    border: none;
    box-shadow: 0.5px 0.5px 2px black;
    `


const VC_List_Div = styled.div`
    width: 99%;
    height: 80%;
    margin : auto;
    border-radius: 10px;
    background-color: #ffffff19;
    box-shadow: 0.5px 0.5px 2px black;
    overflow: auto;
`

const VC_List_Container = styled.div`
    position: relative;
    width: 100%;
    height: 20%;
    top:2%; 
    color: black;
    margin-top: 10px; /* 위쪽 간격 */
    margin-bottom: 10px; /* 아래쪽 간격 */
    border-radius: 5px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #ffffff;
    
`

const VCcontentDiv = styled.div`
    width: ${props => props.width};
    overflow: auto;
`

//버튼 props줘서 하나로 만들자.

const AddVCbutton = styled.button`
    color: white;
    background-color: #4176d1;
    padding: 5px 7px; 
    border-radius: 8px;
    border: none;
    margin: 4px 2px;
    box-shadow: 0.5px 0.5px 2px black;
    &:hover {
        background-color: White;
        color: black;
    }
`

const DeleteVCbutton = styled.button`
    color: white;
    background-color: #bd4646;
    padding: 5px 7px;
    border-radius: 8px;
    margin: 4px 12px;
    border: none;
    box-shadow: 0.5px 0.5px 2px black;
    &:hover {
        background-color: White;
        color: black;
    }
`    

export default VCviewer;