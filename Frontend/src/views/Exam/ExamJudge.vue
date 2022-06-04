<template>
  <div>
    <div class="container">
      <!--      班级选择器-->
      <div class="handle-box">
        <el-select v-model="select_class" placeholder="请选择班级" class="handle-select mr10">
          <el-option label="所有班级" value=""></el-option>
          <el-option v-for="classes in Classes" :label="classes.className" :value="classes.className"/>
        </el-select>
        <el-select v-model="select_subject" placeholder="请选择科目" class="handle-select mr10">
          <el-option label="所有科目" value=""></el-option>
          <el-option v-for="subject in Subjects" :label="subject.subjectName" :value="subject.subjectName"/>
        </el-select>
        <el-input v-model="search_title" placeholder="请输入考试名称" class="handle-input mr10"></el-input>
        <el-button type="primary" icon="el-icon-search" @click="load">搜索</el-button>
      </div>
      <!--      表格-->
      <el-table :data="ExamData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
        <el-table-column prop="realName" label="学生姓名" width="78" align="center"></el-table-column>
        <el-table-column label="班级" width="160" align="center">
          <template #default="scope">{{ scope.row.className }}</template>
        </el-table-column>
        <el-table-column prop="subjectName" label="科目" align="center" width="130"></el-table-column>
        <el-table-column prop="title" label="考试名称"></el-table-column>
        <el-table-column label="选择题得分" width="170" align="center">
          <template #default="scope">
            <div v-if="scope.row.gradeChoice == -2">
              <el-button type="danger" plain size="small" style="float: right">本场考试没有选择题
              </el-button>
            </div>
            <div v-else-if="scope.row.gradeChoice == -1">
              <el-tag type="info">未评测</el-tag>
              <el-button type="warning" plain size="small" style="float: right" @click="AutoJudge(scope.row)">自动评测
              </el-button>
            </div>
            <div v-else>
              <el-tag type="success" size="large">{{ scope.row.gradeChoice }} 分</el-tag>
              <el-button type="warning" plain size="small" style="float: right" @click="AutoJudge(scope.row)">重新评测
              </el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="主观题得分" align="center" width="170">
          <template #default="scope">
            <div v-if="scope.row.gradeSubject == -2">
              <el-button type="danger" plain size="small" style="float: right">本场考试没有主观题
              </el-button>
            </div>
            <div v-else-if="scope.row.gradeSubject == -1">
              <el-tag type="info">未批阅</el-tag>
              <el-button type="warning" plain size="small" style="float: right" @click="ManualJudge(scope.row)">手动批阅
              </el-button>
            </div>
            <div v-else>
              <el-tag type="success" size="large">{{ scope.row.gradeSubject }} 分</el-tag>
              <el-button type="warning" plain size="small" style="float: right" @click="ManualJudge(scope.row)">重新批阅
              </el-button>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="总得分" align="center" width="150">
          <template #default="scope">
            <div v-if="scope.row.gradeTotal == -1" style="font-weight: bolder">
              <el-tag type="info">未完成所有题型打分</el-tag>
            </div>
            <div v-else>
              <el-tag type="primary" size="large">{{ scope.row.gradeTotal }} 分</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="submitTime" label="提交时间" align="center" width="180"></el-table-column>
        <el-table-column label="操作" width="180" align="center" fixed="right">
          <template #default="scope">
            <el-button type="text" icon="el-icon-lx-attention" @click="handleEdit(scope.row)">查看
            </el-button>
            <el-popconfirm title="你确定要删除这条记录吗?" @confirm="deleteExamData(scope.row.id)">
              <template #reference>
                <el-button type="text" icon="el-icon-delete" class="red">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--  分页-->
    <div style="margin-top: 10px;">
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
    <!--    弹框 -->
    <el-dialog
        v-model="dialogVisible"
        width="60%"
        :before-close="handleClose"
        center
        top="10vh"
    >
      <el-button
          :plain="selectColor(problem)"
          :type="getColor(problem)"
          size="small"
          v-for="(problem, index) in ProblemSubject"
          :disable-transitions="false"
          style="margin: 5px"
          @click="load1(problem.index)">
        {{ problem.index + 1 }}
      </el-button>
      <el-carousel @change="changeSubject" :autoplay="false" height="60vh" type="card">
        <el-carousel-item v-for="(problem, index) in ProblemSubject">
          <div style="text-align: center; vertical-align: center">
            <h1 style="color: #000000">#{{ index + 1 }}. {{ problem.title }}</h1>
            <el-tag type="success" effect="dark" size="small" style="font-size: 15px; font-weight: bolder">分值：
              {{ problem.score }}
            </el-tag>
          </div>
          <el-card style="margin-top: 10px;">
            <template #header>
              <h2 style="text-align: center;"><b>题目描述</b></h2>
            </template>
            <p style="word-break: break-all;">{{ problem.description }}</p>
          </el-card>
          <el-card style="margin-top: 10px;">
            <template #header>
              <h2 style="text-align: center">学生答案</h2>
            </template>
            <p style="word-break: break-all;">{{ UserSubjcet[index] }}</p>
          </el-card>
          <el-card style="margin-top: 10px;">
            <template #header>
              <h2 style="text-align: center;"><b>教师打分</b></h2>
            </template>
            <center>
              <el-input-number v-model.number="teacherScore[index]" :min="0" :max="visProblem.score"
                               @change="setSession"/>
            </center>
          </el-card>
        </el-carousel-item>
      </el-carousel>
      <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取消</el-button>
              <el-button type="primary" @click="submitJudge">提交</el-button>
            </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "../../utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  data() {
    return {
      dialogVisible: false,
      select_class: '',
      select_subject: '',
      search_title: '',
      total: 0,
      pageSize: 10,
      currentPage: 1,
      ExamData: [],
      nowdata: {}, // 当前批阅的数据
      UserSubjcet: [],
      Classes: [],  // 班级
      Subjects: [], // 学科
      ProblemSubject: [],
      teacherScore: [],
      visProblem: {
        index: 0
      },
    }
  },
  methods: {
    changeSubject(index) {
      this.load1(index)
    },
    setSession() {
      sessionStorage.setItem("teacherScore_" + this.nowdata.id.toString(), JSON.stringify(this.teacherScore));
    },
    getColor(problem) {
      if (this.teacherScore[problem.index] != null) return "primary"
      return "info"
    },
    selectColor(problem) {
      return this.visProblem.index != problem.index;
    },
    // 加载所有提交数据
    load() {
      request.get("/examdata/all", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search_title: this.search_title,
          select_class: this.select_class,
          select_subject: this.select_subject
        }
      }).then(res => {
        this.ExamData = res.data.records
        for (let i = 0; i < this.ExamData.length; i++) {
          if (this.ExamData[i].gradeChoice == -1 || this.ExamData[i].gradeSubject == -1) this.ExamData[i].gradeTotal = -1
          else this.ExamData[i].gradeTotal = Math.max(0,this.ExamData[i].gradeChoice) + Math.max(0,this.ExamData[i].gradeSubject)
          request.get("/user/realname", {params: {uid: this.ExamData[i].uid}}).then(res => {
            this.ExamData[i].realName = res.data.realName
          })
        }
        this.total = res.data.total
      })
    },
    // 根据 pid 获取某道主观题
    load1(index) {
      this.visProblem = this.ProblemSubject[index]
      this.dialogVisible = true
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum
      this.load()
    },
    handleClose() {
      ElMessageBox.confirm('你确定要退出批阅吗?',"提示",{
        type:"warning"
      })
          .then(() => {
            this.dialogVisible = false
          })
          .catch(() => {
          })
    },
    handleEdit(exam_user) {
      this.$router.push({
        path: '/editorexam',
        query: {
          eid: exam_user.eid,
          id: exam_user.id,
          userChoice: exam_user.userChoice,
          userSubject: exam_user.userSubject,
          autoScore: exam_user.autoScore,
          teacherScore: exam_user.teacherScore,
        }
      })
    },
    deleteExamData(id) {
      request.delete("/examdata", {params: {id: id}}).then(res => {
        ElMessage({
          type: "success",
          message: "删除成功"
        })
        this.load()
      })
    },
    AutoJudge(exam_user) {
      request.put("/examdata/choicejudge", exam_user).then(res => {
        if(res.code == -1) {
          ElMessage({
            type: "error",
            message: res.msg
          })
          return
        }
        ElMessage({
          type: "success",
          message: "评测结束"
        })
        this.load()
      })
    },
    ManualJudge(exam_user) {
      this.nowdata = exam_user
      this.UserSubjcet = exam_user.userSubject.split("|#|")
      let teacherScore = sessionStorage.getItem("teacherScore_" + this.nowdata.id.toString());
      if (teacherScore != null) this.teacherScore = JSON.parse(teacherScore);
      else this.teacherScore = []
      request.get("/exam", {
        params: {
          eid: exam_user.eid
        }
      }).then( async res => {
        if(res.data.problemSubject.length == 0) {
          ElMessage({
            type: "error",
            message: "本场考试没有主观题"
          })
          return
        }
        let ProblemSubject = res.data.problemSubject.split("|#|")
        let ScoreSubject = res.data.scoreSubject.split("|#|")
        this.ProblemSubject = []
        for (let i = 0; i < ProblemSubject.length; i++) {
          await request.get("/problemsubject", {params: {pid: ProblemSubject[i]}}).then(res1 => {
            let x = res1.data
            x.index = i
            x.score = ScoreSubject[i]
            x.type = 2
            this.ProblemSubject.push(x)
          })
        }
        this.load1(0)
      })
    },
    submitJudge() {
      let score = 0
      for (let i = 0; i < this.ProblemSubject.length; i++) {
        if (this.teacherScore[i] == null) {
          ElMessage({type: "warning", message: "未完成所有题目批阅"})
          return
        }
        score += this.teacherScore[i]
      }
      this.nowdata.gradeSubject = score
      this.nowdata.teacherScore = this.teacherScore.join("|#|")
      if (this.nowdata.gradeSubject == -1 || this.nowdata.gradeChoice == -1) this.nowdata.gradeTotal = -1
      this.nowdata.gradeTotal = this.nowdata.gradeChoice + this.nowdata.gradeSubject
      request.put("/examdata/subjectjudge", this.nowdata).then(res => {
        this.load()
        this.dialogVisible = false
        ElMessage({type: "success", message: "批阅完成"})
      })
    },
  },
  created() {
    this.load()
    request.get("/class/all").then(res => {
      this.Classes = res.data
    })
    request.get("/subject/all").then(res => {
      this.Subjects = res.data
    })
  },
};
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

/deep/ .el-carousel__item.is-animating {
  background: #f7d1d1;
}

/deep/ .el-dialog--center .el-dialog__body {
  text-align: initial;
  padding: 0 10px 0 10px;
  background: #baddde;
}

/deep/ .el-card {
  border: 1px solid #0c0c0c;
}

/deep/ .el-card__header {
  background-color: #7497b4;
  padding: 1px
}

/deep/ .el-carousel__indicator--horizontal button {
  width: 8px;
  height: 8px;
  background: #ffffff;
  border-radius: 50%;
  opacity: 0.5;
}

/deep/ .el-carousel__indicator--horizontal.is-active button {
  width: 24px;
  height: 8px;
  background: #ffffff;
  opacity: 0.5;
  border-radius: 10px;
}

</style>
