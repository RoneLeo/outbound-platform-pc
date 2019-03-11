<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 产品管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" v-loading="loading">
            <el-row>
                <el-form :inline="true" class="demo-form-inline">
                    <el-form-item label="产品类型">
                        <el-select v-model="sblx" placeholder="请选择" @change="getSblxData">
                            <el-option
                                    v-for="item in equipmentType"
                                    :key="item.id*1.9098"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>

                </el-form>

            </el-row>
            <el-table :data="tableData" class="table" ref="multipleTable">
                <el-table-column prop="cpmc" label="产品名称"></el-table-column>
                <el-table-column prop="sblx" label="产品类型" :formatter="formatterSBLX"></el-table-column>
                <!--<el-table-column label="产品图片">-->
                    <!--<template slot-scope="scope">-->
                        <!--<a target="_blank" :href='`http://182.151.22.247:8081${scope.row.cptp}`'>{{scope.row.cptp}}</a>-->
                    <!--</template>-->
                <!--</el-table-column>-->
                <!--<el-table-column prop="cptp" label="产品图片"></el-table-column>-->
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-button type="text" icon="el-icon-lx-attention" @click="look(scope.$index, scope.row)">点击查看
                        </el-button>
                        <el-button type="text" icon="el-icon-delete" class="red"
                                   @click="delFile(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="padding: 28px 8px">
                <el-button type="primary" @click="add" style="float: left">新增产品</el-button>
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
                   :close-on-click-modal="false" @closed="modelClose(addForm)">
            <el-form :ref="addForm" :model="addForm" label-width="100px">
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
                <el-form-item label="文件"
                              prop="file"
                              :rules="[{ required: true, message: '文件不能为空', trigger: 'blur' }]">
                    <input type="file" @change="getFile($event)" accept="image/*"/>
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
                modelTitle: '添加文件',
                tableData: [],
                modelVisible: false,
                addForm: {},
                file: {},
                size: 10,
                page: 1,
                totalElements: 0,
                loading: false,
                addLoading: false,
                equipmentType: [],
                sblx: null
            }
        },
        mounted () {
            this.$axios.post('/equipmentType/findAllByGsid').then((res) => {
                let data = res.data;
                this.equipmentType = data;
                if(this.$route.params.sblx) {
                    this.sblx = this.$route.params.sblx;
                }else {
                    this.sblx = this.equipmentType && this.equipmentType.length ? this.equipmentType[0].id : '';
                }
                this.getData();
            });
        },
        created() {

        },
        computed: {},
        methods: {
            getSblxData() {
                this.page = 1;
                this.getData();
            },
            formatterSBLX(row) {
                return this.$common.dictParse(row.sblx, this.equipmentType);
            },
            delFile(index, row) {
                this.$confirm('是否确认删除该产品?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/equipment/delete', this.$qs.stringify({id: row.id})).then((res) => {
                        this.$message.success('已删除！');
                        this.getData();
                    });
                }).catch(() => {
                });
            },
            formatterWjlx(row) {
                return this.$common.dictParse(row.wjlx, this.$dict.fileType);
            },
            look(index, row) {
                this.$router.push({
                    name: 'addProduct', params: {
                        pId: row.id
                    }
                })
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
                this.getData();
            },
            // 获取 easy-mock 的模拟数据
            getData() {
                this.loading = true;
                this.$axios.post('/equipment/findAllByGsidAndSblx', this.$qs.stringify({
                    sblx: this.sblx,
                    page: this.page,
                    size: this.size
                })).then((res) => {
                        this.loading = false;
                        this.tableData = res.data;
                        this.totalElements = res.totalElements
                });
            },

            add(){
                this.$router.push('/addProduct');
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
                        for (var value of formData.values()) {
                            console.log(value);
                        }
                        let config = {
                            headers: {
                                'Content-Type': 'multipart/form-data'
                            }
                        };
                        this.$axios.post('/file/add', formData, config).then(res => {
                            this.addLoading = false
                            this.modelVisible = false;
                            this.getData();
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
