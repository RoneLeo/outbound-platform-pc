<template>
    <div>
        <div class="container">
            <h3>{{caseTitle}} </h3>
        </div>
        <div class="case-info">
            <el-button class="return-btn" type="success" @click="backToCaseList">返回案件列表</el-button>

            <el-tabs type="border-card">
                <el-tab-pane label="案件基本信息">
                    <div id="caseInfo">
                        <el-form class="case-detail-form" ref="detailForm" :model="form" label-width="120px" label-position="right">
                            <div class="case-item clearfix">
                                <div class="case-title">基本信息</div>
                                <el-form-item :label="baseCaseJson.ajmc">
                                    {{caseForm.ajmc}}
                                </el-form-item>
                                <el-form-item :label="baseCaseJson.gaxlh">
                                    {{caseForm.gaxlh}}
                                </el-form-item>
                                <el-form-item :label="baseCaseJson.pcid">
                                    {{caseForm.pcid}}
                                </el-form-item>
                                <el-form-item :label="baseCaseJson.ajyj">
                                    {{caseForm.ajyj}}
                                </el-form-item>
                            </div>
                            <div class="case-item clearfix">
                                <div class="case-title">卡号信息</div>
                                <template v-for="(item, index) in caseForm.khxx">
                                    <template v-for="(value, key, index) in item">
                                        <el-form-item v-if="key!='id' && key !='ajid' && key !='xszt' && value!= null" :label="baseCaseJson[key]">
                                            {{value}}
                                        </el-form-item>
                                    </template>
                                </template>
                            </div>
                            <div class="case-item clearfix">
                                <div class="case-title">催收员信息</div>
                                <template v-for="(item, index) in caseForm.csyxx">
                                    <template v-for="(value, key, index) in item">
                                        <el-form-item v-if="key!='id' && key !='ajid' && key !='xszt' && value!= null" :label="baseCaseJson[key]">
                                            {{value}}
                                        </el-form-item>
                                    </template>
                                </template>
                            </div>
                            <div class="case-item clearfix">
                                <div class="case-title">贷款信息</div>
                                <template v-for="(item, index) in caseForm.dkxx">
                                    <template v-for="(value, key, index) in item">
                                        <el-form-item v-if="key!='id' && key !='ajid' && key !='xszt' && value!= null" :label="baseCaseJson[key]">
                                            {{value}}
                                        </el-form-item>
                                    </template>
                                </template>
                            </div>
                            <div class="case-item clearfix">
                                <div class="case-title">对象信息</div>
                                <template v-for="(item, index) in caseForm.dxxx">
                                    <template v-for="(value, key, index) in item">
                                        <el-form-item v-if="key!='id' && key !='ajid' && key !='xszt' && value!= null" :label="baseCaseJson[key]">
                                            {{value}}{{value}}{{value}}{{value}}{{value}}{{value}}{{value}}{{value}}
                                        </el-form-item>
                                    </template>
                                </template>
                            </div>
                            <div class="case-item clearfix">
                                <div class="case-title">外访信息</div>
                                <template v-for="(item, index) in caseForm.wfxx">
                                    <template v-for="(value, key, index) in item">
                                        <el-form-item v-if="key!='id' && key !='ajid' && key !='xszt' && value!= 'null'" :label="baseCaseJson[key]">
                                            {{value}}
                                        </el-form-item>
                                    </template>
                                </template>
                            </div>
                            <div class="case-item clearfix">
                                <div class="case-title">案人信息</div>
                                <template v-for="(item, index) in caseForm.arxx">
                                    <template v-for="(value, key, index) in item">
                                        <el-form-item v-if="key!='id' && key !='ajid' && key !='xszt' && value!= null" :label="baseCaseJson[key]">
                                            {{value}}
                                        </el-form-item>
                                    </template>
                                </template>
                            </div>
                            <div class="case-item clearfix">
                                <div class="case-title">联系人信息</div>
                                <template v-for="(item, index) in caseForm.lxrxx">
                                    <div class="line"></div>
                                    <template v-for="(value, key, index) in item">
                                        <el-form-item v-if="key!='id' && key !='ajid' && key !='xszt' && value!= null" :label="baseCaseJson[key]">
                                            {{value}}
                                        </el-form-item>
                                    </template>
                                </template>
                            </div>
                            <div class="case-item clearfix">
                                <div class="case-title">其他信息</div>
                                <template v-for="(item, index) in caseForm.qtxx">
                                    <template v-for="(value, key, index) in item">
                                        <el-form-item v-if="key!='id' && key !='ajid' && key !='xszt' && value!= null" :label="baseCaseJson[key]">
                                            {{value}}
                                        </el-form-item>
                                    </template>
                                </template>
                            </div>
                            <div class="case-item clearfix">
                                <div class="case-title">备注信息</div>
                                <template v-for="(item, index) in caseForm.bzxx">
                                    <div class="line"></div>
                                    <template v-for="(value, key, index) in item">
                                        <el-form-item v-if="key!='id' && key !='ajid' && key !='xszt' && value!= null" :label="baseCaseJson[key]">
                                            {{value}}
                                        </el-form-item>
                                    </template>

                                </template>
                            </div>
                        </el-form>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="案件任务信息">
                    <el-table :data="tableData" class="table" ref="multipleTable">
                        <el-table-column prop="rwmc" label="任务名称"></el-table-column>
                        <el-table-column prop="rwzt" label="任务状态":formatter="RwztFormatter"></el-table-column>
                        <el-table-column prop="rwfs" label="任务方式":formatter="RwfsFormatter"></el-table-column>
                        <el-table-column prop="rwzxr" label="任务执行人"></el-table-column>
                        <el-table-column prop="rwyj" label="任务佣金"></el-table-column>
                        <el-table-column prop="sjyj" label="实际佣金"></el-table-column>
                        <el-table-column prop="rwjzsj" label="任务截止时间"></el-table-column>
                        <el-table-column prop="rwwcsj" label="任务完成时间"></el-table-column>
                        <el-table-column prop="shbz" label="审核备注"></el-table-column>
                        <el-table-column prop="rwms" label="任务描述"></el-table-column>
                        <el-table-column label="操作">
                            <template slot-scope="scope">
                                <el-button size="mini" type="text" @click="handleTask(scope.$index, scope.row)">
                                    指派
                                </el-button>
                                <el-button size="mini" type="primary" @click="handleTaskRecord(scope.$index, scope.row)">
                                    记录
                                </el-button>
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
                </el-tab-pane>
            </el-tabs>
        </div>


        <!--任务记录-->
        <el-dialog :title="taskTitle" :visible.sync="recordModelVisible" width="50%">
            <div>
                <el-steps :active="1">
                    <el-step title="记录1" description="任务记录详细信息"></el-step>
                    <el-step title="记录2" description="任务记录详细信息"></el-step>
                    <el-step title="记录3" description="任务记录详细信息"></el-step>
                </el-steps>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button @click="recordModelVisible = false">关 闭</el-button>
            </div>
        </el-dialog>
        <!--任务指派-->
        <el-dialog title="指派任务" :visible.sync="assignedModelVisible" width="50%">
            <div>
                <h3>请选择任务接收人</h3>
                <div></div>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button @click="assignedModelVisible = false">关 闭</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import util from '../common/util';
    import baseCaseJson from '../common/baseCaseJson'
    export default {
        name: 'caseInfo',
        data() {
            return {
                caseForm: {},
                caseTitle: '',
                taskTitle: '任务一的记录信息',
                recordModelVisible: false,
                assignedModelVisible: false,

                /******/


                tableData: [],
                modelVisible: false,
                form: {},
                importForm: {},
                file: {},
                importModelVisible: false,
                qys: [],
                ajlxs: [],
                ajzts:[],
                pcs: [],
                pageSize: 15,
                currentPage: 1,
                total: 0,
                baseCaseJson: baseCaseJson,
                searchInput: {},
                selectedDates: [],
                pickerOptions2: {
                    shortcuts: [{
                        text: '最近一周',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近一个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近三个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);
                        }
                    }]
                },
            }
        },
        created() {
            this.getCaseInfo();
            this.getDictData();
            this.getTaskData();
        },

        methods: {
            getCaseInfo(){
                let caseInfo = this.$route.params;
                this.caseTitle = caseInfo.ajmc;
                if(caseInfo.id){
                    this.$axios.get('/casebase/findAllInfoById?id=' + caseInfo.id).then(res => {
                        this.caseForm = Object.assign({}, res.data);
                    });
                }
                else {
                    this.$router.push({name:'cases'});
                }

            },
            getTaskData(){
                this.$axios.post('task/findAllByCondition', {page: this.currentPage, pagesize: this.pageSize}).then(res => {
                    this.tableData = res.data;
                    this.total = res.counts;
                })
            },

            getDictData(){
                this.$axios.post('/dict/findDictListByZddm', {zddm: 'D_SYS_AJQYDM', zxbz: 0}).then((res) => {
                    if(res.resCode == 200){
                        this.qys = res.data;
                    }else if(res.resCode == 100) {
                        this.$router.push('/login');
                    }
                });
                this.$axios.post('/dict/findDictListByZddm', {zddm: 'D_SYS_AJLXDM', zxbz: 0}).then((res) => {
                    if(res.resCode == 200){
                        this.ajlxs = res.data;
                    }else if(res.resCode == 100) {
                        this.$router.push('/login');
                    }
                });
                this.$axios.post('/dict/findDictListByZddm', {zddm: 'D_SYS_AJZTDM', zxbz: 0}).then((res) => {
                    if(res.resCode == 200){
                        this.ajzts = res.data;
                    }else if(res.resCode == 100) {
                        this.$router.push('/login');
                    }
                });
                this.$axios.post('/dict/findDictListByZddm',{zddm:'D_SYS_RWFSDM',zxbz:0}).then((res)=>{
                    if(res.resCode==200){
                        this.rwfss=res.data;
                    }else if(res.resCode==100){
                        this.$router.push('/login');
                    }
                });
                this.$axios.post('/dict/findDictListByZddm',{zddm:'D_SYS_RWZTDM',zxbz:0}).then((res)=>{
                    if(res.resCode==200){
                        this.rwzts=res.data;
                    }else if(res.resCode==100){
                        this.$router.push('/login');
                    }
                });
                this.$axios.post('/batch/findAll',{page:1,pagesize: 1000}).then((res) => {
                    if(res.resCode == 200){
                        this.pcs = res.data;
                    }
                });

            },


            backToCaseList(){
                this.$router.push({name:'cases'});
            },
            clearCondition() {
                this.selectedDates = [];
                this.searchInput = {};
                this.getData();
            },
            searchByCondition() {
                this.currentPage = 1;
                this.$axios.post('/casebase/findAllByCondition',Object.assign({}, {page: this.currentPage, pagesize: this.pageSize,begin: this.selectedDates[0], end: this.selectedDates[1]}, this.searchInput)).then(res => {
                    if(res.resCode == 200) {
                        this.tableData = res.data;
                        this.total = res.counts;
                    }
                })
            },
            handleTask(index, row) {
                //alert('指派任务')
                this.assignedModelVisible = true;
                
            },
            handleTaskRecord(index, row) {
                console.log(row);
                let rwid = row.id;
                this.$axios.post('feedback/findAllByRwid', {rwid:rwid,page: 1, pagesize: 10000}).then(res => {
                    console.log('任务记录:', res);
                    this.recordModelVisible = true;
                })
            },
            AjlxFormatter(row) {
                return util.dictParse(row.ajlx, this.ajlxs);
            },
            AjztFormatter(row) {
                return util.dictParse(row.ajzt, this.ajzts);
            },
            AjqyFormatter(row) {
                return util.dictParse(row.ajqy, this.qys);
            },
            RwztFormatter(row){
                return util.dictParse(row.rwzt,this.rwzts);
            },
            RwfsFormatter(row){
                return util.dictParse(row.rwfs,this.rwfss);
            },
            saveImport(){
                let param = new FormData();
                param.append('pcid', this.importForm.pcid);
                param.append('ajqy', this.importForm.ajqy);
                param.append('ajlx', this.importForm.ajlx);
                param.append('file', this.file);
                let config = {
                    headers:{'Content-Type':'multipart/form-data'}
                };
                this.$axios.post('/batch/importExcel', param, config).then(res => {
                    if(res.resCode == 200){
                        this.$message.success('案件导入成功!');
                        this.importModelVisible = false;
                    }
                });
            },
            handlerUpload(e) {
                this.file = e.target.files[0];
            },
            importCase() {
                this.importModelVisible = true;
                this.importForm = {}
            },
            closeClear() {
                this.$refs.importForm.resetFields()
                this.$refs.detailForm.resetFields()
            },

            search() {
                this.is_search = true;
            },
            add(){
                this.form = {
                };
                this.modelVisible = true;
            },

            handleOrder(index, row) {
                this.$router.push({name:'order', params: row});
            },
            handleDelete(index, row) {
                this.$confirm('此操作将注销本案件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.post('/user/delete', this.$qs.stringify({uuid: row.uuid})).then((res) => {
                        this.getData();
                        this.$message.success('已删除！');
                    });
                }).catch(() => {
                });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            // 保存编辑
            saveEdit() {
                let url = '/user/add';
                if(this.caseForm.uuid) {
                    url = '/user/update';
                }
                this.$axios.post(url ,this.$qs.stringify(Object.assign({}, this.form))).then((res) => {
                    this.$message.success(res.resMsg);
                    this.modelVisible = false;
                    this.getData();
                });
            },
            // 确定删除
            deleteRow(){
                this.tableData.splice(this.idx, 1);
                this.$message.success('删除成功');
                this.delVisible = false;
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

<style lang="scss">
    .case-info{
        position: relative;
        .return-btn{
            position: absolute;
            right:5px;
            top:5px;
            z-index: 1;
        }
    }

    #caseInfo{
        .el-form{
            min-height:400px;
            /*overflow: auto;*/
            .el-form-item{
                margin:0;
            }
        }
        .case-detail-form{
            .case-item{
                background: #f7f7f7;
                margin:0px 15px 4px 15px;
                .case-title {
                    color: #292929;
                    font-weight:bold;
                    border-left: 2px solid #0F639E;
                    background: #e4e4e4;
                    padding:5px;
                    font-size: 16px;
                    margin:0;
                }
                .line{
                    height: 1px;
                    border-bottom: dotted 1px #e4e4e4;
                }
            }
            .el-form-item{
                width: 33%;
                display: inline-block;

            }
        }
    }
</style>
