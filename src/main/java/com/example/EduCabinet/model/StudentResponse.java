package com.example.EduCabinet.model;

import com.example.EduCabinet.model.enums.HomeworkStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student_responses")
public class StudentResponse {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "homework_id")
    private Homework homework;

    private Integer mark;


    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "student_id")
    private Student student;

    private String responseText;

    private LocalDateTime responseTime;

    @Enumerated(EnumType.STRING)
    private HomeworkStatus homeworkStatus;

    private String fileName;
    private String fileType;

    @Lob
    private byte[] responseFile;

}

