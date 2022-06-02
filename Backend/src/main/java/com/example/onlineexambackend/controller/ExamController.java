package com.example.onlineexambackend.controller;
import com.example.onlineexambackend.common.Result;
import com.example.onlineexambackend.entity.Exam;
import com.example.onlineexambackend.mapper.ExamMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Resource
    private ExamMapper examMapper;

    @GetMapping
    public Result<?> getExamData(@RequestParam(value = "eid") Integer eid){
        Exam exam = examMapper.selectById(eid);
        return Result.success(exam);
    }
}
