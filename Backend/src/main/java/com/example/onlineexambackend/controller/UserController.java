package com.example.onlineexambackend.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.onlineexambackend.common.Result;
import com.example.onlineexambackend.entity.ExamData;
import com.example.onlineexambackend.entity.ProblemSubject;
import com.example.onlineexambackend.entity.User;
import com.example.onlineexambackend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @DeleteMapping
    public Result<?> deleteById(@RequestParam(value = "uid") Integer uid){
        userMapper.deleteById(uid);
        return Result.success();
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody User user){
        userMapper.updateById(user);
        return Result.success();
    }
    @GetMapping("/all")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "", value = "select_class") String select_class,
                              @RequestParam(defaultValue = "", value = "search_real_name") String search_real_name){
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.like(User::getClassName, select_class).and
                (q1->{q1.like(User::getRealName, search_real_name);});
        Page<User> Page = new Page<>(pageNum, pageSize);
        Page.addOrder(OrderItem.desc("uid")); // ζη§ uid ζεΊ
        Page<User> examDataPage = userMapper.selectPage(Page, wrapper);
        return Result.success(examDataPage);
    }

    @GetMapping
    public Result<?> getUserByID(@RequestParam(value = "uid") Integer id){
        User user = userMapper.selectById(id);
        return Result.success(user);
    }
    @PostMapping("/register")
    public Result<?>register(@RequestBody User user){
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername());
        User res = userMapper.selectOne(wrapper);
        if(res != null) return Result.error("-1", "η¨ζ·εε·²ε­ε¨");
        user.setPassword(SecureUtil.md5(user.getPassword()));
        user.setCreateTime(new Date());
        userMapper.insert(user);
        return Result.success();
    }
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).last("limit 1");
        User res = userMapper.selectOne(wrapper);
        if(res == null) return Result.error("-1", "η¨ζ·εζε―η ιθ――");
        if(!res.getPassword().equals(SecureUtil.md5(user.getPassword()))) return Result.error("-1", "η¨ζ·εζε―η ιθ――");
        return Result.success(res);
    }

    @GetMapping("/get_user")
    public Result<?> getUser(@RequestParam("uid") Integer uid){
        User user = userMapper.selectById(uid);
        return Result.success();
    }

    @GetMapping("/count")
    public Result<?> getUser(){
        Long user_count = userMapper.selectCount(null);
        return Result.success(user_count);
    }
}
