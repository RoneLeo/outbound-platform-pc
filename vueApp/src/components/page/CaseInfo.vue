<template>
    <div>
        <div class="container">
            <div class="case-desc clearfix">
                <div class="left">
                    <span class="pre">案件名称:</span>
                    <span class="val">{{caseInfo.ajmc}}</span>，
                </div>
                <div class="left">
                    <span class="pre">案件状态:</span>
                    <span class="val">{{caseInfo.ajztmc}}</span>，
                </div>
                <div class="left">
                    <span class="pre">案件类型:</span>
                    <span class="val">{{caseInfo.ajlxmc}}</span>，
                </div>
                <div class="left">
                    <span class="pre">案件区域:</span>
                    <span class="val">{{caseInfo.ajqymc}}</span>，
                </div>
                <div class="left">
                    <span class="pre">总佣金:</span>
                    <span class="val">{{caseInfo.ajyj}}</span>
                </div>
            </div>
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
                <el-tab-pane :label="`案件任务信息(`+total+`)`">
                    <div>
                        <el-button size="mini" type="success" @click="taskModelVisible=true;taskForm={}">添加任务</el-button>
                    </div>
                    <el-table :data="tableData" class="table" ref="multipleTable">
                        <el-table-column prop="rwmc" label="任务名称"></el-table-column>
                        <el-table-column prop="rwzt" label="任务状态" :formatter="rwztFormatter"></el-table-column>
                        <el-table-column prop="rwfs" label="任务方式" :formatter="rwfsFormatter"></el-table-column>
                        <el-table-column prop="rwzxrmc" label="任务执行人"></el-table-column>
                        <el-table-column prop="rwyj" label="任务佣金"></el-table-column>
                        <el-table-column prop="sjyj" label="实际佣金"></el-table-column>
                        <el-table-column prop="rwjzsj" label="任务截止时间"></el-table-column>
                        <el-table-column prop="rwwcsj" label="任务完成时间"></el-table-column>
                        <el-table-column prop="rwms" label="任务描述"></el-table-column>
                        <el-table-column label="操作" width="150">
                            <template slot-scope="scope">
                                <el-button size="mini" type="text" @click="handleTask(scope.$index, scope.row)">
                                    任务指派
                                </el-button>
                                <el-button size="mini" type="text" @click="handleTaskRecord(scope.$index, scope.row)">
                                    反馈记录
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
        <el-dialog class="assign-task" title="指派任务" :visible.sync="assignedModelVisible" width="30%">
            <el-form  label-width="120px" >
                <el-form-item style="width:100%;" label="任务接收人员">
                    <el-select v-model="taskPersonId" placeholder="请选择">
                        <el-option
                                v-for="item in ywyArr"
                                :key="item.id"
                                :label="item.mz"
                                :value="item.id" >
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="assignedModelVisible = false">取 消</el-button>
                <el-button @click="assignTask" type="primary">指 派</el-button>
            </div>
        </el-dialog>

        <!--任务添加,编辑-->
        <el-dialog  title="任务信息" :visible.sync="taskModelVisible" width="60%">
            <el-form class="task-form clearfix" ref="taskForm" :model="taskForm" label-width="120px" >
                <el-form-item label="任务名称" prop="rwmc"
                              :rules="[{ required: true, message: '任务名称不能为空', trigger: 'blur' }]">
                    <el-input v-model="taskForm.rwmc"></el-input>
                </el-form-item>
                <el-form-item label="任务佣金" prop="rwyj">
                    <el-input v-model="taskForm.rwyj"></el-input>
                </el-form-item>

                <el-form-item label="任务方式">
                    <el-select v-model="taskForm.rwfs" placeholder="请选择">
                        <el-option
                                v-for="item in rwfsArr"
                                :key="item.ctdm"
                                :label="item.ctmc"
                                :value="item.ctdm">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="任务状态">
                    <el-select v-model="taskForm.rwzt" placeholder="请选择">
                        <el-option
                                v-for="item in rwztArr"
                                :key="item.ctdm"
                                :label="item.ctmc"
                                :value="item.ctdm">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="截止时间" prop="rwjzsj">
                    <el-date-picker
                            v-model="taskForm.rwjzsj"
                            type="datetime">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="完成时间" prop="rwjzsj">
                    <el-input v-model="taskForm.rwwcsj" readonly></el-input>
                </el-form-item>
                <el-form-item class="el-form-item-1" label="任务描述">
                    <el-input type="textarea" v-model="taskForm.rwms"></el-input>
                </el-form-item>

                <div class="clearfix">
                    <el-form-item label="立即指派">
                        <el-switch v-model="taskForm.isAssign"></el-switch>
                    </el-form-item>
                    <template v-if="taskForm.isAssign">
                        <el-form-item label="任务接收人">
                            <el-select v-model="taskForm.rwzxr" placeholder="请选择">
                                <el-option
                                        v-for="item in ywyArr"
                                        :key="item.id"
                                        :label="item.mz"
                                        :value="item.id" >
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </template>

                </div>
                <el-form-item class="el-form-item-1" label="审核备注">
                    <el-input type="textarea" readonly v-model="taskForm.shbz"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="taskModelVisible = false">取 消</el-button>
                <el-button @click="saveTask" type="primary">保 存</el-button>
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
                taskPersonId: '',
                ywyArr: [], //业务员
                rwfsArr: [], //任务方式
                rwztArr: [], //任务状态
                caseInfo: {},
                caseForm: {},
                taskForm: {},
                caseTitle: '',
                taskTitle: '任务一的记录信息',
                recordModelVisible: false,
                assignedModelVisible: false,
                taskModelVisible: false,
                ajlxs: [],
                ajzts:[],
                ajqys: [],
                taskId:'',
                tableData: [],
                pageSize: 15,
                currentPage: 1,
                total: 0,
                baseCaseJson: baseCaseJson,
            }
        },
        created() {
            this.getCaseInfo();
            this.getDictData();
            this.getTaskData();
            this.getPerson();
        },

        methods: {
            //获取业务员信息
            getPerson(){
                this.$axios.get('user/fingByAreacodeAndName').then(res => {
                    this.ywyArr = res.data;
                })
            },
            getCaseInfo(){
                this.caseInfo = this.$route.params;
                this.caseInfo.ajqymc = util.dictParse(this.caseInfo.ajqy, this.ajqys);
                if(this.caseInfo.id){
                    this.$axios.get('/casebase/findAllInfoById?id=' + this.caseInfo.id).then(res => {
                        this.caseForm = Object.assign({}, res.data);
                    });
                }
                else {
                    this.$router.push({name:'cases'});
                }

            },
            getTaskData(){
                this.$axios.post('casebase/findAllTaskByAjid', {ajid:this.caseInfo.id,page: this.currentPage, pagesize: this.pageSize}).then(res => {
                    this.tableData = res.data;
                    this.total = res.counts;
                })
            },
            getDictData(){
                this.$axios.post('/dict/findDictListByZddm', {zddm: 'D_SYS_AJQYDM'}).then((res) => {
                    this.ajqys = res.data;
                    this.caseInfo.ajqymc = util.dictParse(this.caseInfo.ajqy, this.ajqys);
                });
                this.$axios.post('/dict/findDictListByZddm', {zddm: 'D_SYS_AJLXDM'}).then((res) => {
                    this.ajlxs = res.data;
                    this.caseInfo.ajlxmc = util.dictParse(this.caseInfo.ajlx, this.ajlxs);
                });
                this.$axios.post('/dict/findDictListByZddm', {zddm: 'D_SYS_AJZTDM'}).then((res) => {
                    this.ajzts = res.data;
                    this.caseInfo.ajztmc = util.dictParse(this.caseInfo.ajzt, this.ajzts);
                });
                //任务方式
                this.$axios.post('/dict/findDictListByZddm', {zddm: 'D_SYS_RWFSDM'}).then((res) => {
                    this.rwfsArr = res.data;
                });
                //任务状态
                this.$axios.post('/dict/findDictListByZddm', {zddm: 'D_SYS_RWZTDM'}).then((res) => {
                    this.rwztArr = res.data;
                });

            },

            backToCaseList(){
                this.$router.push({name:'cases'});
            },
            assignTask(){
                this.$axios.post('task/appoint', {id: this.taskId,ywyid:this.taskPersonId}).then(res => {
                    this.assignedModelVisible = false;
                    this.$message.success('指派成功!');
                    this.getTaskData();
                })
            },
            handleTask(index, row) {
                this.taskId = row.id;
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
            handleSizeChange(val) {
                this.pageSize = val;
                this.getData();
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.getData()
            },
            saveTask(){
                let param = this.taskForm;
                param.ajid = this.caseInfo.id;
                this.$axios.post('task/add', param).then(res => {
                    this.taskModelVisible = false;
                    this.getTaskData();
                })
            },
            rwztFormatter(row) {
                return util.dictParse(row.rwzt, this.rwztArr);
            },
            rwfsFormatter(row) {
                return util.dictParse(row.rwfs, this.rwfsArr);
            },



            //************
            // 保存编辑
            saveEdit() {
                let url = '/user/add';
                if(this.caseForm.uuid) {
                    url = '/user/update';
                }
                this.$axios.post(url ,this.$qs.stringify(Object.assign({}, this.form))).then((res) => {
                    this.$message.success(res.resMsg);
                    this.getData();
                });
            },
            // 确定删除
            deleteRow(){
                this.tableData.splice(this.idx, 1);
                this.$message.success('删除成功');
                this.delVisible = false;
            },

        }
    }

</script>

<style scoped lang="scss">
    .task-form{
        .el-form-item{
            float:left;
            width: 50%;
        }
        .el-form-item-1{
            width: 100%;
        }
    }
    .case-desc{
        padding: 5px 0;
        .left{
            margin-right: 15px;
            color: #454545;
            .val{
                color: #00ab00;
                font-weight: bold;
            }
        }
    }
    .assign-task{
        .el-select{
            width: 100%;
            display: inline-block;
        }
    }

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
