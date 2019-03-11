<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 招聘信息管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="handle-box">

        </div>
        <div class="container">
            <el-table :data="tableData" class="table" ref="multipleTable">
                <el-table-column prop="zpgw" label="招聘岗位"></el-table-column>
                <el-table-column prop="zpbm" label="招聘部门"></el-table-column>
                <el-table-column prop="xzdy" label="薪资待遇"></el-table-column>
                <el-table-column prop="zpqx" label="招聘期限"></el-table-column>
                <!--<el-table-column prop="xxsm" label="详细说明"></el-table-column>-->
                <el-table-column prop="cjsj" label="创建时间" width="160"></el-table-column>
                <el-table-column label="操作"  align="center" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="padding: 20px 8px">
                <el-button type="primary" icon="el-icon-plus" @click="add">新增招聘信息</el-button>
                <el-pagination
                        style="float: right;"
                        @current-change="handleCurrentChange"
                        :current-page="page"
                        :page-size="size"
                        layout="prev, pager, next"
                        :total="totalElements">
                </el-pagination>
            </div>
        </div>

        <!-- 弹出框 -->
        <el-dialog :title="modelTitle" :visible.sync="modelVisible" width="40%"
                   :close-on-click-modal="false" @closed="closeClear">
            <el-form ref="form" :model="form" label-width="100px">
                <el-form-item label="招聘岗位"
                              prop="zpgw"
                              :rules="[{ required: true, message: '招聘岗位不能为空', trigger: 'blur' }]"
                              style="width: 92%">
                    <el-input v-model="form.zpgw"></el-input>
                </el-form-item>
                <el-form-item label="招聘部门"
                              prop="zpbm"
                              :rules="[{ required: true, message: '招聘部门不能为空', trigger: 'blur' }]"
                              style="width: 92%">
                    <el-input v-model="form.zpbm"></el-input>
                </el-form-item>
                <el-form-item label="薪资待遇"
                              prop="xzdy"
                              :rules="[{ required: true, message: '薪资待遇不能为空', trigger: 'blur' }]"
                              style="width: 92%">
                    <el-input v-model="form.xzdy"></el-input>
                </el-form-item>
                <el-form-item label="招聘期限"
                              prop="zpqx"
                              :rules="[{ required: true, message: '文招聘期限不能为空', trigger: 'blur' }]"
                              style="width: 92%">
                    <el-input v-model="form.zpqx"></el-input>
                </el-form-item>
                <el-form-item label="详细说明"
                              prop="xxsm"
                              :rules="[{ required: true, message: '详细说明不能为空', trigger: 'blur' }]"
                              style="width: 92%">
                    <el-input v-model="form.xxsm" type="textarea" :autosize="{ minRows: 2, maxRows: 10}"></el-input>
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
                modelTitle: '招聘信息详情',
                url: './static/vuetable.json',
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
                size: 10,
                totalElements: 0
            }
        },
        created() {
            this.getData();
        },
        computed: {

        },
        methods: {
            closeClear() {
                this.$refs.form.resetFields()
            },
            // 分页导航
            handleCurrentChange(val) {
                this.page = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.$axios.post('/recruitment/findAllByGsidByPage', this.$qs.stringify({page: this.page, size: this.size})).then((res) => {
                        this.tableData = res.data.content;
                        this.totalElements = res.data.totalElements;
                });
            },

            add(){
                this.form = {};
                this.modelVisible = true;
            },


            handleEdit(index, row) {
                this.form = Object.assign({}, row);
                this.modelVisible = true;
            },
            handleDelete(index, row) {
                this.$confirm('此操作将永久删除此招聘信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/recruitment/delete', this.$qs.stringify({id: row.id})).then(res => {
                        this.getData();
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                    })
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
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        let url = '/recruitment/add';
                        if(this.form.id) {
                            url = '/recruitment/update'
                        }
                        this.$axios.post(url, this.$qs.stringify(Object.assign({},this.form))).then(res => {
                            this.modelVisible = false;
                            this.getData();
                        })
                    }
                })
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
