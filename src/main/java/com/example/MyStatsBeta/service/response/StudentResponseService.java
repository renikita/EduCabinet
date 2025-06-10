package com.example.MyStatsBeta.service.response;

import com.example.MyStatsBeta.model.Homework;
import com.example.MyStatsBeta.model.Student;
import com.example.MyStatsBeta.model.StudentResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StudentResponseService {


    void saveResponse(StudentResponse response);

    List<StudentResponse> findResponsesByStudentAndHomework(Student student, Homework homework);

    List<StudentResponse> findAllResponsesByStudent(Student student);

    StudentResponse findByStudentAndHomework(Student student, Homework homework);
    StudentResponse findByStudent(Student student);
    StudentResponse findByHomework(Homework homework);

}
