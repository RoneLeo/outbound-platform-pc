<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 文件管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" v-loading="loading">
            <el-tabs v-model="activeName" @tab-click="handleClick">
                <el-tab-pane label="资质证书" name="first">
                    <el-table :data="tableData" class="table" :show-header="false" ref="multipleTable">
                        <el-table-column prop="wjmc" label="文件名称"></el-table-column>
                        <el-table-column prop="cjsj" label="创建时间" width="200"></el-table-column>
                        <el-table-column label="操作" align="center" width="200">
                            <template slot-scope="scope">
                                <el-button type="text" icon="el-icon-lx-attention"
                                           @click="lookFile(scope.$index, scope.row)">点击查看
                                </el-button>
                                <el-button type="text" icon="el-icon-delete" class="red"
                                           @click="delFile(scope.$index, scope.row)">删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div style="padding: 28px 8px">
                        <el-button type="primary" @click="add" style="float: left">上传文件</el-button>
                        <el-pagination
                                style="float: right;"
                                @current-change="handleCurrentChange"
                                :current-page="page"
                                :page-size="size"
                                layout="prev, pager, next"
                                :total="totalElements">
                        </el-pagination>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="banner图片" name="second">
                    <el-table :data="tableData1" class="table" :show-header="false" ref="multipleTable">
                        <el-table-column prop="wjmc" label="文件名称"></el-table-column>
                        <el-table-column prop="cjsj" label="创建时间" width="200"></el-table-column>
                        <el-table-column label="操作" align="center" width="200">
                            <template slot-scope="scope">
                                <el-button type="text" icon="el-icon-lx-attention"
                                           @click="lookFile(scope.$index, scope.row)">点击查看
                                </el-button>
                                <el-button type="text" icon="el-icon-delete" class="red"
                                           @click="delFile(scope.$index, scope.row)">删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div style="padding: 28px 8px">
                        <el-button type="primary" @click="add" style="float: left">上传文件</el-button>
                        <el-pagination
                                style="float: right;"
                                @current-change="handleCurrentChange1"
                                :current-page="page1"
                                :page-size="size1"
                                layout="prev, pager, next"
                                :total="totalElements1">
                        </el-pagination>
                    </div>
                </el-tab-pane>
            </el-tabs>

        </div>

        <!-- 弹出框 -->
        <el-dialog :title="modelTitle" :visible.sync="modelVisible" width="40%"
                   :close-on-click-modal="false" @closed="closeClear">
            <el-form ref="addForm" :model="addForm" label-width="100px">
                <el-form-item label="文件名称"
                              prop="wjmc"
                              :rules="[{ required: true, message: '文件名称不能为空', trigger: 'blur' }]">
                    <el-input v-model="addForm.wjmc"></el-input>
                </el-form-item>
                <el-form-item label="文件类型"
                              prop="wjlx"
                              :rules="[{ required: true, message: '文件类型不能为空', trigger: 'blur' }]">
                    <el-select v-model="addForm.wjlx" placeholder="请选择文件类型">
                        <el-option v-for="item in this.$dict.fileType" :key="item.id+Math.random()" :label="item.name"
                                   :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="文件">
                    <input id="fileInput" ref="pathClear" type="file" @change="getFile($event)" accept="image/*"/>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="modelVisible = false">取 消</el-button>
                <el-button type="primary" :loading="addLoading" @click="saveEdit(addForm)">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog :title="wjmc" :visible.sync="dialogTPVisible" width="60%" @closed="reset">
                <div style="width: 100%;height: 400px;display: flex;justify-content: center;align-items: center;">
                    <img :src="wjlj" alt="" style="max-width:100%;max-height: 100%;margin:auto;border-radius: 5px;">
                </div>

        </el-dialog>
    </div>
</template>

<script>

    export default {
        name: 'basetable',
        data() {
            return {
                activeName: 'first',
                modelTitle: '添加文件',
                tableData: [],
                tableData1: [],
                modelVisible: false,
                addForm: {},
                file: {},
                size: 10,
                page: 1,
                totalElements: 0,
                size1: 10,
                page1: 1,
                totalElements1: 0,
                loading: true,
                addLoading: false,
                dialogTPVisible: false,
                wjlj: '',
                wjmc: ''
            }
        },
        created() {
            this.getData(1, this.page, this.size);
        },
        computed: {},
        methods: {
            reset() {
                this.wjlj = '';
                this.wjmc = '';
            },
            closeClear() {
                this.$refs.addForm.resetFields()
                if(this.$refs.pathClear) {
                    this.$refs.pathClear.value =''
                }
            },
            handleClick(tab, event) {
                if(tab.name === 'first'){
                    this.getData(1, this.page, this.size);
                }else {
                    this.getData(2, this.page1, this.size1);
                }
                console.log(tab, event);
            },
            delFile(index, row) {
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/file/delete', this.$qs.stringify({id: row.id})).then((res) => {
                        this.$message.success('已删除！');
                        if(this.activeName == 'first') {
                            this.getData(1, this.page, this.size);
                        }else {
                            this.getData(2, this.page1, this.size1);
                        }
                    });
                }).catch(() => {

                });
            },
            formatterWjlx(row) {
                return this.$common.dictParse(row.wjlx, this.$dict.fileType);
            },
            lookFile(index, row) {
                this.wjmc = row.wjmc;
                this.wjlj = 'http://47.96.85.104:80' + row.wjlj;
                this.dialogTPVisible = true;
//                window.open('http://182.151.22.247:8081' + row.wjlj);
            },
            modelClose(addForm) {
                this.$refs[addForm].resetFields();
            },
            getFile(event) {
                this.file = event.target.files[0];
                this.addForm.file = this.file;
            },
            // 分页导航
            handleCurrentChange(val) {
                this.page = val;
                this.getData(1,this.page, this.size);
            },
            handleCurrentChange1(val) {
                this.page1 = val;
                this.getData(2, this.page1, this.size1);
            },
            getData(type,page,size) {
                this.loading = true;
                this.$axios.post('/file/findAllByGsidByPage', this.$qs.stringify({
                    wjlx: type,
                    page: page,
                    size: size
                })).then((res) => {
                    if (res.resCode == 200) {
                        this.loading = false;
                        if(type === 1) {
                            this.tableData = res.data.content;
                            this.totalElements = res.data.totalElements
                        }else {
                            this.tableData1 = res.data.content;
                            this.totalElements1 = res.data.totalElements
                        }
                    }
                });
            },
            add(){
                this.addForm = {};
                this.file = {};
                this.modelVisible = true;

            },
            // 保存编辑
            saveEdit(addForm) {
                this.$refs.addForm.validate((valid) => {
                    if (valid) {
                        this.addLoading = true;
                        let formData = new FormData();
                        for (let key in this.addForm) {
                            formData.append(key, this.addForm[key]);
                        }
                        let config = {
                            headers: {
                                'Content-Type': 'multipart/form-data'
                            }
                        };
                        this.$axios.post('/file/add', formData, config).then(res => {
                            this.addLoading = false
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
