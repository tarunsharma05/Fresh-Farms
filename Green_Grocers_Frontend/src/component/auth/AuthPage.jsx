
import React, { useState, useEffect } from 'react';
import './AuthPage.css';

const AuthPage = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    username: '', // Add username state for sign-up
    name: '', // Add name state for sign-up
    phone: '', // Add phone state for sign-up
    confirmPassword: '', // Add confirmPassword state for sign-up
  });
  const [errors, setErrors] = useState({});

  const toggleForm = () => {
    setIsLogin(!isLogin);
    setErrors({}); // Clear any previous errors when toggling the form
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validateForm(formData);
    if (Object.keys(validationErrors).length === 0) {
      // Form is valid, proceed with submission
      console.log('Form data:', formData);
    } else {
      // Form has validation errors, set errors state
      setErrors(validationErrors);
    }
  };

  const validateForm = (data) => {
    const errors = {};
    if (!data.email) {
      errors.email = 'Email is required';
    }
    if (!data.password) {
      errors.password = 'Password is required';
    }
    if (!isLogin) {
      if (!data.username) {
        errors.username = 'Username is required';
      }
      if (!data.name) {
        errors.name = 'Name is required';
      }
      if (!data.phone) {
        errors.phone = 'Phone is required';
      }
      if (!data.confirmPassword) {
        errors.confirmPassword = 'Confirm Password is required';
      } else if (data.password !== data.confirmPassword) {
        errors.confirmPassword = 'Passwords do not match';
      }
    }
    return errors;
  };

  useEffect(() => {
    // Hide the navbar when on the AuthPage
    const navbar = document.querySelector('.navbar');
    if (navbar) {
      navbar.style.display = 'none';
    }
    return () => {
      if (navbar) {
        navbar.style.display = '';
      }
    };
  }, []);

  return (
    <div className="auth-container">
      <div className={`form-container ${isLogin ? 'login' : 'signup'}`}>
        <div className="form-content">
          <form className={isLogin ? 'login-form' : 'signup-form'} onSubmit={handleSubmit}>
            <h2>{isLogin ? 'Login' : 'Sign Up'}</h2>
            {errors.email && <p className="error-message">{errors.email}</p>}
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={formData.email}
              onChange={handleInputChange}
            />
            {errors.password && <p className="error-message">{errors.password}</p>}
            <input
              type="password"
              name="password"
              placeholder="Password"
              value={formData.password}
              onChange={handleInputChange}
              
            />
            {!isLogin && (
              <>
                {errors.username && <p className="error-message">{errors.username}</p>}
                <input
                  type="text"
                  name="username"
                  placeholder="Username"
                  value={formData.username}
                  onChange={handleInputChange}
                  
                />
                {errors.name && <p className="error-message">{errors.name}</p>}
                <input
                  type="text"
                  name="name"
                  placeholder="Name"
                  value={formData.name}
                  onChange={handleInputChange}
                  
                />
                {errors.phone && <p className="error-message">{errors.phone}</p>}
                <input
                  type="text"
                  name="phone"
                  placeholder="Phone"
                  value={formData.phone}
                  onChange={handleInputChange}
                  
                />
                {errors.confirmPassword && <p className="error-message">{errors.confirmPassword}</p>}
                <input
                  type="password"
                  name="confirmPassword"
                  placeholder="Confirm Password"
                  value={formData.confirmPassword}
                  onChange={handleInputChange}
                  
                />
              </>
            )}
            <button type="submit">{isLogin ? 'Log In' : 'Sign Up'}</button>
          </form>
        </div>
        <div className="alternative-content">
          {isLogin ? (
            <div>
              <h2>Welcome Back!</h2>
              <p>To keep connected with us, please login with your personal info</p>
              <button onClick={toggleForm}>Sign Up</button>
            </div>
          ) : (
            <div>
              <h2>Hello, Friend!</h2>
              <p>Enter your personal details and start journey with us</p>
              <button onClick={toggleForm}>Log In</button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default AuthPage;


