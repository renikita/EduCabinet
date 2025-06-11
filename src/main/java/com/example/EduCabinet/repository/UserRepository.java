package com.example.EduCabinet.repository;

import com.example.EduCabinet.modelparent.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);

    User findByName(String name);

    List<User> findByRole(User.Role role);
}
