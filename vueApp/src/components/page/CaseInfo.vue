<template>
    <div id="caseInfoModule">
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
            <div class="return-btn">
                <el-button  type="success" @click="backToCaseList">返回案件列表</el-button>
                <el-button  type="success" @click="getTaskData();getCaseInfo();"><i class="el-icon-lx-refresh"></i> 刷新</el-button>
            </div>
            <el-tabs type="border-card">
                <el-tab-pane label="案件基本信息">
                    <div id="caseInfo">
                        <el-form class="case-detail-form" ref="caseForm" :model="caseForm" label-width="120px" label-position="right">
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
                                <el-button v-if="scope.row.rwzt >= 4"  size="mini" type="text" @click="handleCheck(scope.$index, scope.row)">
                                    审核
                                </el-button>
                                <el-button v-if="scope.row.rwzt <= 3" size="mini" type="text" @click="handleEdit(scope.$index, scope.row)">
                                    编辑
                                </el-button>
                                <el-button v-if="scope.row.rwzt <= 2"  size="mini" type="text" @click="handleTask(scope.$index, scope.row)">
                                    指派
                                </el-button>
                                <el-button v-if="scope.row.rwzt >= 3" size="mini" type="text" @click="handleTaskRecord(scope.$index, scope.row)">
                                    反馈情况
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
                <el-form-item label="截止时间" prop="rwjzsj">
                    <el-date-picker
                            v-model="taskForm.rwjzsj"
                            type="datetime">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="任务状态">
                    <el-select v-model="taskForm.rwzt" placeholder="不可修改">
                        <el-option
                                disabled readonly
                                v-for="item in rwztArr"
                                :key="item.ctdm"
                                :label="item.ctmc"
                                :value="item.ctdm">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="完成时间" prop="rwjzsj">
                    <el-input v-model="taskForm.rwwcsj" readonly disabled></el-input>
                </el-form-item>
                <div class="clearfix">
                    <template v-if="!taskForm.id">
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
                    <template v-else="">
                        <el-form-item label="任务接收人">
                            <el-input v-model="taskForm.rwzxrmc" readonly disabled></el-input>
                        </el-form-item>
                    </template>
                    <el-form-item label="任务审核人">
                        <el-input v-model="taskForm.shrxm" readonly disabled></el-input>
                    </el-form-item>
                </div>
                <el-form-item class="el-form-item-1" label="任务描述">
                    <el-input resize="none" type="textarea" v-model="taskForm.rwms"></el-input>
                </el-form-item>
                <el-form-item class="el-form-item-1" label="审核备注">
                    <el-input resize="none" type="textarea" readonly v-model="taskForm.shbz"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="taskModelVisible = false">取 消</el-button>
                <el-button @click="saveTask" type="primary">保 存</el-button>
            </div>
        </el-dialog>

        <!--反馈记录-->
        <el-dialog id="feedbackContent" :title="taskTitle+` - 反馈信息`" :visible.sync="recordModelVisible" width="70%">
            <div class="clearfix content">
                <div class="item">
                    <span class="pre">处理人员:</span>
                    <span class="val">{{recordForm.fkrxm}}</span>
                </div>
                <div class="item">
                    <span class="pre">任务状态:</span>
                    <span class="val">{{recordForm.rwzt}}</span>
                </div>
                <div class="item">
                    <span class="pre">反馈时间:</span>
                    <span class="val">{{recordForm.fksj}}</span>
                </div>
                <p class="item item-all">
                    <span class="pre">反馈内容:</span>
                    <span class="val">{{recordForm.fknr}}</span>
                </p>
                <div class="item item-all">
                    <div class="pre">图像资料:</div>
                    <div class="record-file">
                        <template v-for="item in recordFile.photo">
                            <img :src="baseUrl + item" alt="">
                        </template>
                    </div>
                </div>
                <div class="item item-all">
                    <div class="pre">音频资料:</div>
                    <div class="record-file">
                        <template v-for="item2 in recordFile.audio">
                            <audio controls="controls" :src="baseUrl + item2">
                                您的浏览器不支持 audio。
                            </audio>
                        </template>
                    </div>
                </div>
                <div class="item item-all">
                    <div class="pre">视频资料:</div>
                    <div class="record-file">
                        <template v-for="item3 in recordFile.video">
                            <video width="150" :src="baseUrl + item3" controls="controls"></video>
                        </template>
                    </div>
                </div>
            </div>
            <div slot="footer" class="dialog-footer">
                <!--<el-button type="primary"  @click="recordModelVisible = false;">立即审核</el-button>-->
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

        <!--任务审核-->
        <el-dialog class="task-form"  title="审核任务" :visible.sync="checkModelVisible" width="40%">
            <el-form  label-width="100px" >
                <el-form-item class="el-form-item-1" label="审核状态">
                    <el-radio-group v-model="checkForm.rwzt">
                        <el-radio label="5">审核不通过</el-radio>
                        <el-radio label="6">审核通过</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item class="el-form-item-1" label="审核备注">
                    <el-input resize="none" type="textarea" v-model="checkForm.shbz"></el-input>
                </el-form-item>
                <el-form-item label="实际佣金">
                    <el-input-number  v-model="checkForm.sjyj"></el-input-number>
                </el-form-item>
                <el-form-item label="任务佣金">
                    <el-input readonly  v-model="checkForm.rwyj"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="checkModelVisible = false">取 消</el-button>
                <el-button @click="checkTask" type="primary">保存</el-button>
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
                baseUrl: '',
                taskPersonId: '',
                ywyArr: [], //业务员
                rwfsArr: [], //任务方式
                rwztArr: [], //任务状态
                caseInfo: {},
                caseForm: {},
                taskForm: {},
                checkForm: {},
                recordForm: {},
                recordFile: {},
                caseTitle: '',
                taskTitle: '',
                recordModelVisible: false,
                assignedModelVisible: false,
                taskModelVisible: false,
                checkModelVisible: false,
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
            this.baseUrl = this.$baseURL;
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
                this.$axios.post('casebase/findAllTaskByAjidAndRwzt', {ajid:this.caseInfo.id,page: this.currentPage, pagesize: this.pageSize}).then(res => {
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

            //审核
            handleCheck(index, row){
                console.log(row);
                this.checkModelVisible = true;
                this.checkForm = Object.assign({}, row);
                this.checkForm.sjyj = this.checkForm.rwyj;
                this.checkForm.rwzt = String(this.checkForm.rwzt);
            },
            handleEdit(index, row){
                // console.log(row);
                this.taskModelVisible = true;
                this.taskForm = Object.assign({}, row);
                this.taskForm.isAssign = true;

            },
            handleTask(index, row) {
                this.taskId = row.id;
                this.assignedModelVisible = true;
            },
            handleTaskRecord(index, row) {
                console.log(row)
                this.taskTitle = row.rwmc
                let rwid = row.id;
                let rwzt = row.rwzt;
                this.$axios.post('feedback/findAllByRwid?rwid='+rwid).then(res => {
                    console.log('任务记录:', res);
                    if(res.data.id){
                        this.recordForm = res.data;
                        this.recordFile = res.data.fkfj;
                        this.recordForm.rwzt = util.dictParse(rwzt, this.rwztArr);
                        this.recordModelVisible = true;
                    }
                });
            },
            //指派保存
            assignTask(){
                this.$axios.post('task/appoint', {id: this.taskId,ywyid:this.taskPersonId}).then(res => {
                    this.assignedModelVisible = false;
                    this.$message.success('指派成功!');
                    this.getTaskData();
                })
            },
            //审核保存
            checkTask(){
                this.$axios.post('task/check', this.checkForm).then(res => {
                    if(res.resCode == 200){
                        this.checkModelVisible = false;
                        this.$message.success('审核完成!');
                        this.getTaskData();
                    }
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
                let url = 'task/add';
                param.ajid = this.caseInfo.id;
                if(param.id){
                    url = 'task/update';
                }
                this.$axios.post(url, param).then(res => {
                    this.taskModelVisible = false;
                    this.getTaskData();
                })
            },
            rwztFormatter(row) {
                return util.dictParse(row.rwzt, this.rwztArr);
            },
            rwfsFormatter(row) {
                return util.dictParse(row.rwfs, this.rwfsArr);
            }
        }
    }

</script>

<style scoped lang="scss">
    #caseInfoModule{
        .task-form{
            .el-form-item{
                float:left;
                width: 50%;
            }
            .el-form-item-1{
                width: 100% !important;
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
                max-height:700px;
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
        #feedbackContent{
            .content{
                max-height: 500px;
                overflow: auto;
                clear: both;
            }
            .el-dialog{
                .el-dialog__body{
                    padding:0 20px!important;
                }
                .item{
                    width:33%;
                    float: left;
                    margin-bottom: 10px;
                    .pre{

                    }
                    .val{
                        color: #292929;
                    }
                }
                .item-all{
                    width:100%;
                }
                .record-file{
                    img{
                        max-height:150px;
                        width:150px;
                        display: inline-block;
                        margin:2px;
                    }
                    video{
                        margin:2px;
                    }
                }
            }


        }
    }

</style>
