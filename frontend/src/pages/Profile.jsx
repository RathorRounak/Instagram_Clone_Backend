import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  getProfile,
  getPostsByUser,
  followUser,
  unfollowUser,
} from "../services/api.js";
import Post from "../components/Post.jsx";

function Profile() {
  const { username } = useParams();
  const [profile, setProfile] = useState(null);
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [followLoading, setFollowLoading] = useState(false);

  useEffect(() => {
    const loadData = async () => {
      setLoading(true);
      setError("");
      try {
        const [profileData, postsData] = await Promise.all([
          getProfile(username), // GET /profile/{username}
          getPostsByUser(username), // GET /posts/{username}
        ]);
        // TODO: adjust field names based on backend profile/ post models
        setProfile(profileData || null);
        setPosts(Array.isArray(postsData) ? postsData : postsData?.posts || []);
      } catch (err) {
        setError(err.message || "Failed to load profile");
      } finally {
        setLoading(false);
      }
    };

    if (username) {
      loadData();
    }
  }, [username]);

  const isFollowing = !!profile?.following; // TODO: confirm field for follow state

  const handleToggleFollow = async () => {
    if (!username || !profile) return;
    setFollowLoading(true);
    try {
      if (isFollowing) {
        await unfollowUser(username); // DELETE /follow/{username}
        setProfile((prev) => (prev ? { ...prev, following: false } : prev));
      } else {
        await followUser(username); // POST /follow/{username}
        setProfile((prev) => (prev ? { ...prev, following: true } : prev));
      }
    } catch (err) {
      // TODO: optionally surface follow/unfollow errors
      console.error(err);
    } finally {
      setFollowLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="page profile-page">
        <p>Loading profile...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="page profile-page">
        <p className="error-text">{error}</p>
      </div>
    );
  }

  if (!profile) {
    return (
      <div className="page profile-page">
        <p>Profile not found.</p>
      </div>
    );
  }

  return (
    <div className="page profile-page">
      <section className="card profile-header">
        <div className="avatar-large">{username.charAt(0).toUpperCase()}</div>
        <div className="profile-info">
          <h2>@{username}</h2>
          <p>{profile.bio || "No bio yet."}</p>
          <div className="profile-stats">
            <span>{profile.postsCount ?? posts.length} posts</span>
            <span>{profile.followersCount ?? 0} followers</span>
            <span>{profile.followingCount ?? 0} following</span>
          </div>
        </div>
        <button
          type="button"
          className="secondary-button"
          onClick={handleToggleFollow}
          disabled={followLoading}
        >
          {isFollowing ? "Unfollow" : "Follow"}
        </button>
      </section>

      <section className="card list-card">
        <h3>Posts</h3>
        {posts.length === 0 && <p>No posts yet.</p>}
        <div className="post-list">
          {posts.map((post) => (
            <Post key={post.id} post={post} />
          ))}
        </div>
      </section>
    </div>
  );
}

export default Profile;
