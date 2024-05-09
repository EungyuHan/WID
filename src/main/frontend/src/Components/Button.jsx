import React from 'react';
import styled from 'styled-components';

/* 가장 간단한 버튼 모양  */


function Button(props) {
    const ID = props.name;
    return(
        <ButtonEx onClick={props.onClick} disabled={props.disabled}>{ID}</ButtonEx>
    )
}


const ButtonEx = styled.button`
background-color: ${props => props.disabled ? 'gray' : '#0A377C' };
border: none;
color: white;
padding: 15px 32px;
text-align: center;
text-decoration: none;
display: inline-block;
font-size: 16px;
font-family: 'Arial', sans-serif;
margin: 4px 2px;
cursor: ${props => props.disabled ? 'not-allowed' : 'pointer'};
border-radius: 8px;
transition: ${props => props.disabled ? 'none' : 'background-color 0.5s ease'};
box-shadow: 0.5px 0.5px 2px black;
${props => props.disabled ? '' : `
    &:hover {
      background-color: White;
      color: black;
    }
  `}
`;


export default Button;