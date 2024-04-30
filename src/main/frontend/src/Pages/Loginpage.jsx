import styled from 'styled-components';
import Button from '../Components/Button';
/* 스타일드 컴포넌트 양식을 사용할 것, 따라서 스타일드 컴포넌트에 대한 개념 공부 필요함*/

const LoginButton = styled.button`
background-color: #4CAF50; /* 초록색 */
border: none;
color: white;
padding: 15px 32px;
text-align: center;
text-decoration: none;
display: inline-block;
font-size: 16px;
margin: 4px 2px;
cursor: pointer;
border-radius: 8px;
`;

function Loginpage(){
    return(
        <LoginButton>Login</LoginButton>
        
    )
}

export default Loginpage;