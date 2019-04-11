<template>
    <div>
        <div class="container">
            <el-button size="mini" type="success" @click="handleAdd">添加用户</el-button>
            <el-radio-group v-model="userType" @change="changeType" size="mini">
                <el-radio-button label="0">已激活</el-radio-button>
                <el-radio-button label="1">已注销</el-radio-button>
                <el-radio-button label="">全部</el-radio-button>
            </el-radio-group>
        </div>
        <div class="container">
            <el-table :data="userData" >
                <el-table-column prop="yhm" label="账号" :show-overflow-tooltip="false" fixed
                                 :sortable="false" align="left"></el-table-column>
                <el-table-column prop="mz" label="姓名" :show-overflow-tooltip="false"  fixed :sortable="false"
                                 align="left"></el-table-column>
                <el-table-column prop="szxzqdm" label="区域" :show-overflow-tooltip="false" width="" :sortable="false"
                                 align="left" :formatter="orgFormatter"></el-table-column>
                <el-table-column prop="js" label="角色" :show-overflow-tooltip="false" width="" :sortable="false"
                                 align="left" :formatter="roleFormatter"></el-table-column>
                <el-table-column prop="lxdh" label="联系电话" :show-overflow-tooltip="false"
                                 :sortable="false" align="left"></el-table-column>

                <el-table-column prop="cjsj" label="创建时间" :show-overflow-tooltip="false"
                                 :sortable="false" align="left"></el-table-column>
                <el-table-column prop="sqm" label="微信授权码"></el-table-column>

                <el-table-column label="操作" align="left" width="120">
                    <template slot-scope="scope" >
                        <span v-if="scope.row.zt==0">
                            <el-button  type="text" size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                            <el-button v-if="scope.row.zt==0"  type="text" size="mini" @click="handleCancel(scope.$index, scope.row)" style="color: #c2970e;">注销</el-button>
                        </span>
                        <span v-else>
                             <el-button v-if="scope.row.zt==1"  type="text" size="mini" @click="handleCancel(scope.$index, scope.row)" style="color: #00ab00;">激活</el-button>
                            <el-button v-if="scope.row.zt==1" type="text" size="mini" @click="handleDel(scope.$index, scope.row)" style="color: #ff0000;">删除</el-button>
                        </span>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                        :current-page.sync="currentPage"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        background
                        :page-sizes="[15, 20, 50]"
                        :page-size="pageSize"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                </el-pagination>
            </div>



            <el-dialog title="用户信息" width="60%" :visible.sync="addFormVisible" :close-on-click-modal="false">

                <el-form class="userForm" :model="addForm" label-width="120px" ref="addForm">
                    <el-form-item label="用户姓名" prop="mz">
                        <el-input v-model="addForm.mz" placeholder="请输入中文姓名"></el-input>
                    </el-form-item>
                    <el-form-item label="用户账号" prop="yhm"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-input v-model="addForm.yhm" placeholder="请输入英文或数字组合"></el-input>
                    </el-form-item>
                    <el-form-item label="所属区域" prop="szxzqdm"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-select v-model="addForm.szxzqdm" placeholder="请选择">
                            <el-option
                                    v-for="item in caseArea"
                                    :key="item.ctdm"
                                    :label="item.ctmc"
                                    :value="item.ctdm+''" >
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="用户角色" prop="js"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-select v-model="addForm.js" placeholder="请选择">
                            <el-option
                                    v-for="item in roles"
                                    :key="item.ctdm"
                                    :label="item.ctmc"
                                    :value="item.ctdm" >
                            </el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="性别" prop="xb">
                        <el-input v-model="addForm.xb"></el-input>
                    </el-form-item>
                    <el-form-item label="年龄" prop="nl">
                        <el-input-number v-model="addForm.nl"></el-input-number>
                    </el-form-item>
                    <el-form-item label="联系电话" prop="lxdh">
                        <el-input v-model="addForm.lxdh"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱" prop="yx">
                        <el-input v-model="addForm.yx"></el-input>
                    </el-form-item>
                    <el-form-item label="住址" prop="dz">
                        <el-input v-model="addForm.dz"></el-input>
                    </el-form-item>
                    <el-form-item label="微信授权码" prop="sqm" >
                        <el-input v-model="addForm.dz" readonly></el-input>
                    </el-form-item>
                </el-form>
                <el-alert
                        v-if="formStatus == 'add'"
                        center
                        title="用户默认密码: 666666"
                        type="success"
                        :closable="false">
                </el-alert>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="addFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveData">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import util from '../common/util';
    export default {
        name: 'basetable',
        data() {
            return {
                formStatus: 'add',
                userType: '0',//0-系统用户,1-小程序
                userData: [],
                addFormVisible: false,
                addForm: {},
                currentPage: 1,
                pageSize: 15,
                total: 0,
                roles: [],
                caseArea: []
            }
        },
        created(){
            this.getData();
            this.getDictData();

        },
        methods: {
            getDictData(){
                //案件区域
                this.$axios.post('/dict/findDictListByZddm', {zddm: 'D_SYS_AJQYDM', zxbz: 0}).then((res) => {
                    this.caseArea = res.data;
                });
                //人员角色
                this.$axios.post('/dict/findDictListByZddm', {zddm: 'D_SYS_RYJSDM', zxbz: 0}).then((res) => {
                    this.roles = res.data;
                });
            },
            changeType(){
                this.getData();
            },
            saveData() {
                this.$refs.addForm.validate((valid) => {
                    if (valid) {
                        let url = '/user/add';
                        //this.addForm.lx = 0;
                        if(this.addForm.id) {
                            url = '/user/update'
                        }
                        this.$axios.post(url, Object.assign({}, this.addForm)).then( (res) => {
                            this.addFormVisible = false;
                            this.getData();
                        });
                    } else {
                        return false;
                    }
                });
            },
            roleFormatter(row) {
                return util.dictParse(row.js,this.roles);
            },
            orgFormatter(row) {
                return util.dictParse(row.szxzqdm,this.caseArea);
            },
            getData() {
                this.$axios.post('/user/findAll', {zt: this.userType, page: this.currentPage, pagesize: this.pageSize}).then((res) => {
                    if(res.resCode == 200){
                        this.userData = res.data;
                        this.total = res.counts;
                    }else if(res.resCode == 100) {
                        this.$router.push('/login');
                    }
                });
            },

            handleAdd: function () {
                this.formStatus = 'add';
                this.addFormVisible = true;
                this.addForm = {};
            },
            handleEdit: function (index, row) {
                this.formStatus = 'edit';
                this.addForm = Object.assign({}, row);
                this.addFormVisible = true;

            },

            //删除
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
            //注销,激活
            handleCancel(index, row) {
                let zt = row.zt; //0为激活状态,1位注销状态
                let text = '注销';
                let zxbz = 1;
                if(zt == 1){
                    text = '激活';
                    zxbz = 0;
                }
                this.$confirm('此操作将' + text+ '该账号, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/user/cancelAccount', {id: row.id,zt: zxbz}).then(res => {
                        if(res.resCode == 200) {
                            this.getData();
                            this.$message({
                                type: 'success',
                                message: text + '成功!'
                            });
                        }
                    })
                }).catch(() => {});
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
