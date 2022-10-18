<template>
  <div style="width: 100%">
    <div style="color: deepskyblue;font-size: 30px;padding: 30px 400px;">
      缺陷预测
    </div>
    <div style="display: flex">
      <div>

        <div style="border: 1px solid lightskyblue;display: flex; margin: 30px 280px;">
          <div style="text-align: center; padding: 10px; color: deepskyblue;width: 100px">
            算法选择
          </div>
          <div style="width: 300px;padding: 0 30px">
            <el-radio v-model="form.algorithm" label="KNN" size="large">KNN</el-radio>
            <el-radio v-model="form.algorithm" label="逻辑回归" size="large">逻辑回归</el-radio>
          </div>
        </div>

        <div style="display: flex ;border: 1px solid lightskyblue;margin: 30px 280px;">
          <div style="text-align: center; padding: 10px; color: deepskyblue;width: 100px">
            训练集选择
          </div>
          <div style="width: 300px;padding: 0 30px">
            <el-radio v-model="form.trainDataset" label="JDT.csv" size="large">JDT</el-radio>
            <el-radio v-model="form.trainDataset" label="Lucene.csv" size="large">Lucene</el-radio>
            <el-radio v-model="form.trainDataset" label="PDE.csv" size="large">PDE</el-radio>
          </div>
        </div>

        <div style="display: flex ;border: 1px solid lightskyblue;margin: 30px 280px;">
          <div style="text-align: center; padding: 10px; color: deepskyblue;width: 100px">
            测试集选择
          </div>
          <div style="width: 300px;padding: 0 30px">
            <el-radio v-model="form.file" label="JDT.csv" size="large">JDT</el-radio>
            <el-radio v-model="form.file" label="Lucene.csv" size="large">Lucene</el-radio>
            <el-radio v-model="form.file" label="PDE.csv" size="large">PDE</el-radio>
          </div>
        </div>

        <div style="display: flex ;border: 1px solid lightskyblue;margin: 30px 280px;">
          <div style="text-align: center; padding: 10px; color: deepskyblue;width: 100px">
            准确率
          </div>
          <div style="width: 300px;padding: 10px 30px">
            {{accuracy}}
          </div>
        </div>

<!--        <div style="display: flex ;border: 1px solid lightskyblue;margin: 30px 280px;">-->
<!--&lt;!&ndash;          <label type="primary" style="width: 400px;margin: 30px 280px;">准确率</label>&ndash;&gt;-->
<!--          <div style="text-align: center; padding: 10px; color: deepskyblue;width: 100px">-->
<!--            准确率-->
<!--          </div>-->
<!--          <label></label>-->
<!--        </div>-->

<!--        <div style="margin: 20px;width: 400px">-->
<!--          <el-upload-->
<!--              drag-->
<!--              action="http://localhost:9090/files/upload"-->
<!--              :on-success="filesUploadSuccess"-->
<!--          >-->
<!--            <el-icon class="el-icon&#45;&#45;upload"><upload-filled /></el-icon>-->
<!--            <div class="el-upload__text" style="color: deepskyblue">-->
<!--              添加文件-->
<!--            </div>-->
<!--          </el-upload>-->
<!--        </div>-->

        <div>
          <el-button type="primary" @click="preview" style="width: 500px;margin: 30px 280px;">
            开始预测
          </el-button>
        </div>

        <div>
          <el-button type="primary" @click="refresh" style="width: 500px;margin: 10px 280px;">
            重新预测
          </el-button>
        </div>


      </div>

<!--      <div style="margin: 40px  30px">-->
<!--        <el-table :data="tableData" border style="width: 100%">-->
<!--          <el-table-column prop="id" label="id" width="50" />-->
<!--          <el-table-column prop="date" label="日期" width="120" value-format="yyyy-MM-dd HH:mm:ss" />-->
<!--          <el-table-column prop="trainDataset" label="训练集" width="100"/>-->
<!--          <el-table-column prop="file" label="测试集" width="100"/>-->
<!--&lt;!&ndash;          <el-table-column label="测试集" width="150" prop="file">&ndash;&gt;-->
<!--&lt;!&ndash;            <template #default="scope">&ndash;&gt;-->
<!--&lt;!&ndash;              <el-button link type="primary" size="small" @click="look(scope.row.file)"> 查看 </el-button>&ndash;&gt;-->
<!--&lt;!&ndash;            </template>&ndash;&gt;-->
<!--&lt;!&ndash;          </el-table-column>&ndash;&gt;-->
<!--          <el-table-column prop="algorithm" label="算法" width="150"/>-->
<!--          <el-table-column prop="accuracy" label="准确率" width="150"/>-->
<!--&lt;!&ndash;          <el-table-column label="结果" width="150" prop="result">&ndash;&gt;-->
<!--&lt;!&ndash;            <template #default="scope">&ndash;&gt;-->
<!--&lt;!&ndash;              <el-button link type="primary" size="small" @click="predict(scope.row.result)"> 查看 </el-button>&ndash;&gt;-->
<!--&lt;!&ndash;            </template>&ndash;&gt;-->
<!--&lt;!&ndash;          </el-table-column>&ndash;&gt;-->
<!--        </el-table>-->
<!--      </div>-->

    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Predict",
  data(){
    return {
      // tableData: [],
      accuracy: 0,
      form: {},
    }
  },
  // created() {
  //   this.load()
  // },
  methods: {
    preview() {
      this.form.date = new Date().getTime()
      request.post("/predict", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "预测成功"
          })
          console.log(res)
          this.accuracy = res.data
          // this.load()
          // this.form={}
        }else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
    refresh(){
      this.form={}
      this.accuracy = 0
    },
    // load() {
    //   request.get("/predict").then(res => {
    //     console.log(res)
    //     this.tableData=res.data
    //   })
    // },
  }

}
</script>

<style scoped>
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