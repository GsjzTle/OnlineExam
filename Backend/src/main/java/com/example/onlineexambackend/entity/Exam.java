package com.example.onlineexambackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("exam_exam")
@Data
public class Exam {
    @TableId(type = IdType.AUTO)
    private Integer eid;
    private Integer uid;
    private String className;
    private String subjectName;
    private String author;
    private String title;
    private String description;
    private String problemChoice; // 选择题列表
    private String problemSubject; // 主观题列表
    private String scoreChoice; // 选择题分值
    private String scoreSubject; // 主观题分值
    private Integer visible;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
