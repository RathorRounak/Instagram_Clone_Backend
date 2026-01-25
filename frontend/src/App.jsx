import React from "react";
import { Routes, Route, Link, Navigate, useNavigate } from "react-router-dom";
import Login from "./pages/Login.jsx";
import Register from "./pages/Register.jsx";
import Feed from "./pages/Feed.jsx";
import Profile from "./pages/Profile.jsx";
import { logout as logoutApi } from "./services/api.js";

function Layout({ children }) {
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      await logoutApi(); // POST /logout with credentials: "include"
    } catch (e) {
      // TODO: optionally show error message
      console.error(e);
    } finally {
      navigate("/login");
    }
  };

  return (
    <div className="app-container">
      <header className="app-header">
        <Link to="/" className="brand">
          Instagram Clone
        </Link>
        <nav className="nav-links">
          <Link to="/" className="nav-link">
            Feed
          </Link>
          <Link to="/login" className="nav-link">
            Login
          </Link>
          <Link to="/register" className="nav-link">
            Register
          </Link>
          <button type="button" className="nav-button" onClick={handleLogout}>
            Logout
          </button>
        </nav>
      </header>
      <main className="app-main">{children}</main>
    </div>
  );
}

function App() {
  return (
    <Routes>
      <Route
        path="/login"
        element={
          <Layout>
            <Login />
          </Layout>
        }
      />
      <Route
        path="/register"
        element={
          <Layout>
            <Register />
          </Layout>
        }
      />
      <Route
        path="/"
        element={
          <Layout>
            <Feed />
          </Layout>
        }
      />
      <Route
        path="/profile/:username"
        element={
          <Layout>
            <Profile />
          </Layout>
        }
      />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}

export default App;
