import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/Register.css';

function Register() {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: ''
  });
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
 
    const gmailRegex = /^[a-zA-Z0-9._%+-]+@gmail\.com$/;
    if (!gmailRegex.test(formData.email)) {
      setMessage('Please use a valid Gmail address (@gmail.com)');
      return;
    }


    if (formData.password !== formData.confirmPassword) {
      setMessage('Passwords do not match!');
      return;
    }
    
    try {
  
      const { confirmPassword, ...registrationData } = formData;
      
      const response = await axios.post('http://localhost:8080/api/auth/register', registrationData, {
        headers: {
          'Content-Type': 'application/json'
        }
      });
      
      alert('Registration successful! Please login.');
      navigate('/login');
    } catch (error) {
      setMessage('Registration failed: ' + (error.response?.data || error.message));
    }
  };

  return (
    <div className="register-container">
      <div className="register-card">
        <h2 className="register-title">Create Account</h2>
        <p className="register-subtitle">Sign up to get started</p>
        
        <form onSubmit={handleSubmit}>
          <div className="register-input-group">
            <label className="register-label">First Name</label>
            <input
              type="text"
              name="firstName"
              placeholder="Enter your first name"
              value={formData.firstName}
              onChange={handleChange}
              required
              className="register-input"
            />
          </div>

          <div className="register-input-group">
            <label className="register-label">Last Name</label>
            <input
              type="text"
              name="lastName"
              placeholder="Enter your last name"
              value={formData.lastName}
              onChange={handleChange}
              required
              className="register-input"
            />
          </div>

          <div className="register-input-group">
            <label className="register-label">Email Address</label>
            <input
              type="email"
              name="email"
              placeholder="Enter your email"
              value={formData.email}
              onChange={handleChange}
              required
              className="register-input"
            />
          </div>

          <div className="register-input-group">
            <label className="register-label">Password</label>
            <input
              type="password"
              name="password"
              placeholder="Enter your password"
              value={formData.password}
              onChange={handleChange}
              required
              className="register-input"
            />
          </div>

          <div className="register-input-group">
            <label className="register-label">Confirm Password</label>
            <input
              type="password"
              name="confirmPassword"
              placeholder="Re-enter your password"
              value={formData.confirmPassword}
              onChange={handleChange}
              required
              className="register-input"
            />
          </div>

          <button type="submit" className="register-button">
            Register
          </button>
        </form>

        {message && <p className="register-error-message">{message}</p>}

        <p className="register-footer">
          Already have an account? <a href="/login" className="register-link">Login here</a>
        </p>
      </div>
    </div>
  );
}

export default Register;