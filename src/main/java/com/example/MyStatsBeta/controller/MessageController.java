package com.example.MyStatsBeta.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageController {



    @GetMapping("/message")
    public String ShowMessage(HttpSession session) {
        String role = (String) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");
        //check who is it teacher or student
        if (role == null || userId == null) {
            return "redirect:/login";
        }
//        if (role.equals("TEACHER")) {
//            return "teachermessage";
//        } else if (role.equals("STUDENT")) {
//            return "studentmessage";
//        }
        //we will do it in one page for both

        return "message";
    }

}
