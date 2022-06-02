package com.example.onlineexambackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("user_user")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer uid;
    private String realName;
    private String className;
    private String username;
    private String password;
    private Integer role;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
