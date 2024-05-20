import React from "react";
import Loginpage from "./Pages/Loginpage";
import UserMainPage from "./Pages/UserMainPage";
import { BrowserRouter ,Route, Routes} from 'react-router-dom';
import CheckPrivateModal from "./Components/CheckPrivateModal";
import UserCreateVC from "./Pages/UserCreateVC";
import IssuerMainPage from "./Pages/IssuerMainPage";
import IssuerCheck from "./Pages/IssuerCheck";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Loginpage />}></Route>
        <Route path='/MainPage' element={<UserMainPage/>}></Route>
        <Route path='/Something' element={<UserCreateVC/>}></Route>
        <Route path='/IssuerMainPage' element={<IssuerMainPage/>}></Route>
        <Route path='/Check' element={<IssuerCheck/>}></Route>
      </Routes>
    </BrowserRouter>
      
  );
}

export default App;
