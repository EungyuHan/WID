import React,{ useState } from 'react';
import styled from 'styled-components';
import Button from './Button';
import axios from 'axios';

function UserSendPage(props) {
    const [search, setSearch] = useState("");
    const [selectedCompany, setSelected] = useState([]);

    let companyList = ["삼성", "LG", "네이버", "페이스북", "아마존", "정승 네트워크"];

    const addCompany = (company) => {
      if(selectedCompany.includes(company)){
        alert("이미 있어용");
      }
      else{
        const newList = [...selectedCompany, company];
        setSelected(newList);
      }
    }

    const deleteItem = (companyName) => {
      const newList = selectedCompany.filter(company => company !== companyName);
      setSelected(newList);
    }

    const sendServer = () => {
      const sendData = {verifierEmail:"test3@naver.com"}
      axios.post('http://localhost:8080/folder/1/send',sendData,{
        headers: {
            Authorization: `${localStorage.getItem('authToken')}`
        }
    })
    }

    const renderCompanyList = (search) => {
      if(!search){
        return companyList.map((company)=>(
          <CompanyDiv>
            <h5>로고</h5>
            <h4>{company}</h4>
            <button onClick={()=>{addCompany(company)}}>선택</button>
          </CompanyDiv>
      ))

      }
      else{
        const searchList = companyList.filter((data)=> data.includes(search));
        return searchList.map((company)=> (
          <CompanyDiv>
          <h5>로고</h5>
          <h4>{company}</h4>
          <button onClick={()=>{addCompany(company)}}>선택</button>
        </CompanyDiv>
        ))
      }
      
    }

    const renderSelectedCompany = () => {
      return selectedCompany.map((company)=> (
        <SelectedItem onClick={()=>{deleteItem(company)}}>{company}</SelectedItem>
      ))
    }


    return (
      <Modals>
        <ModalContent>
          <CompanyListDiv>
            <VCSearch>
              <Input placeholder='검색하고자 하는 회사 입력' onChange={(e)=>{setSearch(e.target.value);}}></Input>
            </VCSearch>
            {renderCompanyList(search)}
          </CompanyListDiv>

          <SelectedDiv>
            <h4>선택된 회사 리스트입니다.</h4>
            <SelectedListDiv>
            {renderSelectedCompany()}
            </SelectedListDiv>
            
            <Button name={"제출하기"} onClick={sendServer}></Button>
          </SelectedDiv>
        </ModalContent>
      </Modals>
    );
  }

  const Modals = styled.div`
  width: 100%;
  height: 100%;
  position: absolute;
  display: block;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.7);
  z-index:2;
`
const ModalContent = styled.div`
    display:flex;
    justify-content: space-around;
    position: relative;
    top: 10%;
    width: 40%;
    height: 70%;
    padding: 40px;
    margin: auto;
    text-align: center;
    background-color: #cacfd3;
    border-radius: 10px;
    box-shadow:0 2px 3px 0 rgba(34,36,38,0.15);
    overflow: none;
`

const CompanyListDiv = styled.div`
  position: relative;
  height: 100%;
  width: 50%;
  background-color: white;
  border-radius: 10px;
  overflow: auto;
`

const CompanyDiv = styled.div`
  position: relative;
  height:15%;
  width: 100%;
  color: white;
  background-color: #69737c;
  border-radius : 10px;
  display: flex;
  justify-content : space-evenly;
  align-items: center;
  margin-top:2%;
  box-shadow: 0.5px 0.5px 2px black;
`


const SelectedDiv = styled.div`
  position: relative;
  height: 100%;
  width: 40%;
  background-color: white;
  border-radius: 10px;
`

const SelectedListDiv = styled.div`
  position: relative;
  height: 70%;
  width: 100%;
  background-color: white; 
  border-radius: 10px;
  overflow: auto;
  
`
const SelectedItem = styled.button`
  position: relative;
  height: 20%;
  width: 90%;
  margin:auto;
  margin-top:2%;
  border-radius: 10px;
  border : none;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
  color: white;
  background-color: #384ce9;
  &:hover {
      background-color: #e05454;
      color: white;
    }
  box-shadow: 0.5px 0.5px 2px black;
`

const VCSearch = styled.div`
    width: 100%;
    height:15%;
    color: white;
    background-color: #b3b2b2;
    border-top-left-radius: 10px ;
    border-top-right-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
    box-shadow: 0.5px 0.5px 2px black;
`

const Input = styled.input`
padding: 8px 20px;
margin: 5px 10px;
border-radius: 5px;
border: none;
box-shadow: 0.5px 0.5px 2px black;
`

export default UserSendPage;
