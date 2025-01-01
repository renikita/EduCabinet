package com.example.MyStatsBeta.controller;

import com.example.MyStatsBeta.model.Homework;
import com.example.MyStatsBeta.model.Student;
import com.example.MyStatsBeta.model.StudentResponse;
import com.example.MyStatsBeta.model.Teacher;
import com.example.MyStatsBeta.model.enums.HomeworkStatus;
import com.example.MyStatsBeta.repository.HomeworkRepository;
import com.example.MyStatsBeta.repository.StudentRepository;
import com.example.MyStatsBeta.repository.StudentResponseRepository;
import com.example.MyStatsBeta.repository.TeacherRepository;
import com.example.MyStatsBeta.service.HomeworkService;
import com.example.MyStatsBeta.service.TeacherService;
import com.example.MyStatsBeta.service.db.HomeworkServicedb;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.ejb.Local;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/api/teachers")
public class TeacherController {

    private TeacherRepository teacherRepository;

    private StudentRepository studentRepository;

    private final StudentResponseRepository studentResponseRepository;

    private HomeworkRepository homeworkRepository;

    @Qualifier("homeworkServicedb")
    @Autowired
    private HomeworkService homeworkService;

    private String localStudentName = null;

    @Autowired
    public TeacherController(TeacherRepository teacherRepository, StudentRepository studentRepository, StudentResponseRepository studentResponseRepository, @Qualifier("homeworkServicedb") HomeworkService homeworkService, HomeworkRepository homeworkRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.studentResponseRepository = studentResponseRepository;
        this.homeworkService = homeworkService;
        this.homeworkRepository = homeworkRepository;
    }

    @GetMapping("/teacherdashboard/{studentName}")
    public String getStudentName(@PathVariable String studentName, Model model) {

        System.out.println("getStudentName is Loaded!");
        localStudentName = studentName;


        return "redirect:/api/teachers/teacherdashboard";
    }

    @GetMapping("/teacherdashboard/all")
    public String AllStudents(Model model) {

        localStudentName = "All";

        return "redirect:/api/teachers/teacherdashboard";
    }


    @GetMapping("/teacherdashboard")
    public String loadTeacherDashboard(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");

        List<Homework> homeworkList = homeworkRepository.findAll();
        Integer userId = (Integer) session.getAttribute("userId");

        Teacher teacher = teacherRepository.findById(userId).orElse(null);
        model.addAttribute("name", teacher != null ? teacher.getName() : null);

        List<Student> students = studentRepository.findAll();

        // обмеження терміну
        LocalDateTime MinTerminTime = LocalDateTime.now();
        MinTerminTime = MinTerminTime.plusHours(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        String formattedDateTime = MinTerminTime.format(formatter);

        model.addAttribute("localmindate", formattedDateTime);
        LocalDateTime MaxTerminTime = LocalDateTime.now();
        MaxTerminTime = MaxTerminTime.plusMonths(12);
        formattedDateTime = MaxTerminTime.format(formatter);
        model.addAttribute("localmaxdate", formattedDateTime);
        if (localStudentName != null) {




            if (localStudentName.equals("All")) {
                Student student;
                List<StudentResponse> responses = studentResponseRepository.findAll();


                model.addAttribute("studentname", localStudentName);
                model.addAttribute("responseList", responses);
                model.addAttribute("homeworkList", homeworkList);
                model.addAttribute("students", students);

                return "teacherdashboard";
            }
            System.out.println(localStudentName);

            Student student;
            List<StudentResponse> responses = studentResponseRepository.findAllResponsesByStudent(student = studentRepository.findByName(localStudentName));


            model.addAttribute("studentname", localStudentName);
            model.addAttribute("responseList", responses);

        }

        System.out.println(homeworkList);
        model.addAttribute("homeworkList", homeworkList);
        model.addAttribute("students", students);


        return "teacherdashboard";
    }

    @PostMapping("/addhomework")
    public String addhomework(@RequestParam("article") String name_homework,@RequestParam("task") String task, @RequestParam("description") String description,
                              @RequestParam("deadline") LocalDateTime dateTime,
                              @RequestParam("student") String studentName, HttpServletRequest request) {

        

        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        Teacher teacher = teacherRepository.findById(userId).orElse(null);


        Homework createHomework = new Homework();
        createHomework.setNameHomework(name_homework);
        createHomework.setTask(task);
        createHomework.setDescription(description);
        createHomework.setDeadline(dateTime);
        LocalDateTime localDateTime = LocalDateTime.now();
        createHomework.setTeacher(teacher);
        createHomework.setUploadTime(localDateTime);

        String[] nameArray = studentName.split(",");


        List<Student> studentList = new ArrayList<>();
        for (String name : nameArray) {
            Student student = studentRepository.findByName(name);
            if (student != null) {
                studentList.add(student);
            }
        }

        createHomework.setStudents(studentList);

        homeworkRepository.save(createHomework);

        System.out.println("studentList - :");


        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i) != null) {
                Student student = studentList.get(i);
                StudentResponse createNewResponse = new StudentResponse();
                createNewResponse.setHomework(createHomework);
                createNewResponse.setStudent(student);
                createNewResponse.setHomework(createHomework);
                studentResponseRepository.save(createNewResponse);
                System.out.println(student);
            }
        }
        System.out.println(studentList);
        StudentResponse createNewResponse = new StudentResponse();


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
            System.out.println(response);
        });


        return "redirect:/api/teachers/teacherdashboard";

    }

    @PostMapping("/deletehomework")
    public String deleteHomework(@RequestParam("homework") Integer[] homeworkIds) {
        for (Integer homeworkId : homeworkIds) {
            Optional<StudentResponse> response = studentResponseRepository.findById(homeworkId);
            response.ifPresent(studentResponseRepository::delete);
        }
        return "redirect:/api/teachers/teacherdashboard";
    }

    @PostMapping("/upload")
    public String uploadHomework(@RequestParam("markInteger") Integer mark,
                                 @RequestParam("homeworkId") Integer homeworkId,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession();
        var ref = new Object() {
            StudentResponse response;
        };
        List<StudentResponse> studentResponses = studentResponseRepository.findAll();
        studentResponses.forEach(studentResponse ->
        {
            if (studentResponse.getId().equals(homeworkId)) {
                ref.response = studentResponse;
            }

        });

        Optional<Student> studentOptional = studentRepository.findById(ref.response.getStudent().getId());
        Student student = studentOptional.get();
        StudentResponse response = studentResponseRepository.findByStudentAndHomework(student, ref.response.getHomework());

        response.setMark(mark);
        response.setHomeworkStatus(HomeworkStatus.COMPLETED);
        studentResponseRepository.save(response);

        // System.out.println("/UPLOAD: \n\n" + homework);
        return "redirect:/api/teachers/teacherdashboard";
    }
}

