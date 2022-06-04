<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="id" label="编号" width="180"></el-table-column>
    <el-table-column prop="name" label="名称" width="180"></el-table-column>
  </el-table>
</template>

<script>
import request from "../../utils/request";

export default {
  data() {
    return {
      realName: "haha",
      tableData:[{
        id:"1",
        name:"小唐",
      }, {
        id:"2",
        name:"刘大壮",
      } , {
        id:"3",
        name:"臭臭",
      }]
    }
  },
  methods: {
    load(){
      request.get("/user/test", {
        params:{
          uid: "3"
        }
      }).then(res => {
        this.realName = res.data.realName
      })
    }
  },
  created() {
    this.load()
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