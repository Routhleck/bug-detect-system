<template>
  <div>
    <el-menu
        style="width: 200px; min-height: calc(100vh - 50px)"
        default-active="user"
        router
        class="el-menu-vertical-demo">
      <!--       注意上方使用的一个router方法，当加入这个属性之后，下方的<el-menu-item index="login">中的index即为跳转路由；这就是el-menu的自带的内部方法-->
<!--      导航一-->
      <el-sub-menu index="1">
        <template #title>
          <el-icon><location /></el-icon>
          系统管理
        </template>
          <el-menu-item index="login">
            <el-icon><House /></el-icon>主页
          </el-menu-item>
          <el-menu-item index="user">
            <el-icon><Avatar /></el-icon>用户管理
          </el-menu-item>
      </el-sub-menu>
      <el-menu-item index="data" :route="{path: '/'}">
        <el-icon><Setting /></el-icon>数据管理
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import { Location,Setting,House,Avatar} from '@element-plus/icons';
export default {
  name: "Aside",
  components:{
    Location,Setting,House,Avatar
  }
}
</script>

<style scoped>

</style>