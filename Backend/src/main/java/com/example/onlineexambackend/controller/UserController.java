package com.example.onlineexambackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.onlineexambackend.common.Result;
import com.example.onlineexambackend.entity.ProblemSubject;
import com.example.onlineexambackend.entity.User;
import com.example.onlineexambackend.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserMapper userMapper;

    @GetMapping("/realname")
    public Result<?> getUserByID(@RequestParam(value = "uid") Integer uid){
        User user = userMapper.selectById(uid);
        return Result.success(user);
    }


}
