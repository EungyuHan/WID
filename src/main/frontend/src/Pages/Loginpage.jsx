import styled,{keyframes} from 'styled-components';
import React,{ useState  } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../Components/Button';
import Waves from '../Components/Waves';
import CreateUser from '../Components/CreateUser';
import SelectUser from '../Components/SelectUser';
import axios from 'axios';
import base64 from 'base-64';
import { useAuth } from '../LoginComponent/AuthContext';

function Loginpage(){
    const [id, setID] = useState("");
    const [password, setPassword] = useState("");
    const [isModalOpen, setModalOpen] = useState(false);
    const [isSelected, setSelect] = useState(false);
    const [selectedValue, setSelectedValue] = useState('');
    
    const { login } = useAuth();
    const navigate = useNavigate();
    
    const [userInfo, setUserInfo] = useState({
        "username" : null,
        "password" : null,
        "name" : null,
        "email" : null,
        "phone" : null
    });


    const toggleModal = () => {
        setModalOpen(!isModalOpen)
    }
    const Selected = () => {
        setSelect(!isSelected)
    }
    const handleRadioChange = (event) => {
        setSelectedValue(event.target.value);
    };
    
    const makeUser = () => {
        toggleModal();
        console.log(isModalOpen);
        return 0;
    }
    const LoginFunc = (e) => {
        e.preventDefault();
        if(!id) {
            return alert("사용자 ID를 입력해주세요");
        }
        else if (!password) {
            return alert("passward를 입력해주세요");
        }
        else {
            const formData = new FormData();
            formData.append("username" , id);
            formData.append("password", password);
            axios.post('http://localhost:8080/login',formData)
            .then(res => {
                login(res.headers.authorization);
                let token = res.headers.authorization.replace(/^Bearer\s+/, '');
                let payload = token.substring(token.indexOf('.')+1,token.lastIndexOf('.'));
                let decoded = base64.decode(payload);
                let decodedTOJson = JSON.parse(decoded);
                
                if(decodedTOJson.role == "ROLE_USER"){
                    navigate('/MainPage');
                }
                else if(decodedTOJson.role == "ROLE_ISSUER"){
                    navigate('/AdminPage');
                }
                else if(decodedTOJson.role == "ROLE_VERIFIER"){
                    navigate('/VerifierPage')
                }
            }
                );
        }
    }

    return(
        <BackGround>
            <img src='img/logo.png' width={`300px`} height={`300px`}></img>
            <LoginComponents>
                <Welcometext>활동내역 증명 서비스 WID에 오신것을 환영합니다!</Welcometext>
                <form onSubmit={LoginFunc}>
                        <InputID type='text' value={id} placeholder='ID' onChange={(e)=>{
                            setID(e.target.value)
                        }}></InputID>
                        
                        <InputPW type='password' value={password} placeholder='PW' onChange={(e) => {
                            setPassword(e.target.value)
                        }}></InputPW>
                        <Button name="로그인" onClick={LoginFunc}></Button>  
                </form>
            
                <CreateUserButton onClick={makeUser}>WID가 처음이세요?</CreateUserButton>
            </LoginComponents>
            <Waves></Waves>

            { isModalOpen && isSelected===false &&(<SelectUser isSelected={Selected} UserInfo={userInfo} ></SelectUser>)}
            { isModalOpen && isSelected &&(<CreateUser onClose={toggleModal} UserInfo={userInfo}></CreateUser>)}

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
box-shadow: 0.5px 0.5px 2px black;
`

const InputPW = styled.input`
padding: 10px 25px;
margin: 5px 10px;
border-radius: 5px;
border: none;
box-shadow: 0.5px 0.5px 2px black;
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

export default Loginpage;