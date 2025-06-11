package com.example.EduCabinet.service;

import com.example.EduCabinet.modelparent.User;
import com.example.EduCabinet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {


    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String login, String password) {

        User user = userRepository.findByLogin(login);


        return (user != null && user.getPassword().equals(password));
    }

    public String getUserRole(String login) {
        User user = userRepository.findByLogin(login);

        if (user != null && user.getRole() == User.Role.TEACHER) {
            return "TEACHER";
        } else if (user != null && user.getRole() == User.Role.STUDENT) {
            return "STUDENT";
        } else {
            return "UNKNOWN";
        }
    }
}
