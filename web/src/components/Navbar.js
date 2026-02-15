import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import axios from 'axios';
import '../styles/Navbar.css';

function Navbar() {
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = async () => {
  try {
    const token = localStorage.getItem('token');  // ← Get real token
    
    await axios.post('http://localhost:8080/api/auth/logout', null, {
      params: { token: token }  // ← Send real token
    });
    
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    
    alert('Logged out successfully!');
    navigate('/login');
  } catch (error) {
    console.error('Logout error:', error);
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    navigate('/login');
  }
};

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <div className="navbar-brand">
          <h2 className="navbar-logo">User Auth App</h2>
        </div>
        
        <div className="navbar-menu">
          <button 
            className={`navbar-item ${location.pathname === '/dashboard' ? 'active' : ''}`}
            onClick={() => navigate('/dashboard')}
          >
            Dashboard
          </button>
          <button 
            className={`navbar-item ${location.pathname === '/profile' ? 'active' : ''}`}
            onClick={() => navigate('/profile')}
          >
            Profile
          </button>
          <button 
            className="navbar-logout-button"
            onClick={handleLogout}
          >
            Logout
          </button>
        </div>
      </div>
    </nav>
  );
}

export default Navbar;