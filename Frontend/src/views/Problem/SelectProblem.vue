<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 基础表格
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box">
        <el-select v-model="select_subject" placeholder="请选择科目" class="handle-select mr10">
          <el-option label="所有科目" value=""></el-option>
          <el-option v-for="subject in Subjects" :label="subject.subjectName" :value="subject.subjectName"/>
        </el-select>
        <el-button type="primary" icon="el-icon-search" @click="handleSearch" style="margin-left: 10px">搜索</el-button>
        <span style="margin-left: 1100px;">
          <el-button type="primary" icon="el-icon-search" @click="dialogVisible = true">添加试题</el-button>
         <el-dialog
      v-model="dialogVisible"
      title="Tips"
      width="30%"
      :before-close="handleClose">
    <el-form :model="form" label-width="120px">
    <el-form-item label="题目内容">
      <el-input v-model="form.name" />
    </el-form-item>
      <el-form-item label="学科">
      <el-select v-model="form.subjectName" placeholder="请选择学科">
        <el-option label="操作系统" value="操作系统" />
        <el-option label="计算机网络" value="计算机网络" />
      </el-select>
    </el-form-item>
      <el-form-item label="题目选项">
      <el-input v-model="form.select" />
    </el-form-item>
      <el-form-item label="正确答案">
      <el-input v-model="form.answer" />
    </el-form-item>

      </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogVisible = false">提交</el-button>
      </span>
    </template>
  </el-dialog>
        </span>
      </div>
      <el-table :data="ProblemChoice" border class="table" ref="multipleTable"
                header-cell-class-name="table-header">
        <el-table-column prop="pid" label="ID" width="55" align="center"></el-table-column>
        <el-table-column label="学科">
          <template #default="scope">{{ scope.row.subjectName }}</template>
        </el-table-column>
        <el-table-column prop="description" label="题目内容"></el-table-column>
        <el-table-column prop="options" label="题目选项">
          <template #default="scope">
            <li v-for="option in scope.row.options">
              {{ option }}
            </li>
          </template>
        </el-table-column>
        <el-table-column prop="answer" label="正确答案"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑
            </el-button>
            <el-popconfirm title="你确定要删除这条记录吗?" @confirm="handleDelete(scope.row.pid)">
              <template #reference>
                <el-button type="text" icon="el-icon-delete" class="red">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import request from "../../utils/request";
import {ElMessage} from "element-plus";


export default {
  name: "basetable",

  data() {
    return {
      select_subject:'',
      Subjects:[],
      dialogVisible: false,
      ProblemChoice: [],
      form:{
        name:'',
        subjectName:'',
        select:[],
        answer:'',
      }
    }
  },
  methods: {
    load() {
      request.get("/problemchoice/all", {
        params: {}
      }).then(res => {
        this.ProblemChoice = res.data;
        for (let i = 0; i < res.data.length; i++) {
          this.ProblemChoice[i].options = res.data[i].options.split("|#|")
          console.log(res.data[i])
        }
        console.log(res)
      })
    },
    handleDelete(pid) {
      request.delete("/problemchoice", {params: {pid: pid}}).then(res => {
        ElMessage({
          type: "success",
          message: "删除成功"
        })
        this.load()
      })
    },
  },
  created() {
    request.get("/problemchoice/all").then(res => {
      this.ProblemChoice = res.data;
      for (let i = 0; i < res.data.length; i++) {
        this.ProblemChoice[i].options = res.data[i].options.split("|#|")
        console.log(res.data[i])
      }
    })

    request.get("/subject/all").then(res => {
      this.Subjects = res.data
      console.log(res.data)
    })
  }
};
</script>

<style scoped>
.dialog-footer button:first-child {
  margin-right: 10px;
}

.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 200px;
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

.mr20 {
  margin-right: 20px;
}

.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 40px;
}
</style>