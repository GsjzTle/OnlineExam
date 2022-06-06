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
        <!--        弹窗-->
        <el-dialog
            v-model="dialogVisible"
            title="请填写试题信息"
            width="60%">
          <el-form :model="form" label-width="25%">
            <el-form-item label="题目名称">
              <el-input v-model="form.title"/>
            </el-form-item>
            <el-form-item label="题目描述">
              <el-input type="textarea" v-model="form.description"/>
            </el-form-item>
            <el-form-item label="题目学科">
              <el-select v-model="form.subjectName" placeholder="请选择科目" class="handle-select mr10">
                <el-option v-for="subject in Subjects" :label="subject.subjectName" :value="subject.subjectName"/>
              </el-select>
            </el-form-item>
            <el-form-item label="题目选项（多个选项间用 |#| 分割）">
              <el-input v-model="form.options"/>
              <li v-for="(data,index) in form.options.split('|#|')">
                {{ data }} （选项编号为：{{ index }}）
              </li>
            </el-form-item>
            <el-form-item label="正确答案（选项编号）">
              <el-input v-model="form.answer"/>
            </el-form-item>

          </el-form>
          <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitProblem(form)">提交</el-button>
      </span>
          </template>
        </el-dialog>
        <el-dialog
            v-model="dialogVisible1"
            title="请编辑试题信息"
            width="60%">
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
            <el-form-item label="题目选项（多个选项间用 |#| 分割）">
              <el-input v-model="form1.options"/>
              <li v-for="(data,index) in form.options.split('|#|')">
                {{ data }} （选项编号为：{{ index }}）
              </li>
            </el-form-item>
            <el-form-item label="正确答案（选项编号）">
              <el-input v-model="form1.answer"/>
            </el-form-item>

          </el-form>
          <template #footer>
            <el-button @click="dialogVisible1 = false">取消</el-button>
            <el-button type="primary" @click="updateProblem">提交</el-button>
          </template>
        </el-dialog>
        <!--        表格-->
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
    <div>
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     pager-count="10"
                     background
                     layout="total, sizes, prev, pager, next, jumper"
                     :page-sizes="[5, 10, 20]"
                     :total="total"
                     :page-size="pageSize"
                     :current-page="currentPage"/>
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
      select_subject: '',
      Subjects: [],
      dialogVisible: false,
      dialogVisible1: false,
      ProblemChoice: [],
      form: {
        title: '',
        subjectName: '操作系统',
        options: '1|#|2|#|3|#|4',
        answer: '0',
      },
      form1: {},
      total: 0,
      pageSize: 5,
      currentPage: 1,
    }
  },
  methods: {
    load() {
      request.get("/problemchoice/page", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          select_subject: this.select_subject
        }
      }).then(res => {
        this.ProblemChoice = res.data.records;
        console.log(res.data)
        for (let i = 0; i < this.ProblemChoice.length; i++) {
          this.ProblemChoice[i].options = this.ProblemChoice[i].options.split("|#|")
        }
        this.total = res.data.total
      })
    },
    handleSearch() {
      this.load()
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
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum
      this.load()
    },
    handleEdit(problem) {
      this.form1.pid = problem.pid
      this.form1.title = problem.title
      this.form1.description = problem.description
      this.form1.subjectName = problem.subjectName
      this.form1.answer = problem.answer
      this.form1.options = problem.options.join("|#|")
      this.dialogVisible1 = true
    },
    submitProblem(form) {
      request.post('/problemchoice', form).then(res => {
        ElMessage({
          type: 'success',
          message: "添加成功"
        })
        this.load()
      })
      this.dialogVisible = false
    },
    updateProblem(){
      request.put('/problemchoice', this.form1).then(res => {
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
    this.load()
    request.get("/subject/all").then(res => {
      this.Subjects = res.data
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