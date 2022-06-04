package com.example.onlineexambackend.entity;

import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("exam_data")
@Data
public class ExamData {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer eid;
    private Integer uid;
    private String className;
    private String subjectName;
    private String title;
    private String autoScore; // 选择题自动评分
    private String teacherScore; // 主观题教师评分
    private String scoreChoice;
    private String scoreSubject;
    private String userChoice;
    private String userSubject;
    private Integer gradeChoice;
    private Integer gradeSubject;
    private Integer gradeTotal;
    private Integer submitted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date submitTime;
}
