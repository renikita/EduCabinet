package com.example.EduCabinet.service;

import com.example.EduCabinet.model.Homework;

import java.util.List;

public interface HomeworkService {

    Homework save(Homework homework);

    Homework findById(Integer id);

    List<Homework> findAll();

    List<Homework> saveAllAndFlush(Iterable<Homework> students);
}
