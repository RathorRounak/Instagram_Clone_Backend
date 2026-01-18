package CLONE.Instagram.service;

import CLONE.Instagram.DTO.*;
import CLONE.Instagram.entity.Follow;
import CLONE.Instagram.entity.Users;
import CLONE.Instagram.repository.FollowRepository;
import CLONE.Instagram.repository.UserRepository;
import CLONE.Instagram.util.Authutil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BackendLogic {
    private final UserRepository ur ;
    private final Map<String, Integer> loginAttempts = new HashMap<>();
    private final FollowRepository fp;
    public BackendLogic(UserRepository ur, FollowRepository fp) {
        this.ur=ur;
        this.fp = fp;
    }
    public Status register(Store store){
        if(store.getUsername() ==null || store.getPassword()==null){
            return new Status("error", "Username and password cannot be null");
        }
        if(store.getPassword().length()<9){
            return new Status("error","Length must be greater than 7");
        }
        if(store.getUsername().length()<7){
            return new Status("error","Length must be greater than 7");
        }
        if(store.getUsername().equals(ur.findByUsername(store.getUsername()))){
            return new Status("error","Username already exists");
        }
        Users user = new Users();
        user.setUsername(store.getUsername());
        user.setPassword(store.getPassword());
        ur.save(user);

        return new Status("success", "Registration Successful");
    }

    public Status login(Store store, HttpSession session, HttpServletRequest req){
    if(store.getUsername()==null || store.getPassword()==null){
        return new Status("error","Username or password cannot be null");
    }
    if(store.getUsername().isEmpty() || store.getPassword().isEmpty()){
        return new Status("error","Username or password cannot be empty");
    }
    Users user = ur.findByUsername(store.getUsername()).orElse(null);
    if(user==null){
        return new Status("error","User not found");
    }
    String ip = req.getRemoteAddr();
    int attempts = loginAttempts.getOrDefault(ip,0);
    if(attempts>3){
        return new Status("error","Login attempts exceeded");
    }

    if(!user.getPassword().equals(store.getPassword())){
        loginAttempts.put(ip,attempts+1);
        return new Status("error","Wrong Password");
    }
        loginAttempts.remove(ip);
        session.setAttribute("username",store.getUsername());
        return new Status("success", "Login Successful");
    }

    public Status saveProfile(ProfileEdit req, HttpSession session){
        if(!Authutil.isLoggedIn(session)){
            return new Status("error","You are not logged in");
        }
        String username = (String)session.getAttribute("username");
        Users user = ur.findByUsername(username).orElse(null);
        if(user==null){
            return new Status("error","User not found");
        }

        if (req.getBio() != null) {
            if (req.getBio().length() > 150) {
                return new Status("error", "Bio too long");
            }
            user.setBio(req.getBio());
        }

        if (req.getPfPhoto() != null) {
            user.setPfPhoto(req.getPfPhoto());
        }
        ur.save(user);

        return new Status("success","Profile saved successfully");

    }
    public UserProfileResponse viewProfile(HttpSession session){
        if(!Authutil.isLoggedIn(session)){
            return new UserProfileResponse("Login required");
        }
        String username = (String)session.getAttribute("username");
        Users user = ur.findByUsername(username).orElseThrow();
        return new UserProfileResponse(
                user.getUsername(),
                user.getBio(),
                user.getPfPhoto()
        );

    }

    public Status followUser(String targetUsername, HttpSession session) {

        if (!Authutil.isLoggedIn(session)) {
            return new Status("error", "Login required");
        }
        String currentUsername = (String)session.getAttribute("username");
        Users user = ur.findByUsername(currentUsername).orElseThrow();

        if (currentUsername.equals(targetUsername)) {
            return new Status("error", "You cannot follow yourself");
        }

        Users following = ur.findByUsername(targetUsername).orElseThrow();
        Users follower = ur.findByUsername(currentUsername).orElseThrow();
        if (fp.existsByFollowerAndFollowing(follower, following)) {
            return new Status("error", "Already following");
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        fp.save(follow);
        return new Status("success", "Following successfully");
    }
    @Transactional
    public Status unfollowUser(String targetUsername,HttpSession session){
        if(!Authutil.isLoggedIn(session)){
            return new Status("error", "Login required");
        }
        String currentUsername = (String)session.getAttribute("username");
        Users follower = ur.findByUsername(currentUsername).orElse(null);
        Users following = ur.findByUsername(targetUsername).orElse(null);
        if(follower==null){
            return new Status("error","User not found");
        }
        if(following==null){
            return new Status("error","User not found");
        }
        if(!fp.existsByFollowerAndFollowing(follower,following)){
            return new Status("error","Your are not following this user");
        }
        fp.deleteByFollowerAndFollowing(follower,following);
        return new Status("success", "Unfollowed successfully");
    }

    public List<UsernameResponse> getFollowers(HttpSession session){

        if (!Authutil.isLoggedIn(session)) {
            throw new RuntimeException("Login required");
        }
        String username = (String)session.getAttribute("username");
        Users user = ur.findByUsername(username).orElseThrow();
        return  fp.findByFollowing(user)
                .stream()
                .map(f -> new UsernameResponse(f.getFollower().getUsername()))
                .toList();

    }
    public List<UsernameResponse> getFollowing(HttpSession session){

        if (!Authutil.isLoggedIn(session)) {
            throw new RuntimeException("Login required");
        }
        String username = (String)session.getAttribute("username");
        Users user = ur.findByUsername(username).orElseThrow();

        return  fp.findByFollower(user)
                .stream()
                .map(f ->new UsernameResponse(f.getFollowing().getUsername()))
                .toList();
    }
}
