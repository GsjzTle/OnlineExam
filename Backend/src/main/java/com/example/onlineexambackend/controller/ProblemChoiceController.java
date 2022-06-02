package com.example.onlineexambackend.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onlineexambackend.common.Result;
import com.example.onlineexambackend.entity.ExamData;
import com.example.onlineexambackend.entity.ProblemChoice;
import com.example.onlineexambackend.mapper.ProblemChoiceMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/problemchoice")
public class ProblemChoiceController {
    @Resource
    private ProblemChoiceMapper problemChoiceMapper;

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<ProblemChoice> wrapper = Wrappers.lambdaQuery();
        if(StrUtil.isNotBlank(search)) {
            wrapper.like(ProblemChoice::getTitle, search).or
                    (query->{query.like(ProblemChoice::getPid, search);});
        }
        Page<ProblemChoice> Page = new Page<>(pageNum, pageSize);
        Page.addOrder(OrderItem.asc("pid")); // 按照 id 排序
        Page<ProblemChoice> problemChoicePage = problemChoiceMapper.selectPage(Page, wrapper);
        return Result.success(problemChoicePage);
    }
    @GetMapping
    public Result<?> getProblemChoice(@RequestParam(value = "pid")Integer pid){
        ProblemChoice problemChoice = problemChoiceMapper.selectById(pid);
        return Result.success(problemChoice);
    }

    @GetMapping("/all")
    public Result<?> getAll(){
        List<ProblemChoice> problemChoices = problemChoiceMapper.selectList(null);
        return Result.success(problemChoices);
    }

    @GetMapping("/subject")
    public Result<?> getBySubjectName(@RequestParam(value = "subjectName") String subjectname){
        LambdaQueryWrapper<ProblemChoice> wrapper = Wrappers.lambdaQuery();
        wrapper.like(ProblemChoice::getSubjectName, subjectname);
        List<ProblemChoice> problemChoices = problemChoiceMapper.selectList(wrapper);
        return Result.success(problemChoices);
    }
}
