import React, { createContext, useState, useContext, useEffect } from 'react';
import axios from 'axios';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
const [token, setToken] = useState(null);

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