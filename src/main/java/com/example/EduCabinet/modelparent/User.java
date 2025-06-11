package com.example.EduCabinet.modelparent;

import com.example.EduCabinet.model.Settings;
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
    private String email;
    private Role role;
    private Status status;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Settings settings;



}
