package com.example.EduCabinet.repository;

import com.example.EduCabinet.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByLogin(String login);

    @Query("SELECT sr FROM Student sr WHERE sr.name = :name")
    Student findByName(String name);
}
