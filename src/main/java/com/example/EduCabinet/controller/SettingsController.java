package com.example.EduCabinet.controller;


import com.example.EduCabinet.service.SettingsService;
import com.example.EduCabinet.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/settings")
public class SettingsController {

    private final SettingsService settingsService;
    private final UserService userService;

    public SettingsController(SettingsService settingsService, UserService userService) {
        this.settingsService = settingsService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String settings(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");
        if (role == null || userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("role", role);
        model.addAttribute("userId", userId);

        model.addAttribute("name", userService.findById(userId).get().getName());

        model.addAttribute("settings", settingsService.findByUserId(userId));

        return "settings";
    }


    @PostMapping("/changeprofile")
    public String updateProfile(@RequestParam String login,
            @RequestParam String email,
            HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");
        if (role == null || userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("role", role);
        model.addAttribute("userId", userId);

        model.addAttribute("name", userService.findById(userId).get().getName());
        model.addAttribute("settings", settingsService.findByUserId(userId));
        if(login != null && !login.isEmpty()) {
            userService.findById(userId).get().setLogin(login);
            model.addAttribute("answer", "Login updated successfully");
        }

        if (email != null && !email.isEmpty()) {
            userService.findById(userId).get().setEmail(email);
            model.addAttribute("answer", "Email updated successfully");
        }
        if ((email == null || email.isEmpty()) && (login == null || login.isEmpty())) {
            model.addAttribute("error", "No changes made with login or email");

        }


        return "settings";
    }

    @PostMapping("/updatepassword")
    public String updatePassword(@RequestParam String oldPassword,
            @RequestParam String newPassword,
            HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");
        if (role == null || userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("role", role);
        model.addAttribute("userId", userId);

        model.addAttribute("name", userService.findById(userId).get().getName());
        model.addAttribute("settings", settingsService.findByUserId(userId));
        if (oldPassword != null && !oldPassword.isEmpty() && newPassword != null && !newPassword.isEmpty()) {
            if (userService.findById(userId).get().getPassword().equals(oldPassword)) {
                userService.findById(userId).get().setPassword(newPassword);
                userService.save(userService.findById(userId).get());
                model.addAttribute("answer", "Password updated successfully");
            } else {
                model.addAttribute("error", "Old password is incorrect");
            }
        } else {
            model.addAttribute("error", "No changes made with password");
        }

        return "settings";
    }

    @PostMapping("/updatetheme")
    public String updateTheme(@RequestParam(name = "theme", required = false) String theme,
            HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");
        if (role == null || userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("role", role);
        model.addAttribute("userId", userId);

        model.addAttribute("name", userService.findById(userId).get().getName());
        model.addAttribute("settings", settingsService.findByUserId(userId));

        if (theme != null){
                theme = "DARK";
                settingsService.changeTheme(userId, theme);

        }else{
                theme = "LIGHT";
                settingsService.changeTheme(userId, theme);
        }

        model.addAttribute("answer", "Theme updated successfully");
        return "settings";
    }

    @PostMapping("/updatelanguage")
    public String updateLanguage(@RequestParam String language,
            HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        settingsService.changeLanguage(userId, language);

        return "redirect:/settings/";
    }
    @PostMapping("/updatenotifications")
    public String updateNotifications(@RequestParam boolean notificationsEnabled,
            HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        settingsService.changeNotifications(userId, notificationsEnabled);

        return "redirect:/settings/";
    }
    @PostMapping("/resetsettings")
    public String resetSettings(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        settingsService.setDefaultSettings(userId);

        return "redirect:/settings/";
    }



}
