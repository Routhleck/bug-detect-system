<template>
  <div style="width: 100%">
    <div style="color: deepskyblue;font-size: 30px;padding: 30px 450px;">
      缺陷预测
    </div>
    <div style="display: flex">
      <div>
        <div style="margin: 20px;width: 500px">
          <el-upload
              drag
              action="http://localhost:9090/files/upload"
              :on-success="filesUploadSuccess"
          >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text" style="color: deepskyblue">
              添加文件
            </div>
          </el-upload>
        </div>

        <div style="border: 1px solid lightskyblue;display: flex">
          <div style="text-align: center; padding: 10px; color: deepskyblue;width: 100px">
            算法选择
          </div>
          <div style="width: 400px;padding: 0 30px">
            <el-radio v-model="form.alg" label="KNN" size="large">KNN</el-radio>
            <el-radio v-model="form.alg" label="逻辑回归" size="large">逻辑回归</el-radio>
            <el-button type="primary" @click="preview" style="margin: 0 20px">
              开始预测
            </el-button>
          </div>
        </div>

      </div>

      <div style="margin: 40px  30px">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="id" label="id" width="50" />
          <el-table-column prop="uploadTime" label="日期" width="180" value-format="yyyy-MM-dd HH:mm:ss" />
          <el-table-column label="文件" width="150" prop="file">
            <template #default="scope">
              <el-button link type="primary" size="small" @click="look(scope.row.file)"> 查看 </el-button>
            </template>
          </el-table-column>
          <el-table-column prop="alg" label="算法" width="150"/>
          <el-table-column label="结果" width="150" prop="result">
            <template #default="scope">
              <el-button link type="primary" size="small" @click="predict(scope.row.result)"> 查看 </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "PreView",
  data(){
    return{
      tableData:[],
      form:{}
    }
  },
  methods:{
    predict(result){
      window.location.href=result
    },
    example(){
      window.location.href="http://localhost:9090/files/example/Lucene.csv"
    },
    look(file){
      window.location.href=file
    },
    preview(){
      this.form.uploadTime = new Date().getTime()
      request.post("/api/preview",this.form).then(res =>{
        console.log(res)
        this.load()
      })
    },
    filesUploadSuccess(res){
      console.log(res)
      this.form.file = res.data
    },
    load(){
      request.get("/api/preview").then(res =>{
        console.log(res)
        this.tableData=res.data
      })
    }
  },
  created() {
    this.load()
  }
}
</script>

<style scoped>

</style>