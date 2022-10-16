<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px)"
        :default-active="path"
        router
        class="el-menu-vertical-demo">
      <el-sub-menu index="1">
        <template #title>系统管理</template>
        <el-menu-item index="/predict">预测</el-menu-item>
        <el-menu-item index="/user" v-if="user.role === 1">用户管理</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/book">书籍管理</el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Aside",
  data(){
    return{
      user:{},
      path: this.$route.path
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user")||"{}"
    this.user = JSON.parse(userStr)

    request.get("/user/" + this.user.id).then(res=>{
      if (res.code === '0'){
        this.user =res.data
      }
    })
  }
}
</script>

<style scoped>

</style>