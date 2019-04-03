<template>
    <div>
        <div class="container">
            <el-button size="mini" type="success" @click="handleAdd">新增批次</el-button>
        </div>
        <div class="container">
            <el-table :data="datavalues"
                      size="mini"
                      :stripe="false" style="width: 100%;overflow: auto; font-size: 14px;">
                <el-table-column type="index" width="35" fixed></el-table-column>
                <el-table-column prop="pcid" label="批次id" :show-overflow-tooltip="false"  fixed
                                 :sortable="false" align="center"></el-table-column>
                <el-table-column prop="pcmc" label="批次名称" :show-overflow-tooltip="false" fixed :sortable="false"
                                 align="center"></el-table-column>
                <el-table-column label="操作" align="center" >
                    <template slot-scope="scope">
                        <a :href="$this.baseUrl +`http://182.151.22.247:8083/batch/exportExcel?pcid=${scope.row.pcid}`" target="_blank">
                            <el-button type="text" size="mini"  style="color: green;">导出</el-button>
                        </a>
                        <el-button type="text" size="mini" @click="handleDel(scope.$index, scope.row)" style="color: #ff0000;">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                               :current-page.sync="currentPage" :page-sizes="[5, 10, 20, 50]" :page-size="pageSize"
                               layout="sizes, prev, pager, next" :total="total">
                </el-pagination>
            </div>



            <el-dialog id="fieldDialog" title="模板字段信息" width="60%" :visible.sync="addFormVisible" :close-on-click-modal="false"
                       @closed="clear">
                <el-form class="baseCaseForm" :model="dataForm" label-width="120px" ref="dataForm">
                    <el-form-item label="批次名称" prop="pcmc"
                                  :rules="[{ required: true, message: '输入不能为空', trigger: 'blur' }]">
                        <el-input v-model="dataForm.pcmc"></el-input>
                    </el-form-item>
                    <el-form-item :label="type" v-for="(type, index) in baseType" :key="index">
                        <!--联系人信息-->
                        <template v-if="index == 7">
                            <el-checkbox-group v-model="zdidArr[index]">
                                <el-checkbox size="mini" border v-for="item in fieldCases[index]" :label="item.id" :key="item.id">{{item.name}}</el-checkbox>
                            </el-checkbox-group>
                            <span class="">
                                联系人个数<el-input-number  v-if="!dataForm.pcid" v-model="lxrnum" :min="1" :max="10" size="mini" label="描述文字" style="margin-left: 10px"></el-input-number>
                            </span>
                        </template>
                        <template v-else-if="index == 9">
                            <el-checkbox-group v-model="zdidArr[index]">
                                <el-checkbox size="mini" border v-for="item in fieldCases[index]" :label="item.id" :key="item.id">{{item.name}}</el-checkbox>
                            </el-checkbox-group>
                            备注个数<el-input-number  v-if="!dataForm.pcid" v-model="lxrnum" :min="1" :max="10" size="mini" label="描述文字" style="margin-left: 10px"></el-input-number>
                        </template>
                        <template v-else>
                            <el-checkbox-group v-model="zdidArr[index]">
                                <el-checkbox size="mini" border v-for="item in fieldCases[index]" :label="item.id" :key="item.id">{{item.name}}</el-checkbox>
                            </el-checkbox-group>
                        </template>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="addFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="submit">确 定</el-button>
                </div>
            </el-dialog>


            <!-- 选择模板字段 -->
            <el-dialog id="dialog2" title="模板字段选择"  width="60%"
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
    </div>
</template>
<script>
    export default {
        data() {
            return {
                baseType:[],
                fieldCases: [],
                dictType: '',
                datavalues: [],
                addFormVisible: false,
                dataForm: {},
                currentPage: 1,
                pageSize: 5,
                total: 0,
                zdidArr: [],

                lxrnum: 1
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
                                this.zdidArr.push([])
                                this.baseType.push(item.name);
                            })
                        }
                    }
                })
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
                this.$axios.post('/batch/findAllPcidByPage', {page: this.currentPage, pagesize: this.pageSize}).then((res) => {
                    if(res.resCode == 200){
                        this.datavalues = res.data;
                        this.total = res.counts;
                    }else if(res.resCode == 100) {
                        this.$router.push('/login');
                    }
                });
            },



            handleExport(index, row) {
                this.$axios.post('/batch/exportExcel', {pcid: row.pcic}).then(res => {

                })
            },
            changeType(){
                this.getData();
            },
            submit() {
                this.$refs.dataForm.validate((valid) => {
                    if (valid) {
                        console.log(this.dataForm, this.zdidArr)
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
                        if(this.dataForm.id) {
                            url = '/batch/update'
                        }
                        this.$axios.post(url, {pcmc: this.dataForm.pcmc, zdids: zdids.join(',')}).then( (res) => {
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

            clear() {
                this.$refs.dataForm.resetFields();
            },
            handleAdd: function () {
                this.addFormVisible = true;
                this.dataForm = {};
                this.lxrnum = 1;
            },
            handleEdit: function (index, row) {
                this.dataForm = Object.assign({}, row);
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
