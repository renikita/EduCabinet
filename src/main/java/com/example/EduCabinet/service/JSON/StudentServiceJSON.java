package com.example.EduCabinet.service.JSON;

import com.example.EduCabinet.model.Student;
import com.example.EduCabinet.service.StudentService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
@Service
public class StudentServiceJSON implements StudentService {

    final String fileName = "students.json";

    final Gson gson = new Gson();
    @Override
    public Student save(Student student) {
        List<Student> list = findAll();
        list.add(student);
        saveAllAndFlush(list);
        return student;
    }

    @Override
    public Student findById(Integer id) {
        return null;
    }

    @Override
    public Student findByName(String name) {
        return null;
    }

    @Override
    public Student ChangeName(String nameBefore, String nameAfter) {
        return null;
    }


    @Override
    public List<Student> findAll() {
        try {
            ArrayList<Student> list = gson.fromJson(new FileReader(fileName), new TypeToken<>(){});
            return list;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Student> saveAllAndFlush(Iterable<Student> students) {
        String json = gson.toJson(students);
        try(PrintStream printStream = new PrintStream(fileName)){
            printStream.print(json);
        }catch (Exception e){
            e.printStackTrace();
        }
        return (List<Student>) students;
    }



}
