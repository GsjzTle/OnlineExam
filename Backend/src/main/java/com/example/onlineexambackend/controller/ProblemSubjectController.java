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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/problemsubject")
public class ProblemSubjectController {
    @Resource
    private ProblemSubjectMapper problemSubjectMapper;

    @DeleteMapping
    public Result<?> deleteById(@RequestParam(value = "pid") Integer pid){
        problemSubjectMapper.deleteById(pid);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "", value = "select_subject") String search){
        LambdaQueryWrapper<ProblemSubject> wrapper = Wrappers.lambdaQuery();
        if(StrUtil.isNotBlank(search)) {
            wrapper.like(ProblemSubject::getSubjectName, search);
        }
        Page<ProblemSubject> Page = new Page<>(pageNum, pageSize);
        Page.addOrder(OrderItem.desc("pid")); // 按照 id 排序
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

    @GetMapping("/subjectName")
    public Result<?> getBySubjectName(@RequestParam(value = "subjectName") String subjectname){
        LambdaQueryWrapper<ProblemSubject> wrapper = Wrappers.lambdaQuery();
        wrapper.like(ProblemSubject::getSubjectName, subjectname);
        List<ProblemSubject> problemSubjects = problemSubjectMapper.selectList(wrapper);
        return Result.success(problemSubjects);
    }

    @GetMapping("/random")
    public Result<?> getRandom(@RequestParam(value = "subjectName") String subjectname, @RequestParam(value = "number") Integer number){
        LambdaQueryWrapper<ProblemSubject> wrapper = Wrappers.lambdaQuery();
        wrapper.like(ProblemSubject::getSubjectName, subjectname);
        List<ProblemSubject> problemSubjects = problemSubjectMapper.selectList(wrapper);
        List<ProblemSubject> randomSubjects = new ArrayList<>();
        int total = problemSubjects.size();
        int[] vis = new int[total];
        Arrays.fill(vis, 0);
        Random rand = new Random();
        while(number > 0){
            int x = rand.nextInt(total);
            while(vis[x] == 1) {
                x = rand.nextInt(total);
            }
            vis[x] = 1;
            number --;
            ProblemSubject problemSubject = problemSubjects.get(x);
            problemSubject.setScore(rand.nextInt(20) + 1);
            randomSubjects.add(problemSubject);
        }
        return Result.success(randomSubjects);
    }
    @PostMapping
    public Result<?> addProblemChoice(@RequestBody ProblemSubject problemSubject){
        problemSubject.setCreateTime(new Date());
        problemSubjectMapper.insert(problemSubject);
        return Result.success();
    }
    @PutMapping
    public Result<?> updateProblemChoice(@RequestBody ProblemSubject problemSubject){
        problemSubjectMapper.updateById(problemSubject);
        return Result.success();
    }
}
