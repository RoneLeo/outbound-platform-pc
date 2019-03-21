<template>
    <div v-loading="loading" element-loading-text="数据加载中...">
        <div class="container">
            <el-tabs v-model="dictId" @tab-click="tabClick">
                <el-tab-pane v-for="(item,index) in tabArr"  :label="item.zdmc" :name="item.id+''" :key="index"></el-tab-pane>
                <div style="margin-bottom: 10px;">
                    <el-button size="mini" type="success" @click="handleAdd">添加字典词条</el-button>
                </div>
                <!---->
                <el-table v-loading="tableLoading" border :data="tableData" class="table" ref="multipleTable" @selection-change="handleSelectionChange">
                    <el-table-column prop="ctmc" label="字典词条名称"></el-table-column>
                    <el-table-column prop="ctdm" label="字典词条代码"></el-table-column>
                    <el-table-column prop="zxbz" label="是否注销">
                        <template slot-scope="scope">
                            {{scope.row.zxbz == 1 ? '是' : '否'}}
                        </template>
                    </el-table-column>
                    <el-table-column label="操作">
                        <template slot-scope="scope">
                            <el-button type="text"  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                            <el-button type="text"  @click="handleDelete(scope.$index, scope.row)">
                                    {{scope.row.zxbz == 1 ? '激活' : '注销'}}
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-tabs>

        </div>

        <!-- 弹出框 -->
        <el-dialog :title="modelTitle" :visible.sync="modelVisible" width="35%"
                   :close-on-click-modal="false" @closed="closeClear">
            <el-form ref="form" :model="dictItemForm" label-width="100px">
                <el-form-item label="词条代码" v-if="dictItemForm.id">
                    <el-input v-model="dictItemForm.ctdm" readonly></el-input>
                </el-form-item>
                <el-form-item label="词条名称"
                              prop="ctmc"
                              :rules="[{ required: true, message: '词条名称不能为空', trigger: 'blur' }]">
                    <el-input v-model="dictItemForm.ctmc"></el-input>
                </el-form-item>
                <el-form-item label="是否启用" v-if="dictItemForm.id">
                    <el-select v-model="dictItemForm.zxbz">
                        <el-option label="启用" value="0"></el-option>
                        <el-option label="注销" value="1"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="modelVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveData">确 定</el-button>
            </span>
        </el-dialog>

        <!-- 删除提示框 -->
        <el-dialog title="提示" :visible.sync="delVisible" width="300px" center>
            <div class="del-dialog-cnt">删除不可恢复，是否确定删除？</div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="delVisible = false">取 消</el-button>
                <el-button type="primary" >确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: 'basetable',
        data() {
            return {
                loading: false,
                dictId: '',
                dictName: '',
                dictItemForm: {},
                tabArr:[],
                tableLoading: false,
                tableData: [],
                modelVisible: false,
                modelTitle: '',
                delVisible: false,
                cancelBtnTxt: '注销'


            }
        },
        created() {
            this.getDictData();
        },
        methods: {
            tabClick(){
                this.getDictItem();
            },
            //获取字典
            getDictData(){
                this.loading = true;
                this.$axios.post('dict/findDicts').then((res) => {
                    this.loading = false;
                    let tabArr = res.data;
                    this.dictName = tabArr[0].zdmc ;
                    this.dictId =  String(tabArr[0].id);
                    this.tabArr = tabArr;
                    this.getDictItem();
                    if(tabArr.length == 0){
                        this.$msgbox({
                            type: 'warning',
                            title: '温馨提示',
                            message: '暂无字典数据,请到字典管理中添加数据字典',
                            showCancelButton: false,
                            confirmButtonText: '确定',
                            closeOnClickModal: false,
                            cancelButtonText: '取消',
                        }).then(() => {
                            $.router.push('/dict');
                        }).catch(() => {
                            $.router.push('/dict');
                        });
                    }
                });
            },
            //根据字典ID获取字典项
            getDictItem(){
                this.tableLoading = true;
                this.$axios.post('dict/findDictList',{zdid: this.dictId}).then((res) => {
                    this.tableData = res.data;
                    this.tableLoading = false;
                });
            },
            handleAdd(){
                this.dictItemForm = {};
                this.modelTitle = this.dictName + ' - 新增词条';
                this.modelVisible = true;
            },
            handleEdit(index, row) {
                this.dictItemForm = Object.assign({}, row);
                this.modelTitle = this.dictName + ' - 编辑词条';
                this.modelVisible = true;
            },
            // 保存编辑
            saveData() {
                let url = 'dict/addDictList';
                if(this.dictItemForm.id){
                    url = 'dict/updateDictList';
                }
                this.dictItemForm.zdid = this.dictId;
                this.$axios.post(url ,this.dictItemForm).then((res) => {
                    this.modelVisible = false;
                    this.getDictItem();
                });
            },
            handleDelete(index, row) {
                let url = 'dict/cancellationDicListById';
                let txt = '注销';
                if(row.zxbz == 1){
                    url = 'dict/activationDicListById';
                    txt = '激活';
                }
                this.$confirm('此操作将'+txt+'字典项, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post(url, {id: row.id}).then((res) => {
                        this.getDictData();
                        this.$message.success('已'+txt+'！');
                    });
                }).catch(() => {
                });
            },




            closeClear() {
                this.$refs.form.resetFields()
            },
            // 分页导航
            handleCurrentChange(val) {
                this.getData();
            },
            search() {
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
            resetMM(index, row) {
                this.$axios.post('/user/resetMm' ,this.$qs.stringify({id: row.uuid})).then((res) => {
                    this.$message.success('已将密码重置为123456，请告知用户尽快修改密码！');
//                    this.modelVisible = false;
                    this.getData();
                });
            },

            handleSelectionChange(val) {
            },

        }
    }

</script>

