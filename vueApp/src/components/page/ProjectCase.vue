<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 工程案例</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" v-loading="loading">
            <el-table :data="tableData" class="table" ref="multipleTable">
                <el-table-column prop="gcmc" label="工程名称" width="300" :show-overflow-tooltip="true"></el-table-column>
                <el-table-column prop="aljs" label="案例介绍" :show-overflow-tooltip="true"></el-table-column>
                <!--<el-table-column label="展示图片">-->
                    <!--<template slot-scope="scope">-->
                        <!--<a target="_blank" :href='`http://182.151.22.247:8081${scope.row.tp}`'>{{scope.row.tp}}</a>-->
                    <!--</template>-->
                <!--</el-table-column>-->
                <el-table-column prop="cjsj" label="创建时间" width="160"></el-table-column>
                <el-table-column label="操作" align="center" width="160">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-lx-edit"
                                   @click="editFile(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button type="text" icon="el-icon-delete" class="red" @click="delFile(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="padding: 28px 8px">
                <el-button type="primary" @click="add" style="float: left">上传工程案例</el-button>
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
        <el-dialog :title="modelTitle" :visible.sync="modelVisible" width="60%"
                   :close-on-click-modal="false" @closed="closeClear">
            <el-form ref="addForm" :model="addForm" label-width="100px">
                <el-form-item label="工程名称"
                              prop="gcmc"
                              :rules="[{ required: true, message: '工程名称不能为空', trigger: 'blur' }]">
                    <el-input v-model="addForm.gcmc"></el-input>
                </el-form-item>
                <el-form-item label="案例介绍"
                              prop="aljs"
                              :rules="[{ required: true, message: '案例介绍不能为空', trigger: 'blur' }]">
                    <el-input v-model="addForm.aljs" type="textarea" :autosize="{ minRows: 5, maxRows: 18}"></el-input>
                </el-form-item>
                <el-form-item label="展示图片"
                              v-if="!addForm.id || !addForm.tp">
                    <input id="fileInput" ref="pathClear" type="file" @change="getFile($event)" accept="image/*"/>
                </el-form-item>
                <el-form-item label="展示图片"
                              v-if="addForm.tp">
                    <div style="position: relative;display: inline-block;width: auto;height: auto;">
                        <img :src="`http://47.96.85.104:80${addForm.tp}`" alt="" class="image">
                        <i v-if="addForm.tp" class="el-icon-error image-icon" @click="deleteTP"></i>
                    </div>

                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="modelVisible = false">取 消</el-button>
                <el-button type="primary" :loading="addLoading" @click="saveEdit(addForm)">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>

    export default {
        name: 'basetable',
        data() {
            return {
                modelTitle: '工程案例详情',
                tableData: [],
                modelVisible: false,
                addForm: {},
                file: {},
                size: 10,
                page: 1,
                totalElements: 0,
                loading: true,
                addLoading: false,
                equipmentType: []
            }
        },
        created() {
            this.getData();
        },
        computed: {},
        methods: {
            deleteTP() {
                this.addForm.tp = '';
                this.file = {};
            },
            closeClear() {
                this.$refs.addForm.resetFields()
                if(this.$refs.pathClear) {
                    this.$refs.pathClear.value =''
                }
            },
            editFile(index, row) {
                this.addForm = Object.assign({}, row);
                this.modelVisible = true;
            },
            delFile(index, row) {
                this.$confirm('此操作将永久删除该案例, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/projectCase/delete', this.$qs.stringify({id: row.id})).then((res) => {
                        this.$message.success('已删除！');
                        this.getData();
                    });
                }).catch(() => {
                });
            },
            getFile(event) {
                this.file = event.target.files[0];
                this.addForm.file = this.file;
            },
            // 分页导航
            handleCurrentChange(val) {
                this.page = val;
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.loading = true;
                this.$axios.post('/projectCase/findAllByGsidByPage', this.$qs.stringify({ page: this.page, size: this.size})).then((res) => {
                    if (res.resCode == 200) {
                        this.loading = false;
                        this.tableData = res.data.content;
                        this.totalElements = res.data.totalElements
                    }
                });
            },

            add(){
                this.addForm = {};
                this.modelVisible = true;
                this.file = {};
            },

            // 保存编辑
            saveEdit(addForm) {
                this.$refs.addForm.validate((valid) => {
                    if (valid) {
                        this.addLoading = true;
                        let url = '/projectCase/add';
                        if(this.addForm.id) {
                            url = '/projectCase/update'
                        }
                        let formData = new FormData();
                        for(let key in this.addForm) {
                            formData.append(key, this.addForm[key]);
                        }
                        let config = {
                            headers: {
                                'Content-Type': 'multipart/form-data'
                            }
                        };
                        this.$axios.post(url, formData, config).then(res => {
                            this.addLoading = false;
                            this.modelVisible = false;
                            if(this.activeName == 'first') {
                                this.getData(1, this.page, this.size);
                            }else {
                                this.getData(2, this.page1, this.size1);
                            }
                        })
                    }
                })
            },
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

    .del-dialog-cnt {
        font-size: 16px;
        text-align: center
    }

    .table {
        width: 100%;
        font-size: 14px;
    }

    .red {
        color: #ff0000;
    }
</style>
