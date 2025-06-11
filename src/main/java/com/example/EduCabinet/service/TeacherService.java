package com.example.EduCabinet.service;

import com.example.EduCabinet.model.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherService {

    Teacher save(Teacher Teacher);

    Teacher findById(Integer id);

    public List<Teacher> findAll();

    public List<Teacher> saveAllAndFlush(Iterable<Teacher> teachers);


}
