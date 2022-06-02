<template>
  <div class="container">
    <el-input v-model="input" placeholder="请输入班级" />
    <el-button @click="RequestTable">搜索</el-button>
    <el-tag>{{input}}</el-tag>

    <el-table
        border
        :data="tableData"
        style="width: 100%">
      <el-table-column prop="realName" label="真实姓名" width="180"/>
      <el-table-column prop="className" label="班级" width="180"/>
    </el-table>
  </div>
</template>

<script>

import request from "../../utils/request";

export default {
  name: "basetable",
  // 数据
  data() {
    return {
      input:"",
      tableData: [{name: "haha", id: "1",}, {name: "hehe", id: "2",}]
    }
  },
  // 创建一个页面时会执行的事情
  created() {
    this.RequestTable()
  },
  // 函数
  methods: {
    RequestTable(){
      // 1.请求方法
      // 2.请求参数
      // 3.请求地址
      request.get("/user/getTable", {
        params: {
          className: this.input
        }
      }).then(res => {
        this.tableData = res.data
      })
    }
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
</style>
