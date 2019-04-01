<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 委托案件</el-breadcrumb-item>
                <el-breadcrumb-item>派单信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>

        <div class="container">
            <el-row class="order-area">
                <el-col :span="8">
                    <div class="case-info">
                        <h4>案件信息</h4>
                        <img src="/static/img/demo/case-info.png" alt="">
                        <div class="case-btn text-center">
                            <!--<el-button>取 消</el-button>-->
                            <el-button type="primary" @click="goBack()">返回案件</el-button>
                        </div>
                    </div>
                </el-col>
                <el-col :span="16">
                    <div class="order-info">
                        <h4>派单信息</h4>
                        <img src="/static/img/demo/order-img.png" alt="">
                    </div>
                </el-col>
            </el-row>
        </div>

        <!-- 弹出框 -->
        <el-dialog :title="modelTitle" :visible.sync="modelVisible" width="50%"
                   :close-on-click-modal="false" @closed="closeClear">
            <el-form class="clearfix" ref="form" :model="form" label-width="100px">
                <el-form-item label="案件序号">
                    <el-input v-model="form.ajxh"></el-input>
                </el-form-item>
                <el-form-item label="案件状态">
                    <el-input v-model="form.ajzt"></el-input>
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
    </div>
</template>

<script>
    export default {
        name: 'case',
        data() {
            return {
                isCaseNo: false,
                caseNo: '',
                modelTitle: '案件详情',
                url: './static/jsonData/case.json',
                tableData: [],
                cur_page: 1,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                is_search: false,
                modelVisible: false,
                delVisible: false,
                form: {

                },
                idx: -1,
                dict: this.$dict,

                ajid: 1,
            }
        },
        created() {
            this.getData();
            this.getParams();
        },
        computed: {

        },
        methods: {
            goBack(){
                this.$router.go(-1)
            },
            getParams(){
                let params = this.$route.params;
                console.log(params)
                this.ajid = params.id
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
                this.$refs.form.resetFields()
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
            handleEdit(index, row) {
                this.form = Object.assign({}, row);
                this.modelVisible = true;
            },
            handleTask(index, row) {
                this.$router.push({ path: '/flowUp', query: row});
            },
            resetMM(index, row) {
                this.$axios.post('/user/resetMm' ,this.$qs.stringify({id: row.uuid})).then((res) => {
                    this.$message.success('已将密码重置为123456，请告知用户尽快修改密码！');
//                    this.modelVisible = false;
                    this.getData();
                });
            },
            handleDelete(index, row) {
                this.$confirm('此操作将注销本案件, 是否继续?', '提示', {
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

<style scoped lang="scss">
    .order-area{
        img{
            width: 95%;
        }
        .case-btn{
            margin-top:25px;
        }
    }
    h4{
        text-align: center;
        color:#555;
    }
    .case-info{
        min-height:600px;
        border-right:1px solid #ccc;

    }
    .el-form-item{
        width:50%;
        float: left;
    }
    .el-form-1{
        width:100%;
    }
</style>
