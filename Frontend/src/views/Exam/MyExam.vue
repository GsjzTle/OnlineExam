<template>
  <el-card>
    <el-empty description="没有任何考试数据" v-if="ExamData==null || ExamData.length == 0"/>
    <el-timeline>
      <el-timeline-item v-for="(exam, index) in ExamData" size="large" type="danger" :timestamp="exam.beginTime"
                        placement="top" v-show="exam.visible != 1">
        <el-card style="width: 80%">
<!--          三种不同状态 -->
          <el-tag style="text-align: center;font-weight: bold;float: right;"
                  v-if="getStatus(exam) == 1" type="success" effect="dark" size="small">未开始
          </el-tag>
          <el-tag style="text-align: center;font-weight: bold;float: right;"
                  v-if="getStatus(exam) == 2"
                  type="warning" effect="dark" size="small">
            进行中
          </el-tag>
          <el-tag style="text-align: center;font-weight: bold;float: right;"
                  v-if="getStatus(exam) == 3" type="info" effect="dark" size="small">已结束
          </el-tag>
          <h1 style="margin-top: -1vh">{{ exam.title }}</h1>
          <br/>
          <el-tag size="small" effect="dark">{{ exam.subjectName }}</el-tag>
          <br/>
          <br/>
          <p style="font-family: -webkit-pictograph,cursive;font-size: 20px">开始时间 : {{ exam.beginTime }}</p>
          <p style="font-family: -webkit-pictograph,cursive;font-size: 20px">结束时间 : {{ exam.endTime }}</p>
          <br/>
          <el-row>
            <el-col :span="6">
              <span style="color: #7088a6; font-size: 16px;">考试负责人 : {{ exam.author }}</span>
            </el-col>
            <el-col :span="3" :offset="15">
<!--              三种不同状态对应的按钮-->
              <el-button v-if="getStatus(exam) == 1" class="my_botton" type="text" @click="hint"><u>{{ exam.countDownTime }}</u></el-button>
              <el-button v-if="getStatus(exam) == 2" class="my_botton" type="text" @click="goExam(exam)"><u>进入考试</u></el-button>
              <el-button v-if="getStatus(exam) == 3" class="my_botton" type="text" @click="showExam(exam)"><u>查看成绩</u></el-button>
            </el-col>
          </el-row>
        </el-card>
      </el-timeline-item>
    </el-timeline>
    <!--  分页 -->
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
  </el-card>
</template>

<script>
import request from "../../utils/request";
import {ElMessageBox} from "element-plus";

export default {
  data() {
    return {
      select_subject: "",
      total: 0,
      pageSize: 10,
      currentPage: 1,
      User: {},
      ExamData: [],
    }
  },
  methods: {
    load() {
      console.log(this.User)
      request.get("/exam/all", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          className: this.User.className,
          subjectName: this.select_subject
        }
      }).then(res => {
        this.total = res.data.total
        console.log(res.data)
        this.ExamData = res.data.records
        for(let i = 0 ; i <  this.ExamData.length ; i ++){
          this.ExamData[i].countDownTime = ""
          this.countDown(i)
        }
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
    getStatus(exam) {
      if (Date.parse(exam.beginTime) > new Date()) return 1
      if (Date.parse(exam.beginTime) <= new Date() && Date.parse(exam.endTime) >= new Date()) return 2
      if (Date.parse(exam.endTime) < new Date()) return 3
    },
    twoTimeInterval(_time) {
      let endTime = new Date(_time);
      let startTime = new Date();
      if(startTime.getTime() >= endTime.getTime()) return "倒计时结束"
      let usedTime = endTime - startTime; // 相差的毫秒数
      let days = Math.floor(usedTime / (24 * 3600 * 1000)); // 计算出天数
      let leavel = usedTime % (24 * 3600 * 1000); // 计算天数后剩余的时间
      let hours = Math.floor(leavel / (3600 * 1000)); // 计算剩余的小时数
      let leavel2 = leavel % (3600 * 1000); // 计算剩余小时后剩余的毫秒数
      let minutes = Math.floor(leavel2 / (60 * 1000)); // 计算剩余的分钟数
      let leavel3 = leavel2 % (60 * 1000); // 计算剩余分钟后剩余的毫秒数
      let s = Math.floor(leavel3 / (1000));
      return "剩余 : " + days + '天' + hours + '时' + minutes + '分' + s + "秒";
    },
    countDown(i) {
      let that = this;
      let item = that.ExamData[i];
      that.ExamData[i].countDownFn = setInterval(() => {
        if (that.twoTimeInterval(item.beginTime) == "倒计时结束") {
          clearInterval(that.ExamData[i].countDownFn); //清除定时器
        } else {
          item.countDownTime = that.twoTimeInterval(item.beginTime);
          that.ExamData[i].countDownTime = item.countDownTime
          // that.$set(that.ExamData, item.countDownTime, that.twoTimeInterval(item.beginTime));
        }
      }, 1000);
    },
    goExam(exam){
      this.$router.push({
        path: "/examonline",
        query:{
          eid : exam.eid
        }
      })
    },
    showExam(exam){
      this.$router.push({
        path: "/showexam",
        query: {
          eid : exam.eid,
          uid : this.User.uid
        }
      })
    },
    hint(){
      ElMessageBox.alert('考试未开始，请耐心等待', '提示', {confirmButtonText: 'OK', type:'warning'})
    }
  },
  created() {
    let user = window.localStorage.getItem("_User")
    if (user == null) this.$router.push('/login')
    else this.User = JSON.parse(user)
    this.load()
  },

}
</script>
<style>
.my_botton {
  font-size: 16px;
  float: right;
  color: #5d96d0;
  vertical-align: center;
  margin-top: -1vh
}
</style>