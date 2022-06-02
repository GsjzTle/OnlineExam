<template>
  <div>
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
              <el-input placeholder="请输入考试名称" v-model="EXAM.title" maxlength="20" show-word-limit @change="setSession"/>
            </el-descriptions-item>
            <el-descriptions-item label-align="right" align="center">
              <template #label>
                <span style="color: #ff1313;font-size: 14px">* </span>
                <span style="font-size: 14px">开始时间</span>
              </template>
              <el-date-picker size="large" v-model="EXAM.beginTime" type="datetime" placeholder="请选择时间"
                               @change="setSession"/>
            </el-descriptions-item>
            <el-descriptions-item label-align="right" align="center">
              <template #label>
                <span style="color: #ff1313;font-size: 14px">* </span>
                <span style="font-size: 14px">结束时间</span>
              </template>
              <el-date-picker size="large" v-model="EXAM.endTime" type="datetime" placeholder="请选择时间"
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
              <el-input placeholder="请输入考试描述" v-model="EXAM.description" type="textarea"
                        />
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
        <el-row>
          <el-col :span="11" :offset="1">
            <el-card style="height: 85vh">
              <template #header>
                <center><h2>手动组卷</h2></center>
              </template>
              <el-divider><el-tag size="large"><h3>{{ EXAM.subjectName }} : 选择题</h3></el-tag></el-divider>
              <el-collapse>
                <el-collapse-item v-if="VisChoice[index] != -1" v-for="(data, index) in ProblemChoice" :key="data"
                                  :name="String(index+1)">
                  <template #title>
                    <span style="font-size: 16px;display: block">{{ index + 1 }}. {{ data.title }}</span>
                  </template>

                  <div style="font-size: 14px"><p style="font-size: 14px">{{ data.description }}</p>
                    选择分值 :
                    <el-input-number style="margin-top: 5px" size="small" v-model.number="ProblemChoice[index].score"
                                     :min="1" :max="10"/>
                    <el-button style="float: right;" type="text" color="#DC3545" @click="addChoice(data, index)">
                      <i class="el-icon-lx-add"/>添加
                    </el-button>
                  </div>
                </el-collapse-item>
              </el-collapse>
              <br/>
              <br/>
              <el-divider><el-tag size="large"><h3>{{ EXAM.subjectName }} : 主观题</h3></el-tag></el-divider>
              <el-collapse>
                <el-collapse-item v-for="(data, index) in ProblemSubject" :key="data" :name="String(index+1)">
                  <template #title>
                    <span style="font-size: 16px;display: block">{{ index + 1 }}. {{ data.title }}</span>
                  </template>
                  <div style="font-size: 14px">
                    <p>{{ data.description }}</p>
                    选择分值 :
                    <el-input-number size="small" v-model.number="data.score" :min="1" :max="10"/>
                    <el-button style="float: right;" type="text" color="#DC3545" @click="addSubject(data, index)">
                      <i class="el-icon-lx-add"/>添加
                    </el-button>
                  </div>
                </el-collapse-item>
              </el-collapse>
              <el-divider style="margin-top: 20vh;"><el-button circle size="large" @click="" type="warning" round plain> 随机组卷 </el-button></el-divider>
            </el-card>
          </el-col>
          <el-col :span="11" :offset="1">
            <!--            预览    -->
            <el-card style="height: 85vh;">
              <template #header>
                <center><h2>预览</h2></center>
              </template>

              <el-divider><el-tag><h3>{{ EXAM.subjectName }} : 选择题</h3></el-tag></el-divider>
              <el-collapse>
                <el-collapse-item v-for="(data, index) in Choice" :key="data" :name="String(index+1)">
                  <template #title>
                    <span style="font-size: 16px;display: block">{{ index + 1 }}. {{ data.title }}</span>
                  </template>

                  <div style="font-size: 14px">
                    <p>{{ data.description }}</p>
                    <el-tag type="success">{{data.score}} 分</el-tag>
                    <el-button style="float: right;"  type="text" color="#DC3545" @click="deleteChoice(data, index)"><i
                        class="el-icon-lx-delete"/>删除
                    </el-button>
                  </div>
                </el-collapse-item>
              </el-collapse>
              <el-divider><el-tag><h3>{{ EXAM.subjectName }} : 主观题</h3></el-tag></el-divider>
              <el-collapse>
                <el-collapse-item v-for="(data, index) in Subject" :key="data" :name="String(index+1)">
                  <template #title>
                    <span style="font-size: 16px;display: block">{{ index + 1 }}. {{ data.title }}</span>
                  </template>

                  <div style="font-size: 14px">
                    <p>{{ data.description }}</p>
                    <el-tag type="success">{{data.score}} 分</el-tag>
                    <el-button style="float: right;" type="text" color="#DC3545" @click="deleteSubject(data, index)"><i
                        class="el-icon-lx-delete"/>删除
                    </el-button>
                  </div>
                </el-collapse-item>
              </el-collapse>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane name="third">
        <template #label>
          <p style="font-size: 20px"><i class="el-icon-lx-cascades"></i> 发布考试</p>
        </template>
        <div class="container">
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>

</template>

<script>
import request from "../../utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  data() {
    return {
      EXAM: {},
      Classes: [],
      Subjects: [],
      activeName: "first",
      ProblemChoice: [],
      ProblemSubject: [],
      Choice: [],
      VisChoice: [],
      Subject: [],
      VisSubject: [],
      score: "",
    }
  },
  methods: {
    load() {
      request.get("/problemsubject/subject", {
        params: {
          "subjectName": this.EXAM.subjectName
        }
      }).then(res => {
        this.ProblemSubject = res.data
        for (let i = 0; i < this.ProblemSubject.length; i++) this.ProblemSubject.score = 0
      })
      request.get("/problemchoice/subject", {
        params: {
          "subjectName": this.EXAM.subjectName
        }
      }).then(res => {
        this.ProblemChoice = res.data
        for (let i = 0; i < this.ProblemChoice.length; i++) this.ProblemChoice.score = 0
      })
    },
    addSubject(data, index) {
      if (this.VisSubject[index] == -1) {
        ElMessage({
          type: "warning",
          message: "已添加，请勿重复操作"
        })
        return;
      }
      this.VisSubject[index] = -1
      this.Subject[index] = data
      console.log(this.Subject)
    },
    addChoice(data, index) {
      console.log(this.VisChoice)
      if (this.VisChoice[index] == -1) {
        ElMessage({
          type: "warning",
          message: "已添加，请勿重复操作"
        })
        return;
      }
      this.VisChoice[index] = -1
      this.Choice[index] = data
      console.log(this.Choice)
    },
    deleteSubject(data, index) {
      this.VisSubject[index] = 0;
      this.Subject.shift(index)
    },
    deleteChoice(data, index) {
      this.VisChoice[index] = 0;
      this.Choice.shift(index)
    },
    setSession() {
      sessionStorage.setItem("EXAM", JSON.stringify(this.EXAM));
    },
    checkFirst() {
      if (this.EXAM.title == null || this.EXAM.title.length == 0 || this.EXAM.beginTime == null ||
          this.EXAM.endTime == null || this.EXAM.className == null || this.EXAM.subjectName == null) {
        ElMessageBox.alert('考试信息未填写完善', '提示', {confirmButtonText: 'OK', type: "warning"})
        this.activeName = "first"
        return false
      }
      let begin = new Date(this.EXAM.beginTime)
      let end = new Date(this.EXAM.endTime)
      if (begin > end) {
        ElMessageBox.alert('开始时间必须严格小于结束时间', '提示', {confirmButtonText: 'OK', type: "warning"})
        this.activeName = "first"
        return false
      }
      this.load()
      return true
    },
    test() {
      console.log(this.activeName)
    }
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

.my-label {
  background: #333333;
}

</style>
