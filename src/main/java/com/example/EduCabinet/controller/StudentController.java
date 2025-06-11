package com.example.EduCabinet.controller;

import com.example.EduCabinet.model.Homework;
import com.example.EduCabinet.model.Student;
import com.example.EduCabinet.model.Teacher;
import com.example.EduCabinet.model.enums.HomeworkStatus;
import com.example.EduCabinet.repository.HomeworkRepository;
import com.example.EduCabinet.repository.StudentRepository;
import com.example.EduCabinet.repository.StudentResponseRepository;
import com.example.EduCabinet.repository.TeacherRepository;
import com.example.EduCabinet.service.HomeworkService;
import com.example.EduCabinet.service.StudentService;
import com.example.EduCabinet.model.StudentResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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



    @GetMapping("/mystats")
    public String showMyStatsPage(Model model, HttpServletRequest request) {


        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        LocalDateTime uploadtime = LocalDateTime.now();

        Integer userId = (Integer) session.getAttribute("userId");

        Student student = studentRepository.findById(userId).orElse(null);
        if (student == null) {
            return "redirect:/login";
        }

        model.addAttribute("settings", student.getSettings());
        model.addAttribute("name", student != null ? student.getName() : null);



        Teacher teacher = student != null ? student.getTeacher() : null;
        model.addAttribute("teachername", teacher != null ? teacher.getName() : null);
        List<Homework> list = homeworkService.findAll();




        List<StudentResponse> responseList = studentResponseRepository.findAllResponsesByStudent(student);

        model.addAttribute("responseList", responseList);



        return "mystats";
    }


    @PostMapping("/upload")
    public String uploadHomework(@RequestParam("homeworkText") String homeworkText,
                                 @RequestParam("homeworkId") Integer homeworkId,
                                 @RequestParam("responseFile") MultipartFile responseFile,
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

            try {
                if (!responseFile.isEmpty()) {
                    response.setFileName(responseFile.getOriginalFilename());
                    response.setFileType(responseFile.getContentType());
                    response.setResponseFile(responseFile.getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/error";
            }

            studentResponseRepository.save(response);
        }

        return "redirect:/api/students/mystats";
    }

    @GetMapping("/download/{responseId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("responseId") Integer responseId, HttpSession session) {
        StudentResponse response = studentResponseRepository.findById(responseId)
                .orElseThrow(() -> new RuntimeException("Response not found"));
        //is it teacher and student?
        String role = (String) session.getAttribute("role");
        if (role == null || (!role.equals("STUDENT") && !role.equals("TEACHER"))) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        byte[] fileBytes = response.getResponseFile();
        if (fileBytes == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(response.getFileType()));
        headers.setContentDisposition(ContentDisposition.attachment().filename(response.getFileName()).build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileBytes);
    }
}

