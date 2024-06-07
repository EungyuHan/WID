import React from "react";
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Loginpage from "./Pages/Loginpage";
import UserMainPage from "./Pages/UserMainPage";
import UserCreateVC from "./Pages/UserCreateVC";
import IssuerMainPage from "./Pages/IssuerMainPage";
import IssuerCheck from "./Pages/IssuerCheck";
import IssuerConfirmPage from "./Pages/IssuerConfirmPage";
import UserCreateVC from "./Pages/UserCreateVC";
import UserCreateVP from "./Pages/UserCreateVP";
import UserSendModal from "./Components/UserSendModal";
import ProtectedRoute from "./LoginComponent/ProtectedRoute";
import { AuthProvider } from "./LoginComponent/AuthContext";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<Loginpage />}></Route>
          <Route path="/MainPage" element={<ProtectedRoute component={UserMainPage} />} />
          <Route path="/CreateVC" element={<ProtectedRoute component={UserCreateVC} />} />
          <Route path="/CreateVP" element={<ProtectedRoute component={UserCreateVP} />} />
          <Route path="/Working" element={<ProtectedRoute component={UserSendModal} />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
    
  );
}

export default App;
