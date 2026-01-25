import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { likePost, unlikePost, getLikesCount } from "../services/api.js";

function Post({ post }) {
  const [liked, setLiked] = useState(!!post.likedByCurrentUser); // TODO: confirm field name
  const [likes, setLikes] = useState(post.likesCount ?? 0);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const loadLikes = async () => {
      try {
        const data = await getLikesCount(post.id); // GET /posts/{id}/likes/count
        // TODO: adjust based on backend response shape
        if (typeof data === "number") {
          setLikes(data);
        } else if (data && typeof data.count === "number") {
          setLikes(data.count);
        }
      } catch (err) {
        // silently ignore like count errors for now
        console.error(err);
      }
    };

    if (post?.id != null) {
      loadLikes();
    }
  }, [post?.id]);

  const handleToggleLike = async () => {
    if (!post?.id) return;
    setLoading(true);
    const nextLiked = !liked;
    // optimistic update
    setLiked(nextLiked);
    setLikes((prev) => prev + (nextLiked ? 1 : -1));

    try {
      if (nextLiked) {
        await likePost(post.id); // POST /posts/{id}/like
      } else {
        await unlikePost(post.id); // DELETE /posts/{id}/like
      }
    } catch (err) {
      // rollback on error
      console.error(err);
      setLiked(!nextLiked);
      setLikes((prev) => prev + (nextLiked ? -1 : 1));
    } finally {
      setLoading(false);
    }
  };

  const authorUsername = post.username || post.authorUsername || "user"; // TODO: confirm author username field

  return (
    <article className="post">
      <header className="post-header">
        <Link to={`/profile/${encodeURIComponent(authorUsername)}`} className="post-author">
          @{authorUsername}
        </Link>
      </header>
      {post.imageUrl && (
        <div className="post-image-wrapper">
          <img src={post.imageUrl} alt={post.caption || "Post image"} className="post-image" />
        </div>
      )}
      <div className="post-body">
        {post.caption && <p>{post.caption}</p>}
      </div>
      <footer className="post-footer">
        <button
          type="button"
          className={`like-button ${liked ? "liked" : ""}`}
          onClick={handleToggleLike}
          disabled={loading}
        >
          {liked ? "Unlike" : "Like"} ({likes})
        </button>
      </footer>
    </article>
  );
}

export default Post;
