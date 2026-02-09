import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

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

  const handleLogout = () => {
    localStorage.removeItem('user');
    alert('Logged out successfully!');
    navigate('/login');
  };

  if (!user) return <div>Loading...</div>;

  return (
    <div style={{ maxWidth: '600px', margin: '50px auto', padding: '20px', border: '1px solid #ccc', borderRadius: '5px' }}>
      <h2>Dashboard / Profile</h2>
      <div style={{ marginTop: '20px', background: '#f5f5f5', padding: '15px', borderRadius: '5px' }}>
        <p><strong>First Name:</strong> {user.firstName}</p>
        <p><strong>Last Name:</strong> {user.lastName}</p>
        <p><strong>Email:</strong> {user.email}</p>
      </div>
      <button 
        onClick={handleLogout}
        style={{ marginTop: '20px', padding: '10px 20px', background: '#dc3545', color: 'white', border: 'none', cursor: 'pointer', borderRadius: '3px' }}
      >
        Logout
      </button>
    </div>
  );
}

export default Dashboard;