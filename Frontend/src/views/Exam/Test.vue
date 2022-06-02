<template>
  <div>
    {{test}}
  </div>
</template>


<script>
import request from "../../utils/request";
export default{
  data(){
    return {
      test:"1",
    }
  },
  methods:{

    SendRequest(){
      // 1. 请求方法  get post delete put
      // 2. 请求参数
      // 3. 请求地址
      request.get("/user/test", {params:{"id": 1,}}).then(res =>{
        this.test = res.data.realName;
      })

    }
  },
  created() {
    this.SendRequest()
  }
}
</script>
