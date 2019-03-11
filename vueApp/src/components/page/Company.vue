<template>
    <div class="table">
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-cascades"></i> 公司信息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container" >
            <div style="text-align: right;max-width: 95%;margin-bottom: 10px">
                <el-tooltip v-show="!newCompanyBoxShow" class="item" effect="light" content="添加分公司" placement="bottom">
                    <el-button type="primary" icon="el-icon-plus" @click="addNewCompany" circle style="box-shadow: 1px 2px 2px 1px #ccc"></el-button>
                </el-tooltip>
                <el-tooltip v-show="newCompanyBoxShow" class="item" effect="light" content="返回主页面" placement="bottom">
                    <el-button type="primary" icon="el-icon-back" @click="goBack" circle style="box-shadow: 1px 2px 2px 1px #ccc;margin-right: 5px;"></el-button>
                </el-tooltip>
                <el-tooltip v-show="newCompanyBoxShow" class="item" effect="light" content="保存分公司信息" placement="bottom">
                    <el-button type="success" icon="el-icon-check" @click="saveNewCompany" circle style="box-shadow: 1px 2px 2px 1px #ccc"></el-button>
                </el-tooltip>
            </div>
            <div style="display: flex;justify-content: center;">
                <div v-show="!newCompanyBoxShow" class="company-box"
                     style="min-width: 900px; max-width: 95%; height: auto;padding: 20px 0 50px;">
                    <el-form ref="companyForm" :model="companyForm" label-width="120px" size="medium">
                        <el-form-item label="公司名称" style="display: block;width: 98%">
                            <el-input v-model="companyForm.gsmc" :disabled="disabled" placeholder="（必填项）"> </el-input>
                        </el-form-item>
                        <el-form-item label="经纬度">
                            <el-input v-model="companyForm.jwd" :disabled="true" placeholder="（必填项）"></el-input>
                        </el-form-item>
                        <el-form-item label="联系地址">
                            <el-input v-model="companyForm.lxdz" :disabled="disabled" placeholder="（必填项）"></el-input>
                        </el-form-item>
                        <el-form-item label="" style="display: block;width: 98%">
                            <div id="AMap_container" style="width: 100%; height: 400px;"></div>
                        </el-form-item>
                        <el-form-item label="联系电话">
                            <el-input v-model="companyForm.lxdh" :disabled="disabled" placeholder="（必填项）"></el-input>
                        </el-form-item>
                        <el-form-item label="联系传真">
                            <el-input v-model="companyForm.lxcz" :disabled="disabled" placeholder="（必填项）"></el-input>
                        </el-form-item>
                        <el-form-item label="联系邮编">
                            <el-input v-model="companyForm.lxyb" :disabled="disabled" placeholder="（必填项）"></el-input>
                        </el-form-item>
                        <el-form-item label="联系邮箱">
                            <el-input v-model="companyForm.lxyx" :disabled="disabled" placeholder="（必填项）"></el-input>
                        </el-form-item>
                        <el-form-item label="公司网站">
                            <el-input v-model="companyForm.gswz" :disabled="disabled"></el-input>
                        </el-form-item>
                        <el-form-item label="备案信息">
                            <el-input v-model="companyForm.baxx" :disabled="disabled"></el-input>
                        </el-form-item>
                        <el-form-item label="公司描述">
                            <el-input v-model="companyForm.gsms" :disabled="disabled"></el-input>
                        </el-form-item>
                        <el-form-item label="搜索关键字">
                            <el-input v-model="companyForm.gjz" :disabled="disabled"></el-input>
                        </el-form-item>
                        <el-form-item label="公司简介" style="display: block;width: 98%">
                            <el-input v-model="companyForm.gsjj" type="textarea" :autosize="{ minRows: 15, maxRows: 30}"
                                      :disabled="disabled" s></el-input>
                        </el-form-item>
                    </el-form>
                    <div style="display: flex;justify-content: center;">
                        <template v-if="companyForm.id">
                            <el-button size="medium" type="primary" icon="el-icon-lx-edit" style="margin-right: 20px"
                                       :disabled="!disabled"
                                       @click="edit">编辑
                            </el-button>
                            <el-button size="medium" type="success" icon="el-icon-check" @click="save(companyForm, 1)" :disabled="disabled">
                                保存
                            </el-button>
                        </template>
                        <template v-else="">
                            <el-button size="medium" type="success" icon="el-icon-check" @click="save(companyForm, 1)" :disabled="disabled">
                                保存
                            </el-button>
                        </template>
                    </div>
                    <div class="company-list" style="margin-top: 80px">
                        <!--<h3>子公司信息</h3>-->
                            <div class="company-list-item" v-for="item in subcompanyForm" :key="item.id*0.989870023" style="">
                                <div style="position: absolute;top: 8px;right: 14px;">
                                    <el-button-group>
                                        <el-tooltip class="item" effect="light" content="编辑分公司信息" placement="bottom" >
                                            <el-button type="warning" icon="el-icon-edit" @click="editNewCompany(item)" round style="box-shadow: 1px 2px 2px 1px #ccc;"></el-button>
                                        </el-tooltip>
                                        <el-tooltip class="item" effect="light" content="删除分公司" placement="bottom" >
                                            <el-button type="danger" icon="el-icon-delete" @click="deleteNewCompany(item)" round style="box-shadow: 1px 2px 2px 1px #ccc;"></el-button>
                                        </el-tooltip>
                                    </el-button-group>
                                </div>
                                <div class="subtitle" style="">{{item.gsmc}}</div>
                                <el-form :model="item" label-width="120px" size="medium">
                                    <el-form-item label="联系电话">
                                        {{item.lxdh}}
                                    </el-form-item>
                                    <el-form-item label="联系邮箱">
                                        {{item.lxyx}}
                                    </el-form-item>
                                    <el-form-item label="联系地址" style="width: 98%">
                                        {{item.lxdz}}
                                    </el-form-item>
                                </el-form>
                            </div>
                    </div>
                </div>
                <div v-show="newCompanyBoxShow" class="subcompany-box"
                     style="min-width: 900px; max-width: 90%; height: auto;padding: 20px 0 50px;">
                    <el-form ref="newCompanyForm" :model="newCompanyData" label-width="120px" size="medium">
                        <el-form-item label="公司名称" style="display: block;width: 98%">
                            <el-input v-model="newCompanyData.gsmc" :disabled="disabled2" placeholder="（必填项）"></el-input>
                        </el-form-item>
                        <el-form-item label="联系电话">
                            <el-input v-model="newCompanyData.lxdh" :disabled="disabled2" placeholder="（必填项）"></el-input>
                        </el-form-item>
                        <el-form-item label="联系传真">
                            <el-input v-model="newCompanyData.lxcz" :disabled="disabled2" placeholder="（必填项）"></el-input>
                        </el-form-item>
                        <el-form-item label="联系邮编">
                            <el-input v-model="newCompanyData.lxyb" :disabled="disabled2" placeholder="（必填项）"></el-input>
                        </el-form-item>
                        <el-form-item label="联系邮箱">
                            <el-input v-model="newCompanyData.lxyx" :disabled="disabled2" placeholder="（必填项）"></el-input>
                        </el-form-item>
                        <el-form-item label="经纬度">
                            <el-input v-model="newCompanyData.jwd" :disabled="true" placeholder="（必填项）"></el-input>
                        </el-form-item>
                        <el-form-item label="联系地址">
                            <el-input v-model="newCompanyData.lxdz" :disabled="disabled2" placeholder="（必填项）"></el-input>
                        </el-form-item>
                        <el-form-item label="" style="display: block;width: 98%">
                            <div id="map2" style="width: 100%; height: 400px;"></div>
                        </el-form-item>
                    </el-form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'basetable',
        data() {
            return {
                companyForm: {},
                subcompanyForm: [],
                disabled: false,
                map: {},
                positionPicker: {},
                marker: {},
                map2: {},
                positionPicker2: {},
                marker2: {},
                gsmc: '',
                newCompanyBoxShow: false,
                newCompanyData: {},
                disabled2: false
            }
        },
        mounted() {
            this.$nextTick(() => {
                this.initMap();
                window.setTimeout(() => {
                    this.getData();
                }, 300)
            })
        },
        created() {
            this.$axios.get('/company/findGsmcByGsid').then((res) => {
                this.gsmc = res.data;
            });
        },
        computed: {},
        destoryed() {
            this.map && this.map.remove(this.marker);
            this.map && this.map.destory();
        },
        methods: {
            goBack() {
                this.newCompanyBoxShow = false;
                this.newCompanyData = {}
            },
            deleteNewCompany(item) {
                this.$axios.get('/contactUs/delete?id=' + item.id).then(res => {
                    for(let i = 0; i < this.subcompanyForm.length; i ++) {
                        if(this.subcompanyForm[i].id === item.id) {
                            this.subcompanyForm.splice(i,1);
                        }
                    }
                    this.$message({
                        message: res.resMsg,
                        type: 'success'
                    });
                })
            },
            editNewCompany(item) {
                this.newCompanyBoxShow = true;
                this.newCompanyData = item;
                let position = item.jwd && item.jwd.split(',');
                this.positionPicker2.start(new AMap.LngLat(position[1], position[0]));
            },
            saveNewCompany() {
                this.newCompanyData = Object.assign({}, this.newCompanyData);
                this.save(this.newCompanyData, 2);
            },
            addNewCompany() {
                this.newCompanyData = {
                    jwd: '',
                    lxdz: ''
                }
                this.newCompanyBoxShow = true;
                this.positionPicker2.start(this.map2.getCenter());
            },
            initMap() {
                this.map = new AMap.Map('AMap_container', {
                    zoom: 16,
                })
                this.map2 = new AMap.Map('map2', {
                    zoom: 16,
                })
                let UIloaded = true;
                if (typeof AMapUI == 'undefined') {
                    UIloaded = false;
                    this.$axios.get('http://webapi.amap.com/ui/1.0/main.js?v=1.0.11').then(res => {
                        if (typeof AMapUI != 'undefined') {
                            UIloaded = true;
                        } else {
                            this.$message.error('无法加载地图');
                        }
                    })
                }
                if (UIloaded) {
                    AMapUI.loadUI(['misc/PositionPicker'], (PositionPicker) => {
                        this.positionPicker = new PositionPicker({
                            mode: 'dragMap',//设定为拖拽地图模式，可选'dragMap'、'dragMarker'，默认为'dragMap'
                            map: this.map//依赖地图对象
                        });
                        this.positionPicker.on('success', (positionResult) => {
                            this.companyForm.jwd = positionResult.position.lat + ',' + positionResult.position.lng;
                            this.companyForm.lxdz = positionResult.address;
                        });
                        this.positionPicker2 = new PositionPicker({
                            mode: 'dragMap',//设定为拖拽地图模式，可选'dragMap'、'dragMarker'，默认为'dragMap'
                            map: this.map2//依赖地图对象
                        });
                        this.positionPicker2.on('success', (positionResult) => {
                            this.newCompanyData.jwd = positionResult.position.lat + ',' + positionResult.position.lng;
                            this.newCompanyData.lxdz = positionResult.address;
                        });
                    });
                }
            },
            setMarker() {
                let positions = this.companyForm.jwd.split(',');
                let position = new AMap.LngLat(positions[1], positions[0]);
                if (this.map) {
                    this.map.setCenter(position);
                    this.marker = new AMap.Marker({
                        icon: "//vdata.amap.com/icons/b18/1/2.png",
                        position: position,
                        offset: new AMap.Pixel(-12, -12)
                    });
                    this.marker.setMap(this.map);
                }
            },
            edit() {
                this.disabled = false;
                this.map.remove(this.marker);
                let positions = this.companyForm.jwd.split(',');
                let position = new AMap.LngLat(positions[1], positions[0]);
                this.positionPicker.start(position)
            },
            save(data, gslx) {
                let url = '/contactUs/add';
                if(data.id) {
                    url = '/contactUs/update'
                }
                this.$axios.post(url, this.$qs.stringify(Object.assign({}, data, {gslx: gslx}))).then((res) => {
                    if(res.resCode === 200) {
                        if(gslx === 1) {
                            this.disabled = true;
                            this.positionPicker.stop();
                            this.setMarker();
                        }
                        if(gslx === 2) {
                            this.newCompanyBoxShow = false;
                            this.positionPicker2.stop();
                            this.getData();
                        }
                        this.$message({
                            message: res.resMsg,
                            type: 'success'
                        });
                    }else {
                        this.$message({
                            message: res.resMsg,
                            type: 'error'
                        });
                    }
                });
            },
            getData() {
                this.subcompanyForm = [];
                this.$axios.post('/contactUs/findByGsid').then((res) => {
                     let data = res.data;
                     for(let i = 0; i < data.length; i ++) {
                         if(data[i].gslx === 1) {  //总公司
                             this.companyForm = data[i];
                         }else {
                             this.subcompanyForm.push(data[i]);
                         }
                     }
                    if (this.companyForm !== null) {
                        this.disabled = true;
                        this.setMarker();
                    }else {
                        this.companyForm = {
                            gsmc: this.gsmc,
                            lxdh: '',
                            lxdz:'',
                            lxcz: '',
                            gswz: '',
                            lxyb: '',
                            lxyx: '',
                        }
                        this.positionPicker.start(this.map.getCenter());
                    }

                });
            },
        }
    }

</script>
<style>
    .amap-icon img, .amap-marker-content img {
        width: 24px;
        height: 24px;
    }

    .company-box .el-form-item, .company-list .el-form-item{
        width: 49%;
        display: inline-block;
    }

    .subcompany-box .el-form-item {
        width: 49%;
        display: inline-block;
    }
    .company-list .el-form-item {
        margin-bottom: 5px;
    }

    .company-box .el-form-item--mini.el-form-item, .company-box .el-form-item--small.el-form-item {
        margin-bottom: 22px;
    }

    .company-box .el-input.is-disabled .el-input__inner {
        background-color: #fff;
        color: #303133;
    }

    .company-box .el-textarea.is-disabled .el-textarea__inner {
        background-color: #fff;
        color: #303133;
    }
</style>

<style scoped>
    /*.company-list:hover ul>li {*/

    /*}*/
    /*.company-list:hover .company-list-item .subtitle{*/

    /*}*/
    .company-list-item {
        padding-top: 10px;
        padding-bottom: 20px;
        /*margin-top: 20px;*/
        margin-bottom: 30px;
        border: 1px solid #dedede;
        border-radius: 5px;
        position: relative;
        box-shadow: 1px 1px 1px 1px rgba(0, 96, 144, .2);
    }
    .company-list .company-list-item .subtitle {
        line-height: 40px;
        font-size: 16px;
        color: #2E3F59;
        padding-left: 50px;
        cursor: pointer;
        font-weight: 500;
        letter-spacing: 1px;
    }
    .company-list .company-list-item .subtitle:hover {

    }
    .company-list .company-list-item form:hover {
        transform: translateY(8px);
    }
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
