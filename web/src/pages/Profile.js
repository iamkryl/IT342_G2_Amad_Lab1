import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import '../styles/Profile.css';

function Profile() {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const userData = localStorage.getItem('user');
    if (!userData) {
      navigate('/login');
    } else {
      setUser(JSON.parse(userData));
    }
  }, [navigate]);

  if (!user) return <div className="profile-loading">Loading...</div>;

  return (
    <div>
      <Navbar />
      <div className="profile-container">
        <div className="profile-content">
          <div className="profile-header">
            <div className="profile-avatar">
              {user.firstName.charAt(0)}{user.lastName.charAt(0)}
            </div>
            <h1 className="profile-name">{user.firstName} {user.lastName}</h1>
            <p className="profile-email">{user.email}</p>
          </div>

          <div className="profile-card">
            <h2 className="profile-card-title">Personal Information</h2>
            
            <div className="profile-info-row">
              <span className="profile-info-label">First Name:</span>
              <span className="profile-info-value">{user.firstName}</span>
            </div>
            
            <div className="profile-info-row">
              <span className="profile-info-label">Last Name:</span>
              <span className="profile-info-value">{user.lastName}</span>
            </div>
            
            <div className="profile-info-row">
              <span className="profile-info-label">Email Address:</span>
              <span className="profile-info-value">{user.email}</span>
            </div>
            
            <div className="profile-info-row">
              <span className="profile-info-label">User ID:</span>
              <span className="profile-info-value">{user.token?.replace('dummy-token-', '') || 'N/A'}</span>
            </div>
            
            <div className="profile-info-row">
              <span className="profile-info-label">Account Status:</span>
              <span className="profile-status-active">Active</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Profile;