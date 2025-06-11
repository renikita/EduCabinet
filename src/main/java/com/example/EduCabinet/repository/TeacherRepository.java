package com.example.EduCabinet.repository;

import com.example.EduCabinet.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByLogin(String login);
}
