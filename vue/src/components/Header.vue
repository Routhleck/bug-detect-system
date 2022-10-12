<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #252222; display: flex">
    <div style="width: 200px; padding-left: 70px; font-weight: bold; color: #7a0c3d">后台管理</div>
    <div style="flex: 1"></div>
    <div style="width: 100px">
      <el-dropdown>
        <span class="el-dropdown-link">
          <br>{{ user?.nickName}}
          <el-icon class="el-icon--right">
        <arrow-down />
      </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/person')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: ['user'],
  data() {
    return{
      user: {}
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
  }
}
</script>

<style scoped>

</style>