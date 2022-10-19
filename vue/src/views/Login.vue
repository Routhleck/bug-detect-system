<template>
  <div class="bg"
      style="width: 100%;height: 100vh;background-color: darkslateblue;overflow: hidden;">
    <div style="width: 400px;margin: 150px auto">
      <div style="color: #cccccc;font-size: 30px;text-align: center">欢迎登录</div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-form-item prop="username">
          <el-input :prefix-icon="Avatar" v-model="form.username" />
        </el-form-item>

        <el-form-item prop="password">
          <el-input :prefix-icon="Lock" v-model="form.password" show-password />
        </el-form-item>

        <el-form-item>
          <div style="display: flex">
            <el-input prefix-icon="Key" v-model="form.validCode" style="width: 50%;" placeholder="请输入验证码"></el-input>
            <ValidCode @input="createValidCode" />
          </div>
        </el-form-item>

        <el-form-item>
          <el-button style="width: 100%" type="primary" plain @click="login">登 录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button style="width: 100%" type="primary" plain @click="register">注 册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script>
import { Avatar, Lock, Key } from "@element-plus/icons-vue"
import request from "@/utils/request";
import ValidCode from "@/components/ValidCode";
export default {
  name: "Login",
  components:{
    ValidCode
  },
  setup(){
    return{
      Avatar,
      Lock,
      Key,
    }
  },
  data(){
    return{
      form:{},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      },
      validCode:''
    }
  },
  methods:{
    register(){
      this.$router.push("/register");
    },
    createValidCode(data) {
      this.validCode = data
    },
    login(){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (!this.form.validCode) {
            this.$message.error("请填写验证码")
            return
          }
          if(this.form.validCode.toLowerCase() !== this.validCode.toLowerCase()) {
            this.$message.error("验证码错误")
            return
          }
          request.post("/user/login", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "登录成功"
              })
              sessionStorage.setItem("user", JSON.stringify(res.data))  // 缓存用户信息
              this.$router.push("/predict")
            } else {
              this.$message({
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


<style>
.bg {
  background-image: url("../image/back.jpg");
  background-size: 100% 100%;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  align-items: center;

}
</style>