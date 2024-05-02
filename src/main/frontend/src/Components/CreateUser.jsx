import styled,{keyframes} from 'styled-components';
import React,{ useState } from 'react';
import Button from './Button';


function CreateUser(props){
    const [id, setID] = useState("");
    const [passward, setPassward] = useState("");
    const [checkPW, setCheckPW] = useState("");
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");

    const Check = (e) => {
        e.preventDefault();
        if(!id) {
            return alert("사용자 ID를 입력해주세요");
        }
        else if (!passward) {
            return alert("passward를 입력해주세요");
        }
        else if (!checkPW){
            return alert("비밀번호가 서로 동일하지 않습니다");
        }
        else if (id, passward, checkPW) {
            return alert("계정을 생성중입니다 잠시만 기다려주세요");
        }

        /* API호출하는 로직 추후에 더 작성해야함  */
    }

    

    return(
        <Modals>
            <ModalContent>
            <button onClick={props.onClose}>취소</button>
                <form onSubmit={Check}>
                <img src='img/logo.png' width={`150px`} height={`150px`}></img>
                    <div>
                    <Create type='text' size="30" value={id} placeholder="사용할 ID를 입력해주세요" onChange={(e)=>{
                        setID(e.target.value)
                    }}></Create>
                    </div>
                    <div>
                    <Create type='text' size="30" value={passward} placeholder="사용할 패스워드를 입력해주세요" onChange={(e)=>{
                        setPassward(e.target.value)
                    }}></Create>
                    </div>
                    <div>
                    <Create type='text' size="30"value={checkPW} placeholder="패스워드를 한번 더 입력해주세요" onChange={(e)=>{
                        setCheckPW(e.target.value)
                    }}></Create>
                    </div>
                    <div>
                    <Create type='text' size="30"value={name} placeholder="이름" onChange={(e)=>{
                        setName(e.target.value)
                    }}></Create>
                    <Create type='text' size="30"value={email} placeholder="이메일" onChange={(e)=>{
                        setEmail(e.target.value)
                    }}></Create>
                    </div>
                    <Create type='text' size="30"value={phone} placeholder="전화번호" onChange={(e)=>{
                        setPhone(e.target.value)
                    }}></Create>
                    <div>
                    <Button name="제출하기"><input type='submit'></input></Button>
                    </div>
                    
                </form>
                
                
            </ModalContent>
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
const ModalContent = styled.div`
    position: relative;
    top: 10%;
    display: block;
    width: 50%;
    height: 70%;
    padding: 40px;
    margin: auto;
    text-align: center;
    background-color: #cacfd3;
    border-radius: 10px;
    box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
     
`
const Create = styled.input`
    padding: 10px 25px;
    margin: 5px 10px;
    border-radius: 5px;
    border: none;
    box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
     
`
const CreatePW = styled.input`
    padding: 10px 25px;
    margin: 5px 10px;
    border-radius: 5px;
    border: none;
    box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
     
`

const Instruct = styled.h3`
    color: black
    letter-spacing: 1px;
    font-size: 17px;
    font-family: 'Arial', sans-serif;
`

export default CreateUser;