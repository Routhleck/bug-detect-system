<template>
<!--  第一层是总图层-->
  <div style="width: 100%; height: 100vh; background-color: #4e438c; overflow: hidden">
<!--    登录总图层-->
    <div style="width: 400px; margin: 150px auto">
<!--字体图层-->
      <div style="color: #cccccc; font-size: 30px; text-align: center; padding: 30px">欢迎注册</div>
      <el-form ref="form" :model="form" size="large" :rules="rules">
<!--        使用icon的四个步骤-->
      <el-form-item  prop ="username">
<!--        其一，在el-input组件里面声明:prefix-icon-->
        <el-input :prefix-icon="User" v-model="form.username" >
<!--          其二，在el-input组件中加入一个template标签并定义其为#prefix-->
          <template #prefix>
            <el-icon class="el-input__icon"><User /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop ="password">
        <el-input :prefix-icon="Lock" v-model="form.password" show-password>
          <template #prefix>
            <el-icon class="el-input__icon"><Lock /></el-icon>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop ="confirm">
          <el-input :prefix-icon="Lock" v-model="form.confirm" show-password>
            <template #prefix>
              <el-icon class="el-input__icon"><Lock /></el-icon>
            </template>
          </el-input>
      </el-form-item>
      <el-form-item>
       <el-button style="width: 100%" type="primary" @click="register">注册</el-button>
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
  name: "Register",
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
        confirm:[
          {required: true, message: '再次确认你输入的密码', trigger: 'blur'}
          // { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
        ],
      },
    }
  },
  methods:{
    register() {

      if(this.form.password !== this.form.confirm){
        this.$message({
          type: "error",
          message: "两次输入的密码不一致！"
        })
        return
      }

      // 这里设置的意思是只有在rules规定满足的情况下valid之后才会执行request请求
      this.$refs['form'].validate((valid) => {
        if (valid) {
          // request就是用于向后端发送请求的工具
          request.post("/user/register", this.form).then(res => {
            if (res.code === '0'){
              this.$message({
                type: "success",
                message: "注册成功"
              })
              // 注册成功之后进行页面跳转，即为登录界面
              this.$router.push("/login")
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