import styled,{keyframes} from 'styled-components';
import { useState } from 'react';
import Button from '../Components/Button';
/* 스타일드 컴포넌트 양식을 사용할 것, 따라서 스타일드 컴포넌트에 대한 개념 공부 필요함*/


const BackGround = styled.div`
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
   
`
const InputID = styled.input`
padding: 10px 25px;
margin: 5px 10px;
`

const InputPW = styled.input`
padding: 10px 25px;
margin: 5px 10px;
`

const CreateUserButton = styled.button`
    padding: 10px 25px;
    margin: 5px 10px;
    border-radius: 8px;
    border: none;
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
            <InputID type='text' value={id} placeholder='ID' onChange={(e)=>{
                        setID(e.target.value)
                    }}></InputID>
                <InputPW type='text' value={passward} placeholder='PW' onChange={(e) => {
                        setPassward(e.target.value)
                    }}></InputPW>
                <Button name="로그인"></Button>
                <CreateUserButton onClick={makeUser}>WID가 처음이세요?</CreateUserButton>
            </LoginComponents>
        </BackGround>
            
            
            
            
            

            
    )
}

export default Loginpage;