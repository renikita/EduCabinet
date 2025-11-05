package com.example.EduCabinet;

import com.example.EduCabinet.model.Homework;
import com.example.EduCabinet.model.Student;
import com.example.EduCabinet.model.Teacher;
import com.example.EduCabinet.model.enums.HomeworkStatus;
import com.example.EduCabinet.modelparent.User;
import com.example.EduCabinet.repository.HomeworkRepository;
import com.example.EduCabinet.repository.StudentRepository;
import com.example.EduCabinet.repository.StudentResponseRepository;
import com.example.EduCabinet.repository.TeacherRepository;
import com.example.EduCabinet.service.HomeworkService;
import com.example.EduCabinet.service.SettingsService;
import com.example.EduCabinet.model.StudentResponse;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class EduCabinetApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduCabinetApplication.class, args);

    }

    //if Data in DB will be uncreated or void, uncomment this function
    @Bean
    public CommandLineRunner demoData(TeacherRepository teacherRepository, StudentRepository studentRepository, HomeworkRepository homeworkRepository, StudentResponseRepository studentResponseRepository, HomeworkService homeworkService, SettingsService settingsService) {
        return args -> {

            Teacher teacher = new Teacher();
            teacher.setLogin("teacher_demo");
            teacher.setName("Demo Teacher");
            teacher.setPassword("password123");
            teacher.setRole(User.Role.TEACHER);
            teacher.setStatus(User.Status.ACTIVATED);

            teacherRepository.save(teacher);
            settingsService.setDefaultSettings(teacher.getId());

            Student student = new Student();
            student.setLogin("student_demo1");
            student.setName("Demo Student 1");
            student.setPassword("password123");
            student.setRole(User.Role.STUDENT);
            student.setStatus(User.Status.ACTIVATED);
            student.setTeacher(teacher);
            studentRepository.save(student);
            settingsService.setDefaultSettings(student.getId());

            Student student1 = new Student();
            student1.setLogin("student_demo2");
            student1.setName("Demo Student 2");
            student1.setPassword("password123");
            student1.setRole(User.Role.STUDENT);
            student1.setStatus(User.Status.ACTIVATED);
            student1.setTeacher(teacher);
            studentRepository.save(student1);
            settingsService.setDefaultSettings(student1.getId());

            Student student2 = new Student();
            student2.setLogin("student_demo3");
            student2.setName("Demo Student 3");
            student2.setPassword("password123");
            student2.setRole(User.Role.STUDENT);
            student2.setStatus(User.Status.ACTIVATED);
            student2.setTeacher(teacher);
            studentRepository.save(student2);
            settingsService.setDefaultSettings(student2.getId());

            teacher.getStudents().add(student);
            teacher.getStudents().add(student1);
            teacher.getStudents().add(student2);
            teacherRepository.save(teacher);
            List<Student> students = studentRepository.findAll();

            LocalDateTime uploadTime = LocalDateTime.of(2025, 6, 10, 10, 30);
            Homework homework = new Homework();
            homework.setNameHomework("Java Utilities");
            homework.setTask("Working with Java, Add new method");
            homework.setDescription("Develop a Java program for working with various utilities. The main goal of this task is to implement a new method in an existing utility that allows you to perform various actions with data." +
                    " The new method must be designed to process text data and must perform some functionality, such as encryption, filtering, or transformation.\n" +
                    "The task also includes writing the necessary tests to verify the correct operation of the new method and ensure compliance with the specification requirements.");
            homework.setUploadTime(uploadTime);
            uploadTime = LocalDateTime.of(2026, 3, 23, 10, 30);
            homework.setDeadline(uploadTime);
            homework.setTeacher(teacher);
            homework.setStudents(students);
            homeworkRepository.save(homework);

            StudentResponse studentResponse = new StudentResponse();
            studentResponse.setStudent(student);
            studentResponse.setResponseText(null);
            studentResponse.setMark(null);
            studentResponse.setResponseTime(null);
            studentResponse.setHomework(homework);
            studentResponseRepository.save(studentResponse);

            StudentResponse studentResponse1 = new StudentResponse();
            studentResponse1.setStudent(student1);
            studentResponse1.setResponseText(null);
            studentResponse1.setMark(null);
            studentResponse1.setResponseTime(null);
            studentResponse1.setHomework(homework);
            studentResponseRepository.save(studentResponse1);

            LocalDateTime uploadTime1 = LocalDateTime.of(2025, 1, 23, 10, 30);
            Homework homework1 = new Homework();
            homework1.setNameHomework("Java Utilities");
            homework1.setTask("Working with Java, Add new attribute");
            homework1.setDescription("Develop a Java program for working with various utilities. The main goal of this task is to expand the functionality of existing utilities by adding a new attribute that will allow storing additional data for processing." +
                    " The new attribute can be used to implement various operations or to store additional information about the data.\n" +
                    "The task also includes writing the necessary tests to verify the correct operation of the new attribute and ensure compliance with the specification requirements.");

            homework1.setUploadTime(uploadTime1);
            uploadTime1 = LocalDateTime.of(2026, 2, 23, 10, 30);
            homework1.setDeadline(uploadTime1);
            homework1.setTeacher(teacher);
            homework1.setStudents(students);

            homeworkRepository.save(homework1);

            StudentResponse studentResponse2 = new StudentResponse();
            studentResponse2.setStudent(student);
            studentResponse2.setResponseText(null);
            studentResponse2.setMark(null);
            studentResponse2.setResponseTime(null);
            studentResponse2.setHomework(homework1);
            studentResponseRepository.save(studentResponse2);

            StudentResponse studentResponse3 = new StudentResponse();
            studentResponse3.setStudent(student1);
            studentResponse3.setResponseText(null);
            studentResponse3.setMark(null);
            studentResponse3.setResponseTime(null);
            studentResponse3.setHomework(homework1);
            studentResponseRepository.save(studentResponse3);

            List<Homework> homeworkList = homeworkRepository.findAll();

            student.setHomeworks(homeworkList);
            student1.setHomeworks(homeworkList);

            studentRepository.save(student);
            studentRepository.save(student1);

            List<StudentResponse> responses = studentResponseRepository.findAll();
            responses.forEach(response -> {
                LocalDateTime uploadtime = LocalDateTime.now();
                if (response.getMark() != null) {
                    response.setHomeworkStatus(HomeworkStatus.COMPLETED);
                } else if (response.getResponseText() != null) {
                    response.setHomeworkStatus(HomeworkStatus.ON_CHECK);
                } else if (response.getHomework().getDeadline().isBefore(uploadtime)) {
                    response.setHomeworkStatus(HomeworkStatus.PENAL);
                } else {
                    response.setHomeworkStatus(HomeworkStatus.CURRENT);
                }
                studentResponseRepository.save(response);
            });

            System.out.println("\n\n------------------------------------------------------------\n" +
                    "LOGIN DETAILS\n" +
                    "------------------------------------------------------------\n" +
                    "\n" +
                    "Here are the credentials you can use to log into the EduCabinet demo version after starting the application:\n" +
                    "\n" +
                    "Login\t\t\t\tPassword\n" +
                    "teacher_demo\t\tpassword123\n" +
                    "student_demo1\t\tpassword123\n" +
                    "student_demo2\t\tpassword123\n" +
                    "student_demo3\t\tpassword123\n\n\n\n");
        };
    }

}