<template>
    <el-container>
        <el-container>
            <el-main class="mainMain" v-loading='loding'>
                <!--面包屑导航区域-->
                <div class="gl">
                    <el-select class="data_data" @change="select_data" :disabled="disabled_data" v-model="value_data"
                        placeholder="请选择训练集">
                        <el-option v-for="item in options_data" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </div>
                <div class="gl">
                    <el-select class="csv_select" @change="select_csv" :disabled="disabled_csv" v-model="value_csv"
                        placeholder="请选择测试集">
                        <el-option v-for="item in options_csv" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </div>
                <div class="gl">
                    <el-select class="alg_select" @change="select_alg" :disabled="disabled_alg" v-model="value_alg"
                        placeholder="请选择算法">
                        <el-option v-for="item in options_alg" :key="item.value" :label="item.label"
                            :value="item.value">
                        </el-option>
                    </el-select>
                </div>
                <div class="gl">
                    <el-button round>重新选择</el-button>
                </div>
                <div class="gl">
                    <el-button round>开始预测</el-button>
                </div>
                <!-- <el-collapse v-model="activeNames" @change="handleChange">
                    <el-collapse-item title="data" name="2">
                        <el-table :data="preList" border stripe style="position:relative">
                            <el-table-column fixed type="index" label="index"></el-table-column>
                            <el-tableColumn label="ck_oo_numberOfPrivateMethods" prop="date"></el-tableColumn>
                            <el-tableColumn label="LDHH_lcom" prop="avg_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_fanIn" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="numberOfNonTrivialBugsFoundUntil:" prop="min_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_numberOfPublicAttributes" prop="prec"></el-tableColumn>
                            <el-tableColumn label="WCHU_numberOfAttributes" prop="pressure"></el-tableColumn>
                            <el-tableColumn label="CvsWEntropy" prop="wind_dir"></el-tableColumn>
                            <el-tableColumn label="LDHH_numberOfPublicMethods" prop="wind_speed"></el-tableColumn>
                            <el-tableColumn label="WCHU_fanIn" prop="wind_speed"></el-tableColumn>
                            <el-tableColumn label="LDHH_numberOfPrivateAttributes" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="CvsEntropy" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_numberOfPublicAttributes" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_numberOfPrivateMethods" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_numberOfMethods" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_numberOfPublicAttributes" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_noc" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="numberOfCriticalBugsFoundUntil:" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_wmc" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_numberOfPrivateMethods" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_numberOfPrivateAttributes" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="CvsLogEntropy" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_noc" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_numberOfAttributesInherited" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_wmc" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_fanOut" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_numberOfLinesOfCode" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_numberOfAttributesInherited" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_numberOfMethods" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_dit" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_fanIn" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_noc" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_dit" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_lcom" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_numberOfAttributesInherited" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_rfc" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_wmc" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_numberOfAttributes" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_numberOfLinesOfCode" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_fanOut" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_lcom" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_cbo" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_rfc" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_numberOfAttributes" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="numberOfHighPriorityBugsFoundUntil:" prop="max_temp">
                            </el-tableColumn>
                            <el-tableColumn label="ck_oo_numberOfPrivateAttributes" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="numberOfMajorBugsFoundUntil:" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_numberOfPublicMethods" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_dit" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_cbo" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="CvsLinEntropy" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_numberOfMethodsInherited" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="numberOfBugsFoundUntil:" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_fanOut" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_numberOfMethodsInherited" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_rfc" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_numberOfMethodsInherited" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="ck_oo_numberOfPublicMethods" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_cbo" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="WCHU_numberOfLinesOfCode" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="CvsExpEntropy" prop="max_temp"></el-tableColumn>
                            <el-tableColumn label="LDHH_numberOfMethods" prop="max_temp"></el-tableColumn>
                            <el-tableColumn fixed="right" label="class" prop="max_temp"></el-tableColumn>
                        </el-table>
                    </el-collapse-item>
                </el-collapse> -->
                <!-- <el-divider content-position="center" style="position: relative;"></el-divider> -->


            </el-main>
            <el-footer class="mainFoot">
                <div class="kaifa">小组开发出品</div>
            </el-footer>
        </el-container>
    </el-container>
</template>

<script>


export default {
    data() {
        return {
            loding: false,
            options_data: [{
                value: '选项1',
                label: 'csv1'
            }, {
                value: '选项2',
                label: 'csv2'
            }],
            value_data: '请选择训练集',
            options_csv: [{
                value: '选项1',
                label: 'csv1'
            }, {
                value: '选项2',
                label: 'csv2'
            }],
            value_csv: '请选择测试集',
            options_alg: [{
                value: '选项1',
                label: 'KNN'
            }, {
                value: '选项2',
                label: 'SVM'
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
            console.log(val);
        },
        select_data() {
            this.disabled_data = true;
        },
        select_csv() {
            this.disabled_csv = true;
        },
        select_alg() {
            this.disabled_alg = true;
        },

    }


}

</script>

<style scoped>
.head-bread {
    position: relative;
    color: #fff;
    font-size: 20px;
}
.gl {
    text-align: center;
    margin-bottom: 30px;
}
.gl2 {
    text-align: center;
}
.kaifa {
    margin: 0px auto 40px auto;
    text-align: center;
    font-size: 20px;
    line-height: 60px;
    color: #fff;
    font-family: "Helvetica Neue";
}
.mainMain {
    /* background: #87CEFA; */
    height: 800px;
    flex-grow: 1;
    /* background-image: url("../assets/bg.png"); */
    background-size: cover;
    background-repeat: no-repeat;
}

.mainFoot {
    background: #409EFF;
}
</style>