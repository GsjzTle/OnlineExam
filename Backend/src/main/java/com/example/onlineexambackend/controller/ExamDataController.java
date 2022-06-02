package com.example.onlineexambackend.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onlineexambackend.common.Result;

import com.example.onlineexambackend.entity.Exam;
import com.example.onlineexambackend.entity.ExamData;
import com.example.onlineexambackend.mapper.ExamDataMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/examdata")
public class ExamDataController {
    @Resource
    private ExamDataMapper examDataMapper;

    @GetMapping("/all")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "", value = "search_title") String search_title,
                              @RequestParam(defaultValue = "", value = "select_class") String select_class,
                              @RequestParam(defaultValue = "", value = "select_subject") String select_subject){
        LambdaQueryWrapper<ExamData> wrapper = Wrappers.lambdaQuery();
        wrapper.like(ExamData::getTitle, search_title).and
                    (q1->{q1.like(ExamData::getClassName, select_class);}).and
                    (q2->{q2.like(ExamData::getSubjectName, select_subject);});

        Page<ExamData> Page = new Page<>(pageNum, pageSize);
        Page.addOrder(OrderItem.asc("id")); // 按照 id 排序
        Page<ExamData> examDataPage = examDataMapper.selectPage(Page, wrapper);
        return Result.success(examDataPage);
    }

    @PutMapping
    public Result<?> addExamData(@RequestBody ExamData examData){
        LambdaQueryWrapper<ExamData> wrapper = Wrappers.lambdaQuery();
        wrapper.like(ExamData::getEid, examData.getEid()).and
                (q1->{q1.like(ExamData::getUid, examData.getUid());});
        long cnt = examDataMapper.selectCount(wrapper);
        if(cnt != 0) return Result.error("-1", "您已提交过答案");
        examData.setSubmitTime(new Date());
        examDataMapper.insert(examData);
        return Result.success();
    }
    @DeleteMapping
    public Result<?> deleteExamData(@RequestParam(value = "id")Integer id){
        examDataMapper.deleteById(id);
        return Result.success();
    }
    @PutMapping("/choicejudge")
    public Result<?> choicejudgeExamData(@RequestBody ExamData examData){
        String[] answer_choice = examData.getAnswerChoice().split("\\|#\\|");
        String[] user_score = examData.getUserChoice().split("\\|#\\|");
        String[] score_choice = examData.getScoreChoice().split("\\|#\\|");
        int score = 0;
        for(int i = 0 ; i < answer_choice.length ; i ++){
            if(Objects.equals(answer_choice[i], user_score[i])) score += Integer.parseInt(score_choice[i]);
        }
        examData.setGradeChoice(score);
        examDataMapper.updateById(examData);
        return Result.success();
    }
    @PutMapping("/subjectjudge")
    public Result<?> subjectExamData(@RequestBody ExamData examData){
        examDataMapper.updateById(examData);
        return Result.success();
    }
}
