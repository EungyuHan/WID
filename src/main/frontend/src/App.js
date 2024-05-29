import React from "react";
import Loginpage from "./Pages/Loginpage";
import UserMainPage from "./Pages/UserMainPage";
import { BrowserRouter ,Route, Routes} from 'react-router-dom';
import CheckPrivateModal from "./Components/CheckPrivateModal";
import UserCreateVC from "./Pages/UserCreateVC";
import UserCreateVP from "./Pages/UserCreateVP";
import UserSendPage from "./Pages/UserSendPage";
import ProtectedRoute from "./LoginComponent/ProtectedRoute";
import { AuthProvider } from "./LoginComponent/AuthContext";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path='/' element={<Loginpage />}></Route>
          <Route path='/MainPage' element={<UserMainPage/>}></Route>
          <Route path='/CreateVC' element={<UserCreateVC/>}></Route>
          <Route path='/CreateVP' element={<UserCreateVP/>}></Route>
          <Route path='/Working' element={<UserSendPage/>}></Route>
          {/* <Route path='/MainPage' element={<ProtectedRoute component={UserMainPage}/>} />
          <Route path='/CreateVC' element={<ProtectedRoute component={UserCreateVC} />} />
          <Route path='/CreateVP' element={<ProtectedRoute component={UserCreateVP} />} />
          <Route path='/Working' element={<ProtectedRoute component={UserSendPage} />} /> */}
        </Routes>
      </BrowserRouter>
    </AuthProvider>
    
      
  );
}

export default App;
