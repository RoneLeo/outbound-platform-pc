<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 网上订购列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" style="background-color: transparent; border: 0;padding: 0px 20px">
            <el-row class="row-bg" :gutter="20" style="min-height: 500px">
                <el-col :span="8" v-for="item in tableData" :key="item.id+item.gsid+Math.random(100)">
                    <div class="grid-content" style="background-color: #fff;border: 1px solid #ccc;border-radius: 5px;padding: 20px 0;display: flex;justify-content: center;align-items: center;margin: 10px 0;height: 100%;">
                        <div style="width: 65%;font-size: 16px">
                            <el-form label-width="80px" :model="item">
                                <el-form-item label="产品名称">
                                    <span class="value">{{item.cpmc}}</span>
                                </el-form-item>
                                <el-form-item label="订购数量">
                                    <span class="value">{{item.dgsl}}</span>
                                </el-form-item>
                                <el-form-item label="公司名称">
                                    <span class="value">{{item.gsmc}}</span>
                                </el-form-item>
                                <el-form-item label="客户姓名">
                                    <span class="value">{{item.ndxm}}</span>
                                </el-form-item>
                                <el-form-item label="联系电话">
                                    <span class="value">{{item.lxdh}}</span>
                                </el-form-item>
                                <el-form-item label="电子信箱">
                                    <span class="value">{{item.dzyx}}</span>
                                </el-form-item>
                                <el-form-item label="咨询内容">
                                    <span class="value">{{item.zxnr}}</span>
                                </el-form-item>
                            </el-form>
                            <div class="item">
                                <span style="float: right;color: #0095FF">{{item.cjsj}}</span>
                            </div>

                        </div>

                    </div>
                </el-col>
                <!--<el-col :span="6"><div class="grid-content bg-purple-light"></div></el-col>-->
                <!--<el-col :span="6"><div class="grid-content bg-purple"></div></el-col>-->
            </el-row>
            <div style="background-color: #fff;padding: 10px;height: 52px">
                <el-pagination
                        style="float: right;display: inline-block;margin: 10px;"
                        @current-change="handleCurrentChange"
                        :current-page="page"
                        :page-size="size"
                        layout="prev, pager, next"
                        :total="totalElements">
                </el-pagination>
            </div>
            <!--<ul style="">-->
                <!--<li v-for="item in tableData" style="background-color: #fff;border: 1px solid #ccc;border-radius: 5px;padding: 10px;width: 30%;display: inline-block;float: left;margin: 10px">-->
                    <!--{{item.cpmc}}-->
                <!--</li>-->
            <!--</ul>-->

        </div>




    </div>
</template>

<script>
    export default {
        name: 'basetable',
        data() {
            return {
                tableData: [],
                cur_page: 1,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                is_search: false,
                modelVisible: false,
                delVisible: false,
                form: {},
                idx: -1,
                dict: this.$dict,
                page: 1,
                size: 3,
                totalElements: 0
            }
        },
        created() {
            this.getData();
        },
        computed: {

        },
        methods: {
            handleCurrentChange(val) {
                this.page = val;
                this.getData();
            },

            // 获取 easy-mock 的模拟数据
            getData() {
                this.$axios.post('/netService/findAllByGsidByPage', this.$qs.stringify({page: this.page, size: this.size})).then((res) => {
                        this.tableData = res.data.content;
                        this.totalElements = res.data.totalElements;
                });
            },
            search() {
                this.is_search = true;
            },
            add(){
                this.form = {};
                this.modelVisible = true;
            },
            formatter(row, column) {
                return row.address;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleEdit(index, row) {
                this.idx = index;
                const item = this.tableData[index];
                this.form = {
                    name: item.name,
                    date: item.date,
                    address: item.address
                }
                this.modelVisible = true;
            },
            handleDelete(index, row) {
                this.idx = index;
                this.delVisible = true;
            },
            delAll() {
                const length = this.multipleSelection.length;
                let str = '';
                this.del_list = this.del_list.concat(this.multipleSelection);
                for (let i = 0; i < length; i++) {
                    str += this.multipleSelection[i].name + ' ';
                }
                this.$message.error('删除了' + str);
                this.multipleSelection = [];
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            // 保存编辑
            saveEdit() {
                console.log(this.form)
                    this.$set(this.tableData, this.idx, this.form);
                    this.$message.success(`修改第 ${this.idx+1} 行成功`);

                this.modelVisible = false;
                this.$axios.post('/user/add',this.form).then((res) => {
                    console.log(111,res);
                });
            },
            // 确定删除
            deleteRow(){
                this.tableData.splice(this.idx, 1);
                this.$message.success('删除成功');
                this.delVisible = false;
            }
        }
    }

</script>
<style>
    .grid-content .el-form-item__label,.grid-content .el-form-item__content {
        font-size: 16px;
    }
    .grid-content .el-form-item__label {
        color: #909399;
    }
    .grid-content .el-form-item--mini.el-form-item, .grid-content .el-form-item--small.el-form-item {
        margin-bottom: 5px;
    }
</style>

<style scoped>
    .item {
        margin: 12px 0;
    }
    .mc {
        color: #909399;padding-right: 8px;
        letter-spacing: 1px;
    }
    .value {
        color: #606266
    }
    .handle-box {
        margin-bottom: 20px;
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .del-dialog-cnt{
        font-size: 16px;
        text-align: center
    }
    .table{
        width: 100%;
        font-size: 14px;
    }
    .red{
        color: #ff0000;
    }
</style>
