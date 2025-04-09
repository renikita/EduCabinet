package com.example.MyStatsBeta.controller;

import com.example.MyStatsBeta.model.Homework;
import com.example.MyStatsBeta.model.Student;
import com.example.MyStatsBeta.model.Teacher;
import com.example.MyStatsBeta.model.enums.HomeworkStatus;
import com.example.MyStatsBeta.repository.HomeworkRepository;
import com.example.MyStatsBeta.repository.StudentRepository;
import com.example.MyStatsBeta.repository.StudentResponseRepository;
import com.example.MyStatsBeta.repository.TeacherRepository;
import com.example.MyStatsBeta.service.HomeworkService;
import com.example.MyStatsBeta.service.StudentService;
import com.example.MyStatsBeta.model.StudentResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.ejb.Local;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api/students")
public class StudentController {

    private HomeworkRepository homeworkRepository;
    @Qualifier("homeworkServicedb")
    @Autowired
    private HomeworkService homeworkService;

    private StudentService studentServiceDB;
    private StudentResponseRepository studentResponseRepository;
    private StudentService studentServiceJSON;
    private TeacherRepository teacherRepository;

    private StudentRepository studentRepository;

    @Autowired
    public StudentController(HomeworkRepository homeworkRepository, StudentResponseRepository studentResponseRepository,
                             StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.homeworkRepository = homeworkRepository;
        this.studentResponseRepository = studentResponseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @GetMapping("/settings")
    public String showMySettingsPage(Model model, HttpServletRequest request){
     HttpSession session = request.getSession();
     String role = (String) session.getAttribute("role");

     Integer userId = (Integer) session.getAttribute("userId");

     return "settings";
    }

    @GetMapping("/mystats")
    public String showMyStatsPage(Model model, HttpServletRequest request) {

        //check id over sout
        System.out.println("Console: Listening mystats...\n" + request.getSession().getId());
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        LocalDateTime uploadtime = LocalDateTime.now();

        Integer userId = (Integer) session.getAttribute("userId");

        Student student = studentRepository.findById(userId).orElse(null);




        model.addAttribute("name", student != null ? student.getName() : null);

        Teacher teacher = student != null ? student.getTeacher() : null;
        model.addAttribute("teachername", teacher != null ? teacher.getName() : null);
        List<Homework> list = homeworkService.findAll();
        System.out.println("Console: Listening list...\n" + list.isEmpty());

        /*list.forEach(homework -> {
            List<StudentResponse> responses = studentResponseRepository.findResponsesByStudentAndHomework(student, homework);
            responses.forEach(response -> {
                if(response.getMark() != null){
                    response.setHomeworkStatus(HomeworkStatus.COMPLETED);
                } else if (response.getResponseText() != null) {
                    response.setHomeworkStatus(HomeworkStatus.ON_CHECK);
                }
                else if(homework.getDeadline().isBefore(uploadtime)){
                    response.setHomeworkStatus(HomeworkStatus.PENAL);
                }
                else {
                    response.setHomeworkStatus(HomeworkStatus.CURRENT);
                }
                studentResponseRepository.save(response);
                System.out.println(response);
            });

        });*/

        List<StudentResponse> responseList = studentResponseRepository.findAllResponsesByStudent(student);
        System.out.println("___________________\nResponse list get data: " + responseList);
        model.addAttribute("responseList", responseList);
        /*System.out.println("___________________\nHomework list get data: " + list);
        model.addAttribute("homeworkList", list);*/


        return "mystats";
    }



    @PostMapping("/upload")
    public String uploadHomework(@RequestParam("homeworkText") String homeworkText,
                                 @RequestParam("homeworkId") Integer homeworkId,
                                 HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        Student student = studentRepository.findById(userId).orElse(null);
        Homework homework = homeworkRepository.findById(homeworkId).orElse(null);
        if (student != null && homework != null) {
            LocalDateTime uploadTime = LocalDateTime.now();
            StudentResponse response = studentResponseRepository.findByStudentAndHomework(student, homework);
            response.setResponseTime(uploadTime);
            response.setResponseText(homeworkText);
            response.setHomeworkStatus(HomeworkStatus.ON_CHECK);
            studentResponseRepository.save(response);



           // System.out.println("/UPLOAD: \n\n" + homework);
        }
            return "redirect:/api/students/mystats";
        }
    }










        /*String role = (String) session.getAttribute("role");
        System.out.println("Role: " + role);*
       /* if (session.toString() != null && session.equals("STUDENT")) {
            Long userId = (Long) session.getAttribute("userId");
            Student student = studentRepository.findById(userId).orElse(null);
            if (student != null) {
                model.addAttribute("login", student.getLogin());
            }
            model.addAttribute("role", session.toString());

            return "mystats";
        } else if (session.toString() != null && session.equals("TEACHER")) {
            return "redirect:/teacherdashboard";
        } else {
            return "redirect:/login";

     }   }*/



/*

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/upload")
    public void uploadHomework(@RequestBody Student student) {
        studentService.uploadHomework(student);
    }
*/

    /*@GetMapping("/{id}/homework")
    public String getStudentHomeworkPage(@PathVariable Integer id, Model model, HttpServletRequest request) {
        List<Homework> homeworkList = studentService.getStudentHomework(id);
        model.addAttribute("homeworkList", homeworkList);

        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        model.addAttribute("role", role);

        return "studentHomeworkPage";
    }*/

   /* @PostMapping("/{studentId}/homework/{homeworkId}/status/{status}")
    public void updateHomeworkStatus(@PathVariable Long studentId, @PathVariable Long homeworkId, @PathVariable Homework.HomeworkStatus status) {
        studentService.updateHomeworkStatus(studentId, homeworkId, status);
    }*/

