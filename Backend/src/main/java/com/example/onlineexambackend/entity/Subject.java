package com.example.onlineexambackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("subject_subject")
@Data
public class Subject {
    @TableId(type = IdType.AUTO)
    private Integer sid;
    private String subjectName;
}
