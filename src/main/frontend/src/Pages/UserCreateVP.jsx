import React, { useState } from 'react';
import styled,{keyframes} from 'styled-components';
import Waves from '../Components/Waves';
import Button from '../Components/Button';
import { Link } from 'react-router-dom';
import SetItemNameModal from '../Components/SetItemNameModal';
import VCviewer from '../Components/VCviewer';
import UserSendModal from '../Components/UserSendModal';


function UserCreateVP() {
    const [item, setItem] = useState(0);
    const [itemList, setItemList] = useState([]);   
    const [isModalOpen, setIsModalOpen] = useState(false); 
    const [contentsList , setContentList] = useState([]);
    const [show, setShow] = useState(false);
    const [content, setContent] = useState([]);
    const [focusIndex, setFocus] = useState(0);
    const [isSubmitClicked, setSubmit] = useState(false);

    const toggleDiv = (focus) => {
    setShow(!show);
    setFocus(focus);
    };
    
    const addItem = () => {
        setModal();
    }
    
    const setModal = () => {
        setIsModalOpen(!isModalOpen);
    }

    const getItemName = (name) => {
        const newName = name;
        const newItemID = item+1;
        setItem(newItemID);
        const newItemList = [...itemList, { id: newItemID, name: newName }];
        const newContentsList = [...contentsList, {id: newItemID, name: newName}];
        setItemList(newItemList);
        setContentList(newContentsList);
    }


    const renderItemList = () => {
        return itemList.map((itemName, index) => (
                <ItemListContainer key={itemName.id} 
                onClick={()=>{toggleDiv(itemName.id)}}>
                <h4>{itemName.name}</h4>
                </ItemListContainer>
                )
        )
    }

    const renderContentList = () => {
        return contentsList.map((contentName)=>(
            <ContentsListDiv key={contentName.id}>
                <h3>{contentName.name}</h3>
                <hr></hr>
                    <div>
                    {renderMatchingContent(contentName.id)}
                    </div>
                
            </ContentsListDiv>
        )
    )
    }


    const renderMatchingContent = (id) => {
        const matchedContents = content.filter(item => item.id === id-1);

        return matchedContents.length > 0 ? (
                matchedContents.map((item, idx) => (
                    <div>
                        <h4 key={idx}>{item.content.summary}</h4>
                        <div key={idx}>{"진행일자 :"+ item.content.term}</div>
                        <div key={idx}>{"이슈어:" + item.content.professor}</div>
                        <div key={idx}>{item.content.organizer}</div>
                    </div>
                    
                    
                ))) : null;
    };

    const submit = () => {
        // 현재 내용이 비워져있지는 않은지등에 대한 검사가 필요하다.
        if(item === 0){
            alert("현재 제출할 내용이 없습니다.");
        }
        else if(content.length === 0) {
            alert("현재 추가된 VC가 없습니다 VC를 추가해주세요");
        }
        else{
            setSubmit(!isSubmitClicked);
        }
        
    }
    
    
    return(
        <BackGround>
            <div div style={{ zIndex: 1 }}>
                <LogoContainer>
                <img src='img/logo.png' width={`80px`} height={`80px`} alt='Logo'></img>
                </LogoContainer>
                <HomeContainer>
                <Link to="/MainPage">
                        <img src='img/Home.png' width={`60px`} height={`60px`} alt='Logo'></img>
                        </Link>
                </HomeContainer>
                <HelpContainer>
                <Button name="제출하기" onClick={submit}> </Button>
                </HelpContainer>

                <SelectBarDiv>
                        <ItemDiv>
                            {item === 0 && <div></div>}
                            {renderItemList()}
                        </ItemDiv>
                        <ItemAddButtonDiv>
                            <Button name="항목추가하기" onClick={addItem} ></Button>
                        </ItemAddButtonDiv>
                        
                </SelectBarDiv>
                

                <ContentDiv>
                    {renderContentList()}
                    <SlideVCdiv show={show}>
                        <VCviewer content={content} setContent={setContent} focusIndex={focusIndex-1}/>
                    </SlideVCdiv>
                </ContentDiv>
            </div>
            
            <Waves/>
            {isModalOpen && <SetItemNameModal onClick={setModal} getString={getItemName}></SetItemNameModal>}
            {isSubmitClicked && <UserSendModal></UserSendModal>}
        </BackGround>
    )
}



const BackGround = styled.div`
    position: fixed;
    background: linear-gradient(to right, #FFFFFF, #0083b0);
    width: 100%;
    height: 100vh;
    padding = 0px;
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
const SelectBarDiv = styled.div`
    position: fixed;
    top : 12%;
    left : 3%;
    width: 20%;
    height: 80%;
    border-radius : 15px;
    background-color: rgba(0, 0, 0, 0.25);
`

const ContentDiv = styled.div`
    position: fixed;
    top : 12%;
    left : 27%;
    width: 70%;
    height: 85%;
    border-radius : 15px;
    background-color: rgba(0, 0, 0, 0.25);
    overflow:auto;
`

const ItemDiv = styled.div`
    position: relative;
    top: 3%;
    width:90%;
    height:80%;
    margin: auto;
    border-radius: 10px;
    display:block;
    background-color: #383838;
    overflow:auto;
    box-shadow: 0.5px 0.5px 2px black;
`

const ItemAddButtonDiv = styled.div`
    position: relative;
    top:5%;
    width:90%;
    height:10%;
    margin: auto;
    display: flex;
    justify-content: center;
    align-items: center;
`

const ItemListContainer = styled.button`
    position: relative;
    top:12%;
    left: 50%;
    width: 90%;
    height: 20%;
    border-radius: 15px;
    border: none;
    margin-bottom: 5%;
    box-shadow: 0.5px 0.5px 2px black;
    background-color: White;
    transform: translate(-50%, -50%);
    &:hover {
    background-color: #878787;
    color: black;
    }
`
const ContentsListDiv = styled.div`
    position: relative;
    top: 2%;
    width: 95%;
    height: auto;
    margin: auto;
    border-radius: 5px;
    background-color: #ffffffd1;
`

const SlideVCdiv = styled.div`
    position: fixed;
    bottom: ${(props) => (props.show ? '5%' : '-50%')};
    left: 30%;
    width: 63%;
    height: 35%;
    background-color: rgba(0, 0, 0, 0.65);
    color: white;
    text-align: center;
    border-radius: 10px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
    transition: bottom 0.5s ease-in-out;
`;


export default UserCreateVP;