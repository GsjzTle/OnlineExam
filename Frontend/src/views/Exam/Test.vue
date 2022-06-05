<template>
<div>
  {{ha}}
</div>
</template>

<script>
import request from "../../utils/request";

export default {
  data() {
    return {
      ha:"",
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
      }],
    }
  },
  methods: {
    load(){
      // 前端发送请求
      // 后端接收前端的请求，并处理然后将数据返回给前端（返回响应）

      // 请求地址。请求参数。
      // get post put delete
      request.get("/user/get_user",{
        params: {
              uid: 1,
            }
      }).then(res => {
        this.ha = res.data.username
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