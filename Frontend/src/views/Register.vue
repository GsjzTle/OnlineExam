<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">在线考试系统</div>
      <el-form :model="data" :rules="rules" ref="register" label-width="0px" class="ms-content">
        <el-form-item prop="username">
          <el-input v-model="data.username" placeholder="用户名">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" placeholder="密码" v-model="data.password">
            <template #prepend>
              <el-button icon="el-icon-lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="check">
          <el-input type="password" placeholder="确认密码" v-model="data.check">
            <template #prepend>
              <el-button icon="el-icon-lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="Register">注册</el-button>
          <el-button type="warning" @click="$router.push('/login')">已有账户？点击登录！</el-button>
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
    let validator_check = (rule, value, callback) => {
      if (this.data.password !== value) {
        callback(new Error('密码不一致'))
      } else {
        callback()
      }
    }
    return {
      rules: {
        username: [
          {required: true, message: '用户名不能为空', trigger: 'blur',},
          {min: 5, max: 20, message: '长度在 5 到 20 个字符', trigger: 'blur'},
          {
            required: true,
            pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9.·-]+$/,
            message: '用户名不支持特殊字符',
            trigger: 'blur'
          }
        ],
        password: [
          {required: true, message: '密码不能为空', trigger: 'blur',},
          {min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur'},
        ],
        check: [
          {required: true, message: '密码不能为空', trigger: 'blur',},
          {min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur'},
          {validator: validator_check, trigger: 'blur'},
        ],
      },
      data: {
        username: "",
        password: "",
        check:"",
      },
    }
  },
  methods:{
    Register(){
      this.$refs['register'].validate((valid) => {
        if (valid) {
          console.log(this.data)
          request.post("/user/register", this.data).then(res => {
            if (res.code == '0') {
              ElMessage({
                type: "success",
                message: "注册成功"
              })
              this.$router.push("/login") //登录成功后页面跳转到主页
            } else {
              ElMessage({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })
    }
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