<template>
  <div style="padding: 10px">
<!--    功能区-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button type="primary">导入</el-button>
      <el-button type="primary">导出</el-button>
    </div>
<!--    搜索区-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="请输入要搜索的内容。" clearable style="width: 25%"/>
      <el-button type="primary" style="margin-left: 5px" @click="load">搜索</el-button>
    </div>
    <el-table :data="tableData" border stripe style="width: auto">
      <el-table-column prop="modelID" label="模型ID" width="180" sortable=""/>
      <el-table-column prop="userID'" label="用户ID" width="180" />
      <el-table-column prop="username'" label="用户名" width="180" />
      <el-table-column prop="trainDataset" label="训练数据集" />
      <el-table-column prop="testData" label="测试数据" />
      <el-table-column prop="algorithm" label="算法" />
      <el-table-column prop="date" label="日期" />
      <el-table-column  label="操作" >
        <template #default="scope">
          <el-button  size="small"  @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确认删除吗？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button size="small" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :currentPage="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"/>

      <el-dialog v-model="dialogVisible" title="提示" width="30%">
        <el-form :model="form"  label-width="120px">
          <el-form-item label="modelID">
            <el-input v-model="form.modelID" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="用户ID">
            <el-input v-model="form.userID" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="用户名称">
            <el-input v-model="form.username" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="训练数据集">
            <el-input v-model="form.trainDataset" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="测试数据">
            <el-input v-model="form.testData" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="算法">
            <el-input v-model="form.algorithm" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="Countable？">
            <el-radio v-model="form.isCountable" label="1">是</el-radio>
            <el-radio v-model="form.isCountable" label="0">否</el-radio>
          </el-form-item>
          <el-form-item label="日期">
            <el-date-picker v-model="form.createTime" value-format="YYYY-MM-DD" type="date" style="width: 80%" clearable>
            </el-date-picker>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="save">确定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script >

// 这里的request其实就是集成了axios工具的一个方法
import request from "@/utils/ruquest";

export default {
  name: 'HomeView',
  components: {

  },
  data() {
    return{
      form:{},
      dialogVisible: false,
      search:'',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData:[]
    }
  },
  created() {
    this.load()
  },
  methods:{
    load() {
      request.get("/model", {
        params: {
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    add(){
      this.dialogVisible = true
      this.form = {}
    },
    save(){
      if (this.form.id) {//更新
        request.put("/model", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load() //刷新表格数据
          this.dialogVisible = false //关闭弹窗
        })
      } else {
        // 使用axios进行数据的传输交互
        request.post("/model", this.form).then(res => {
          console.log(res)
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
          this.load() //刷新表格数据
          this.dialogVisible = false //关闭弹窗
        })
      }

    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(modelID){
      console.log(id)
      // 这里与后端userController的deleteMapping中的 “@DeleteMapping("/{id}")” 这个相对应，通过占位传递给后台
      request.delete("/model/" + modelID).then(res =>{
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        // 删除之后重新加载表格数据
        this.load()
      })
    },
    handleSizeChange(pageSize) {  // 改变当前每页的个数触发
      // 注意更新pageSize
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum){  // 改变当前页码触发
      // 注意更新pageNum
      this.currentPage = pageNum
      this.load()
    }
  }
}
</script>
