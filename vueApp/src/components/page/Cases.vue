<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 委托案件</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="handle-box container">
            <el-button size="mini" type="success" @click="importCase">导入案件</el-button>
            <el-button size="mini" type="primary" @click="add">添加案件</el-button>
        </div>
        <div class="container">
            <el-table :data="tableData" class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column prop="ajly" label="案件来源"></el-table-column>
                <el-table-column prop="fj" label="附件"></el-table-column>
                <el-table-column prop="dxxm" label="对象姓名"></el-table-column>
                <el-table-column prop="xb" label="性别"></el-table-column>
                <el-table-column prop="nl" label="年龄"></el-table-column>
                <el-table-column prop="ajxh" label="个案序号"></el-table-column>
                <el-table-column prop="ajzt" label="催收状态"></el-table-column>
                <el-table-column prop="wtje" label="委案金额"></el-table-column>
                <el-table-column prop="hkje" label="已还款"></el-table-column>
                <el-table-column prop="csy" label="催收员"></el-table-column>
                <el-table-column prop="csdq" label="催收地区"></el-table-column>
                <el-table-column prop="wfcs" label="外访期次"></el-table-column>
                <el-table-column prop="wfyy" label="外访原因"></el-table-column>
                <el-table-column prop="yq" label="要求"></el-table-column>
                <el-table-column prop="sqsj" label="申请时间"></el-table-column>
                <el-table-column prop="wtf" label="委托方"></el-table-column>

                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="handleEdit(scope.$index, scope.row)">详情</el-button>
                        <el-button type="text" @click="handleOrder(scope.$index, scope.row)">派单</el-button>
                        <el-button type="text" @click="handleDelete(scope.$index, scope.row)">注销</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <el-dialog title="导入案件" :visible.sync="importModelVisible" width="50%"
                   :close-on-click-modal="false" @closed="closeClear">
            <el-form class="clearfix" ref="importForm" :model="importForm" label-width="100px">
                <el-form-item label="批次id">
                    <el-input v-model="importForm.pcid" placeholder="请选择"></el-input>
                </el-form-item>
                <el-form-item label="案件区域">
                    <el-select v-model="importForm.ajqy" placeholder="请选择">
                        <el-option
                                v-for="item in qys"
                                :key="item.ctdm"
                                :label="item.ctmc"
                                :value="item.ctdm" >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="案件类型">
                    <el-select v-model="importForm.ajlx" placeholder="请选择">
                        <el-option
                                v-for="item in ajlxs"
                                :key="item.ctdm"
                                :label="item.ctmc"
                                :value="item.ctdm" >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="文件">
                    <!--<input @change="fileImage" type="file" accept="image/jpeg,image/x-png,image/gif" id="" value="" />-->
                    <input type="file" id="myFile" class="inputfile" @change="handlerUpload"></input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="importModelVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveImport">导 入</el-button>
            </span>
        </el-dialog>
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
                form: {},

                dict: this.$dict,
                importForm: {},
                file: {},
                importModelVisible: false,
                qys: [],
                ajlxs: [],
            }
        },
        created() {
            this.$axios.post('/dict/findDictListByZddm', {zddm: 'D_SYS_AJQYDM', zxbz: 0}).then((res) => {
                if(res.resCode == 200){
                    this.qys = res.data;
                }else if(res.resCode == 100) {
                    this.$router.push('/login');
                }
            });
            this.$axios.post('/dict/findDictListByZddm', {zddm: 'D_SYS_AJLXDM', zxbz: 0}).then((res) => {
                if(res.resCode == 200){
                    this.ajlxs = res.data;
                }else if(res.resCode == 100) {
                    this.$router.push('/login');
                }
            });
            this.getData();
        },
        computed: {

        },
        methods: {
            saveImport( ){
                let param = new FormData();
                param.append('pcid', this.importForm.pcid)
                param.append('ajqy', this.importForm.ajqy)
                param.append('ajlx', this.importForm.ajlx)
                param.append('file', this.file)
                let config = {
                    headers:{'Content-Type':'multipart/form-data'}
                }
                console.log(param.get("file"))
                this.$axios.post('/batch/importExcel', param, config ).then(res => {
                    console.log(res)
                })
            },
            handlerUpload(e) {
                this.file = e.target.files[0];
            },
            importCase() {
                this.importModelVisible = true;
                this.importForm = {}
            },
            closeClear() {
                this.$refs.importForm.resetFields()
                this.$refs.form.resetFields()
            },
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {

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
            handleOrder(index, row) {
                this.$router.push({name:'order', params: row});
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

<style scoped>
    .el-form-item{
        width:50%;
        float: left;
    }
    .el-form-1{
        width:100%;
    }
</style>
