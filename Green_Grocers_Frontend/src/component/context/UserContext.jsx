import React, { createContext, useContext, useState } from 'react';

// Create a context for the user
const UserContext = createContext();

// Create a provider component
export const UserProvider = ({ children }) => {
    // State to store the customer ID
    const [customerId, setCustomerId] = useState(2); // Replace with actual logic to get customerId

    // State to store the admin status
    const isAdmin = false;

    return (
        // Provide the user context to children components
        <UserContext.Provider value={{ customerId, setCustomerId, isAdmin }}>
            {children}
        </UserContext.Provider>
    );
};

// Custom hook to use the UserContext
export const useUser = () => {
    try {
        // Try to get the context value
        return useContext(UserContext);
    } catch (error) {
        // Handle any errors that occur while getting the context
        console.error('Error using UserContext:', error);
        // You might want to return a default value or throw the error again
        throw error;
    }
};
