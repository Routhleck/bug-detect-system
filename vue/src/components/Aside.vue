<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px)"
        :default-active="path"
        router
        class="el-menu-vertical-demo">
      <el-sub-menu index="1">
        <template #title>
          <el-icon><house /></el-icon>
          <span>主页</span>
        </template>
        <el-menu-item index="/predict">
          <el-icon><View /></el-icon>
          <span>缺陷管理</span>
        </el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="2">
        <template #title>
          <el-icon><setting /></el-icon>
          <span>系统管理</span>
        </template>
        <el-menu-item index="/user" v-if="user.role === 1">
          <el-icon><UserFilled /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
        <el-menu-item index="/record">
          <el-icon><Memo /></el-icon>
          <span>历史记录</span>
        </el-menu-item>
        <el-menu-item index="/person">
          <el-icon><edit /></el-icon>
          <span>个人信息</span>
        </el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script>
import request from "@/utils/request";
import {Edit, House, UserFilled, View} from "@element-plus/icons";
import User from "@/views/User";

export default {
  name: "Aside",
  data(){
    return{
      user:{},
      path: this.$route.path
    }
  },
  components:{
    UserFilled,
    User,
    Edit,
    View,
    House,
  },
  created() {
    let userStr = sessionStorage.getItem("user")||"{}"
    this.user = JSON.parse(userStr)

    // request.get("/user/" + this.user.id).then(res=>{
    //   if (res.code === '0'){
    //     this.user =res.data
    //   }
    // })
  }
}
</script>

<style scoped>

</style>