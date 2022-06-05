<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">在线考试系统</div>
      <el-form :model="data" :rules="rules" ref="login" label-width="0px" class="ms-content">
        <el-form-item prop="username">
          <el-input v-model="data.username" placeholder="username">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" placeholder="password" v-model="data.password">
            <template #prepend>
              <el-button icon="el-icon-lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="Login">登录</el-button>
          <el-button type="warning" @click="$router.push('/register')">新用户？点击注册！</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "../utils/request";
import {ElMessage} from "element-plus";

export default {
  data() {
    return {
      rules: {
        username: [
          {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
          },
        ],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
        ],
      },
      data: {
        username: "",
        password: "",
      },
    }
  },
  methods:{
    Login(){
      request.post("/user/login", this.data).then(res => {
        if(res.code == 0){
          ElMessage({
            type:'success',
            message:"欢迎回来, "+res.data.realName
          })
          window.localStorage.setItem("_User", JSON.stringify(res.data))
          this.$router.push('/')
        } else {
          ElMessage({
            type:"error",
            message:res.msg
          })
        }
      })
    },
  }

}
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url(../assets/img/login-bg.jpg);
  background-size: 100%;
}

.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  border-bottom: 1px solid #ddd;
}

.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}

.ms-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #fff;
}
/deep/ .el-button + .el-button{

  margin-left: 0;
}
</style>