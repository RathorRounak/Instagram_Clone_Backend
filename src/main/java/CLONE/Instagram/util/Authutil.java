package CLONE.Instagram.util;

import jakarta.servlet.http.HttpSession;

public class Authutil {

    public static boolean isLoggedIn(HttpSession session){
        return session != null && session.getAttribute("username") != null;
    }
}
