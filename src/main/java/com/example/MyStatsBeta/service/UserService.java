package com.example.MyStatsBeta.service;

import com.example.MyStatsBeta.modelparent.User;
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
    User changePassword(String passwordBefore, String passwordAfter);
    List<User> findByRole(User.Role role);
    List<User> findAll();

}
