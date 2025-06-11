package com.example.EduCabinet.service.db;

import com.example.EduCabinet.model.Teacher;
import com.example.EduCabinet.repository.TeacherRepository;
import com.example.EduCabinet.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServicedb implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public Teacher
 save(Teacher Teacher) {
        return teacherRepository.save(Teacher);
    }
    @Override
    public Teacher findById(Integer id){
        return teacherRepository.findById(id).get();
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public List<Teacher> saveAllAndFlush(Iterable<Teacher> teachers){
        throw new RuntimeException("Not Supported");
    }

}
