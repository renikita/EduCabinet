package com.example.EduCabinet.service.db;

import com.example.EduCabinet.model.Message;
import com.example.EduCabinet.modelparent.User;
import com.example.EduCabinet.repository.MessageRepository;
import com.example.EduCabinet.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Service
public class MessageServicedb implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public void sendMessage(Message message) {
        //save method
        //required validation as separately function
        String content = message.getContent();
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty");
        }
        if (message.getSender() == null || message.getReceiver() == null) {
            throw new IllegalArgumentException("Sender or receiver cannot be null");
        }
        if (message.getSender().equals(message.getReceiver())) {
            throw new IllegalArgumentException("Sender and receiver cannot be the same");
        }

       messageRepository.save(message);
    }

    @Override
    public void deleteMessage(Integer messageId, User sender) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            if (message.getSender().equals(sender)) {
                messageRepository.delete(message);
            } else {
                throw new IllegalArgumentException("Only the sender can delete the message");
            }
        } else {
            throw new IllegalArgumentException("Message not found");
        }
    }

    @Override
    public Page<Message> getMessages(User user1, User user2) {
        if (user1 == null || user2 == null) {
            throw new IllegalArgumentException("Users cannot be null");
        }
        if (user1.equals(user2)) {
            throw new IllegalArgumentException("Cannot retrieve messages between the same user");
        }
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.ASC, "timestamp"));
        return messageRepository.findMessagesBetweenUsers(user1, user2, pageable);
    }

    @Override
    public void updateMessage(Integer messageId, String newContent, User sender) {
        //valid
        if (newContent == null || newContent.trim().isEmpty()) {
            throw new IllegalArgumentException("New content cannot be null or empty");
        }
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            if (message.getSender().equals(sender)) {
                message.setContent(newContent);
                messageRepository.save(message);
            } else {
                throw new IllegalArgumentException("Only the sender can update the message");
            }
        } else {
            throw new IllegalArgumentException("Message not found");
        }
    }
}
