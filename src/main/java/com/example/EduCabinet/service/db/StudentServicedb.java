package com.example.EduCabinet.service.db;

import com.example.EduCabinet.model.Student;
import com.example.EduCabinet.repository.StudentRepository;
import com.example.EduCabinet.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
public class StudentServicedb implements StudentService {
@Autowired
    StudentRepository studentRepository;

@Override
public Student save(Student student) {
    return studentRepository.save(student);
}
@Override
    public Student findById(Integer id){
    return studentRepository.findById(id).get();
}

    @Override
    public Student findByName(String name) {
        return studentRepository.findByName(name);
    }

    @Override
    public Student ChangeName(String nameBefore, String nameAfter) {
        return null;
    }


    @Override
    public List<Student> findAll() {
    return studentRepository.findAll();
}

@Override
    public List<Student> saveAllAndFlush(Iterable<Student> students){
    throw new RuntimeException("Not Supported");
}


}
