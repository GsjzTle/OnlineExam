package com.example.onlineexambackend.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onlineexambackend.common.Result;
import com.example.onlineexambackend.entity.Exam;
import com.example.onlineexambackend.entity.ExamData;
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
        exam.setCreateTime(new Date());
        examMapper.insert(exam);
        return Result.success();
    }

    @GetMapping("/all")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "", value = "className") String className,
                              @RequestParam(defaultValue = "", value = "select_subject") String select_subject){
        LambdaQueryWrapper<Exam> wrapper = Wrappers.lambdaQuery();
        wrapper.like(Exam::getClassName, className).and
                (q1->{q1.like(Exam::getSubjectName, select_subject);});

        Page<Exam> Page = new Page<>(pageNum, pageSize);
        Page.addOrder(OrderItem.desc("begin_time")); // 按照 id 排序
        Page<Exam> examDataPage = examMapper.selectPage(Page, wrapper);
        return Result.success(examDataPage);
    }
}
