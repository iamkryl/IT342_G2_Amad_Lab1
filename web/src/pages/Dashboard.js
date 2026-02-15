import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import '../styles/Dashboard.css';

function Dashboard() {
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

  if (!user) return <div className="dashboard-loading">Loading...</div>;

  return (
    <div>
      <Navbar />
      <div className="dashboard-container">
        <div className="dashboard-content">
          <div className="dashboard-welcome-banner">
            <h1 className="dashboard-welcome-title">
              Welcome back, {user.firstName}! 👋
            </h1>
            <p className="dashboard-welcome-subtitle">
              Here's your account overview
            </p>
          </div>

          <div className="dashboard-card">
            <h2 className="dashboard-card-title">Quick Stats</h2>
            
            <div className="dashboard-stats-grid">
              <div className="dashboard-stat-card">
                <div className="dashboard-stat-icon">📧</div>
                <div className="dashboard-stat-content">
                  <p className="dashboard-stat-label">Email</p>
                  <p className="dashboard-stat-value">{user.email}</p>
                </div>
              </div>

              <div className="dashboard-stat-card">
                <div className="dashboard-stat-icon">✅</div>
                <div className="dashboard-stat-content">
                  <p className="dashboard-stat-label">Status</p>
                  <p className="dashboard-stat-value dashboard-status-active">Active</p>
                </div>
              </div>

              <div className="dashboard-stat-card">
                <div className="dashboard-stat-icon">👤</div>
                <div className="dashboard-stat-content">
                  <p className="dashboard-stat-label">Full Name</p>
                  <p className="dashboard-stat-value">{user.firstName} {user.lastName}</p>
                </div>
              </div>
            </div>
          </div>

          <div className="dashboard-card">
            <h2 className="dashboard-card-title">Account Information</h2>
            
            <div className="dashboard-info-row">
              <span className="dashboard-info-label">First Name:</span>
              <span className="dashboard-info-value">{user.firstName}</span>
            </div>
            
            <div className="dashboard-info-row">
              <span className="dashboard-info-label">Last Name:</span>
              <span className="dashboard-info-value">{user.lastName}</span>
            </div>
            
            <div className="dashboard-info-row">
              <span className="dashboard-info-label">Email Address:</span>
              <span className="dashboard-info-value">{user.email}</span>
            </div>
            
            <div className="dashboard-info-row">
              <span className="dashboard-info-label">Account Status:</span>
              <span className="dashboard-info-value dashboard-status-active">Active</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;