package com.example.onlineexambackend.controller;

import com.example.onlineexambackend.common.Result;
import com.example.onlineexambackend.entity.Subject;
import com.example.onlineexambackend.mapper.SubjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    @Resource
    private SubjectMapper subjectMapper;

    @GetMapping("/all")
    public Result<?> getAll(){
        List<Subject> subjects = subjectMapper.selectList(null);
        return Result.success(subjects);
    }
}
