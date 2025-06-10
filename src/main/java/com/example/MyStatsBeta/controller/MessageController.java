package com.example.MyStatsBeta.controller;

import com.example.MyStatsBeta.model.Message;
import com.example.MyStatsBeta.modelparent.User;
import com.example.MyStatsBeta.repository.MessageRepository;
import com.example.MyStatsBeta.repository.StudentRepository;
import com.example.MyStatsBeta.repository.TeacherRepository;
import com.example.MyStatsBeta.repository.UserRepository;
import com.example.MyStatsBeta.service.MessageService;
import com.example.MyStatsBeta.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class MessageController {


    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MessageService messageService;
    private final UserService userService;

    public MessageController(StudentRepository studentRepository, TeacherRepository teacherRepository, UserRepository userRepository, MessageRepository messageRepository, MessageService messageService, UserService userService) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/message")
    public String ShowMessage(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");

        if (role == null || userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("role", role);

        if( role.equals("STUDENT")) {
            studentRepository.findById(userId).ifPresent(student -> model.addAttribute("senderName", student.getName()));
            List<User> teachers = userRepository.findByRole(User.Role.TEACHER);
            model.addAttribute("receivers", teachers);
        } else if (role.equals("TEACHER")) {
            teacherRepository.findById(userId).ifPresent(teacher -> {model.addAttribute("senderName", teacher.getName());});


            List<User> students = userRepository.findByRole(User.Role.STUDENT);
            model.addAttribute("receivers", students);

        }

        return "message";
    }
    @PostMapping("/sendmessage/{receiverId}")
    public String sendMessage(@RequestParam String messageContent,
                              @PathVariable Integer receiverId,
                               HttpSession session) {
        String role = (String) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");
        Integer senderId = (Integer) session.getAttribute("userId");

        if (role == null || userId == null) {
            return "redirect:/login";
        }

        if (messageContent == null || messageContent.isEmpty() || receiverId == null || senderId == null) {
            return "redirect:/message";
        }


        User sender = userService.findById(senderId).orElse(null);
        User receiver = userService.findById(receiverId).orElse(null);
        if (sender == null || receiver == null) {
            return "redirect:/message";
        }
        LocalDateTime timestamp = LocalDateTime.now();

        Message message = new Message();
        message.setContent(messageContent);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setTimestamp(timestamp);
        messageRepository.save(message);

        return "redirect:/message/" + receiverId;
    }


    @GetMapping("/message/{receiverId}")
    public String getMessages(@PathVariable Integer receiverId, Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        Integer userId = (Integer) session.getAttribute("userId");

        if (role == null || userId == null) {
            return "redirect:/login";
        }
        if (receiverId == null || receiverId <= 0) {
            return "redirect:/message";
        }
        model.addAttribute("role", role);

        if (role.equals("STUDENT")) {
            studentRepository.findById(userId).ifPresent(student -> model.addAttribute("senderName", student.getName()));
            List<User> teachers = userRepository.findByRole(User.Role.TEACHER);
            model.addAttribute("receivers", teachers);
        } else if (role.equals("TEACHER")) {
            teacherRepository.findById(userId).ifPresent(teacher -> model.addAttribute("senderName", teacher.getName()));
            List<User> students = userRepository.findByRole(User.Role.STUDENT);
            model.addAttribute("receivers", students);
        }

        // Sender
        User user1 = userService.findById(userId).orElse(null);
        // Receiver
        User user2 = userService.findById(receiverId).orElse(null);
        Page<Message> messages = messageService.getMessages(user1, user2);


        model.addAttribute("messages", messages.getContent());
        model.addAttribute("receiver", user2);
        model.addAttribute("activeReceiverId", receiverId);

        return "message";
    }

}
