package com.example.EduCabinet.service;

import com.example.EduCabinet.modelparent.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    User save(User user);
    Optional<User> findById(Integer id);
    User findByLogin(String login);
    User findByName(String name);
    User changeName(String nameBefore, String nameAfter);
    User changeLogin(String loginBefore, String loginAfter);
    User changeEmail(String emailBefore, String emailAfter);
    User changePassword(String passwordBefore, String passwordAfter);
    List<User> findByRole(User.Role role);
    List<User> findAll();

}
