<template>
    <div  v-loading="loading" element-loading-text="数据加载中...">
        <div class="container">
            <el-button size="mini" type="success" @click="handleAdd">添加批次模板</el-button>
            <el-radio-group v-model="dictType" @change="changeType" size="mini">
                <el-radio-button label="">全部1</el-radio-button>
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


        <!-- 选择模板字段 -->
        <el-dialog id="dialog2" title="模板字段选择" :visible.sync="modelVisible2" width="60%"
                   :close-on-click-modal="false">
            <el-collapse v-model="activeNames" @change="handleChange">
                <el-collapse-item title="一致性 Consistency" name="1">
                    <el-checkbox-group v-model="checkboxGroup6" size="mini">
                        <el-checkbox label="备选项1" border></el-checkbox>
                        <el-checkbox label="备选项2" border></el-checkbox>
                        <el-checkbox label="备选项3" border></el-checkbox>
                        <el-checkbox label="备选项4" border></el-checkbox>
                    </el-checkbox-group>
                </el-collapse-item>
                <el-collapse-item title="反馈 Feedback" name="2">
                    <div>控制反馈：通过界面样式和交互动效让用户可以清晰的感知自己的操作；</div>
                    <div>页面反馈：操作后，通过页面元素的变化清晰地展现当前状态。</div>
                </el-collapse-item>
                <el-collapse-item title="效率 Efficiency" name="3">
                    <div>简化流程：设计简洁直观的操作流程；</div>
                    <div>清晰明确：语言表达清晰且表意明确，让用户快速理解进而作出决策；</div>
                    <div>帮助用户识别：界面简单直白，让用户快速识别而非回忆，减少用户记忆负担。</div>
                </el-collapse-item>
                <el-collapse-item title="可控 Controllability" name="4">
                    <div>用户决策：根据场景可给予用户操作建议或安全提示，但不能代替用户进行决策；</div>
                    <div>结果可控：用户可以自由的进行操作，包括撤销、回退和终止当前操作等。</div>
                </el-collapse-item>
            </el-collapse>
            <span slot="footer" class="dialog-footer">
                <el-button @click="modelVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveDict">确 定</el-button>
            </span>
        </el-dialog>

    </div>
</template>

<script>
    import {debounce} from '../common/debounce'
    import util from '../common/util'
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
                modelVisible2: false,
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
            this.getCaseBaseType();

            //this.getData();

        },
        computed: {

        },
        methods: {
            changeType(){
                //this.getData();
            },
            getCaseBaseType(){
                //this.loading = true;
                this.$axios.post('baseType/findAll').then((res) => {
                    //this.tableData = res.data;
                    //this.loading = false;
                    console.log('baseType:',res);
                });
            },
            getData(){
                this.loading = true;
                this.$axios.post('batch/findAllPcid',{page:1,pagesize:100}).then((res) => {
                    this.tableData = res.data;
                    this.loading = false;
                });
            },
            handleAdd() {
//                this.modelTitle = '添加字典信息';
//                this.dictForm = {};
                this.modelVisible2 = true;
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

<style scoped lang="scss">
    #dialog2{
        .el-checkbox{
            margin:0 !important;
        }
    }
</style>
