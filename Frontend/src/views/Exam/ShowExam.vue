<template>
  <div v-if="SHOW_PAGE">
  <div style="margin-top: -20px">
      <el-progress :percentage="nowpercentage" :stroke-width="12"
                   :status="nowpercentage == 100 ? 'success' : null" style="margin-top: 20px"/>
    </div>
    <el-row style="margin-top: 10px; margin-left: 20px">
      <el-col :span="16">
        <!--        题目信息-->
        <el-card>
          <template #header>
            <div>
              <el-row>
                <el-col :offset="10" :span="7">
                  <span style="font-size: 25px;font-weight: bolder;text-align: center">#{{ visProblem.index + 1 }}. {{ visProblem.title }}</span>
                </el-col>
                <el-col :offset="3" :span="4">
                  <el-check-tag v-if="visProblem.grade_score != null && visProblem.grade_score != ''" checked style="float:  right; margin-top: 8px">得分： {{visProblem.grade_score}} 分</el-check-tag>
                  <el-check-tag v-else checked style="float:  right; margin-top: 8px">暂未评分</el-check-tag>
                </el-col>
              </el-row>
            </div>
          </template>
          <div style="text-align: center;margin-top: -10px; font-weight: bolder">
            <el-tag type="success" size="small" effect="dark">分值 : {{ visProblem.score }} 分</el-tag>
          </div>
          <h3>题目描述</h3>
          <p style="word-break: break-all;margin-top: 15px">{{ visProblem.description }}</p>
          <br/>
          <div v-if="visProblem.type == 1" style="margin-top: 10px">
            <h3>选择答案</h3>
            <el-radio-group v-model="UserChoice[visProblem.index]">
              <div v-for="(i,index) in visProblem.select ">
                <el-radio :label="index" size="large" style="margin-top: 20px"
                          :disabled="submitted==1">{{ i }}
                </el-radio>
              </div>
            </el-radio-group>
          </div>
          <div v-else>
            <h3>输入答案</h3>
            <el-input style="margin-top: 15px;"
                      v-model="UserSubject[visProblem.index - ProblemChoice.length]"
                      type="textarea" placeholder="请将答案填写在此方框中"
                      :disabled="submitted == 1"/>
          </div>
          <el-button type="primary" @click="toPrevious" style="margin-left: 37%;margin-top: 10%;"
                     :disabled="visProblem.index == 0">上一题
          </el-button>
          <el-button type="primary" @click="toNext" style="margin-left: 50px;margin-top: 10%;"
                     :disabled="visProblem.index==ProblemData.length-1">下一题
          </el-button>
        </el-card>
      </el-col>
      <!--      题目列表   -->
      <el-col :offset="1" :span="6">
        <el-card style="width: 100%">
          <el-divider style="margin-top: -2px"><h3>选择题</h3></el-divider>
          <el-empty :image-size="100" v-if="ProblemChoice.length == 0" style="height: 50px"/>
          <el-button
              :plain="selectColor(problem)"
              :type="getColor(problem)"
              size="small"
              v-for="(problem, index) in ProblemChoice"
              :disable-transitions="false"
              style="margin: 5px"
              @click="load(problem.pid, problem.type, problem.index)">
            {{ problem.index + 1 }}
          </el-button>
          <el-divider style="margin-top: 28px"><h3>主观题</h3></el-divider>
          <el-empty :image-size="100" v-if="ProblemSubject.length == 0"/>
          <el-button
              :plain="selectColor(problem)"
              :type="getColor(problem)"
              size="small"
              v-for="(problem, index) in ProblemSubject"
              :disable-transitions="false"
              style="margin: 5px"
              @click="load(problem.pid, problem.type, problem.index)">
            {{ problem.index + 1 }}
          </el-button>
        </el-card>
        <el-button style="width: 100%; margin-top: 8px;font-weight: bolder; color: #f0f0f0;background: #626aef"
                   size="large" @click="goExamData">返回列表
        </el-button>
      </el-col>
    </el-row>

  </div>

</template>

<script>


import request from "../../utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  data() {
    return {
      SHOW_PAGE:false,
      submitted: '1',
      nowpercentage: '',
      option: '',
      reply: '',
      visProblem: {},
      Exam: {},
      User:{uid:"5"},
      ProblemChoice: [],
      ProblemSubject: [],
      ProblemData: [],
      UserChoice: [],
      UserSubject: [],
      ScoreChoice: [],
      ScoreSubject: [],
      AutoScore: [], // 选择题评分
      TeacherScore: [], // 主观题评分
    }
  },
  methods: {
    load(pid, type, index) {
      if (type == 2) {
        request.get("/problemsubject", {params: {"pid": pid}}).then(res => {
          this.visProblem = res.data
          this.visProblem.index = index
          this.visProblem.type = type
          this.visProblem.score = this.ProblemData[index].score
          this.visProblem.grade_score = this.ProblemData[index].grade_score
        })
      } else {
        request.get("/problemchoice", {params: {"pid": pid}}).then(res => {
          this.visProblem = res.data
          this.visProblem.index = index
          this.visProblem.type = type
          this.visProblem.select = res.data.options.split("|#|")
          this.visProblem.score = this.ProblemData[index].score
          this.visProblem.grade_score = this.ProblemData[index].grade_score
        })
      }
    },
    getColor(problem) {
      if (problem.type == 1 && this.UserChoice[problem.index] != null && this.UserChoice[problem.index] != -1) return "primary"
      if (problem.type == 2 && this.UserSubject[problem.index - this.ProblemChoice.length] != null && this.UserSubject[problem.index - this.ProblemChoice.length].trim() != "") return "primary"
      return "info"
    },
    // 问题被选中时  是朴素按钮或者普通按钮
    selectColor(problem) {
      return this.visProblem.index != problem.index;
    },
    toPrevious() {
      this.load(this.ProblemData[this.visProblem.index - 1].pid, this.ProblemData[this.visProblem.index - 1].type, this.visProblem.index - 1)
    },
    toNext() {
      this.load(this.ProblemData[this.visProblem.index + 1].pid, this.ProblemData[this.visProblem.index + 1].type, this.visProblem.index + 1)
    },
    ProgressPercentage() {
      let NowTimeStamp = (new Date()).valueOf();
      let BeginTimeStamp = (new Date(Date.parse(this.Exam.beginTime))).valueOf();
      let EndTimeStamp = (new Date(Date.parse(this.Exam.endTime))).valueOf();
      this.nowpercentage = (NowTimeStamp - BeginTimeStamp) * 100 / (EndTimeStamp - BeginTimeStamp);
      this.nowpercentage = parseInt(this.nowpercentage);
      if (this.nowpercentage < 0) this.nowpercentage = 0;
      if (this.nowpercentage > 100) this.nowpercentage = 100;
      setInterval(this.ProgressPercentage, 1000);
      clearInterval(this.ProgressPercentage)
      this.ProgressPercentage = null
    },
    goExamData() {
      this.$router.push("/examjudge")
    }
  },
  created() {
    request.get('/examdata/exam_user', {
      params:
          {
            eid: this.$route.query.eid,
            uid: this.$route.query.uid == null ? JSON.parse(window.localStorage.getItem("_User")).uid : this.$route.query.uid
          }
    }).then(res => {
      this.SHOW_PAGE = true
      this.User = JSON.parse(window.localStorage.getItem("_User"))
      if(res.code == "-1" || (res.data.visible == 1 && this.User.role == 1)) {
        ElMessage({
          type: "error",
          message: res.msg
        })
        this.$router.go(-1)
        return
      }
      this.Exam = res.data
      this.UserChoice = res.data.userChoice.split("|#|").map(Number)
      this.UserSubject = res.data.userSubject.split("|#|")
      if(res.data.teacherScore != null && res.data.teacherScore.length != 0)this.TeacherScore = res.data.teacherScore.split("|#|")
      if(res.data.autoScore != null && res.data.autoScore.length != 0) this.AutoScore = res.data.autoScore.split("|#|")
      let ProblemChoice = []
      if(res.data.problemChoice.length != 0) ProblemChoice = res.data.problemChoice.split("|#|")
      let ProblemSubject = []
      if(res.data.problemSubject.length != 0) ProblemSubject = res.data.problemSubject.split("|#|")
      let ScoreChoice = []
      if(res.data.scoreChoice.length != 0) ScoreChoice = res.data.scoreChoice.split("|#|")
      let ScoreSubject = []
      if(res.data.scoreSubject.length != 0) ScoreSubject = res.data.scoreSubject.split("|#|")

      let cnt = 0;
      for (let i = 0; i < ProblemChoice.length; i++) {
        let x = {
          "pid": ProblemChoice[i],
          "type": 1,
          "index": cnt,
          "score": ScoreChoice[i],
          "grade_score": this.AutoScore[i]
        }
        this.ProblemChoice.push(x)
        cnt++
        this.ProblemData.push(x)
      }
      for (let i = 0; i < ProblemSubject.length; i++) {
        let x = {
          "pid": ProblemSubject[i],
          "type": 2,
          "index": cnt,
          "score": ScoreSubject[i],
          "grade_score": this.TeacherScore[i],
        }
        this.ProblemSubject.push(x)
        cnt++;
        this.ProblemData.push(x)
      }
      if(this.ProblemChoice.length != 0) this.load(this.ProblemChoice[0].pid, 1, 0)
      else this.load(this.ProblemSubject[0].pid, 2, 0)
    })
    this.ProgressPercentage()
  }

}
</script>

<style scoped>
.remainingTime {
  display: flex;
  background-color: beige;
  align-items: center;
  justify-content: center;
  color: red;
}


/deep/ .el-radio__label {
  font-size: 20px;
  padding-left: 10px;
  vertical-align: center
}
</style>