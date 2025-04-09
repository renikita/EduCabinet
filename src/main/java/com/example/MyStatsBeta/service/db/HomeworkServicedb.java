package com.example.MyStatsBeta.service.db;

import com.example.MyStatsBeta.model.Homework;
import com.example.MyStatsBeta.model.Student;
import com.example.MyStatsBeta.repository.HomeworkRepository;
import com.example.MyStatsBeta.repository.StudentRepository;
import com.example.MyStatsBeta.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkServicedb implements HomeworkService {

    @Autowired
    HomeworkRepository homeworkRepository;

    @Override
    public Homework save(Homework homework) {
        return homeworkRepository.save(homework);
    }
    @Override
    public Homework findById(Integer id){return homeworkRepository.findById(id).get();}
    //homeworkRepository.findById(id).orElseTrow() - > new HomeworkNotFoundException(id); - реліз

    @Override
    public List<Homework> findAll() {
        return homeworkRepository.findAll();
    }

    @Override
    public List<Homework> saveAllAndFlush(Iterable<Homework> homework){
        throw new RuntimeException("Not Supported");
    }
}
