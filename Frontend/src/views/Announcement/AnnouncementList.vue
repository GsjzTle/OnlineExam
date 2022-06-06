<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 公告管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button type="primary" icon="el-icon-search" @click="addAnnouncement">添加公告</el-button>
            </div>
            <el-table :data="AnnouncementData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="content" label="公告内容"></el-table-column>
                <el-table-column prop="createDate" label="创建时间" width="200"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template #default="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
         </div>
    </div>
</template>

<script>

import request from "../../utils/request";

export default {
    data() {
      return {
        AnnouncementData: [
          {
            id: '1',
            content: 'balabalabalablabaalassdasdas',
            createDate: '2019.05.23 20:20:20',
            createMan: 'txc'
          }
        ]
      }
    },
  created() {
    request.get("/problemchoice/all").then(res => {
      this.ProblemChoice = res.data;
      for(let i = 0 ; i < res.data.length ; i ++){
        this.ProblemChoice[i].options = res.data[i].options.split("|#|")
        console.log(res.data[i])
      }
    })
  }
}
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>