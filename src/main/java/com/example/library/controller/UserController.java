package com.example.library.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user")
    public Map<String, Object> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> userInfo = new HashMap<>();
        
        if (authentication != null && authentication.isAuthenticated() && 
            !authentication.getName().equals("anonymousUser")) {
            userInfo.put("username", authentication.getName());
            userInfo.put("authorities", authentication.getAuthorities());
            userInfo.put("authenticated", true);
        } else {
            userInfo.put("authenticated", false);
        }
        
        return userInfo;
    }

    @GetMapping("/test-auth")
    public Map<String, Object> testAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> result = new HashMap<>();
        
        result.put("authentication", authentication != null ? authentication.getName() : "null");
        result.put("isAuthenticated", authentication != null && authentication.isAuthenticated());
        result.put("isAnonymous", authentication != null && "anonymousUser".equals(authentication.getName()));
        result.put("authorities", authentication != null ? authentication.getAuthorities() : "null");
        
        return result;
    }

    @PostMapping("/logout")
    public Map<String, String> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Logged out successfully");
        return responseMap;
    }
} 