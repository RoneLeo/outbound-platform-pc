<template>
    <div>
        <div class="container">
            <el-tabs v-model="tabActive" @tab-click="tabClick">
                <el-tab-pane v-for="(item,index) in tabArr" :label="item.zdzwmc" :name="item.zdywmc" :key="index"></el-tab-pane>
                <div style="margin-bottom: 10px;">
                    <el-button size="mini" type="success" @click="addDict">添加字典项</el-button>
                </div>
                <!---->
                <el-table v-loading="tableLoading" border :data="tableData" class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                    <el-table-column prop="zdzwmc" label="字典项名称"></el-table-column>
                    <el-table-column prop="zdywmc" label="字典项代码"></el-table-column>
                    <!--<el-table-column prop="zt" label="是否注销"></el-table-column>-->
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button type="text"  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                            <el-button type="text" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-tabs>

        </div>

        <!-- 弹出框 -->
        <el-dialog :title="modelTitle" :visible.sync="modelVisible" width="35%"
                   :close-on-click-modal="false" @closed="closeClear">
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="字典名称"
                              prop="zdzwmc"
                              :rules="[{ required: true, message: '字典名称不能为空', trigger: 'blur' }]">
                    <el-input v-model="form.zdzwmc"></el-input>
                </el-form-item>
                <el-form-item label="字典代码"
                              prop="zdywmc"
                              :rules="[{ required: true, message: '字典代码不能为空', trigger: 'blur' }]">
                    <el-input v-model="form.zdywmc"></el-input>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="modelVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
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
                form: {},
                cur_page: 1,
                multipleSelection: [],
                select_cate: '',
                select_word: '',
                del_list: [],
                is_search: false,

                delVisible: false,

                idx: -1,
                dict: this.$dict,
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
                this.$axios.post('dict/findDictAll').then((res) => {
                    this.tableData = res.data;
                    let tabArr = res.data;
                    this.tabActive = tabArr[0].zdywmc;
                    this.tabArr = tabArr;

                    this.tableLoading = false;
                });
            },
            addDict(){
                this.modelVisible = true;
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
            handleEdit(index, row) {
                this.form = Object.assign({}, row);
                this.modelVisible = true;
            },
            resetMM(index, row) {
                this.$axios.post('/user/resetMm' ,this.$qs.stringify({id: row.uuid})).then((res) => {
                    this.$message.success('已将密码重置为123456，请告知用户尽快修改密码！');
//                    this.modelVisible = false;
                    this.getData();
                });
            },
            handleDelete(index, row) {
                this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
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

