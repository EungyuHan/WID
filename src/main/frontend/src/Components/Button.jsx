import React from 'react';
import styled from 'styled-components';

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

function Button() {
    return(
        <LoginButton>
            LoginButton
        </LoginButton>
    )
}

export default Button;