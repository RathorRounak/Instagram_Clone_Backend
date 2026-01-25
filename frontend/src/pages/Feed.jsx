import React, { useEffect, useState } from "react";
import { getFeed, createPost } from "../services/api.js";
import Post from "../components/Post.jsx";

function Feed() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [imageUrl, setImageUrl] = useState("");
  const [caption, setCaption] = useState("");
  const [creating, setCreating] = useState(false);
  const [createError, setCreateError] = useState("");

  const loadFeed = async () => {
    setLoading(true);
    setError("");
    try {
      const data = await getFeed(); // GET /feed
      // TODO: adjust if backend wraps posts in a field (e.g. { content: [...] })
      setPosts(Array.isArray(data) ? data : data?.posts || []);
    } catch (err) {
      setError(err.message || "Failed to load feed");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadFeed();
  }, []);

  const handleCreatePost = async (e) => {
    e.preventDefault();
    setCreateError("");
    setCreating(true);
    try {
      const created = await createPost({ imageUrl, caption }); // POST /posts
      // TODO: adjust based on backend create response structure
      const newPost = created || { id: Date.now(), imageUrl, caption };
      setPosts((prev) => [newPost, ...prev]);
      setImageUrl("");
      setCaption("");
    } catch (err) {
      setCreateError(err.message || "Failed to create post");
    } finally {
      setCreating(false);
    }
  };

  return (
    <div className="page feed-page">
      <section className="card form-card">
        <h2>Create Post</h2>
        <form onSubmit={handleCreatePost}>
          <label className="form-field">
            <span>Image URL</span>
            <input
              type="url"
              value={imageUrl}
              onChange={(e) => setImageUrl(e.target.value)}
              required
            />
          </label>
          <label className="form-field">
            <span>Caption</span>
            <textarea
              value={caption}
              onChange={(e) => setCaption(e.target.value)}
              rows={2}
              required
            />
          </label>
          {createError && <p className="error-text">{createError}</p>}
          <button type="submit" className="primary-button" disabled={creating}>
            {creating ? "Posting..." : "Post"}
          </button>
        </form>
      </section>

      <section className="card list-card">
        <h2>Feed</h2>
        {loading && <p>Loading posts...</p>}
        {error && <p className="error-text">{error}</p>}
        {!loading && !error && posts.length === 0 && <p>No posts yet.</p>}
        <div className="post-list">
          {posts.map((post) => (
            <Post key={post.id} post={post} />
          ))}
        </div>
      </section>
    </div>
  );
}

export default Feed;
