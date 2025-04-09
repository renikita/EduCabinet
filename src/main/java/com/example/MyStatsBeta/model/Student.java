package com.example.MyStatsBeta.model;

import com.example.MyStatsBeta.modelparent.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "students")
public class Student extends User {




    private LocalDateTime uploadTime;

    @ToString.Exclude
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentResponse> responses;


    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "student_homework",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "homework_id")
    )
    private List<Homework> homeworks;


}
