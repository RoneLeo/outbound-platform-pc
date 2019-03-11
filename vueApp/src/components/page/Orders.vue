<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 派单信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-button type="primary" icon="delete" class="handle-del mr10" @click="delAll">批量删除</el-button>
            <el-select v-model="select_cate" placeholder="任务状态" class="handle-select mr10">
                <el-option key="1" label="已接单" value="广东省"></el-option>
                <el-option key="2" label="等待接单" value="湖南省"></el-option>
            </el-select>
            <el-input v-model="select_word" placeholder="筛选关键词" class="handle-input mr10"></el-input>
            <el-button type="primary" icon="search" @click="search">搜索</el-button>
        </div>

        <div class="container">

            <el-table :data="tableData" class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column prop="rwxh" label="任务序号"></el-table-column>
                <el-table-column prop="rwyj" label="任务佣金"></el-table-column>
                <el-table-column prop="rwzt" label="任务状态"></el-table-column>
                <el-table-column prop="jdry" label="接单人员"></el-table-column>
                <el-table-column prop="jdsj" label="接单时间"></el-table-column>
                <!--<el-table-column prop="wfyy" label="外访原因"></el-table-column>-->
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="handleOrder(scope.$index, scope.row)">详情</el-button>
                        <el-button type="text" @click="handleCase(scope.$index, scope.row)">案件信息</el-button>
                        <el-button type="text" @click="handleDelete(scope.$index, scope.row)">撤销</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 弹出框 -->
        <el-dialog :title="modelTitle" :visible.sync="modelVisible" width="50%">
            <el-form class="clearfix" ref="form" :model="form" label-width="100px">
                <el-form-item label="案件序号">
                    <el-input v-model="form.ajxh" readonly></el-input>
                </el-form-item>
                <el-form-item label="案件状态">
                    <el-input v-model="form.ajzt" readonly></el-input>
                </el-form-item>
                <el-form-item label="案件等级">
                    <el-input v-model="form.ajdj"></el-input>
                </el-form-item>
                <el-form-item label="委托方">
                    <el-input v-model="form.wtf"></el-input>
                </el-form-item>
                <el-form-item label="委托金额">
                    <el-input v-model="form.wtje"></el-input>
                </el-form-item>
                <el-form-item label="还款金额">
                    <el-input v-model="form.hkje"></el-input>
                </el-form-item>
                <el-form-item label="对象姓名">
                    <el-input v-model="form.dxxm"></el-input>
                </el-form-item>
                <el-form-item label="性别">
                    <el-input v-model="form.xb"></el-input>
                </el-form-item>
                <el-form-item label="年龄">
                    <el-input v-model="form.nl"></el-input>
                </el-form-item>
                <el-form-item label="催收地区">
                    <el-input v-model="form.csdq"></el-input>
                </el-form-item>
                <el-form-item label="催收地址">
                    <el-input v-model="form.csdz"></el-input>
                </el-form-item>
                <el-form-item label="创建时间">
                    <el-input v-model="form.cjsj"></el-input>
                </el-form-item>
                <el-form-item label="派单次数">
                    <el-input v-model="form.wfcs"></el-input>
                </el-form-item>
                <el-form-item class="el-form-1" label="备注">
                    <el-input v-model="form.bz" type="textarea"></el-input>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="modelVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 派单信息弹出框 -->
        <el-dialog :title="orderModelTitle" :visible.sync="orderModelVisible" width="50%">
            <el-form class="clearfix" ref="orderForm" :model="orderForm" label-width="100px">
                <el-form-item label="案件序号">
                    <el-input v-model="orderForm.ajxh" readonly></el-input>
                </el-form-item>
                <el-form-item label="案件状态">
                    <el-input v-model="orderForm.ajzt" readonly></el-input>
                </el-form-item>
                <el-form-item label="任务序号">
                    <el-input v-model="orderForm.rwxh"></el-input>
                </el-form-item>
                <el-form-item label="任务佣金">
                    <el-input v-model="orderForm.rwyj"></el-input>
                </el-form-item>
                <el-form-item label="任务状态">
                    <el-input v-model="orderForm.rwzt"></el-input>
                </el-form-item>
                <el-form-item label="接单人员">
                    <el-input v-model="orderForm.jdry"></el-input>
                </el-form-item>
                <el-form-item label="接单时间">
                    <el-input v-model="orderForm.jdsj"></el-input>
                </el-form-item>
                <el-form-item class="el-form-1" label="备注">
                    <el-input v-model="orderForm.bz" type="textarea"></el-input>
                </el-form-item>
                <el-form-item class="el-form-1" label="任务要求">
                    <el-input v-model="orderForm.rwyq" type="textarea"></el-input>
                </el-form-item>
                <el-form-item class="el-form-1" label="处理情况">
                    <el-input v-model="orderForm.clqk" type="textarea"></el-input>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="orderModelVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: 'case',
        data() {
            return {
                isCaseNo: false,
                caseNo: '',
                modelTitle: '案件信息',
                orderModelTitle: '派单信息',
                url: './static/jsonData/order.json',
                tableData: [],
                cur_page: 1,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                is_search: false,
                modelVisible: false,
                orderModelVisible: false,
                delVisible: false,
                form: {},
                orderForm:{},
                idx: -1,
                dict: this.$dict
            }
        },
        created() {
            this.getData();
            this.getParams();
        },
        computed: {

        },
        methods: {
            getParams(){
                let params = this.$route.params;
                let ajxh = params.ajxh
                this.caseNo = ajxh;
                if(ajxh){
                    this.isCaseNo = true;
                }else{
                    this.isCaseNo = false;
                }
            },
//            formatterGS(row) {
//                return this.$common.dictParse(row.gsid, this.dict.company);
//            },
            closeClear() {
                //this.$refs.form.resetFields()
            },
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.$axios.get(this.url).then((res) => {
                    //if(res.resCode == 200){
                        this.tableData = res;
                    //}
                });
            },
            search() {
                this.is_search = true;
            },
            add(){
                this.form = {
                };
                this.modelVisible = true;
            },
            formatter(row, column) {
                return row.address;
            },
            filterTag(value, row) {
                return row.tag === value;
            },
            handleCase(index, row) {
                this.form = Object.assign({}, row);
                this.modelVisible = true;
            },
            handleOrder(index, row) {
                this.orderForm = Object.assign({}, row);
                this.orderModelVisible = true;
            },
            resetMM(index, row) {
                this.$axios.post('/user/resetMm' ,this.$qs.stringify({id: row.uuid})).then((res) => {
                    this.$message.success('已将密码重置为123456，请告知用户尽快修改密码！');
//                    this.modelVisible = false;
                    this.getData();
                });
            },
            handleDelete(index, row) {
                this.$confirm('此操作将撤销任务, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/user/delete', this.$qs.stringify({uuid: row.uuid})).then((res) => {
                        this.getData();
                        this.$message.success('已删除！');
                    });
                }).catch(() => {
                });
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
                let url = '/user/add';
                if(this.form.uuid) {
                    url = '/user/update';
                }
                this.$axios.post(url ,this.$qs.stringify(Object.assign({}, this.form))).then((res) => {
                    this.$message.success(res.resMsg);
                    this.modelVisible = false;
                    this.getData();
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

<style scoped>
    .el-form-item{
        width:50%;
        float: left;
    }
    .el-form-1{
        width:100%;
    }
    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .handle-select {
        width: 120px;
    }
</style>
