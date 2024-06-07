import React from "react";
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Loginpage from "./Pages/Loginpage";
import UserMainPage from "./Pages/UserMainPage";
import UserCreateVC from "./Pages/UserCreateVC";
import IssuerMainPage from "./Pages/IssuerMainPage";
import IssuerCheck from "./Pages/IssuerCheck";
import IssuerConfirmPage from "./Pages/IssuerConfirmPage";


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Loginpage />} />
        <Route path='/MainPage' element={<UserMainPage />} />
        <Route path='/Something' element={<UserCreateVC />} />
        <Route path='/IssuerMainPage' element={<IssuerMainPage />} />
        <Route path='/Check' element={<IssuerCheck />} />
        <Route path='/Confirm' element={<IssuerConfirmPage />} />
        

        
      </Routes>
    </BrowserRouter>
  );
}

export default App;
