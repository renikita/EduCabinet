package com.example.MyStatsBeta.modelparent;

import jakarta.persistence.*;
import lombok.*;



@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public enum Role{
        TEACHER,
        STUDENT
    }

    public enum Status{
        ACTIVATED,
        BLOCKED
    }
    private String login;
    private String name;
    private String password;
    private Role role;
    private Status status;


}
