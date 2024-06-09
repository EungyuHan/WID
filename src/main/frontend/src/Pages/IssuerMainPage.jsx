import styled from 'styled-components';
import { useState, useMemo , useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '../Components/Button';
import axios from 'axios';
import HelpModal from '../Components/HelpModal';
import Waves from '../Components/Waves';

function IssuerMainPage(props) {
    const [isOpen, setIsOpen] = useState(false);
    const [isModalopen, setModal] = useState(false);
    const [selectedCourse, setSelectedCourse] = useState('전체');
    const [isHelpClicked, setHelp] = useState(false);
    const [isOK , setOK] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        //처음에 요청받은 데이터를 불러오기 위한 axios코드 
    }, [])

    const toggleDropdown = () => {
      setIsOpen(!isOpen);
    };


    const toggleHelp = () => {
      setHelp(!isHelpClicked);
  }

  const toggleOK = () => {
    setOK(!isOK);
  }

    const handleCourseSelect = (course) => {
        setSelectedCourse(course);
        setIsOpen(false);
      };


    const mails = [
        { id: 1, sender: 'qwer1216914@gmail.com', subject: '[소프트웨어공학캡스톤프로젝트] 소프트웨어공학과 프로젝트 인증 요청합니다.', date: '2022-05-01' },
        { id: 2, sender: 'ivyksy0215@naver.com',  subject: '[데이터베이스] 소프트웨어공학과 프로젝트 인증 요청합니다.', date: '2022-05-02'},
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03' },
        { id: 3, sender: 'nam4867pp@gmail.com', subject: '[학부연구생] 소프트웨어공학과 논문 작성 자료 제출합니다.', date: '2022-05-03'},
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

      const handleNavigate = () => {
        const data = { userId: '싸발', token: 'abc123' };
        navigate("/AdminCheckPage", { state: data });
      }


      const filteredMails = useMemo(() => {
        if (selectedCourse === '전체') {
          return mails;
        } else {
          return mails.filter(mail => mail.subject.includes(`[${selectedCourse}]`));
        }
      }, [selectedCourse, mails]);

      

      const MailList = (props) => {
        return (
          <MailListContainer>
            {props.mails.map((mail) => (
              <MailItem key={mail.id} onClick={() => {handleNavigate()}}>
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
        );
      };
    
    
      return (
        <BackGround>
          <div style={{ zIndex: 1 }}>
            <LogoBar>
              <img src='img/logo.png' width={`102px`} height={`81px`} alt='Logo'></img>
            </LogoBar>
            <UpNavBar>
              <UpNavBarTop>
                <UpNavButton>인증 요청</UpNavButton>
                <UpNavButton onClick={()=> {navigate('/AdminConfirmPage')}}>인증 확인</UpNavButton>
                <UpNavButton onClick={toggleHelp}>도움말</UpNavButton>
                <div style={{ display: 'flex', justifyContent: 'flex-end', alignItems: 'center', marginRight: '80px', flex: 1 }}>
                <Text>안녕하세요, <span style={{ borderBottom: '1px solid black' }}>사용자</span>님</Text>
                </div>
                </UpNavBarTop>
              <UpNavBarBottom>
        
              <CourseDropdown>
              
              <Label htmlFor="dropdown">요청명</Label>
              <CourseDropdownButton onClick={toggleDropdown}>
                {selectedCourse}
              </CourseDropdownButton>
              <CourseDropdownContent isOpen={isOpen}>
                <a href="#" onClick={() => handleCourseSelect('전체')}>전체</a>
                <a href="#" onClick={() => handleCourseSelect('소프트웨어공학캡스톤프로젝트')}>소프트웨어공학캡스톤프로젝트</a>
                <a href="#" onClick={() => handleCourseSelect('데이터베이스')}>데이터베이스</a>
                <a href="#" onClick={() => handleCourseSelect('학부연구생')}>학부연구생</a>
              </CourseDropdownContent>
            </CourseDropdown>
            <Container>
            <Text> <span style={{ borderBottom: '1px solid white', color: 'white' }}>전체 요청 <span style={{ color: '#cf242a' }}>{mails.length}</span></span></Text>
            <div style={{ display: 'flex', justifyContent: 'flex-end', marginRight: '20px', marginTop: '20px' }}>
                </div>
                <ContentsContainer>
                    <InformationDiv>
                    
                    <MailList mails={filteredMails} handleNavigate = {handleNavigate}/>
                    
                  </InformationDiv>
                </ContentsContainer>
                </Container>
              </UpNavBarBottom>
            </UpNavBar>
            
          </div>
          { isHelpClicked && (<HelpModal onClose={toggleHelp}/>)}
        <Waves/>
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
    
    margin-top:10px;
    margin-bottom: 10px;
`;

const MailListContainer = styled.div`
  width: 100%;
  display: grid;
  grid-template-columns: auto;
  grid-gap: 10px;
`;

const MailItem = styled.div`
  display: flex; 
    align-items: flex-start;
    grid-template-columns: auto auto auto auto;
    gap: 10px;
  position:auto;
  width: 100%;
  height: 100%;
  background-color: transparent;
  border-bottom: 1px solid white;
  &:hover {
      background-color: #ffffff40;
      color: black;
    }
    
`;

const MailInfo = styled.div`
  white-space: nowrap;
  overflow: hidden; 
  text-overflow: ellipsis;
`;


const Subject = styled.div`
  margin: 0;
  font-size: 14px;
  font-weight: bold;
  color: white;
  white-space: nowrap;
  overflow: hidden; 
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
  margin-right: 5px;
  width: 200px; 
`;


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


export default IssuerMainPage;