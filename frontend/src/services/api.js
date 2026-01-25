const BASE_URL = "http://localhost:8080";

/**
 * Core request helper
 * - Always sends cookies (session-based auth)
 * - Supports JSON + text responses
 * - Handles Spring Boot Status responses
 */
async function request(path, { method = "GET", body } = {}) {
  const options = {
    method,
    credentials: "include", // ⭐ REQUIRED FOR SESSION
    headers: {
      "Content-Type": "application/json",
    },
  };

  if (body !== undefined) {
    options.body = JSON.stringify(body);
  }

  const response = await fetch(`${BASE_URL}${path}`, options);

  // ❌ Not OK → throw proper error
  if (!response.ok) {
    let message = `Request failed with status ${response.status}`;

    try {
      const data = await response.json();
      if (data?.message) {
        message = data.message;
      }
    } catch (_) {
      // backend may return plain text
    }

    throw new Error(message);
  }

  // ✅ Response OK
  const text = await response.text();
  if (!text) return null;

  try {
    return JSON.parse(text);
  } catch (_) {
    // backend returned plain string
    return text;
  }
}

/* ======================
   AUTH
====================== */

export function login({ username, password }) {
  return request("/login", {
    method: "POST",
    body: { username, password },
  });
}

export function register({ username, password }) {
  return request("/register", {
    method: "POST",
    body: { username, password },
  });
}

export function logout() {
  return request("/logout", {
    method: "POST",
  });
}

/* ======================
   PROFILE
====================== */

export function getProfile(username) {
  return request(`/profile/${encodeURIComponent(username)}`);
}

export function updateProfile({ bio, profilePicUrl }) {
  return request("/profile", {
    method: "PUT",
    body: { bio, profilePicUrl },
  });
}

/* ======================
   POSTS
====================== */

export function createPost({ imageUrl, caption }) {
  return request("/posts", {
    method: "POST",
    body: { imageUrl, caption },
  });
}

export function deletePost(id) {
  return request(`/posts/${id}`, {
    method: "DELETE",
  });
}

export function getPostsByUser(username) {
  return request(`/posts/${encodeURIComponent(username)}`);
}

/* ======================
   FEED
====================== */

export function getFeed() {
  return request("/feed");
}

/* ======================
   FOLLOW
====================== */

export function followUser(username) {
  return request(`/follow/${encodeURIComponent(username)}`, {
    method: "POST",
  });
}

export function unfollowUser(username) {
  return request(`/follow/${encodeURIComponent(username)}`, {
    method: "DELETE",
  });
}

/* ======================
   LIKES
====================== */

export function likePost(id) {
  return request(`/posts/${id}/like`, {
    method: "POST",
  });
}

export function unlikePost(id) {
  return request(`/posts/${id}/like`, {
    method: "DELETE",
  });
}

export function getLikesCount(id) {
  return request(`/posts/${id}/likes/count`);
}
