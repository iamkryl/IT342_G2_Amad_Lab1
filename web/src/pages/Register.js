import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

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
    
    // Check if passwords match
    if (formData.password !== formData.confirmPassword) {
      setMessage('Passwords do not match!');
      return;
    }

    try {
      await axios.post('http://localhost:8080/api/auth/register', null, {
        params: {
          firstName: formData.firstName,
          lastName: formData.lastName,
          email: formData.email,
          password: formData.password
        }
      });
      alert('Registration successful!');
      navigate('/login');
    } catch (error) {
      setMessage('Registration failed: ' + (error.response?.data || error.message));
    }
  };

  return (
    <div style={{ maxWidth: '400px', margin: '50px auto', padding: '20px', border: '1px solid #ccc', borderRadius: '5px' }}>
      <h2>Register</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <input
            type="text"
            name="firstName"
            placeholder="First Name"
            value={formData.firstName}
            onChange={handleChange}
            required
            style={{ width: '100%', padding: '10px', margin: '10px 0', boxSizing: 'border-box' }}
          />
        </div>
        <div>
          <input
            type="text"
            name="lastName"
            placeholder="Last Name"
            value={formData.lastName}
            onChange={handleChange}
            required
            style={{ width: '100%', padding: '10px', margin: '10px 0', boxSizing: 'border-box' }}
          />
        </div>
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
        <div>
          <input
            type="password"
            name="confirmPassword"
            placeholder="Confirm Password"
            value={formData.confirmPassword}
            onChange={handleChange}
            required
            style={{ width: '100%', padding: '10px', margin: '10px 0', boxSizing: 'border-box' }}
          />
        </div>
        <button type="submit" style={{ width: '100%', padding: '10px', background: '#007bff', color: 'white', border: 'none', cursor: 'pointer', borderRadius: '3px' }}>
          Register
        </button>
      </form>
      {message && <p style={{ color: 'red', marginTop: '10px' }}>{message}</p>}
      <p style={{ marginTop: '15px', textAlign: 'center' }}>
        Already have an account? <a href="/login">Login here</a>
      </p>
    </div>
  );
}

export default Register;