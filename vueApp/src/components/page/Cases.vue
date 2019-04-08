<template>
    <div>
        <div class="container">
            <el-form :inline="true" :model="searchInput" class="demo-form-inline" size="mini" label-width="90px" >
                <el-form-item label="模板批次">
                    <el-select v-model="searchInput.pcid" placeholder="请选择">
                        <el-option
                                v-for="item in pcs"
                                :key="item.id"
                                :label="item.pcmc"
                                :value="item.pcid" >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="案件名称">
                    <el-input v-model="searchInput.ajmc" placeholder="输入案件名称" clearable></el-input>
                </el-form-item>
                <el-form-item label="案件类型">
                    <el-select v-model="searchInput.ajlx" placeholder="请选择">
                        <el-option
                                v-for="item in ajlxs"
                                :key="item.ctdm"
                                :label="item.ctmc"
                                :value="item.ctdm">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="案件状态">
                    <el-select v-model="searchInput.ajzt" placeholder="请选择">
                        <el-option
                                v-for="item in ajzts"
                                :key="item.ctdm"
                                :label="item.ctmc"
                                :value="item.ctdm" >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="案件区域">
                    <el-select v-model="searchInput.ajqy" placeholder="请选择">
                        <el-option
                                v-for="item in qys"
                                :key="item.ctdm"
                                :label="item.ctmc"
                                :value="item.ctdm" >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="查询时间段">
                    <el-date-picker
                            v-model="selectedDates"
                            type="daterange"
                            align="right"
                            unlink-panels
                            range-separator="至"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期"
                            value-format="yyyy-MM-dd"
                            :picker-options="pickerOptions2">
                    </el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="clearCondition">清空条件</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="searchByCondition">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="success" @click="importCase">导入案件</el-button>
                </el-form-item>
            </el-form>
            <!--<el-button size="mini" type="primary" @click="add">添加案件</el-button>-->
        </div>
        <div class="container">
            <el-table :data="tableData" class="table" ref="multipleTable">
                <el-table-column prop="ajmc" label="案件名称"></el-table-column>
                <el-table-column prop="ajlx" label="案件类型" :formatter="AjlxFormatter"></el-table-column>
                <el-table-column prop="ajzt" label="案件状态" :formatter="AjztFormatter"></el-table-column>
                <el-table-column prop="ajqy" label="案件区域" :formatter="AjqyFormatter"></el-table-column>
                <el-table-column prop="ajyj" label="案件佣金"></el-table-column>
                <el-table-column prop="pcid" label="模板批次ID"></el-table-column>
                <el-table-column prop="gaxlh" label="个案序列号"></el-table-column>
                <el-table-column prop="pch" label="批次号"></el-table-column>
                <el-table-column prop="drsj" label="导入时间" width="150"></el-table-column>
                <el-table-column label="操作" width="180">
                    <template slot-scope="scope">
                        <el-button type="text" @click="handleCaseInfo(scope.$index, scope.row)">
                            <i class="el-icon-lx-edit"></i>
                            案件详情
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
        </div>

        <!--案件导入弹框-->
        <el-dialog title="导入案件" :visible.sync="importModelVisible" width="50%"
                   :close-on-click-modal="false" @closed="closeClear">
            <el-form class="clearfix" ref="importForm" :model="importForm" label-width="100px">
                <el-form-item label="选择批次">
                    <el-select v-model="importForm.pcid" placeholder="请选择">
                        <el-option
                                v-for="item in pcs"
                                :key="item.id"
                                :label="item.pcmc"
                                :value="item.pcid" >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="案件区域">
                    <el-select v-model="importForm.ajqy" placeholder="请选择">
                        <el-option
                                v-for="item in qys"
                                :key="item.ctdm"
                                :label="item.ctmc"
                                :value="item.ctdm" >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="案件类型">
                    <el-select v-model="importForm.ajlx" placeholder="请选择">
                        <el-option
                                v-for="item in ajlxs"
                                :key="item.ctdm"
                                :label="item.ctmc"
                                :value="item.ctdm" >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="文件">
                    <!--<input @change="fileImage" type="file" accept="image/jpeg,image/x-png,image/gif" id="" value="" />-->
                    <input type="file" id="myFile" class="inputfile" @change="handlerUpload"></input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="importModelVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveImport">导 入</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import util from '../common/util';
    import baseCaseJson from '../common/baseCaseJson'
    export default {
        name: 'case',
        data() {
            return {
                caseTitle: '',
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
            this.getDictData();
            this.getData();
        },

        methods: {
            getData() {
                this.$axios.post('casebase/findAllByCondition', {page: this.currentPage, pagesize: this.pageSize}).then(res => {
                    if(res.resCode == 200) {
                        this.tableData = res.data;
                        this.total = res.counts;
                    }
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
                this.$axios.post('/batch/findAll',{page:1,pagesize: 1000}).then((res) => {
                    if(res.resCode == 200){
                        this.pcs = res.data;
                    }
                });
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
            handleCaseInfo(index, row) {
                this.$router.push({name:'caseInfo', params: row});
                this.$axios.get('/casebase/findAllInfoById?id=' + row.id).then(res => {
                    if(res.resCode == 200) {
                        this.form = Object.assign({}, res.data);
                        this.modelVisible = true;
                        this.caseTitle = '案件: ' + res.data.ajmc;
                    }
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
                this.$router.push({name:'caseInfo', params: row});
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
                if(this.form.uuid) {
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