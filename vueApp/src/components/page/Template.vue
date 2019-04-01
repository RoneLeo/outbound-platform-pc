<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 批次管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <el-button size="mini" type="success" @click="handleAdd">新增批次</el-button>
            <!--<el-radio-group v-model="dictType" @change="changeType" size="mini">-->
                <!--<el-radio-button label="">全部</el-radio-button>-->
                <!--<el-radio-button label="0">已激活</el-radio-button>-->
                <!--<el-radio-button label="1">已注销</el-radio-button>-->
            <!--</el-radio-group>-->
        </div>
        <div class="container">
            <el-table :data="datavalues" :border="true"
                      size="mini"
                      :stripe="false" style="width: 100%;overflow: auto; font-size: 14px;">
                <el-table-column type="index" width="35" fixed></el-table-column>
                <el-table-column prop="pcid" label="批次id" :show-overflow-tooltip="false"  fixed
                                 :sortable="false" align="center"></el-table-column>
                <el-table-column prop="pcmc" label="批次名称" :show-overflow-tooltip="false" fixed :sortable="false"
                                 align="center"></el-table-column>
                <el-table-column label="操作" align="center" >
                    <template slot-scope="scope">
                        <!--<el-button type="text" size="mini" @click="handleEdit(scope.$index, scope.row)">编辑-->
                        <!--</el-button>-->
                        <a :href="`http://182.151.22.247:8083/batch/exportExcel?pcid=${scope.row.pcid}`" target="_blank">
                            <el-button type="text" size="mini"  style="color: green;">导出</el-button>
                        </a>
                        <el-button type="text" size="mini" @click="handleDel(scope.$index, scope.row)" style="color: #ff0000;">删除</el-button>

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
                <el-form class="baseCaseForm" :model="addForm" label-width="120px" ref="addForm">
                    <el-form-item label="批次名称" prop="pcmc"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-input v-model="addForm.pcmc"></el-input>
                    </el-form-item>
                    <el-form-item :label="type" v-for="(type, index) in baseType" :key="index">
                        <template v-if="type == '联系人信息'">
                            <el-checkbox-group v-model="zdidArr[index]">
                                <el-checkbox v-for="item in fieldCases[index]" :label="item.id" :key="item.id">{{item.name}}</el-checkbox>
                            </el-checkbox-group>
                            联系人个数<el-input-number v-if="!addForm.pcid" v-model="lxrnum" :min="1" :max="10" size="mini" label="描述文字" style="margin-left: 10px"></el-input-number>
                        </template>
                        <template v-else="">
                            <el-checkbox-group v-model="zdidArr[index]">
                                <el-checkbox v-for="item in fieldCases[index]" :label="item.id" :key="item.id">{{item.name}}</el-checkbox>
                            </el-checkbox-group>
                        </template>
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
                dictType: '',
                datavalues: [],
                addFormVisible: false,
                addForm: {},
                currentPage: 1,
                pageSize: 5,
                total: 0,
                zdidArr: [],
                baseType: [],
                fieldCases: [],
                lxrnum: 1
            }
        },
        created(){
            this.$axios.get('/baseType/findAll').then(res => {
                if(res.resCode == 200) {
                    let data = res.data;
                    if(data && data.length) {
                        this.baseType = [];
                        this.zdidArr = [];
                        data.forEach(item => {
                            this.zdidArr.push([])
                            this.baseType.push(item.name);
                        })
                    }
                }
            })
            this.$axios.get('/fieldCaseBase/findAll').then(res => {
                if(res.resCode == 200) {
                    let data = res.data;
                    this.fieldCases = data;
                }
            })
            this.getData();
        },
        methods: {
            handleExport(index, row) {
                this.$axios.post('/batch/exportExcel', {pcid: row.pcic}).then(res => {

                })
            },
            changeType(){
                this.getData();
            },
            submit() {
                this.$refs.addForm.validate((valid) => {
                    if (valid) {
                        console.log(this.addForm, this.zdidArr)
                        let zdids = [];
                        this.zdidArr.forEach((arr,index) => {
                            if(index == 7) {  //联系人信息的时候，判断联系人数量
                                for(let i= 0; i< this.lxrnum; i++) {
                                    arr.forEach(item => {
                                        zdids.push(item)
                                    })
                                }
                            }else {
                                arr.forEach(item => {
                                    zdids.push(item)
                                })
                            }
                        })
                        console.log(zdids)
                        let url = '/batch/add';
                        if(this.addForm.id) {
                            url = '/batch/update'
                        }
                        this.$axios.post(url, {pcmc: this.addForm.pcmc, zdids: zdids.join(',')}).then( (res) => {
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
                this.$axios.post('/batch/findAllPcidByPage', {page: this.currentPage, pagesize: this.pageSize}).then((res) => {
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
                this.lxrnum = 1;
            },
            handleEdit: function (index, row) {
                this.addForm = Object.assign({}, row);
                this.addFormVisible = true;
            },

            //del
            handleDel: function (index, row) {
                this.$confirm('此操作将永久删除该批次模板, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/batch/delete', {pcid: row.pcid}).then(res => {
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
    .el-pagination {
        text-align: right;
    }
</style>
