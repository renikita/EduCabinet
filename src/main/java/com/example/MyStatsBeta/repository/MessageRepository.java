package com.example.MyStatsBeta.repository;

import com.example.MyStatsBeta.model.Message;
import com.example.MyStatsBeta.modelparent.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("""
    SELECT m FROM Message m 
    WHERE (m.sender = :user1 AND m.receiver = :user2) 
       OR (m.sender = :user2 AND m.receiver = :user1)
    ORDER BY m.timestamp ASC
""")
    Page<Message> findMessagesBetweenUsers(
            @Param("user1") User user1,
            @Param("user2") User user2,
            Pageable pageable
    );
}
