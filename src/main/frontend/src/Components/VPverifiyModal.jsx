import styled , {keyframes} from 'styled-components';
import React,{ useState , useEffect} from 'react';


function VPverifiyModal(props) {
    const [loading, setLoading] = useState(true);
    const [data, setData] = useState(0);
    useEffect(()=> {
        setTimeout(()=>{setLoading(!loading)},3000);
        setData(data+5);
    },[])
    //불러온 데이터 (인증완료여부)에 따라서 다른 화면을 보여줘야함.
        return (
            <Modals>
                <ModalContent>
                    { loading && 
                        <LoadingContainer>
                            <h3 style={{color:"white"}}>서버로부터 데이터를 받아오는중입니다.</h3>
                            <Spinner/>
                            <h4 style={{color:"white"}}>해당 내용의 진위여부를 파악중이니 잠시만 기다려주세요.</h4>
                            
                        </LoadingContainer>
                            
                    }    

                    { !loading && 
                    <ContentContainer>
                        <div>
                        
                        앙 로딩완료 
                        <h4 style={{color:"white"}}>{data}</h4>
                        </div>
                    </ContentContainer>
                    }
                    <ExitButton onClick={props.onClose}>닫기</ExitButton>
                    
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
    top: 5%;
    display: block;
    width: 60%;
    height: 80%;
    padding: 40px;
    margin: auto;
    text-align: center;
    background-color: #383838;
    border-radius: 10px;
    box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
`
const LoadingContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
    height: 100%;
    width: 100%;
    `;
const spin = keyframes`
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
    `;

const Spinner = styled.div`
    border: 4px solid rgba(0, 0, 0, 0.1);
    border-left-color: #09f;
    border-radius: 50%;
    width: 100px;
    height: 100px;
    animation: ${spin} 1s linear infinite;
    `;

const ContentContainer = styled.div`
    align-items: center;
    height: 100%;
    width: 100%;
    background-color:white;
    border-radius: 10px;
    overflow: auto;
    `;

const ExitButton = styled.button`
    width: 50px;
    height: 30px;
    border-radius: 10px;
    border: none;
    &:hover {
        background-color: White;
        color: black;
    }
`

export default VPverifiyModal;