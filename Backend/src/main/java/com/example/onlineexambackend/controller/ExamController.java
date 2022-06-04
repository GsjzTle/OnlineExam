package com.example.onlineexambackend.controller;
import com.example.onlineexambackend.common.Result;
import com.example.onlineexambackend.entity.Exam;
import com.example.onlineexambackend.mapper.ExamMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Resource
    private ExamMapper examMapper;

    @GetMapping
    public Result<?> getExam(@RequestParam(value = "eid") Integer eid){
        Exam exam = examMapper.selectById(eid);
        return Result.success(exam);
    }

    @PostMapping
    public Result<?> addExam(@RequestBody Exam exam){
        System.out.println(exam.toString());
        exam.setCreateTime(new Date());
        examMapper.insert(exam);
        return Result.success();
    }
}
