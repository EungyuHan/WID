import styled from 'styled-components';
import React,{ useState , useRef , useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import Waves from '../Components/Waves';
import axios from 'axios';
import CheckPrivateModal from '../Components/CheckPrivateModal';
import HelpModal from '../Components/HelpModal';


function UserMainPage(props) {
const [PKchecked, setPKchecked] = useState(false);
const [isHelpClicked, setHelp] = useState(false);
const [vpData, setVPdata] = useState([]);
const refs = useRef({});
const navigate = useNavigate();


useEffect(() => {
    axios.get('http://localhost:3001/VP')
    .then(response => {
        setVPdata(response.data);
    })
},[])

const goToRef = (index) => {  
    if(refs.current[index]){
        refs.current[index].scrollIntoView({ behavior: 'smooth' });
    }
};

const toggleHelp = () => {
    setHelp(!isHelpClicked);
}

const handleNavigate = (destination) => {
    // 서버에 JWT 토큰이 제대로 발행되어있는지 확인하는 코드 
    navigate(destination);
}



const renderContent = () => {
    if(vpData.length === 0){
        return(
            <ContentsConatiner>
                <WelcomeTextArea>
                    <WelcomeText>생성된 활동내역 리스트가 존재하지 않습니다</WelcomeText>
                </WelcomeTextArea>
            </ContentsConatiner>
        )
    }
    else{
        return (
            <ContentsConatiner>
                    <InformationDiv ref={el => refs.current['personalInfo'] = el}>
                        <h3 ></h3>
                        
                    </InformationDiv>
                    <InformationDiv>
                        <h3 ref={el => refs.current['experience'] = el}>Experience</h3>

                    </InformationDiv>
                    <InformationDiv>
                        <h3  ref={el => refs.current['education'] = el}>Education</h3>
                        
                    </InformationDiv>
                    <InformationDiv>
                        <h3 ref={el => refs.current['achievement'] = el} >Achivement</h3>
                        
                    </InformationDiv>
                    <InformationDiv>
                        <h3 ref={el => refs.current['interest'] = el}>Interests</h3>
                        
                    </InformationDiv>
                </ContentsConatiner>
        )
    }
}

const renderNavList = () => {
    const newlistName = vpData.map(item => Object.keys(item)[0]);
    return newlistName.map((itemName)=> (
        <NavButton>{itemName}</NavButton>
    ))
}

return (
    <BackGround>
        <div style={{ zIndex: 1 }}>
        <NavBar>
        <NavBarLeft></NavBarLeft>
        <NavBarRight>
            <LogoBar>
            <img src='img/logo.png' width={`102px`} height={`81px`} alt='Logo'></img>
            </LogoBar>
            <UserprofileContainer>
                <UserProfile></UserProfile>
            </UserprofileContainer>
            {renderNavList()}
        </NavBarRight>
        </NavBar>
        <UpNavBar>
        <UpNavBarTop>
            <UpNavButton onClick={()=>{handleNavigate('/CreateVP')}}>활동내역 관리</UpNavButton>
            <UpNavButton onClick={()=>{handleNavigate('/CreateVC')}}>활동내역 발급</UpNavButton>
            <UpNavButton onClick={()=>{handleNavigate('/Working')}}>활동내역 제출</UpNavButton>
            <UpNavButton onClick={toggleHelp}>도움말</UpNavButton>
        </UpNavBarTop>
        <UpNavBarBottom>
        {renderContent()}            
        </UpNavBarBottom>
        </UpNavBar>
        { PKchecked && (<CheckPrivateModal/>)}
        { isHelpClicked && (<HelpModal onClose={toggleHelp}/>)}
        </div>
        
        <Waves></Waves>
    </BackGround>
);
}


const BackGround = styled.div`
    position: fixed;
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
    background-color: rgba(0, 0, 0, 0.4);
    left:5%;
    top:2%;
    width:90%;
    height:96%;
    border-radius: 10px;
    overflow: auto;
`

const WelcomeTextArea = styled.div`
    position: relative;
    height:100%;
    width:100%;
    display: flex;
    justify-content : center;
    align-items : center;
`
const WelcomeText = styled.h4`
    color: White;
`

const InformationDiv = styled.div`
    width: 95%;
    height: auto;
    background-color: #e9e9e9;
    margin: auto;
    overflow: auto;
    border-radius: 5px;
    margin-top:10px;
    margin-bottom: 10px;
`




export default UserMainPage;