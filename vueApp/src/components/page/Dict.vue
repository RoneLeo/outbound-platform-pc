<template>
    <div>
        <div class="container">
            <el-button size="mini" type="success" @click="handleAdd">添加字典</el-button>
        </div>
        <div class="container">
            <el-table v-loading="tableLoading" border :data="tableData" class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                <el-table-column prop="zdzwmc" label="字典名称"></el-table-column>
                <el-table-column prop="zdywmc" label="字典代码"></el-table-column>
                <el-table-column prop="zxbz" label="是否注销">
                    <template slot-scope="scope">
                        {{scope.row.zxbz == 1 ? '是' : '否'}}
                    </template>
                </el-table-column>
                <el-table-column prop="id" label="操作">
                    <template slot-scope="scope">
                        <el-button type="text"  @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button type="text"  @click="handleLogout(scope.row,'logout')">
                            {{scope.row.zxbz == 1 ? '激活' : '注销'}}
                        </el-button>
                        <el-button type="text" class="red"  @click="handleDelete(scope.row,'del')">删除</el-button>

                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 弹出框 -->
        <el-dialog :title="modelTitle" :visible.sync="modelVisible" width="35%"
                   :close-on-click-modal="false" @closed="closeClear">
            <el-form ref="form" :model="dictForm" label-width="100px">
                <el-form-item label="字典名称"
                              prop="zdzwmc"
                              :rules="[{ required: true, message: '字典名称不能为空', trigger: 'blur' }]">
                    <el-input v-model="dictForm.zdzwmc"></el-input>
                </el-form-item>
                <el-form-item label="字典代码"
                              prop="zdywmc"
                              :rules="[{ required: true, message: '字典代码不能为空', trigger: 'blur' }]">
                    <el-input v-model="dictForm.zdywmc"></el-input>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="modelVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveDict">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" @click="deleteRow">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: 'basetable',
        data() {
            return {
                tabActive: '',
                tabArr:[],
                tableLoading: false,
                tableData: [],
                modelVisible: false,
                modelTitle: '添加字典信息',
                dictForm: {},
                cur_page: 1,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                is_search: false,
                delVisible: false,
            }
        },
        created() {
            this.getData();
        },
        computed: {

        },
        methods: {
            tabClick(tab, event){
                console.log(tab.name);
            },
            getData(){
                this.tableLoading = true;
                this.$axios.post('dict/findDicts').then((res) => {
                    this.tableData = res.data;
                    this.tableLoading = false;
                });
            },

            handleAdd() {
                this.dictForm = {};
                this.modelVisible = true;
            },
            handleEdit(row) {
                this.dictForm = Object.assign({}, row);
                this.modelVisible = true;
            },
            saveDict(){
                let url = 'dict/addDict';
                if(this.dictForm.id){
                    url = 'dict/updateDict';
                }
                let loading = this.$loading();
                this.$axios.post(url,this.dictForm).then((res) => {
                    if(res.resCode == 200){
                        loading.close();
                        this.modelVisible = false;
                        this.getData();
                    }
                });

            },
            handleDelete(row) {
                this.$confirm('此操作将永久删除该字典, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('dict/deleteDictById', {id: row.id}).then((res) => {
                        this.getData();
                        this.$message.success(row.zdzwmc + ' 已删除！');
                    });
                }).catch();
            },
            handleLogout(row) {
                let txt = '注销';
                let url = 'dict/cancellationDictById';
                if(row.zxbz == 1){
                    txt = '激活';
                    url = 'dict/activationDictById';
                }
                this.$confirm('此操作将'+txt+'该字典, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post(url, {id: row.id}).then((res) => {
                        this.getData();
                        this.$message.success(row.zdzwmc + ' 已'+txt+'！');
                    });
                });
            },



            closeClear() {
                this.$refs.form.resetFields()
            },
            // 分页导航
            handleCurrentChange(val) {
                this.cur_page = val;
                this.getData();
            },
            search() {
                this.is_search = true;
            },
            add(){
                this.form = {
                    mm: '123456'
                };
                this.modelVisible = true;
            },
            formatter(row, column) {
                return row.address;
            },
            filterTag(value, row) {
                return row.tag === value;
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
                this.$message.success('删除成功');
                this.delVisible = false;
            }
        }
    }

</script>

