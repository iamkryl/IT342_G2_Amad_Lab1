import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

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
      const response = await axios.post('http://localhost:8080/api/auth/login', null, {
        params: formData
      });
      
      // Save user data
      localStorage.setItem('user', JSON.stringify(response.data));
      
      alert('Login successful!');
      navigate('/dashboard');
    } catch (error) {
      setMessage('Login failed: ' + (error.response?.data || error.message));
    }
  };

  return (
    <div style={{ maxWidth: '400px', margin: '50px auto', padding: '20px', border: '1px solid #ccc', borderRadius: '5px' }}>
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={formData.email}
            onChange={handleChange}
            required
            style={{ width: '100%', padding: '10px', margin: '10px 0', boxSizing: 'border-box' }}
          />
        </div>
        <div>
          <input
            type="password"
            name="password"
            placeholder="Password"
            value={formData.password}
            onChange={handleChange}
            required
            style={{ width: '100%', padding: '10px', margin: '10px 0', boxSizing: 'border-box' }}
          />
        </div>
        <button type="submit" style={{ width: '100%', padding: '10px', background: '#28a745', color: 'white', border: 'none', cursor: 'pointer', borderRadius: '3px' }}>
          Login
        </button>
      </form>
      {message && <p style={{ color: 'red', marginTop: '10px' }}>{message}</p>}
      <p style={{ marginTop: '15px', textAlign: 'center' }}>
        Don't have an account? <a href="/register">Register here</a>
      </p>
    </div>
  );
}

export default Login;