<template>
<!--  第一层是总图层-->
  <div style="width: 100%; height: 100vh; background-color: #4e438c; overflow: hidden">
<!--    登录总图层-->
    <div style="width: 400px; margin: 150px auto">
<!--字体图层-->
      <div style="color: #cccccc; font-size: 30px; text-align: center; padding: 30px">欢迎登录</div>
      <el-form :model="form" size="large" :rules="rules" ref="form">
<!--        使用icon的四个步骤-->
<!--        这里的prop与后面rules中的username是相一致的-->
      <el-form-item prop ="username">
<!--        其一，在el-input组件里面声明:prefix-icon-->
        <el-input :prefix-icon="User" v-model="form.username" >
<!--          其二，在el-input组件中加入一个template标签并定义其为#prefix-->
          <template #prefix>
            <el-icon class="el-input__icon"><User /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop ="password" >
        <el-input  :prefix-icon="Lock" v-model="form.password" show-password>
          <template #prefix>
            <el-icon class="el-input__icon"><Lock /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button style="width: 100%" type="primary" @click="login">登录</el-button>
      </el-form-item>
      <el-form-item>
        <el-button style="width: 100%" type="danger" @click="$router.push('/register')">没有密码？点此注册</el-button>
      </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<el-icon><User /></el-icon>
<el-icon><Lock /></el-icon>

<script>
// 其三，import具体的图标
import {User,Lock} from "@element-plus/icons";
import request from "@/utils/ruquest";

export default {
  name: "Login",
  // 其四，在组件中定义
  components: {
    User, Lock
  },
  data() {
    return{
      form:{},
      rules: {
        username:[
            {required: true, message: '请输入用户名', trigger: 'blur'}
            // { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
        ],
        password:[
          {required: true, message: '请输入密码', trigger: 'blur'}
          // { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
        ],
      }
    }
  },
  created() {
    sessionStorage.removeItem("user")
  },
  methods:{
    login() {
      // 这里设置的意思是只有在rules规定满足的情况下valid之后才会执行request请求
      this.$refs['form'].validate((valid) =>{
        if (valid){
          // request就是用于向后端发送请求的工具
          request.post("/user/login", this.form).then(res => {
            if (res.code === '0'){
              this.$message({
                type: "success",
                message: "登录成功"
              })
              // 登陆成功之后进行页面跳转
              sessionStorage.setItem("user", JSON.stringify(res.data)) //获取用户信息
              this.$router.push("/") // 登录成功之后进行界面的跳转
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

<style scoped>

</style>