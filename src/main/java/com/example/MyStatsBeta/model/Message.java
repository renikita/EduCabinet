package com.example.MyStatsBeta.model;

import com.example.MyStatsBeta.modelparent.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@ManyToOne
@JoinColumn(name="sender_id")
private User sender;
@ManyToOne
@JoinColumn(name="receiver_id")
private User receiver;
private String content;
private LocalDateTime timestamp;
private boolean isDeleted;
}
