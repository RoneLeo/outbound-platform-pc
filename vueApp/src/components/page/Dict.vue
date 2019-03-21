<template>
    <div  v-loading="loading" element-loading-text="数据加载中...">
        <div class="container">
            <el-button size="mini" type="success" @click="handleAdd">添加字典</el-button>
            <el-radio-group v-model="dictType" @change="changeType" size="mini">
                <el-radio-button label="">全部</el-radio-button>
                <el-radio-button label="0">已激活</el-radio-button>
                <el-radio-button label="1">已注销</el-radio-button>
            </el-radio-group>
        </div>
        <div class="container">
            <el-table  border :data="tableData" class="table" ref="multipleTable" >
                <el-table-column prop="zdmc" label="字典名称"></el-table-column>
                <el-table-column prop="zddm" label="字典代码"></el-table-column>
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
                   :close-on-click-modal="false">
            <el-form ref="form" :model="dictForm" label-width="100px">
                <el-form-item label="字典名称"
                              prop="zdmc"
                              :rules="[{ required: true, message: '字典名称不能为空', trigger: 'blur' }]">
                    <el-input v-model="dictForm.zdmc"></el-input>
                </el-form-item>
                <el-form-item label="字典代码"
                              prop="zddm"
                              :rules="[{ required: true, message: '字典代码不能为空', trigger: 'blur' }]">
                    <el-input v-model="dictForm.zddm"></el-input>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="modelVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveDict">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    export default {
        name: 'basetable',
        data() {
            return {
                dictType: '',
                tabActive: '',
                tabArr:[],
                loading: false,
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
            changeType(){
                this.getData();
            },
            getData(){
                this.loading = true;
                this.$axios.post('dict/findDicts',{zxbz:this.dictType}).then((res) => {
                    this.tableData = res.data;
                    this.loading = false;
                });
            },
            handleAdd() {
                this.modelTitle = '添加字典信息';
                this.dictForm = {};
                this.modelVisible = true;
            },
            handleEdit(row) {
                this.modelTitle = '编辑字典信息';
                this.dictForm = Object.assign({}, row);
                this.modelVisible = true;
            },
            saveDict(){
                let url = 'dict/addDict';
                if(this.dictForm.id){
                    url = 'dict/updateDict';
                }
                this.loading = true;
                this.$axios.post(url,this.dictForm).then((res) => {
                    if(res.resCode == 200){
                        this.modelVisible = false;
                        this.loading = false;
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
                        this.$message.success(row.zdmc + ' 已删除！');
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
                        this.$message.success(row.zdmc + ' 已'+txt+'！');
                    });
                });
            }
        }
    }

</script>

