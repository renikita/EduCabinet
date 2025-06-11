package com.example.EduCabinet.service;

import com.example.EduCabinet.model.Message;
import com.example.EduCabinet.modelparent.User;
import org.springframework.data.domain.Page;

public interface MessageService {

    // methods
    //save
    //delete
    //findByObjects
    //update
    //sendto
    // findById

    public void sendMessage(Message message);
    public void deleteMessage(Integer messageId, User sender);
    public Page<Message> getMessages(User user1, User user2);
    public void updateMessage(Integer messageId, String newContent, User sender);

}
