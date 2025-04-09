package com.example.MyStatsBeta.model;

import com.example.MyStatsBeta.modelparent.User;
import lombok.*;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "teachers")
public class Teacher extends User {

    @ToString.Exclude
    @OneToMany(mappedBy = "teacher")
    private List<Student> students = new ArrayList<>();;

    @ToString.Exclude
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Homework> homeworks;
}
