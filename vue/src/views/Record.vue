<template>
  <div style="padding: 10px">

    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入关键字" style="width: 20%" clearable/>
      <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
    </div>


    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="id" label="id" />
      <el-table-column prop="date" label="日期" value-format="yyyy-MM-dd HH:mm:ss" />
      <el-table-column prop="trainDataset" label="训练集" />
      <el-table-column prop="file" label="测试集" />
      <!--          <el-table-column label="测试集" width="150" prop="file">-->
      <!--            <template #default="scope">-->
      <!--              <el-button link type="primary" size="small" @click="look(scope.row.file)"> 查看 </el-button>-->
      <!--            </template>-->
      <!--          </el-table-column>-->
      <el-table-column prop="algorithm" label="算法" />
      <el-table-column prop="accuracy" label="准确率" />
<!--      <el-table-column label="结果" width="100" prop="result">-->
<!--        <template #default="scope">-->
<!--          <el-button link type="primary" size="small" @click="predict(scope.row.result)"> 查看 </el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <el-pagination
        v-model:currentPage="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 20]"
        :small="small"
        :disabled="disabled"
        :background="background"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange">
    </el-pagination>

  </div>

</template>

<script>


import request from "@/utils/request";

export default {
  name: 'Record',
  components: {

  },
  data(){
    return{
      search:'',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      form:{},
      tableData: [
      ]
    }
  },
  created() {
    this.load()
  },
  methods:{
    predict(result){
      window.location.href=result
    },
    load(){
      request.get("/record",{
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res=>{
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    // add(){
    //   this.dialogVisible = true
    //   this.form = {}
    // },
    // save(){
    //   if (this.form.id){//更新
    //     request.put("/user",this.form).then(res => {
    //       console.log(res)
    //       if (res.code === '0') {
    //         this.$message({
    //           type: "success",
    //           message: "更新成功"
    //         })
    //       } else {
    //         this.$message({
    //           type: "success",
    //           message: res.msg
    //         })
    //       }
    //       this.load()
    //       this.dialogVisible = false
    //     })
    //   }else {//新增
    //     request.post("/user",this.form).then(res =>{
    //       console.log(res)
    //       if (res.code === '0') {
    //         this.$message({
    //           type: "success",
    //           message: "新增成功"
    //         })
    //       } else {
    //         this.$message({
    //           type: "success",
    //           message: res.msg
    //         })
    //       }
    //       this.load()
    //       this.dialogVisible = false
    //     })
    //   }
    //
    // },
    // handleEdit(row){
    //   this.form = JSON.parse(JSON.stringify(row))
    //   this.dialogVisible = true
    // },
    // handleDelete(id){
    //   console.log(id)
    //   request.delete("/record/"+id).then(res=>{
    //     if (res.code === '0') {
    //       this.$message({
    //         type: "success",
    //         message: "删除成功"
    //       })
    //     } else {
    //       this.$message({
    //         type: "success",
    //         message: res.msg
    //       })
    //     }
    //     this.load()
    //   })
    // },
    handleSizeChange(){//改变每页个数
      this.load()
    },
    handleCurrentChange(){//改变当前页面
      this.load()
    },
  }
}
</script>
