package CLONE.Instagram.controller;

import CLONE.Instagram.DTO.*;
import CLONE.Instagram.service.BackendLogic;
import CLONE.Instagram.util.Authutil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)

@RestController
public class ApiController {

    private final BackendLogic bl;

    public ApiController(BackendLogic bl) {
        this.bl = bl;
    }

    @PostMapping("/register")
    public ResponseEntity<Status> register(@RequestBody Store store) {

        Status s = bl.register(store);

        if ("error".equals(s.getStatus())) {
            return ResponseEntity.badRequest().body(s);
        }
        return ResponseEntity.ok(s);
    }

    @PostMapping("/login")
    public ResponseEntity<Status> login(@RequestBody Store store, HttpSession session, HttpServletRequest req) {

        Status s = bl.login(store, session,req);

        if ("error".equals(s.getStatus())) {
            return ResponseEntity.badRequest().body(s);
        }
        return ResponseEntity.ok(s);
    }

    @PostMapping("/logout")
    public ResponseEntity<Status> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(new Status("success", "Successfully logged out"));
    }

    @PostMapping("/profile")
    public ResponseEntity<?> profile(@RequestBody ProfileEdit req, HttpSession session) {
        if (!Authutil.isLoggedIn(session)) {
            return ResponseEntity.badRequest()
                    .body(new Status("error", "Please Login first"));
        }
        Status s = bl.saveProfile(req, session);
        if ("error".equals(s.getStatus())) {
            return ResponseEntity.badRequest().body(s);
        }
        return ResponseEntity.ok(s);
    }

    @PostMapping("/follow/{username}")
    public ResponseEntity<Status> follow(
            @PathVariable String username,
            HttpSession session) {

        try{
            Status s = bl.followUser(username, session);
            return ResponseEntity.ok(s);
        }
        catch(Exception e){
            return ResponseEntity.status(401).build();
        }
    }
    @DeleteMapping("/unfollow/{username}")
    public ResponseEntity<Status> unfollow(
            @PathVariable String username,
            HttpSession session) {
        try {
            Status s = bl.unfollowUser(username, session);
            return ResponseEntity.ok(s);
        }

        catch(Exception e){
            return ResponseEntity.status(401).build();
        }

    }
    @GetMapping("/followers")
    public ResponseEntity<List<UsernameResponse>> followers(HttpSession session) {
        try {
            return ResponseEntity.ok(bl.getFollowers(session));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/following")
    public ResponseEntity<List<UsernameResponse>> following(HttpSession session) {
        try {
            return ResponseEntity.ok(bl.getFollowing(session));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/viewProfile")
    public ResponseEntity<?> viewProfile(HttpSession session) {
        try {
            UserProfileResponse p = bl.viewProfile(session);
            return ResponseEntity.ok(p);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
    @PostMapping("editProfile")
    public ResponseEntity<Status> editProfile(@RequestBody ProfileEdit req, HttpSession session) {
        Status s = bl.saveProfile(req,session);
        if("error".equals(s.getStatus())) {
            return ResponseEntity.badRequest().body(s);
        }
        return ResponseEntity.ok(s);
    }
    @PostMapping("/posts")
    public ResponseEntity<Status> createPost(
            @RequestBody CreatePostRequest req,
            HttpSession session) {
        try{
            Status s = bl.createPost(req, session);
            return ResponseEntity.ok(s);
        }
        catch(Exception e){
            return ResponseEntity.status(401).build();
        }
    }
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Status> deletePost(
            @PathVariable Long id,
            HttpSession session) {

        Status s = bl.deletePost(id, session);

        if ("error".equals(s.getStatus())) {
            return ResponseEntity.badRequest().body(s);
        }

        return ResponseEntity.ok(s);
    }

    @GetMapping("/posts/{username}")
    public List<PostResponse> userPosts(@PathVariable String username) {
        return bl.getUserPosts(username);
    }

    @GetMapping("/feed")
    public ResponseEntity<?> feed(HttpSession session) {
        try {
            return ResponseEntity.ok(bl.getFeed(session));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Login required");
        }
    }

    @PostMapping("/posts/{id}/like")
    public ResponseEntity<Status> likePost(
            @PathVariable Long id,
            HttpSession session) {

        Status s = bl.likePost(id, session);

        if ("error".equals(s.getStatus())) {
            return ResponseEntity.badRequest().body(s);
        }

        return ResponseEntity.ok(s);
    }

    @DeleteMapping("/posts/{id}/like")
    public ResponseEntity<Status> unlikePost(
            @PathVariable Long id,
            HttpSession session) {

        Status s = bl.unlikePost(id, session);

        if ("error".equals(s.getStatus())) {
            return ResponseEntity.badRequest().body(s);
        }

        return ResponseEntity.ok(s);
    }

    @GetMapping("/debug/session")
    public String debug(HttpSession session) {
        return String.valueOf(session.getAttribute("username"));
    }

    @GetMapping("/debug/session-id")
    public String sessionId(HttpSession session) {
        return session.getId();
    }






}
