package com.example.EduCabinet.service.response;

import com.example.EduCabinet.model.Homework;
import com.example.EduCabinet.model.Student;
import com.example.EduCabinet.model.StudentResponse;
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
