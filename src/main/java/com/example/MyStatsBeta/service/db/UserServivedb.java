package com.example.MyStatsBeta.service.db;

import com.example.MyStatsBeta.modelparent.User;
import com.example.MyStatsBeta.repository.UserRepository;
import com.example.MyStatsBeta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServivedb implements UserService {


    @Autowired
    UserRepository userRepository;


    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }


    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User changeName(String nameBefore, String nameAfter) {
        String name = userRepository.findByName(nameBefore).getName();
        if (name != null) {
            User user = userRepository.findByName(nameBefore);
            user.setName(nameAfter);


            return userRepository.save(user);
        }
        else{
            throw new RuntimeException("nameBefore must have a value");
        }

    }


    @Override
    public User changeLogin(String loginBefore, String loginAfter) {
        String login = userRepository.findByLogin(loginBefore).getLogin();
        if (login != null) {
            User user = userRepository.findByLogin(loginBefore);
            user.setLogin(loginAfter);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("loginBefore must have a value");
        }
    }

    @Override
    public User changeEmail(String emailBefore, String emailAfter) {
        String email = userRepository.findByLogin(emailBefore).getEmail();
        if (email != null) {
            User user = userRepository.findByLogin(emailBefore);
            user.setEmail(emailAfter);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("emailBefore must have a value");
        }
    }

    @Override
    public User changePassword(String passwordBefore, String passwordAfter) {
        String password = userRepository.findByLogin(passwordBefore).getPassword();
        if (password != null) {
            User user = userRepository.findByLogin(passwordBefore);
            user.setPassword(passwordAfter);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("passwordBefore must have a value");
        }
    }

    @Override
    public List<User> findByRole(User.Role role) {
        return userRepository.findByRole(role);
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
