<template>
  <div style="width: 100%">
    <div style="color: deepskyblue;font-size: 30px;padding: 30px 450px;">
      缺陷预测
    </div>
    <div style="display: flex">
      <div>
        <div style="text-align: center">
          <el-select class="data_data" @change="select_data" :disabled="disabled_data" v-model="form.train_sets"
                     placeholder="请选择训练集">
            <el-option v-for="item in options_data" :key="item.value" :label="item.label" :value="item.value"
                       @click.native="labelClick(item.label)" :disabled="item.disabled">
            </el-option>
          </el-select>
          <!-- </div>
        <div> -->
          <el-select class="csv_select" @change="select_csv" :disabled="disabled_csv" v-model="form.test_sets"
                     placeholder="请选择测试集">
            <el-option v-for="item in options_data" :key="item.value" :label="item.label" :value="item.value"
                       @click.native="labelClick(item.label)" :disabled="item.disabled">
            </el-option>
          </el-select>
        </div>

<!--        <div style="margin: 20px;width: 500px">-->
<!--          <el-upload drag action="http://localhost:9090/files/upload" :on-success="filesUploadSuccess">-->
<!--            <el-icon class="el-icon&#45;&#45;upload">-->
<!--              <upload-filled />-->
<!--            </el-icon>-->
<!--            <div class="el-upload__text" style="color: deepskyblue">-->
<!--              添加文件-->
<!--            </div>-->
<!--          </el-upload>-->
<!--        </div>-->

        <div style="border: 1px solid lightskyblue;display: flex">
          <div style="text-align: center; padding: 10px; color: deepskyblue;width: 100px">
            算法选择
          </div>
          <div style="width: 400px;padding: 0 30px">
            <el-radio v-model="form.algorithm" label="KNN" size="large">KNN</el-radio>
            <el-radio v-model="form.algorithm" label="逻辑回归" size="large">逻辑回归</el-radio>
            <el-button type="primary" @click="preview" style="margin: 0 20px">
              开始预测
            </el-button>
          </div>
        </div>

      </div>

      <div style="margin: 40px  30px">
        <el-table :data="tableData" border style="width: 100%">
<!--          <el-table-column prop="id" label="id" width="50" />-->
          <el-table-column prop="uploadTime" label="日期" width="180" value-format="yyyy-MM-dd HH:mm:ss" />
<!--          <el-table-column label="文件" width="150" prop="file">-->
<!--            <template #default="scope">-->
<!--              <el-button link type="primary" size="small" @click="look(scope.row.file)"> 查看 </el-button>-->
<!--            </template>-->
<!--          </el-table-column>-->
          <el-table-column prop="alg" label="算法" width="150" />
          <el-table-column label="TP" width="150" prop="TP"/>
              <el-table-column label="TN" width="150" prop="TN"/>
                <el-table-column label="FP" width="150" prop="FP"/>
                  <el-table-column label="FN" width="150" prop="FN"/>
          <el-table-column label="正确率" width="150" prop="accuracy">
<!--            <template #default="scope">-->
<!--              <el-button link type="primary" size="small" @click="predict(scope.row.result)"> 查看 </el-button>-->
<!--            </template>-->
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
  data() {
    return {
      tableData: [],
      form: {},
      options_data: [{
        value: 'JDT.csv',
        label: 'JDT.csv',
        disabled: false,
      }, {
        value: 'Lucene.csv',
        label: 'Lucene.csv',
        disabled: false,
      },
        {
          value: 'PDE.csv',
          label: 'PDE.csv',
          disabled: false,
        },
        {
          value: '请选择',
          label: '请选择',
          disabled: false,
        }],
      // value_data: '请选择训练集',
      // value_csv: '请选择测试集',
      disabled_data: false,
      disabled_csv: false,
    }
  },
  methods: {
    getCurrentTime() {
      //获取当前时间并打印
      var _this = this;
      let yy = new Date().getFullYear();
      let mm = new Date().getMonth()+1;
      let dd = new Date().getDate();
      let hh = new Date().getHours();
      let mf = new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes();
      let ss = new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds();
      _this.gettime = yy+'/'+mm+'/'+dd+' '+hh+':'+mf+':'+ss;
      console.log(_this.gettime);
      return _this.gettime
    },
    predict(result) {
      window.location.href = result
    },
    example() {
      window.location.href = "http://localhost:9090/files/example/Lucene.csv"
    },
    look(file) {
      window.location.href = file
    },
    preview() {
      // this.form.uploadTime = new Date().getTime()

      console.log("post")
      console.log(this.form)
      request.post("/file/prediction", this.form).then(res => {
        console.log(res)
        // this.load()
        console.log("get2")

        // this.tableData = res.data
        let that = this
        let tempList = that.tableData
        tempList.push({
          uploadTime : that.getCurrentTime(),
          alg : res.alg,
          TP : res.TP,
          TN : res.TN,
          FP : res.FP,
          FN : res.FN,
          accuracy : res.accuracy
        })
        that.tableData = tempList
        console.log(res.data)
      })
    },
    filesUploadSuccess(res) {
      console.log(res.data)
      this.form.file = res.data
    },
    load() {
      console.log("get")
      request.get("/file/prediction").then(res => {
        console.log("get2")

        // this.tableData = res.data
        let that = this
        let tempList = that.tableData
        tempList.push({
          alg : res.data.alg,
          TP : res.data.TP,
          TN : res.data.TN,
          FP : res.data.FP,
          FN : res.data.FN,
          accuracy : res.data.accuracy
        })
        that.tableData = tempList
        console.log(res.data)
      })
    },
    labelClick(label) {
      console.log(label)
      for (let i = 0; i < 2; i++) {
        this.options_data[i].disabled = false;
        if (this.options_data[i].label == label) {
          this.options_data[i].disabled = true;
          console.log(this.options_data[i]);
        }
      }
    },
  },
  created() {
    this.load()
  },

  // select_data() {
  //   //this.disabled_data = true;

  // },
  // select_csv() {
  //   //this.disabled_csv = true;
  // },
}
</script>

<style scoped>

</style>