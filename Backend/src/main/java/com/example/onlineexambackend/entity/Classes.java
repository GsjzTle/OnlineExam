package com.example.onlineexambackend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("class_class")
@Data
public class Classes {
    @TableId(type = IdType.AUTO)
    private Integer cid;
    private String className;
}
