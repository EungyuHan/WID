import styled,{keyframes} from 'styled-components';
import React,{ useState } from 'react';
import Button from '../Components/Button';
import Waves from '../Components/Waves';


/* 스타일드 컴포넌트 양식을 사용할 것, 따라서 스타일드 컴포넌트에 대한 개념 공부 필요함*/

const BackGround = styled.div`
    position: relative;
    background: linear-gradient(to right, #FFFFFF, #0083b0);
    width: 100%;
    height: 100vh;
    padding = 0px;
    display: flex;
    justify-content: center;
    align-items: center;
`

const LoginComponents = styled.div`
    width: 30%;
    justify-content: center;
    align-items: center;
    z-index: 2;
`

const Welcometext = styled.h3`
    color: white;
    letter-spacing: 1px;
    font-size: 17px;
    font-family: 'Arial', sans-serif;
    
`

const InputID = styled.input`
padding: 10px 25px;
margin: 5px 10px;
border-radius: 5px;
border: none;
`

const InputPW = styled.input`
padding: 10px 25px;
margin: 5px 10px;
border-radius: 5px;
border: none;
`

const CreateUserButton = styled.button`
    padding: 10px 25px;
    margin: 5px 10px;
    border-radius: 8px;
    border: none;
    transition: background-color 0.3s ease;
    box-shadow: 0.5px 1px 3px black;

  &:hover {
    background-color: White;
    color:black;
  }

`

function Loginpage(){
    const [id, setID] = useState("");
    const [passward, setPassward] = useState("");
    const [isModalOpen, setModalOpen] = useState(false);


    const toggleModal = () => {
        setModalOpen(!isModalOpen)
    }
    
    const makeUser = () => {
        toggleModal();
        console.log(isModalOpen);
        return 0;
    }

    return(
        <BackGround>
            <img src='img/logo.png' width={`300px`} height={`300px`}></img>
            <LoginComponents>
            <Welcometext>활동내역 증명 서비스 WID에 오신것을 환영합니다!</Welcometext>
            <InputID type='text' value={id} placeholder='ID' onChange={(e)=>{
                        setID(e.target.value)
                    }}></InputID>
                    <Button name="로그인"></Button>
                <InputPW type='text' value={passward} placeholder='PW' onChange={(e) => {
                        setPassward(e.target.value)
                    }}></InputPW>
                <CreateUserButton onClick={makeUser}>WID가 처음이세요?</CreateUserButton>
            </LoginComponents>
            <Waves></Waves>
        </BackGround>

        
            
    )
}

export default Loginpage;