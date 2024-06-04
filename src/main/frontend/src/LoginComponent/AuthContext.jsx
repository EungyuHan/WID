import React, { createContext, useState, useContext, useEffect } from 'react';



const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useState("토큰있음~");

    const login = (token) => {
    setToken(token);
    localStorage.setItem('authToken', token);
    };

    const logout = () => {
    setToken(null);
    localStorage.removeItem('authToken');
    };

    const loadToken = () => {
    const savedToken = localStorage.getItem('authToken');
    if (savedToken) {
        setToken(savedToken);
    }
    };

    useEffect(() => {
    loadToken();
    }, []);

    return (
    <AuthContext.Provider value={{ token, login, logout }}>
        {children}
    </AuthContext.Provider>
    );
};

export const useAuth = () => {
    return useContext(AuthContext);
};