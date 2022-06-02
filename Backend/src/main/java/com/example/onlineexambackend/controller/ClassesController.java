package com.example.onlineexambackend.controller;

import com.example.onlineexambackend.common.Result;
import com.example.onlineexambackend.entity.Classes;
import com.example.onlineexambackend.mapper.ClassesMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassesController {
    @Resource
    private ClassesMapper classesMapper;

    @GetMapping("/all")
    public Result<?> getAll(){
        List<Classes> classes = classesMapper.selectList(null);
        return Result.success(classes);
    }
}
