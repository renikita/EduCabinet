package com.example.EduCabinet.controller;


import com.example.EduCabinet.modelparent.User;
import com.example.EduCabinet.repository.UserRepository;
import com.example.EduCabinet.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {


    private final UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "auth";
    }

    @PostMapping("/login")
    @ResponseBody
    public Map<String, String> handleLogin(@RequestParam String login, @RequestParam String password, HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        if (authenticationService.authenticate(login, password)) {
            HttpSession session = request.getSession();


            User user = userRepository.findByLogin(login);
            if (user != null && user.getRole() == User.Role.TEACHER) {
                session.setAttribute("role", "TEACHER");
                session.setAttribute("userId", user.getId());
                response.put("role", "TEACHER");
                response.put("userId", String.valueOf(user.getId()));
            } else if (user != null && user.getRole() == User.Role.STUDENT) {
                session.setAttribute("role", "STUDENT");
                session.setAttribute("userId", user.getId());
                response.put("role", "STUDENT");
                response.put("userId", String.valueOf(user.getId()));

            }
        } else {
            response.put("error", "Incorrect username or password.");
        }
        return response;
    }

}


