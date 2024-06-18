import React from "react";
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Loginpage from "./Pages/Loginpage";
import UserMainPage from "./Pages/UserMainPage";
import UserCreateVC from "./Pages/UserCreateVC";
import IssuerMainPage from "./Pages/IssuerMainPage";
import IssuerCheck from "./Pages/IssuerCheck";
import IssuerConfirmPage from "./Pages/IssuerConfirmPage";
import UserCreateVP from "./Pages/UserCreateVP";
import ProtectedRoute from "./LoginComponent/ProtectedRoute";
import { AuthProvider } from "./LoginComponent/AuthContext";
import VerifierPage from "./Pages/VerifierPage";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<Loginpage />}/>
          <Route path="/MainPage" element={<ProtectedRoute component={UserMainPage} />} />
          <Route path="/CreateVC" element={<ProtectedRoute component={UserCreateVC} />} />
          <Route path="/CreateVP" element={<ProtectedRoute component={UserCreateVP} />} />
          <Route path="/AdminPage" element={<ProtectedRoute component={IssuerMainPage}/>}/ >
          <Route path="/AdminConfirmPage" element={<ProtectedRoute component={IssuerConfirmPage}/>}/>
          <Route path="/AdminCheckPage" element={<ProtectedRoute component={IssuerCheck}/>}/>
          <Route path="/VerifierPage" element={<ProtectedRoute component={VerifierPage} />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
    
  );
}

export default App;
