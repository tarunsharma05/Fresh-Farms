// src/components/Header.js
import React, { useState, useEffect, useRef, useContext } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUserCircle, faShoppingCart, faSearch } from '@fortawesome/free-solid-svg-icons';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { CartContext } from './context/CartContext'; // Ensure correct import path
import './style/Header.css';


function Header({isAdmin}) {
    const { cart } = useContext(CartContext); // Use the CartContext to get the cart data
    const [searchOpen, setSearchOpen] = useState(false);
    const [searchType, setSearchType] = useState(isAdmin ? 'customers' : 'products'); // Default search type based on isAdmin
    const [searchQuery, setSearchQuery] = useState('');
    const searchRef = useRef(null);
    const navigate = useNavigate();

    const toggleSearch = () => {
        setSearchOpen(!searchOpen);
    };

    const handleSearchChange = (event) => {
        setSearchQuery(event.target.value);
    };

    const handleSearchTypeChange = (event) => {
        setSearchType(event.target.value);
    };

    const handleSearchSubmit = (event) => {
        event.preventDefault();
        if (searchQuery.trim() !== '') {
            navigate(`/search-results?query=${searchQuery}&type=${searchType}`);
        }
    };

    const handleClickOutside = (event) => {
        if (searchRef.current && !searchRef.current.contains(event.target)) {
            setSearchOpen(false);
        }
    };

    useEffect(() => {
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, []);

    // Calculate the total number of items in the cart
    const totalItems = cart.items.reduce((total, item) => total + item.quantity, 0);

    return (
        <nav className="navbar navbar-expand-lg navbar-dark sticky-nav pr-2">
            <Link className="ml-3 navbar-brand" to="/">
                Green Grocers
            </Link>
            <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>

            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <ul className="navbar-nav mr-3 ml-auto">
                    <li className="nav-item active">
                        <NavLink exact to="/" className="nav-link">Home</NavLink>
                    </li>
                    <li className="nav-item active">
                        <NavLink exact to="/about" className="nav-link">About</NavLink>
                    </li>
                    <li className="nav-item active">
                        <NavLink exact to="/items" className="nav-link">Products</NavLink>
                    </li>
                </ul>

                <div ref={searchRef} className={`search-container ${searchOpen ? 'open' : ''}`}>
                    {!searchOpen && (
                        <FontAwesomeIcon icon={faSearch} className="search-icon mr-3" onClick={toggleSearch} />
                    )}
                    {searchOpen && (
                        <form className="form-inline" onSubmit={handleSearchSubmit}>
                            {isAdmin && (
                                <select className="form-control mr-sm-2" value={searchType} onChange={handleSearchTypeChange}>
                                    <option value="customers">Customers</option>
                                    <option value="products">Products</option>
                                    
                                </select>
                            )}
                            <input
                                className="form-control mr-sm-2"
                                type="search"
                                placeholder="Search"
                                aria-label="Search"
                                value={searchQuery}
                                onChange={handleSearchChange}
                            />
                            <button className="btn btn-primary my-2 my-sm-0" type="submit">Search</button>
                        </form>
                    )}
                </div>
                
                {!isAdmin &&
                <div className='navbar-nav'>
                    <NavLink to="/cart" className="nav-link px-2 text-white">
                        <FontAwesomeIcon icon={faShoppingCart} className="cart-icon" />
                        {totalItems > 0 && <span className="cart-badge">{totalItems}</span>}
                    </NavLink>
                </div> }

                {!isAdmin &&
                <ul className="navbar-nav">
                    <li className="nav-item dropdown">
                        <a className="nav-link dropdown-toggle" href="#" id="profileDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <FontAwesomeIcon icon={faUserCircle} size="lg" />
                        </a>
                        <div className="dropdown-menu dropdown-menu-right" aria-labelledby="profileDropdown">
                            <NavLink exact to="/profile" className="dropdown-item">Profile</NavLink>
                            <div className="dropdown-divider"></div>
                            <a className="dropdown-item" href="/">Logout</a>
                        </div>
                    </li>
                </ul>}

                {isAdmin&& 
                <NavLink to="/admin" className="nav-link px-2 text-white mr-3">
                    Admin
                </NavLink>
                }
            </div>
        </nav>
    );
}

export default Header;






