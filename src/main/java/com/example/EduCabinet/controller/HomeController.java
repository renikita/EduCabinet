package com.example.EduCabinet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/mystats")
    public String myStats() {
        return "mystats";
    }

    @GetMapping("/teacherdashboard")
    public String teacherDashboard() {
        return "teacherdashboard";
    }
}
