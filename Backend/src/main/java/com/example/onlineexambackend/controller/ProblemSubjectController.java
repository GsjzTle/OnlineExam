package com.example.onlineexambackend.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onlineexambackend.common.Result;
import com.example.onlineexambackend.entity.ProblemChoice;
import com.example.onlineexambackend.entity.ProblemSubject;
import com.example.onlineexambackend.mapper.ProblemSubjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/problemsubject")
public class ProblemSubjectController {
    @Resource
    private ProblemSubjectMapper problemSubjectMapper;

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<ProblemSubject> wrapper = Wrappers.lambdaQuery();
        if(StrUtil.isNotBlank(search)) {
            wrapper.like(ProblemSubject::getTitle, search).or
                    (query->{query.like(ProblemSubject::getPid, search);});
        }
        Page<ProblemSubject> Page = new Page<>(pageNum, pageSize);
        Page.addOrder(OrderItem.asc("pid")); // 按照 id 排序
        Page<ProblemSubject> problemSubjectPage = problemSubjectMapper.selectPage(Page, wrapper);
        return Result.success(problemSubjectPage);
    }
    @GetMapping
    public Result<?> getProblemSubject(@RequestParam(value = "pid")Integer pid){
        ProblemSubject problemSubject = problemSubjectMapper.selectById(pid);
        return Result.success(problemSubject);
    }

    @GetMapping("/all")
    public Result<?> getAll(){
        List<ProblemSubject> problemSubjects = problemSubjectMapper.selectList(null);
        return Result.success(problemSubjects);
    }

    @GetMapping("/subject")
    public Result<?> getBySubjectName(@RequestParam(value = "subjectName") String subjectname){
        LambdaQueryWrapper<ProblemSubject> wrapper = Wrappers.lambdaQuery();
        wrapper.like(ProblemSubject::getSubjectName, subjectname);
        List<ProblemSubject> problemSubjects = problemSubjectMapper.selectList(wrapper);
        return Result.success(problemSubjects);
    }

}
