package com.example.MyStatsBeta.controller;


import com.example.MyStatsBeta.modelparent.User;
import com.example.MyStatsBeta.repository.UserRepository;
import com.example.MyStatsBeta.service.AuthenticationService;

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
            System.out.println(session.toString());

            User user = userRepository.findByLogin(login);
            if (user != null && user.getRole() == User.Role.TEACHER) {
                session.setAttribute("role", "TEACHER");
                session.setAttribute("userId", user.getId());
                response.put("role", "TEACHER");
                response.put("userId", String.valueOf(user.getId()));
                System.out.println("Teacher logged in: ");
                System.out.println(user.getId());
            } else if (user != null && user.getRole() == User.Role.STUDENT) {
                session.setAttribute("role", "STUDENT");
                session.setAttribute("userId", user.getId());
                response.put("role", "STUDENT");
                response.put("userId", String.valueOf(user.getId()));
                System.out.println("Student logged in: " + user.getId());
            }
        } else {
            response.put("error", "Incorrect username or password.");
        }
        return response;
    }

}


