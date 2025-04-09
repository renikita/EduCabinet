package com.example.MyStatsBeta.repository;

import com.example.MyStatsBeta.modelparent.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Integer> {

    User findByLogin(String login);

    User findByName(String name);

}
