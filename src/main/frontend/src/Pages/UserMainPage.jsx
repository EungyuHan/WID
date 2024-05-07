import styled,{keyframes} from 'styled-components';
import React,{ useState } from 'react';
import Button from '../Components/Button';
import Waves from '../Components/Waves';
import axios from 'axios';
import CheckPrivateModal from '../Components/CheckPrivateModal';



function UserMainPage(props) {
const [PKchecked, setPKchecked] = useState(false);

return (
    <BackGround>
        <NavBar>
        <NavBarLeft></NavBarLeft>
        <NavBarRight>
            <LogoBar>
            <img src='img/logo.png' width={`102px`} height={`81px`} alt='Logo'></img>
            </LogoBar>
            <UserprofileContainer>
                <UserProfile></UserProfile>
            </UserprofileContainer>
            <NavButton>PERSONAL INFORMATION</NavButton>
            <NavButton>EXPERIENCE</NavButton>
            <NavButton>EDUCATION</NavButton>
            <NavButton>ACHIVEMENTS</NavButton>
            <NavButton>INTERESTS</NavButton>
        </NavBarRight>
        </NavBar>
        <UpNavBar>
        <UpNavBarTop>
            <UpNavButton onClick={()=>{setPKchecked(true)}}>활동내역 관리</UpNavButton>
            <UpNavButton onClick={()=>{setPKchecked(true)}}>활동내역 발급</UpNavButton>
            <UpNavButton onClick={()=>{setPKchecked(true)}}>활동내역 제출</UpNavButton>
            <UpNavButton>도움말</UpNavButton>
        </UpNavBarTop>
        <UpNavBarBottom>
            <ContentsConatiner>
                <InformationDiv><h3>Personal Information</h3></InformationDiv>
                <InformationDiv><h3>Experience</h3></InformationDiv>
                <InformationDiv><h3>Education</h3></InformationDiv>
                <InformationDiv><h3>Achivement</h3></InformationDiv>
            </ContentsConatiner>
        </UpNavBarBottom>
        </UpNavBar>
        { PKchecked && (<CheckPrivateModal></CheckPrivateModal>)}
        

    </BackGround>
);
}


const BackGround = styled.div`
    position: relative;
    background: linear-gradient(to right, #FFFFFF, #0083b0);
    width: 100%;
    height: 100vh;
    display: flex;
    background-attachment: fixed;
`

const NavBar = styled.div`
    position: fixed;
    width: 25%;
    height: 100%;
    background-color: #383838;
    display: flex;
    
`

const NavBarLeft = styled.div`
    position: relative;
    width: 15%;
    height: 100%;
    background-color: #383838;
    border-right: 3px solid white;
`

const NavBarRight = styled.div`
    position: relative;
    width: 85%;
    height: 100%;
    background-color: #383838;
`

const LogoBar = styled.div`
    position: relative;
    width: auto;
    height: 10%;
    background-color: #383838;
    margin: 20px;
    margin-left: 10px;
`

const UserprofileContainer = styled.div`
    position: relative;
    width: auto;
    height: 25%;
    background-color: #383838;
    display: flex;
    justify-content: center;
`

const UserProfile = styled.div`
    position: relative;
    border-radius: 50%;
    width: 150px;
    height: 150px;
    background-color: #FFFFFF;
`

const NavButton = styled.button`
    position: relative;
    width: 100%;
    height: 10%; 
    border: none; /* 테두리 제거 */
    box-shadow: none;
    background-color: #383838;
    color: white;
    text-align: left;
    font-size: 22px;
    font-family: 'Arial', sans-serif;
    padding: 40px;
    transition: background-color 0.5s ease;
    box-shadow: none;
    &:hover {
       background-color: #FFFFFF;
       color: black;
    } 
`

const UpNavBar = styled.div`
    position:fixed;
    width: 75%;
    height: 100%;
    background-color: transparent;
    right: 0;
`

const UpNavBarTop = styled.div`
    position:relative;
    width: 100%;
    height: 10%;
    background-color: transparent;
    display: flex;
    border-bottom: 3px solid black;
    right: 0;
`

const UpNavBarBottom = styled.div`
    position:relative;
    width: 100%;
    height: 90%;
    background-color: transparent;
    display: flex;
`

const UpNavButton = styled.button`
    position: relative;
    width: 25%;
    height: 100%; 
    border: none; 
    box-shadow: none;
    background-color: transparent;
    color: #383838;
    text-align: center;
    font-size: 22px;
    font-family: 'Arial', sans-serif;
    transition: background-color 1s ease;
    box-shadow: none;
    &:hover {
       background-color: #FFFFFF;
       color:black;
    } 
    
` 
const ContentsConatiner = styled.div`
    position: relative;
    background-color: #383838;
    left:5%;
    top:2%;
    width:90%;
    height:96%;
    justify-content: center;
    border-radius: 10px;
    overflow: auto;
`

const InformationDiv = styled.div`
    width: 95%;
    height: 40%;
    background-color: #605e5e;
    margin: auto;
    overflow: auto;
    border-radius: 5px;
    margin-bottom: 10px;
`




export default UserMainPage;