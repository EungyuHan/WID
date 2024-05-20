import styled,{keyframes} from 'styled-components';
import { useState, useMemo } from 'react';
import Button from '../Components/Button';
import axios from 'axios';
import CheckPrivateModal from '../Components/CheckPrivateModal';
// import MailList from './MailList'
// import { useSelector } from 'react-redux';



function IssuerMainPage(props) {
    const [PKchecked, setPKchecked] = useState(false);
    const [isOpen, setIsOpen] = useState(false);
    const [selectedCourse, setSelectedCourse] = useState('전체');
    //const id = useSelector((state) => state.id);
    const toggleDropdown = () => {
      setIsOpen(!isOpen);
    };

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
                <div style={{ display: 'flex', justifyContent: 'flex-end', alignItems: 'center', marginRight: '100px', flex: 1 }}>
                <Text>안녕하세요, <span style={{ borderBottom: '1px solid black' }}>love123@gmail.com</span>님</Text>
                </div>
                </UpNavBarTop>
              <UpNavBarBottom>
              <Text> <span style={{ borderBottom: '1px solid black' }}>전체 요청 <span style={{ color: '#0A377C' }}>20</span></span></Text>
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
                <ContentsContainer>
                    <InformationDiv>
                    <MailList mails={filteredMails} handleMailClick={handleMailClick} />
                    
                  </InformationDiv>
                </ContentsContainer>
                <div style={{ textAlign: 'right', marginRight: '550px', marginTop: '200px' }}>
                  <Button onClick={()=>{setPKchecked(true)}} name="인증하기"/>
                </div>
              </UpNavBarBottom>
            </UpNavBar>
            
          </div>
          { PKchecked && (<CheckPrivateModal></CheckPrivateModal>)}
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
    width: 75%;
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
    width: 30%;
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
const ContentsContainer = styled.div`
    position: relative;
    background-color: transparent;
    left:5%;
    top:20%;
    width:60%;
    height:50%;
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
  margin-right: 5px;
  width: 200px; 
`;


const Checkbox = styled.input.attrs({ type: 'checkbox' })`
  margin-right: 10px;
  margin-left: 10px;
`;


const MailList = ({ mails, handleMailClick }) => {
    return (
      <MailListContainer>
        {mails.map((mail) => (
          <MailItem key={mail.id} onClick={() => handleMailClick(mail.id)}>
            
              <Checkbox />
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
  top: 5%;
  margin-left: 5%;
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