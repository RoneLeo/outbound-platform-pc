<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 用户信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-button size="mini" type="success" @click="handleAdd">添加用户</el-button>
            <el-radio-group v-model="dictType" @change="changeType" size="mini">
                <el-radio-button label="0">已激活</el-radio-button>
                <el-radio-button label="1">已注销</el-radio-button>
                <el-radio-button label="">全部</el-radio-button>
            </el-radio-group>
        </div>
        <div class="container">
            <el-table :data="datavalues" :border="true"
                      size="mini"
                      :stripe="false" style="width: 100%;overflow: auto; font-size: 14px;">
                <el-table-column type="index" width="35" fixed></el-table-column>
                <el-table-column prop="yhm" label="用户名" :show-overflow-tooltip="false" width="100" fixed
                                 :sortable="false" align="left"></el-table-column>
                <el-table-column prop="mz" label="名字" :show-overflow-tooltip="false" width="90" fixed :sortable="false"
                                 align="left"></el-table-column>
                <el-table-column prop="xb" label="性别" :show-overflow-tooltip="false" width="50" :sortable="false"
                                 align="left"></el-table-column>
                <el-table-column prop="nl" label="年龄" :show-overflow-tooltip="false" width="50" :sortable="false"
                                 align="left"></el-table-column>
                <el-table-column prop="lxdh" label="联系电话" :show-overflow-tooltip="false" width="150"
                                 :sortable="false" align="left"></el-table-column>
                <el-table-column prop="yx" label="邮箱" :show-overflow-tooltip="false" width="" :sortable="false"
                                 align="left"></el-table-column>
                <el-table-column prop="dz" label="地址" :show-overflow-tooltip="false" width="" :sortable="false"
                                 align="left"></el-table-column>
                <el-table-column prop="js" label="角色" :show-overflow-tooltip="false" width="" :sortable="false"
                                 align="left" :formatter="JsFormatter"></el-table-column>
                <el-table-column prop="cjsj" label="创建时间" :show-overflow-tooltip="false" width="170"
                                 :sortable="false" align="left"></el-table-column>
                <el-table-column label="操作" align="left" width="120">
                    <template slot-scope="scope">
                        <el-button type="text" size="mini" @click="handleEdit(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button v-show="scope.row.zt==1"  type="text" size="mini" @click="handleDel(scope.$index, scope.row)" style="color: #ff0000;">删除</el-button>
                        <el-button v-show="scope.row.zt==0"  type="text" size="mini" @click="handleCancel(scope.$index, scope.row)" style="color: #ff0000;">注销</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="block">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                               :current-page.sync="currentPage" :page-sizes="[5, 10, 20, 30, 50]" :page-size="pageSize"
                               layout="sizes, prev, pager, next" :total="total">
                </el-pagination>
            </div>
            <el-dialog title="弹框信息" width="55%" :visible.sync="addFormVisible" :close-on-click-modal="false"
                       @closed="clear">
                <el-form class="userForm" :model="addForm" label-width="120px" ref="addForm">
                    <el-form-item label="用户名" prop="yhm"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-input v-model="addForm.yhm"></el-input>
                    </el-form-item>
                    <el-form-item label="名字" prop="mz"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-input v-model="addForm.mz"></el-input>
                    </el-form-item>
                    <el-form-item label="性别" prop="xb"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-input v-model="addForm.xb"></el-input>
                    </el-form-item>
                    <el-form-item label="年龄" prop="nl"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-input-number v-model="addForm.nl" label="描述文字"></el-input-number>
                    </el-form-item>
                    <el-form-item label="联系电话" prop="lxdh"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-input v-model="addForm.lxdh"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱" prop="yx">
                        <el-input v-model="addForm.yx"></el-input>
                    </el-form-item>
                    <el-form-item label="地址" prop="dz"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-input v-model="addForm.dz"></el-input>
                    </el-form-item>
                    <!--<el-form-item label="用户类型" prop="lx"-->
                                  <!--:rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">-->
                        <!--<el-radio-group v-model="addForm.lx">-->
                            <!--<el-radio :label="0">系统用户</el-radio>-->
                            <!--<el-radio :label="1">小程序用户</el-radio>-->
                        <!--</el-radio-group>-->
                    <!--</el-form-item>-->
                    <el-form-item label="角色" prop="js"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-select v-model="addForm.js" placeholder="请选择">
                            <el-option
                                    v-for="item in roles"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="区域" prop="szxzqdm"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-select v-model="addForm.szxzqdm" placeholder="请选择">
                            <el-option
                                    v-for="item in qys"
                                    :key="item.ctdm"
                                    :label="item.ctmc"
                                    :value="item.ctdm" >
                            </el-option>
                        </el-select>
                    </el-form-item>

                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="addFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="submit">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                dictType: '0',
                datavalues: [],
                addFormVisible: false,
                addForm: {},
                currentPage: 1,
                pageSize: 5,
                total: 0,
                roles: [],
                qys: []
            }
        },
        created(){
            this.$axios.get('/role/findAll').then((res) => {
                if(res.resCode == 200){
                    this.roles = res.data;
                }else if(res.resCode == 100) {
                    this.$router.push('/login');
                }
            });
            this.$axios.post('/dict/findDictListByZddm', {zddm: 'D_SYS_AJQYDM', zxbz: 0}).then((res) => {
                if(res.resCode == 200){
                    this.qys = res.data;
                }else if(res.resCode == 100) {
                    this.$router.push('/login');
                }
            });
            this.getData();

        },
        methods: {
            changeType(){
                this.getData();
            },
            submit() {
                this.$refs.addForm.validate((valid) => {
                    if (valid) {
                        let url = '/user/add';
//                        this.addForm.lx = 0;
                        if(this.addForm.id) {
                            url = '/user/update'
                        }
                        this.$axios.post(url, Object.assign({}, this.addForm)).then( (res) => {
                            if(res.resCode == 200){
                                this.addFormVisible = false;
                                this.getData();
                            }else{
                                this.$message.error(res.resMsg);
                            }
                        });
                    } else {
                        return false;
                    }
                });
            },
            JsFormatter(row) {
                return this.$common.dictParse(row.js, this.roles);
            },
            getData() {
                this.$axios.post('/user/findAll', {lx:0, zt: this.dictType, page: this.currentPage, pagesize: this.pageSize}).then((res) => {
                    if(res.resCode == 200){
                        this.datavalues = res.data;
                        this.total = res.counts;
                    }else if(res.resCode == 100) {
                        this.$router.push('/login');
                    }
                });
            },
            clear() {
                this.$refs.addForm.resetFields();
            },
            handleAdd: function () {
                this.addFormVisible = true;
                this.addForm = {};
            },
            handleEdit: function (index, row) {
                this.addForm = Object.assign({}, row);
                this.addFormVisible = true;

            },

            //del
            handleDel: function (index, row) {
                this.$confirm('此操作将永久删除该账号, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/user/delete', {id: row.id}).then(res => {
                        if(res.resCode == 200) {
                            this.getData();
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            handleCancel: function (index, row) {
                this.$confirm('此操作将注销该账号, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/user/cancelAccount', {id: row.id}).then(res => {
                        if(res.resCode == 200) {
                            this.getData();
                            this.$message({
                                type: 'success',
                                message: '已注销!'
                            });
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消'
                    });
                });
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getData();
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getData()
            }
        }
    }
</script>
<style>
    .userForm .el-form-item {
        width: 49%;
        display: inline-block;
    }
    .crumbs {
        margin: 10px 0;
    }

    .container {
        /*padding: 30px;*/
        background-color: #fff;
        border: 1px solid #ddd;
        border-radius: 5px;
    }

    .block {
        margin: 10px 0;
    }

    .el-pagination {
        text-align: right;
    }
</style>
