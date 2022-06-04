package com.example.onlineexambackend.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onlineexambackend.common.Result;

import com.example.onlineexambackend.entity.Exam;
import com.example.onlineexambackend.entity.ExamData;
import com.example.onlineexambackend.entity.ProblemSubject;
import com.example.onlineexambackend.mapper.ExamDataMapper;
import com.example.onlineexambackend.mapper.ExamMapper;
import com.example.onlineexambackend.mapper.ProblemChoiceMapper;
import com.example.onlineexambackend.mapper.ProblemSubjectMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/examdata")
public class ExamDataController {
    @Resource
    private ExamDataMapper examDataMapper;
    @Resource
    private ExamMapper examMapper;
    @Resource
    private ProblemChoiceMapper problemChoiceMapper;

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
        Exam exam = examMapper.selectById(examData.getEid());
        if(exam.getProblemChoice().length() == 0) return Result.error("-1", "本场考试没有选择题!");
        String []choice_problem_id = exam.getProblemChoice().split("\\|#\\|");
        String []answer_choice = new String[choice_problem_id.length];
        for(int i = 0 ; i < choice_problem_id.length ; i ++){
            answer_choice[i] = String.valueOf(problemChoiceMapper.selectById(choice_problem_id[i]).getAnswer());
        }
        String[] user_score = examData.getUserChoice().split("\\|#\\|");
        String[] score_choice = examData.getScoreChoice().split("\\|#\\|");
        int score = 0;
        List<String> auto_score_list = new ArrayList<>();
        for(int i = 0 ; i < answer_choice.length ; i ++){
            if(Objects.equals(answer_choice[i], user_score[i])) {
                score += Integer.parseInt(score_choice[i]);
                auto_score_list.add(score_choice[i]);
            } else {
                auto_score_list.add("0");
            }
        }
        examData.setAutoScore(String.join("|#|", auto_score_list));
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
