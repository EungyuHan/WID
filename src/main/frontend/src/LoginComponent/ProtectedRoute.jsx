import React from 'react';
import { Route, Navigate } from 'react-router-dom';
import { useAuth } from './AuthContext.jsx';

const ProtectedRoute = ({ component: Component, ...rest }) => {
const { token } = useAuth();

return (
    <Route
    {...rest}
    element={token ? <Component {...rest} /> : <Navigate to="/" />}
    />
);
};

export default ProtectedRoute;