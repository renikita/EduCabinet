package com.example.MyStatsBeta.model;


import com.example.MyStatsBeta.modelparent.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "settings")
public class Settings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    public enum Theme {
        LIGHT, DARK
    }
    private Theme currentTheme;
    //language uk, en
    private String language;

    private boolean notificationsEnabled;


}
