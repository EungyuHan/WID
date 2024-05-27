import React,{ useState } from 'react';
import styled,{keyframes} from 'styled-components';
import {DragDropContext , Droppable , Draggable} from 'react-beautiful-dnd';


function UserSendPage() {
  const [show, setShow] = useState(false);

  const toggleDiv = () => {
    setShow(!show);
  };




  
    // 드래그가 끝났을 때 호출되는 함수
  

    return (
      <div>
        <Button onClick={toggleDiv}>Show Div</Button>
        <SlideUpDiv show={show}>
          This is a sliding div!
        </SlideUpDiv>
      </div>
        
      
    );
  }
  

const AppContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  margin: 0;
  overflow: hidden;
`;


const Button = styled.button`
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
`;

const SlideUpDiv = styled.div`
  position: fixed;
  bottom: ${(props) => (props.show ? '10%' : '-80%')};
  left: 40%;
  width: 50%;
  height: 30%;
  padding: 20px;
  background-color: #3498db;
  color: white;
  text-align: center;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
  transition: bottom 0.5s ease-in-out;
`;


export default UserSendPage;
