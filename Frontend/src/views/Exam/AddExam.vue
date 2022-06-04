<template>
  <div>
    <el-card style="position: absolute; width:12vh;bottom: 60vh; right: 2vh; font-weight: bold;height: 15vh;">
      <el-popover
          placement="left"
          trigger="hover"
      >
        <template #reference>
          <el-result :icon="status" style="margin-top: -6vh">
            <template #title>
              <el-button v-if="activeName=='first'" style="font-size: 25px; margin-top: -15vh;" type="text" :disabled="status != 'success'" @click="goNext">设计试卷</el-button>
              <el-button v-else style="font-size: 25px; margin-top: -15vh;" type="text" :disabled="status != 'success'" @click="AddExam">添加</el-button>
            </template>
          </el-result>
        </template>
        {{ hint }}
      </el-popover>
      <!--          <el-button style="font-size: 30px; margin-top: -10vh;color: #00a854" type="text">添加</el-button>-->

    </el-card>
    <el-tabs tab-position="right" :stretch="true" v-model="activeName" :before-leave="checkFirst">
      <el-tab-pane name="first">
        <template #label>
          <p style="font-size: 20px"><i class="el-icon-lx-calendar"></i> 考试信息</p>
        </template>
        <el-card>
          <template #header>
            <span style="font-size: 20px;font-weight: bold;display: block">基本信息</span>
          </template>
          <el-descriptions class="margin-top" :column="3" size="small" border>
            <el-descriptions-item label-align="center" align="center">
              <template #label>
                <span style="color: #ff1313;font-size: 14px">* </span>
                <span style="font-size: 14px">考试名称</span>
              </template>
              <el-input placeholder="请输入考试名称" v-model="EXAM.title" maxlength="20" show-word-limit @input="setSession"/>
            </el-descriptions-item>
            <el-descriptions-item label-align="right" align="center">
              <template #label>
                <span style="color: #ff1313;font-size: 14px">* </span>
                <span style="font-size: 14px">开始时间</span>
              </template>
              <el-date-picker value-format="YYYY-MM-DD HH:mm:ss" size="large" v-model="EXAM.beginTime" type="datetime" placeholder="请选择时间"
                              @change="setSession"/>
            </el-descriptions-item>
            <el-descriptions-item label-align="right" align="center">
              <template #label>
                <span style="color: #ff1313;font-size: 14px">* </span>
                <span style="font-size: 14px">结束时间</span>
              </template>
              <el-date-picker value-format="YYYY-MM-DD HH:mm:ss" size="large" v-model="EXAM.endTime" type="datetime" placeholder="请选择时间"
                              @change="setSession"/>
            </el-descriptions-item>
            <el-descriptions-item label-align="center" align="center">
              <template #label>
                <span style="color: #ff1313;font-size: 14px">* </span>
                <span style="font-size: 14px">班级</span>
              </template>
              <el-select v-model="EXAM.className" placeholder="请选择班级" class="handle-select mr10" size="large"
                         @change="setSession">
                <el-option v-for="classes in Classes" :label="classes.className" :value="classes.className"/>
              </el-select>
            </el-descriptions-item>
            <el-descriptions-item label-align="center" align="center">
              <template #label>
                <span style="color: #ff1313;font-size: 14px">* </span>
                <span style="font-size: 14px">科目</span>
              </template>
              <el-select v-model="EXAM.subjectName" placeholder="请选择科目" class="handle-select mr10" size="large"
                         @change="setSession">
                <el-option v-for="subject in Subjects" :label="subject.subjectName" :value="subject.subjectName"/>
              </el-select>
            </el-descriptions-item>
            <el-descriptions-item label-align="center" align="center">
              <template #label>
                <span style="font-size: 14px">考试描述</span>
              </template>
              <el-input placeholder="请输入考试描述" v-model="EXAM.description" type="textarea" @input="setSession"/>
            </el-descriptions-item>
          </el-descriptions>
          <el-switch
              v-model="EXAM.visible"
              size="large"
              active-text="隐藏考试"
              style="margin-top: 30px; margin-bottom:30px;float: right"
              inactive-value="0"
              active-value="1"
              @change="setSession"
          />
        </el-card>
      </el-tab-pane>
      <el-tab-pane name="second">
        <template #label>
          <p style="font-size: 20px"><i class="el-icon-lx-tag"></i> 设计试卷</p>
        </template>
        <el-row class="row-con">
          <!--            组卷    -->
          <el-col :span="11" :offset="1">
            <el-card style="min-height: 85vh" shadow="hover" class="card">
              <template #header>
                <center><h2>组卷</h2></center>
              </template>
              <!--              选择题 -->
              <el-divider>
                <el-tag size="large"><h3>{{ EXAM.subjectName }} : 选择题</h3></el-tag>
              </el-divider>
              <el-collapse>
                <el-collapse-item v-for="(data, index) in ProblemChoice" :key="data"
                                  :name="String(index+1)">
                  <template #title>
                    <span style="font-size: 16px;display: block">#{{ index + 1 }}.  {{ data.title }}</span>
                  </template>

                  <div style="font-size: 14px"><p style="font-size: 14px">{{ data.description }}</p>
                    选择分值 :
                    <el-input-number style="margin-top: 5px" size="small" v-model.number="Choice_Score[data.pid]"
                                     :min="1" :max="10"/>
                    <el-button style="float: right;" type="text" color="#DC3545" @click="addChoice(data, index)">
                      <i class="el-icon-lx-add"/>添加
                    </el-button>
                  </div>
                </el-collapse-item>
              </el-collapse>
              <br/>
              <br/>
              <!--              主观题 -->
              <el-divider>
                <el-tag size="large"><h3>{{ EXAM.subjectName }} : 主观题</h3></el-tag>
              </el-divider>
              <el-collapse>
                <el-collapse-item v-for="(data, index) in ProblemSubject" :key="data" :name="String(index+1)">
                  <template #title>
                    <span style="font-size: 16px;display: block">#{{ index + 1 }}.  {{ data.title }}</span>
                  </template>
                  <div style="font-size: 14px">
                    <p>{{ data.description }}</p>
                    选择分值 :
                    <el-input-number size="small" v-model.number="Subject_Score[data.pid]" :min="1" :max="20"/>
                    <el-button style="float: right;" type="text" color="#DC3545" @click="addSubject(data, index)">
                      <i class="el-icon-lx-add"/>添加
                    </el-button>
                  </div>
                </el-collapse-item>
              </el-collapse>
              <!--              随机组卷按钮 -->
              <el-divider style="margin-top: 10vh;">
                <el-button circle size="large" @click="AutoConfirm" type="warning" round style="font-weight: bolder">
                  随机组卷
                </el-button>
              </el-divider>
            </el-card>
          </el-col>
          <!--            预览    -->
          <el-col :span="11" :offset="1">
            <el-card style="min-height: 85vh;" shadow="hover" class="card">
              <template #header>
                <center><h2>预览</h2></center>
              </template>
              <!--              选择题-->
              <el-divider>
                <el-tag><h3>{{ EXAM.subjectName }} : 选择题</h3></el-tag>
              </el-divider>
              <el-collapse>
                <el-collapse-item v-for="(data, index) in Choice" :key="data" :name="String(index+1)">
                  <template #title>
                    <span style="font-size: 16px;display: block">#{{ index + 1 }}.  {{ data.title }}</span>
                    <el-tag type="success" effect="dark" size="small" style="margin-left: 10px; margin-top: -2px">
                      {{ Choice_Score[data.pid] }} 分
                    </el-tag>
                  </template>
                  <div style="font-size: 14px">
                    <p>{{ data.description }}</p>
                    <el-button style="float: right;" type="text" color="#DC3545" @click="deleteChoice(data, index)"><i
                        class="el-icon-lx-delete"/>删除
                    </el-button>
                  </div>
                </el-collapse-item>
              </el-collapse>
              <br/>
              <br/>
              <!--              主观题-->
              <el-divider>
                <el-tag><h3>{{ EXAM.subjectName }} : 主观题</h3></el-tag>
              </el-divider>
              <el-collapse>
                <el-collapse-item v-for="(data, index) in Subject" :key="data" :name="String(index+1)">
                  <template #title>
                    <span style="font-size: 16px;display: block">#{{ index + 1 }}.  {{ data.title }}</span>
                    <el-tag type="success" effect="dark" size="small" style="margin-left: 10px; margin-top: -2px">
                      {{ Subject_Score[data.pid] }} 分
                    </el-tag>
                  </template>
                  <div style="font-size: 14px">
                    <p>{{ data.description }}</p>
                    <el-button style="float: right;" type="text" color="#DC3545" @click="deleteSubject(data, index)"><i
                        class="el-icon-lx-delete"/>删除
                    </el-button>
                  </div>
                </el-collapse-item>
              </el-collapse>
              <!--              清空试题 -->
              <el-divider style="margin-top: 10vh;">
                <el-button circle size="large" @click="ClearAll" type="danger" round style="font-weight: bolder">清空试题
                </el-button>
              </el-divider>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <!--     随机组卷 内层-->
      <el-dialog width="30%" title="设置要添加的题目数量" v-model="dialogVisible">
        <el-card>
          <div>选择题数量
            <el-input-number size="small" v-model="ChoiceNum" :min="0" :max="ProblemChoice.length"
                             controls-position="right"/>
          </div>
          <br/>
          <div>主观题数量
            <el-input-number size="small" v-model="SubjectNum" :min="0" :max="ProblemSubject.length"
                             controls-position="right"/>
          </div>
        </el-card>
        <template #footer>
          <el-button @click="dialogVisible = false" size="small">取 消</el-button>
          <el-button type="primary" @click="AutoCombination" size="small">确定</el-button>
        </template>
      </el-dialog>
    </el-tabs>

  </div>

</template>

<script>
import request from "../../utils/request";
import {ElMessage, ElMessageBox, ElNotification} from "element-plus";

export default {
  data() {
    return {
      dialogVisible: false,
      status: "warning",
      hint: "请将考试信息填写完整",
      EXAM: {},
      User:{},
      Classes: [],   // 班级
      Subjects: [],  // 科目
      activeName: "first",
      ProblemChoice: [], // 该科目下的所有选择题
      ProblemSubject: [], // 该科目下的所有主观题
      Choice_Score: [], // 选择题每道题分值
      Subject_Score: [], // 主观题每道题分值
      Choice: [], // 要添加的选择题
      VisChoice: [],
      Subject: [],  // 要添加的主观题
      VisSubject: [],
      ChoiceNum: 3, // 要随机生成的选择题数量
      SubjectNum: 3, // 要随机生成的主观题数量
      score: "",
    }
  },
  methods: {
    load() {
      request.get("/problemsubject/subjectName", {
        params: {
          "subjectName": this.EXAM.subjectName
        }
      }).then(res => {
        this.ProblemSubject = res.data
        for (let i = 0; i < res.data.length; i++) this.Subject_Score[res.data[i].pid] = 1
      })
      request.get("/problemchoice/subjectName", {
        params: {
          "subjectName": this.EXAM.subjectName
        }
      }).then(res => {
        this.ProblemChoice = res.data
        for (let i = 0; i < res.data.length; i++) this.Choice_Score[res.data[i].pid] = 1
      })
    },
    // 添加、删除 选择题、主观题
    addSubject(data, index) {
      if (this.VisSubject[data.pid] == 1) {
        ElMessage({
          type: "warning",
          message: "已添加，请勿重复操作"
        })
        return;
      }
      ElMessage({
        type: "success",
        message: "添加成功"
      })
      this.VisSubject[data.pid] = 1
      if (data.score == null) data.score = 1
      this.Subject.push(data)
      this.Check2()
    },
    addChoice(data, index) {
      if (this.VisChoice[data.pid] == 1) {
        ElMessage({
          type: "warning",
          message: "已添加，请勿重复操作"
        })
        return;
      }
      ElMessage({
        type: "success",
        message: "添加成功"
      })
      this.VisChoice[data.pid] = 1
      if (data.score == null) data.score = 1
      this.Choice.push(data)
      this.Check2()
    },
    deleteSubject(data, index) {
      this.VisSubject[data.pid] = 0;
      this.Subject.splice(index, 1)
      ElMessage({
        type: "success",
        message: "删除成功"
      })
      this.Check2()
    },
    deleteChoice(data, index) {
      this.VisChoice[data.pid] = 0;
      this.Choice.splice(index, 1)
      ElMessage({
        type: "success",
        message: "删除成功"
      })
      this.Check2()
    },
    // 清空所有试题
    ClearAll() {
      ElMessageBox.confirm('你确定要将已添加的所有试题清空吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        ElMessage({
          type: 'success',
          message: '清空成功',
        })
        this.VisSubject = []
        this.VisChoice = []
        this.Subject = []
        this.Choice = []
        this.Check2()
      }).catch(() => {
      })
    },
    // 存储考试信息
    setSession() {
      sessionStorage.setItem("EXAM", JSON.stringify(this.EXAM));
      this.Check1()
    },
    goNext(){
      this.activeName = 'second'
      this.Check2()
    },
    Check1() {
      if (this.EXAM.title == null || this.EXAM.title.length == 0 || this.EXAM.beginTime == null ||
          this.EXAM.endTime == null || this.EXAM.className == null || this.EXAM.subjectName == null) {
        this.hint = '请将考试信息填写完整'
        this.status = "warning"
        return
      }
      let begin = new Date(this.EXAM.beginTime)
      let end = new Date(this.EXAM.endTime)
      if (begin.getTime() >= end.getTime()) {
        this.hint = '考试开始时间必须严格小于结束时间!'
        this.status = "error"
        return
      }
      let begin_add_five_minutes = begin.setTime((begin.getTime() + 5000 * 60))
      if (begin_add_five_minutes > end.getTime()) {
        this.hint = '考试时间太短，请进行调整!'
        this.status = "warning"
      }
      this.status = "success"
      this.hint = "点击进入下一步"
    },
    Check2() {
      if(this.Choice.length == 0 && this.Subject.length == 0) {
        this.status = "warning"
        this.hint = "请至少添加一道试题"
      } else {
        this.status = "success"
        this.hint = "点击即可完成添加"
      }
    },
    // 检查考试信息是否填写完善
    checkFirst() {
      if (this.EXAM.title == null || this.EXAM.title.length == 0 || this.EXAM.beginTime == null ||
          this.EXAM.endTime == null || this.EXAM.className == null || this.EXAM.subjectName == null) {
        ElMessageBox.alert('请将考试信息填写完整', '提示', {confirmButtonText: 'OK', type: "warning"})
        this.activeName = "first"
        return false
      }
      let begin = new Date(this.EXAM.beginTime)
      let end = new Date(this.EXAM.endTime)
      if (begin.getTime() >= end.getTime()) {
        ElMessageBox.alert('考试开始时间必须严格小于结束时间!', '提示', {confirmButtonText: 'OK', type: "error"})
        this.activeName = "first"
        return false
      }
      let begin_add_five_minutes = begin.setTime((begin.getTime() + 5000 * 60))
      if (begin_add_five_minutes > end.getTime()) {
        ElMessageBox.alert('考试时间太短，请进行调整!', '提示', {confirmButtonText: 'OK', type: "warning"})
        this.activeName = "first"
        return false
      }
      this.load()
      this.Check2()
      return true
    },
    AutoConfirm() {
      ElMessageBox.confirm('随机组卷将清空已添加好的试题，是否继续?', '提示', {
        confirmButtonText: '继续',
        cancelButtonText: '放弃',
        type: "warning"
      }).then(() => {
        this.dialogVisible = true
      }).catch(() => {

      })
    },
    AutoCombination() {
      this.VisSubject = []
      this.Subject = []
      if(this.ChoiceNum == 0 && this.SubjectNum == 0){
        ElNotification({
          title: '提示',
          type: "warning",
          message: '至少需要添加一道试题',
          duration: 2500,
        })
        return
      }
      request.get("/problemchoice/random", {
        params: {subjectName: this.EXAM.subjectName, number: this.ChoiceNum}
      }).then(res => {
        this.Choice = []
        this.VisChoice = []
        this.Choice = res.data
        for (let i = 0; i < res.data.length; i++) {
          this.Choice_Score[res.data[i].pid] = res.data[i].score
          this.VisChoice[res.data[i].pid] = 1
        }
        this.Check2()
      })
      request.get("/problemsubject/random", {
        params: {subjectName: this.EXAM.subjectName, number: this.SubjectNum}
      }).then(res => {
        this.Subject = []
        this.VisSubject = []
        this.Subject = res.data
        for (let i = 0; i < this.Subject.length; i++) {
          this.Subject_Score[res.data[i].pid] = res.data[i].score
          this.VisSubject[this.Subject[i].pid] = 1
        }
        this.Check2()
      })
      ElNotification({
        title: '提示',
        type: "success",
        message: '组卷成功',
        duration: 2500,
      })
      this.dialogVisible = false
    },
    AddExam(){
      let user = sessionStorage.getItem("user")
      // 暂未对 user 表进行处理，先用临时数据代替一下
      if(user == null) {
          this.User.uid = 1
          this.User.realName = "小唐"
      } else {
        this.User = JSON.parse(user)
      }
      let problem_Choice = [], problem_Subject = [], score_Choice = [], score_Subject = []
      for(let i = 0 ; i < this.Choice.length ; i ++) {
        problem_Choice.push(this.Choice[i].pid)
        score_Choice.push(this.Choice_Score[this.Choice[i].pid])
      }
      for(let i = 0 ; i < this.Subject.length ; i ++) {
        problem_Subject.push(this.Subject[i].pid)
        score_Subject.push(this.Subject_Score[this.Subject[i].pid])
      }
      this.EXAM.uid = this.User.uid
      this.EXAM.author = this.User.realName
      this.EXAM.problemChoice = problem_Choice.join("|#|")
      this.EXAM.problemSubject = problem_Subject.join("|#|")
      this.EXAM.scoreChoice = score_Choice.join("|#|")
      this.EXAM.scoreSubject = score_Subject.join("|#|")
      console.log(this.EXAM)
      request.post("/exam", this.EXAM).then(res => {
        ElMessage({
          type: "success",
          message: "添加成功!"
        })
      })
    },


  },
  created() {
    request.get("/class/all").then(res => {
      this.Classes = res.data
    })
    request.get("/subject/all").then(res => {
      this.Subjects = res.data
    })
    let EXAM = sessionStorage.getItem("EXAM")
    if (EXAM != null) this.EXAM = JSON.parse(EXAM)
    this.Check1()
  }
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

/deep/ .el-card__header {
  background-color: aliceblue;
  padding: 10px
}

/deep/ .el-card {
  border: 1px solid #97a8be;
}

.my-label {
  background: #333333;
}

.row-con {
  display: flex;
  flex-flow: row wrap;
}

.row-con .card {
  height: 99%;
}
</style>
