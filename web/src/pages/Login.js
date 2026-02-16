import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../styles/Login.css';

function Login() {
  const [formData, setFormData] = useState({
    email: '',
    password: ''
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
    try {
      const response = await axios.post('http://localhost:8080/api/auth/login', formData, {
      headers: {
        'Content-Type': 'application/json'
      }
    });

    localStorage.setItem('token', response.data.token);
    localStorage.setItem('user', JSON.stringify(response.data));

    alert('Login successful!');
    navigate('/dashboard');
    } catch (error) {
      setMessage('Login failed: ' + (error.response?.data || error.message));
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <h2 className="login-title">Welcome Back</h2>
        <p className="login-subtitle">Login to your account</p>
        
        <form onSubmit={handleSubmit}>
          <div className="login-input-group">
            <label className="login-label">Email Address</label>
            <input
              type="email"
              name="email"
              placeholder="Enter your email"
              value={formData.email}
              onChange={handleChange}
              required
              className="login-input"
            />
          </div>

          <div className="login-input-group">
            <label className="login-label">Password</label>
            <input
              type="password"
              name="password"
              placeholder="Enter your password"
              value={formData.password}
              onChange={handleChange}
              required
              className="login-input"
            />
          </div>

          <button type="submit" className="login-button">
            Login
          </button>
        </form>

        {message && <p className="login-error-message">{message}</p>}

        <p className="login-footer">
          Don't have an account? <a href="/register" className="login-link">Register here</a>
        </p>
      </div>
    </div>
  );
}

export default Login;