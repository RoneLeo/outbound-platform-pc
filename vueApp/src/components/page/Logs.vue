<template>
    <div>
        <div class="container">
            <el-form :inline="true" :model="searchForm" class="demo-form-inline" size="mini" label-width="90px" >


                <el-form-item label="操作时间段">
                    <el-date-picker
                            v-model="selectedDates"
                            type="daterange"
                            align="right"
                            unlink-panels
                            range-separator="至"
                            start-placeholder="开始时间"
                            end-placeholder="结束时间"
                            value-format="yyyy-MM-dd"
                            :picker-options="pickerOptions2">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="操作用户">
                    <el-input v-model="searchForm.czr" placeholder="输入用户名称" clearable></el-input>
                </el-form-item>
                <el-form-item label="操作内容">
                    <el-input v-model="searchForm.czsj" placeholder="输入操作内容" clearable></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button @click="clearCondition">清空条件</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="getDataByCondition">查询</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="container">
            <el-table :data="tableData" class="table" ref="multipleTable">
                <el-table-column prop="czr" label="操作用户"></el-table-column>
                <el-table-column prop="cznr" label="操作内容"></el-table-column>
                <el-table-column prop="czsj" label="操作时间"></el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                        :current-page.sync="currentPage"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        background
                        :page-sizes="[15, 20, 50]"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                </el-pagination>
            </div>
        </div>

    </div>
</template>

<script>
    import util from '../common/util';
    import baseCaseJson from '../common/baseCaseJson'
    export default {
        name: 'case',
        data() {
            return {
                pickerOptions2: {
                    shortcuts: [{
                        text: '最近一周',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近一个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近三个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        }
                    }]
                },

                selectedDates:[],
                tableData: [],
                pageSize: 15,
                currentPage: 1,
                total: 0,
                searchForm: {},

            }
        },
        created() {
            this.getDataByCondition();
        },

        methods: {
            getDataByCondition() {
                this.currentPage = 1;
                let param = {
                    page: this.currentPage,
                    pagesize: this.pageSize,
                    czr: this.searchForm.czr,
                    czsj: this.searchForm.czsj, //操作事件(内容)
                    kssj: this.selectedDates[0],
                    jssj: this.selectedDates[1],

                };


                this.$axios.post('log/findAll',param).then(res => {
                    this.tableData = res.data;
                    this.total = res.counts;
                })
            },
            clearCondition() {
                this.selectedDates = [];
                this.searchInput = {};
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getDataByCondition();
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getDataByCondition()
            }
        }
    }

</script>