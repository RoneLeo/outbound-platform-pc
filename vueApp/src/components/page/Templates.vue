<template>
    <div>
        <div class="container">
            <el-button size="mini" type="success" @click="handleAdd">新增批次</el-button>
        </div>
        <div class="container">
            <el-table :data="batchData">
                <el-table-column type="index" width="40" fixed></el-table-column>
                <el-table-column prop="pcmc" label="模板名称"></el-table-column>
                <el-table-column prop="pcid" label="批次ID"></el-table-column>
                <el-table-column prop="cjsj" label="创建时间"></el-table-column>
                <el-table-column label="操作" >
                    <template slot-scope="scope">
                        <el-button type="text" size="mini" @click="handleEdit(scope.$index, scope.row)">详情</el-button>
                        <el-button type="text" size="mini" @click="handleExport(scope.$index, scope.row)">导出</el-button>
                        <el-button type="text" size="mini" @click="handleDel(scope.$index, scope.row)" style="color: #ff0000;">删除</el-button>
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

            <el-dialog id="fieldDialog" title="模板字段信息" width="60%" :visible.sync="addFormVisible" :close-on-click-modal="false" @closed="dialogClose">
                <el-form class="baseCaseForm" :model="dataForm" label-width="120px" ref="dataForm">
                    <el-form-item label="批次名称" prop="pcmc"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-input v-model="dataForm.pcmc"></el-input>
                    </el-form-item>
                    <el-form-item :label="type" v-for="(type, index) in baseType" :key="index">
                        <!--联系人信息-->
                        <template v-if="index == 7">
                            <el-checkbox-group v-model="zdidArr[index]">
                                <el-checkbox v-for="item in fieldCases[index]"  size="mini" border :label="item.id" :key="item.id">{{item.name}}</el-checkbox>
                            </el-checkbox-group>
                            <span class="">
                                联系人个数<el-input-number v-model="lxrNum" :min="1" :max="10" size="mini" label="描述文字" style="margin-left: 10px"></el-input-number>
                            </span>
                        </template>
                        <!--备注信息-->
                        <template v-else-if="index == 9">
                            <el-checkbox-group v-model="zdidArr[index]">
                                <el-checkbox v-for="item in fieldCases[index]"  size="mini" border  :label="item.id" :key="item.id">{{item.name}}</el-checkbox>
                            </el-checkbox-group>
                            备注个数<el-input-number v-model="bzNum" :min="1" :max="10" size="mini" label="描述文字" style="margin-left: 10px"></el-input-number>
                        </template>
                        <!--other-->
                        <template v-else>
                            <el-checkbox-group v-model="zdidArr[index]">
                                <el-checkbox v-for="item in fieldCases[index]"  size="mini" border  :label="item.id" :key="item.id">{{item.name}}</el-checkbox>
                            </el-checkbox-group>
                        </template>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="addFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="saveData">确 定</el-button>
                </div>
            </el-dialog>

        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                checked: true,
                baseType:[],
                fieldCases: [],
                dictType: '',
                batchData: [],
                addFormVisible: false,
                dataForm: {},
                currentPage: 1,
                pageSize: 5,
                total: 0,
                zdidArr: [],
                zdidEditArr: [],
                lxrNum: 1,
                bzNum: 1
            }
        },
        created(){
            this.getBaseType();
            this.getField();
            this.getBatchData();
        },
        methods: {
            getBaseType(){
                this.$axios.get('/baseType/findAll').then(res => {
                    if(res.resCode == 200) {
                        let data = res.data;
                        if(data && data.length) {
                            this.baseType = [];
                            this.zdidArr = [];
                            data.forEach(item => {
                                this.zdidArr.push([]);
                                this.baseType.push(item.name);
                            });
                        }
                    }
                });
            },
            getField(){
                this.$axios.get('/fieldCaseBase/findAll').then(res => {
                    if(res.resCode == 200) {
                        let data = res.data;
                        this.fieldCases = data;
                    }
                })
            },
            getBatchData() {
                this.$axios.post('batch/findAll', {page: this.currentPage, pagesize: this.pageSize}).then((res) => {
                    this.batchData = res.data;
                    this.total = res.counts;
                })
            },
            //导出
            handleExport(index, row) {
                let url = this.$baseURL + '/batch/exportExcel?pcid=' + row.id;
                window.open(url);
            },
            //角色解析
            JsFormatter(row) {
                return this.$common.dictParse(row.js, this.roles);
            },
            handleAdd: function () {
                this.addFormVisible = true;
                this.dataForm = {};
                this.lxrNum = 1;
                this.bzNum = 1;
                this.zdidEditArr = [];
            },
            handleEdit: function (index, row) {
                this.dataForm = Object.assign({}, row);
                this.zdidEditArr = [];
                this.$axios.post('batch/findAllZdzh', {pcid: row.pcid}).then( (res) => {
                    let data = res.data;
                    this.zdidEditArr = data.zdids;
                    this.lxrNum = data.lxrNum;
                    this.bzNum = data.bzNum;
                    this.addFormVisible = true;
                });
            },
            //判断字段选中
            isFieldChecked(id){
                let res = this.zdidEditArr.indexOf(id);
                return res >= 0;
            },
            dialogClose(){
                this.zdidEditArr = [];
            },
            //保存数据
            saveData() {
                this.$refs.dataForm.validate((valid) => {
                    if (valid) {
                        console.log(this.dataForm, this.zdidArr)
                        let zdids = [];
                        this.zdidArr.forEach((arr,index) => {
                            //联系人
                            if(index == 7) {  //联系人信息的时候，判断联系人数量
                                for(let i= 0; i< this.lxrNum; i++) {
                                    arr.forEach(item => {
                                        zdids.push(item)
                                    })
                                }
                            }
                            //备注
                            if(index == 9) {  //联系人信息的时候，判断联系人数量
                                for(let i= 0; i< this.bzNum; i++) {
                                    arr.forEach(item => {
                                        zdids.push(item)
                                    })
                                }
                            }
                            else {
                                arr.forEach(item => {
                                    zdids.push(item)
                                })
                            }
                        });
                        let url = 'batch/add'; //添加
                        let param = {
                            pcmc: this.dataForm.pcmc,
                            zdids: zdids.join(',')
                        };
                        if(this.dataForm.id) {
                            url = 'batch/update';
                            param.pcid = this.dataForm.id;
                        }
                        this.$axios.post(url, param).then( (res) => {
                            if(res.resCode == 200){
                                this.addFormVisible = false;
                                this.getBatchData();
                                this.$message.success('保存成功!');
                            }else{
                                this.$message.error(res.resMsg);
                            }
                        });
                    } else {
                        return false;
                    }
                });
            },

            handleDel: function (index, row) {
                this.$confirm('此操作将永久删除该批次模板, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/batch/delete', {pcid: row.pcid}).then(res => {
                        if(res.resCode == 200) {
                            this.getBatchData();
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        }
                    })
                }).catch(() => {});
            },
            handleSizeChange(val) {
                this.pageSize = val;
                this.getBatchData();
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getBatchData()
            }
        }
    }
</script>
<style scoped lang="scss">
    #fieldDialog{
        .el-checkbox{
            margin: 0 8px 0 0;
            padding: 1px 5px;
            height: 25px;
            min-width: 100px;
        }
        .el-form-item{
            margin-bottom: 10px;
        }
        .el-form{
            max-height: 600px;
            overflow: auto;
        }
    }
</style>
