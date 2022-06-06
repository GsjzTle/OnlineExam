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
        <el-button type="primary" icon="el-icon-search" @click="dialogVisible = true" style="float: right">添加试题
        </el-button>
        <el-dialog
            v-model="dialogVisible"
            title="请填写试题信息"
            width="50%">
          <el-form :model="form" label-width="120px">
            <el-form-item label="题目名称">
              <el-input v-model="form.title"/>
            </el-form-item>
            <el-form-item label="题目描述">
              <el-input v-model="form.description" type="textarea"/>
            </el-form-item>
            <el-form-item label="学科">
              <el-select v-model="form.subjectName" placeholder="请选择科目" class="handle-select mr10">
                <el-option v-for="subject in Subjects" :label="subject.subjectName" :value="subject.subjectName"/>
              </el-select>
            </el-form-item>
          </el-form>
          <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">提交</el-button>
      </span>
          </template>
        </el-dialog>
      </div>
<!--      弹窗-->
      <el-dialog
          v-model="dialogVisible1"
          title="请编辑试题信息"
          width="50%">
        <el-form :model="form1" label-width="25%">
          <el-form-item label="题目名称">
            <el-input v-model="form1.title"/>
          </el-form-item>
          <el-form-item label="题目描述">
            <el-input type="textarea" v-model="form1.description"/>
          </el-form-item>
          <el-form-item label="学科">
            <el-select v-model="form1.subjectName" placeholder="请选择科目" class="handle-select mr10">
              <el-option v-for="subject in Subjects" :label="subject.subjectName" :value="subject.subjectName"/>
            </el-select>
          </el-form-item>

        </el-form>
        <template #footer>
          <el-button @click="dialogVisible1 = false">取消</el-button>
          <el-button type="primary" @click="updateProblem">提交</el-button>
        </template>
      </el-dialog>
<!--      表格-->
      <el-table :data=" ProblemSubject" border class="table" ref="multipleTable"
                header-cell-class-name="table-header">
        <el-table-column prop="pid" label="ID" width="55" align="center"></el-table-column>
        <el-table-column prop="title" label="题目类型"></el-table-column>
        <el-table-column label="学科">
          <template #default="scope">{{ scope.row.subjectName }}</template>
        </el-table-column>
        <el-table-column prop="description" label="题目内容"></el-table-column>
        <el-table-column label="操作" width="180" align="center">
          <template #default="scope">
            <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.row)">编辑
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
      dialogVisible: false,
      dialogVisible1:false,
      ProblemSubject: [],
      form: {
        title: '',
        subjectName: '',
        description: '',
      },
      form1: {},
      select_subject: "",
      Subjects: [],
    }
  },
  methods: {
    load() {
      request.get("/problemsubject/page", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          select_subject: this.select_subject
        }
      }).then(res => {
        this.ProblemSubject = res.data.records;
        this.total = res.data.total
      })
    },
    handleDelete(pid) {
      request.delete("/problemsubject", {params: {pid: pid}}).then(res => {
        ElMessage({
          type: "success",
          message: "删除成功"
        })
        this.load()
      })
    },
    handleSearch() {
      this.load()
    },
    handleEdit(problem){
      this.dialogVisible1 = true
      this.form1 = problem
    },
    submit(){
      request.post('/problemsubject', this.form).then(res => {
        ElMessage({
          type: 'success',
          message: "添加成功"
        })
        this.load()
      })
      this.dialogVisible = false
    },
    updateProblem(){
      request.put('/problemsubject', this.form1).then(res => {
        ElMessage({
          type: 'success',
          message: "更新成功"
        })
        this.load()
      })
      this.dialogVisible1 = false
    }

  },
  created() {
    request.get("/subject/all").then(res => {
      this.Subjects = res.data
    })
    this.load()
  }
};
</script>

<style scoped>
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