import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from './AuthContext';

const ProtectedRoute = ({ component: Component }) => {
    const { token } = useAuth();

    return token ? <Component /> : <Navigate to="/" />;
};

export default ProtectedRoute;