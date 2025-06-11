package com.example.EduCabinet.repository;

import com.example.EduCabinet.model.Homework;
import com.example.EduCabinet.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Integer> {


    List<Homework> findByStudentsIn(List<Student> students);
}
