<template>
    <el-container>
        <el-main class="mainMain" v-loading='loding'>
            <div class="gl">
                <el-select class="data_data" @change="select_data" :disabled="disabled_data" v-model="value_data"
                    placeholder="请选择训练集">
                    <el-option v-for="item in options_data" :key="item.value" :label="item.label"
                        :value="item.value"
                        @click.native="labelClick(item.label)"
                        :disabled="item.disabled">
                    </el-option>
                </el-select>
            </div>
            <div class="gl">
                <el-select class="csv_select" @change="select_csv" :disabled="disabled_csv" v-model="value_csv"
                    placeholder="请选择测试集">
                    <el-option v-for="item in options_data" :key="item.value" :label="item.label"
                        :value="item.value"
                        :disabled="item.disabled">
                    </el-option>
                </el-select>
            </div>
            <div class="gl">
                <el-select class="alg_select" @change="select_alg" :disabled="disabled_alg" v-model="value_alg"
                    placeholder="请选择算法">
                    <el-option v-for="item in options_alg" :key="item.value" :label="item.label"
                        :value="item.value"
                        :disabled="item.disabled">
                    </el-option>
                </el-select>
            </div>
            <div class="gl">
                <el-button round>重新选择</el-button>
            </div>
            <div class="gl">
                <el-button round>开始预测</el-button>
            </div>
        </el-main>
    </el-container>
</template>

<script>
export default {
    data() {
        return {
            loding: false,
            options_data: [{
                value: '选项1',
                label: 'csv1',
                disabled: false,
            }, {
                value: '选项2',
                label: 'csv2',
                disabled: false,
            },{
              value: '选项3',
              label: 'csv3',
              disabled: false,
            }],
            value_data: '请选择训练集',
            // options_csv: [{
            //     value: '选项1',
            //     label: 'csv1',
            //     disabled: false,
            // }, {
            //     value: '选项2',
            //     label: 'csv2',
            //     disabled: false,
            // }],
            value_csv: '请选择测试集',
            options_alg: [{
                value: '选项1',
                label: 'KNN',
                disabled: false,
            }, {
                value: '选项2',
                label: 'SVM',
                disabled: false,
            }],
            value_alg: '请选择算法',
            preList: [],
            activeNames: ['0'],
            disabled_data: false,
            disabled_csv: false,
            disabled_alg: false,
        }
    },
    methods: {
        handleChange(val) {
            //console.log(val);
        },
        labelClick(label){
             console.log(label)
             for(let i=0; i<2; i++){
                if(this.options_data[i].label == label){
                    this.options_data[i].disabled = true;
                    console.log(this.options_data[i]);
                }
             }
        },
        select_data() {
            //this.disabled_data = true;
            
        },
        select_csv() {
            //this.disabled_csv = true;
        },
        select_alg() {
            this.disabled_alg = true;
        },
    }
}
</script>

<style scoped>
</style>