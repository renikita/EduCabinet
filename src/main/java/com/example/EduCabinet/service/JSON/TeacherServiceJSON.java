package com.example.EduCabinet.service.JSON;

import com.example.EduCabinet.model.Teacher;
import com.example.EduCabinet.service.TeacherService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
@Service
public class TeacherServiceJSON implements TeacherService {
    final String fileName = "teachers.json";
    final Gson gson = new Gson();
    @Override
    public Teacher save(Teacher teacher) {
        List<Teacher> list = findAll();
        list.add(teacher);
        saveAllAndFlush(list);
        return teacher;
    }

    @Override
    public Teacher findById(Integer id) {
        return null;
    }

    @Override
    public List<Teacher> findAll() {
        try {
            ArrayList<Teacher> list = gson.fromJson(new FileReader(fileName), new TypeToken<>(){});
            return list;
        }catch (Exception ex){
            return new ArrayList<>();
        }
    }

    @Override
    public List<Teacher> saveAllAndFlush(Iterable<Teacher> teachers) {
        String json = gson.toJson(teachers);
        try(PrintStream printStream = new PrintStream(fileName)){
            printStream.print(json);
        }catch (Exception e){
            e.printStackTrace();
        }
        return (List<Teacher>) teachers;
    }
}
