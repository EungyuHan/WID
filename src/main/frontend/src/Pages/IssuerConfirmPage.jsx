import styled,{keyframes} from 'styled-components';
import { useState, useMemo } from 'react';
import Button from '../Components/Button';
import axios from 'axios';
import CheckPrivateModal from '../Components/CheckPrivateModal';
import CheckCertificationModal from '../Components/CheckCertificationModal';
import ConfirmCertificationModal from '../Components/ConfirmCertificationModal';
import HelpModal from '../Components/HelpModal';


function IssuerConfirmPage(props) {
    const [PKchecked, setPKchecked] = useState(false);
    const [isOpen, setIsOpen] = useState(false);
    const [isModalopen, setModal] = useState(false);
    const [selectedCourse, setSelectedCourse] = useState('전체');
    const [isHelpClicked, setHelp] = useState(false);

    const toggleDropdown = () => {
      setIsOpen(!isOpen);
    };

    const toggleModal = () => {
      /* 제출확인 모달창이 뜨기전에 확인해야하는 필수 제출 요소들이 체크되었는지 확인. */
      setModal(!isModalopen);
  }
    const toggleHelp = () => {
      setHelp(!isHelpClicked);
  }



    const handleCourseSelect = (course) => {
        setSelectedCourse(course);
        setIsOpen(false);
      };

    const goToRef = (index) => {  
            index.current.scrollIntoView({ behavior: 'smooth' });
    };
    
    const mails = [
        { id: 1, sender: 'qwer1216914@gmail.com', subject: '[소프트웨어공학캡스톤프로젝트] 소프트웨어공학과 프로젝트 인증 요청합니다.', date: '2022-05-01' },
        { id: 2, sender: 'ivyksy0215@naver.com',  subject: '[데이터베이스] 소프트웨어공학과 프로젝트 인증 요청합니다.', date: '2022-05-02' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
    ];
    
      const handleMailClick = (id) => {
        console.log(`메일 ID ${id}를 클릭했습니다.`);
        // 이후에 선택된 메일에 대한 동작을 처리할 수 있습니다.
      };
      const filteredMails = useMemo(() => {
        if (selectedCourse === '전체') {
          return mails;
        } else {
          return mails.filter(mail => mail.subject.includes(`[${selectedCourse}]`));
        }
      }, [selectedCourse, mails]);
    
    
      return (
        <BackGround>
          <div style={{ zIndex: 1 }}>
            <LogoBar>
              <img src='img/logo.png' width={`102px`} height={`81px`} alt='Logo'></img>
            </LogoBar>
            <UpNavBar>
              <UpNavBarTop>
                <UpNavButton onClick={()=>{setPKchecked(true)}}>인증 요청</UpNavButton>
                <UpNavButton onClick={()=>{setPKchecked(true)}}>인증 확인</UpNavButton>
                <UpNavButton onClick={toggleHelp}>도움말</UpNavButton>
                <div style={{ display: 'flex', justifyContent: 'flex-end', alignItems: 'center', marginRight: '80px', flex: 1 }}>
                <Text>안녕하세요, <span style={{ borderBottom: '1px solid black' }}>사용자</span>님</Text>
                </div>
                </UpNavBarTop>
              <UpNavBarBottom>
        
             
            <Container>
            <Text> <span style={{ borderBottom: '1px solid white', color: 'white' }}>인증 처리 목록 <span style={{ color: '#cf242a' }}>20</span></span></Text>
            <div style={{ display: 'flex', justifyContent: 'flex-end', marginRight: '20px', marginTop: '20px' }}>
           
                </div>
                <ContentsContainer>
                    <InformationDiv>
                    
                    <MailList mails={filteredMails} handleMailClick={handleMailClick} />
                    
                  </InformationDiv>
                </ContentsContainer>
                </Container>
                <div style={{ textAlign: 'right', marginRight: '550px', marginTop: '200px' }}>
           
                </div>
              </UpNavBarBottom>
            </UpNavBar>
            
          </div>

          { PKchecked && (<CheckPrivateModal></CheckPrivateModal>)}
          { isHelpClicked && (<HelpModal onClose={toggleHelp}/>)}
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


const Text = styled.div`
  margin-top: 30px;
  margin-left: 50px;
  font-size: 18px;
  font-weight: bold;

`

// const YourComponent = ({ id, setID }) => {
//     const setPKchecked = () => {
//     };
// `
const LogoBar = styled.div`
    position: relative;
    width: auto;
    height: 10%;
    background-color: transparent;
    margin: 20px;
    margin-left: 10px;
`

const UpNavBar = styled.div`
    position:fixed;
    width: 80%;
    height: 100%;
    background-color: transparent;
    right: 0;
    top: 0;
`

const UpNavBarTop = styled.div`
    position:relative;
    top: 0; 
    width: 100%;
    height: 10%;
    background-color: transparent;
    display: flex;
    border-bottom: 3px solid black;
    right: 0;
`

const UpNavBarBottom = styled.div`
    position:fixed;
    width: 100%;
    height: 70%;
    background-color: transparent;
    display: flex;
    flex-direction: column;
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

const Container = styled.div`
    position: relative;
    top: 20%;
    left: 2%;
    background-color: #383838;
    width: 65%;
    height: 70%;
    border-radius: 5px;
    `
const ContentsContainer = styled.div`
    position: relative;
    background-color: rgba(0, 0, 0, 0.25);
    transform: translate(-50%);
    top:5%;
    left: 50%;
    width:100%;
    height:60%;
    justify-content: center;
    border-radius: 10px;
    overflow: auto;
`

const InformationDiv = styled.div`
    width: 100%;
    height: auto;
    background-color: transparent;
    margin: auto;
    overflow: hidden;
    border-radius: 5px;
    margin-top:10px;
    margin-bottom: 10px;
`;

const MailListContainer = styled.div`
  width: 100%;
  display: grid;
  grid-template-columns: auto; /* 각 열의 너비를 지정합니다. */
  grid-gap: 10px;
`;

const MailItem = styled.div`
  display: flex; /* 열을 가로로 배치합니다. */
    align-items: flex-start;
    grid-template-columns: auto auto auto auto;
    gap: 10px;
  position:auto;
  width: 100%;
  height: 100%;
  background-color: transparent;
  border-bottom: 1px solid white;
    
`;

const MailInfo = styled.div`
  white-space: nowrap;
  overflow: hidden; /* 텍스트가 넘칠 경우 숨깁니다. */
  text-overflow: ellipsis;
`;


const Subject = styled.div`
  margin: 0;
  font-size: 14px;
  font-weight: bold;
  color: white;
  white-space: nowrap;
  overflow: hidden; /* 텍스트가 넘칠 경우 숨깁니다. */
  text-overflow: ellipsis;
  width: 500px; 
`;

const Date = styled.div`
  margin: 0;
  margin-left: 60px;
  font-size: 14px;
  color: white;
`;

const Sender = styled.div`
  margin: 0;
  font-size: 14px;
  color: white;
  margin-left: 20px;
  width: 200px; 
`;





const MailList = ({ mails, handleMailClick }) => {
    return (
      <MailListContainer>
        {mails.map((mail) => (
          <MailItem key={mail.id} onClick={() => handleMailClick(mail.id)}>
            
              
              <MailInfo>
            <Sender>{mail.sender}</Sender>
          </MailInfo>
          <MailInfo>
            <Subject>{mail.subject}</Subject>
          </MailInfo>
          <MailInfo>
            <Date>{mail.date}</Date>
          </MailInfo>
          </MailItem>
        ))}
      </MailListContainer>
    //   <CreateUserButton onClick={makeUser}>인증하기</CreateUserButton>
    );
  };

  const CourseDropdown = styled.div`
  position: relative;
  top: 12%;
  margin-left: 2%;
  display: inline-block;
`;

const CourseDropdownButton = styled.button`
  background-color: #0A377C;
  color: white;
  padding: 16px;
  font-size: 16px;
  border: none;
  cursor: pointer;
  height: 50px;
`;

const CourseDropdownContent = styled.div`
  display: ${props => props.isOpen ? 'block' : 'none'};
  position: absolute;
  background-color: #f1f1f1;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;

  a {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;

    &:hover {
      background-color: #ddd;
    }
  }
`;

const Label = styled.label`
  margin-right: 8px;
  font-weight: bold;
`;




 
export default IssuerConfirmPage;