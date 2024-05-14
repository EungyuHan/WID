import React, { useState } from 'react';
import styled,{keyframes} from 'styled-components';
import Waves from '../Components/Waves';
import Button from '../Components/Button';
import {Link} from 'react-router-dom';

function UserCreateVP() {
    const [item, setItem] = useState([]);
    const itemList = [];

    const addItem = () => {
        
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
                <Link to="/Help">
                        <img src='img/Help.png' width={`75x`} height={`75px`} alt='Logo'></img>
                        </Link>
                </HelpContainer>

                <SelectBarDiv>
                    <ItemDiv>
                        
                    </ItemDiv>
                    <ItemAddButtonDiv>
                        <Button name="항목추가하기" onClick={addItem}></Button>
                    </ItemAddButtonDiv>
                    
                </SelectBarDiv>

                <ContentDiv>

                </ContentDiv>
            </div>
            <Waves/>
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
`

const ItemDiv = styled.div`
    position: relative;
    top: 3%;
    width:90%;
    height:80%;
    margin: auto;
    border-radius: 10px;
    display:block;
    background-color: #ffffffcc;
    overflow:scroll;
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


export default UserCreateVP;