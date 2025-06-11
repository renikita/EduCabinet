package com.example.EduCabinet.service;

import com.example.EduCabinet.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {

  Student save(Student student);


  Student findById(Integer id);

  Student findByName(String name);

  Student ChangeName(String nameBefore, String nameAfter);

  List<Student> findAll();


  List<Student> saveAllAndFlush(Iterable<Student> students);

}
