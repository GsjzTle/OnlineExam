package com.example.onlineexambackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("problem_subject")
@Data
public class ProblemSubject {
    @TableId(type = IdType.AUTO)
    private Integer pid;
    private Integer uid;
    private Integer sid;
    private String subjectName;
    private String title;
    private String description;
    private String standardAnswer;
    private Integer visible;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @TableField(exist = false)
    private Integer score;
}
