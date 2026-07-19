// Layout.js
import React, { useState } from 'react';

function Layout({ children }) {
    const [searchQuery, setSearchQuery] = useState('');
    const [searchResults, setSearchResults] = useState([]);

    const handleSearchChange = (event) => {
        setSearchQuery(event.target.value);
    };

    const handleSearchSubmit = async (event) => {
        event.preventDefault();
        try {
            // Perform search and update searchResults state
        } catch (error) {
            console.error('Error searching:', error);
        }
    };

    return (
        <div>
            <Header
                searchQuery={searchQuery}
                setSearchQuery={setSearchQuery}
                searchResults={searchResults}
                handleSearchChange={handleSearchChange}
                handleSearchSubmit={handleSearchSubmit}
            />
            <div className="content">{children}</div>
        </div>
    );
}

export default Layout;
