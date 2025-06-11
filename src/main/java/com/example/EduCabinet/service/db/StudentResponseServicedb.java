package com.example.EduCabinet.service.db;


import com.example.EduCabinet.model.Homework;
import com.example.EduCabinet.model.Student;
import com.example.EduCabinet.model.StudentResponse;
import com.example.EduCabinet.repository.StudentResponseRepository;
import com.example.EduCabinet.service.response.StudentResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentResponseServicedb implements StudentResponseService {

    @Autowired
    StudentResponseRepository studentResponseRepository;
    @Override
    public void saveResponse(StudentResponse response) {
        studentResponseRepository.save(response);
    }

    @Override
    public List<StudentResponse> findResponsesByStudentAndHomework(Student student, Homework homework) {
        return studentResponseRepository.findResponsesByStudentAndHomework(student, homework);
    }

    @Override
    public List<StudentResponse> findAllResponsesByStudent(Student student) {
        return studentResponseRepository.findAllResponsesByStudent(student);
    }

    @Override
    public StudentResponse findByStudentAndHomework(Student student, Homework homework) {
        return studentResponseRepository.findByStudentAndHomework(student, homework);
    }

    @Override
    public StudentResponse findByStudent(Student student) {
        return null;
    }

    @Override
    public StudentResponse findByHomework(Homework homework) {
        return null;
    }


}
