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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/problemchoice")
public class ProblemChoiceController {
    @Resource
    private ProblemChoiceMapper problemChoiceMapper;

    @DeleteMapping
    public Result<?> deleteById(@RequestParam(value = "pid") Integer pid) {
        problemChoiceMapper.deleteById(pid);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "", value = "select_subject") String subjectName) {
        LambdaQueryWrapper<ProblemChoice> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(subjectName)) {
            wrapper.like(ProblemChoice::getSubjectName, subjectName).or
                    (query -> {
                        query.like(ProblemChoice::getPid, subjectName);
                    });
        }
        Page<ProblemChoice> Page = new Page<>(pageNum, pageSize);
        Page.addOrder(OrderItem.desc("pid")); // ζη§ id ζεΊ
        Page<ProblemChoice> problemChoicePage = problemChoiceMapper.selectPage(Page, wrapper);
        return Result.success(problemChoicePage);
    }

    @GetMapping
    public Result<?> getProblemChoice(@RequestParam(value = "pid") Integer pid) {
        ProblemChoice problemChoice = problemChoiceMapper.selectById(pid);
        return Result.success(problemChoice);
    }

    @GetMapping("/all")
    public Result<?> getAll() {
        List<ProblemChoice> problemChoices = problemChoiceMapper.selectList(null);
        return Result.success(problemChoices);
    }

    @GetMapping("/subjectName")
    public Result<?> getBySubjectName(@RequestParam(value = "subjectName") String subjectname) {
        LambdaQueryWrapper<ProblemChoice> wrapper = Wrappers.lambdaQuery();
        wrapper.like(ProblemChoice::getSubjectName, subjectname);
        List<ProblemChoice> problemChoices = problemChoiceMapper.selectList(wrapper);
        return Result.success(problemChoices);
    }

    @GetMapping("/random")
    public Result<?> getRandom(@RequestParam(value = "subjectName") String subjectname, @RequestParam(value = "number") Integer number) {
        LambdaQueryWrapper<ProblemChoice> wrapper = Wrappers.lambdaQuery();
        wrapper.like(ProblemChoice::getSubjectName, subjectname);
        List<ProblemChoice> problemChoices = problemChoiceMapper.selectList(wrapper);
        List<ProblemChoice> randomChoices = new ArrayList<>();
        int total = problemChoices.size();
        int[] vis = new int[total];
        Arrays.fill(vis, 0);
        Random rand = new Random();
        while (number > 0) {
            int x = rand.nextInt(total);
            while (vis[x] == 1) {
                x = rand.nextInt(total);
            }
            vis[x] = 1;
            number--;
            ProblemChoice problemChoice = problemChoices.get(x);
            problemChoice.setScore(rand.nextInt(10) + 1);
            randomChoices.add(problemChoice);
        }
        return Result.success(randomChoices);
    }

    @PostMapping
    public Result<?> addProblemChoice(@RequestBody ProblemChoice problemChoice){
        problemChoice.setCreateTime(new Date());
        problemChoiceMapper.insert(problemChoice);
        return Result.success();
    }
    @PutMapping
    public Result<?> updateProblemChoice(@RequestBody ProblemChoice problemChoice){
        problemChoiceMapper.updateById(problemChoice);
        return Result.success();
    }
}
