<template>
    <div class="table">
        <div class="container">
            <div >
                <el-row style="border-bottom: 1px dashed #0095FF;margin-bottom: 10px;padding: 8px 0;">
                    <el-col  :span="22" style="height: 100%;">
                        <el-form :inline="true" :model="form" ref="form" class="demo-form-inline"  label-position="right">
                            <el-form-item label-width="80px" label="产品类型"
                                            prop="sblx"
                                          :rules="[{ required: true, message: '产品类型不能为空', trigger: 'blur' }]">
                                <el-select v-model="form.sblx" placeholder="请选择产品类型">
                                    <el-option v-for="item in this.equipmentType" :key="item.id*2.0987" :label="item.name"
                                               :value="item.id"></el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label-width="150px" label="产品名称"
                                            prop="cpmc"
                                          :rules="[{ required: true, message: '产品名称不能为空', trigger: 'blur' }]">
                                <el-input v-model="form.cpmc" placeholder="产品名称" clearable></el-input>
                            </el-form-item>

                            <el-form-item v-if="!form.id || !form.cptp" label-width="150px" label="产品图片">
                                <input type="file" @change="getFile($event)" accept="image/*"/>
                            </el-form-item>
                            <el-form-item v-else="" label-width="150px" label="产品图片">
                                <img :src="`http://47.96.85.104:80${form.cptp}`" alt="" style="width: 140px;height: 100px;position: relative;border-radius: 5px">
                                <i v-if="form.cptp" class="el-icon-error image-icon" @click="deleteTP"></i>
                            </el-form-item>
                        </el-form>
                    </el-col>
                    <!--<el-col v-else :span="22" style="height: 100%;">-->
                        <!--<span>{{this.$common.dictParse(form.sblx, this.equipmentType)}} > {{form.cpmc}}</span>-->
                        <!--<img :src="`http://182.151.22.247:8081${form.cptp}`" alt="" style="width: 100px;height: 100px;">-->
                    <!--</el-col>-->
                    <el-col :span="2" style="text-align: right;">
                        <el-button icon="el-icon-success" @click="prelook" type="primary">保存</el-button>
                    </el-col>
                </el-row>
            </div>

            <div  style="margin-top: 20px;">
                <!--<h2 style="line-height: 40px;padding-bottom: 10px;color: #606266;font-size: 20px">产品介绍资料</h2>-->
                    <div class="contents" v-for="item,index in contents" :key="index*1.00107" :style="{borderBottom: index == contents.length-1 ? '1px dashed #dcdfe6' : '0', marginBottom: index == contents.length-1 ? '30px': '20px'}">
                        <el-row style="margin-bottom: 5px;">
                            <el-col :span="18">
                                <h3 style="padding: 0 10px;color: #0095FF">{{item.bt}}</h3>
                            </el-col>
                            <el-col :span="6" style="text-align: right">
                                <el-button type="warning" icon="el-icon-delete" @click="deleteItem(item, index)" :disabled="updating === index">删除</el-button>

                                <el-button icon="el-icon-edit" @click="editItem(item, index)" type="primary" :disabled="updating === index">编辑</el-button>
                            </el-col>
                        </el-row>
                        <div v-html="item.xxnr" style="padding:3px 10px;"></div>
                    </div>
            </div>
            <div style="margin-top: 30px;">
                <el-row>
                    <el-col :span="18">
                        <el-form :inline="true" class="demo-form-inline"  label-position="right">
                            <el-form-item label-width="70px" label="小标题">
                                <el-input v-model="bt" placeholder="请输入介绍标题" clearable></el-input>
                            </el-form-item>
                        </el-form>
                    </el-col>
                    <el-col :span="6" style="text-align: right;">
                        <el-button @click="finishItem" type="primary">完成</el-button>
                    </el-col>
                </el-row>
                <UEditor :config=config ref="ueditor"></UEditor>
            </div>
            <!--<button size="primary" type="info" icon="plus" @click="getContent">获取内容</button>-->
        </div>

    </div>
</template>

<script>
import UEditor from '@/components/common/ueditor.vue'
    export default {
        name: 'basetable',
        components: {UEditor},
        data() {
            return {
                config: {
                    //可以在此处定义工具栏的内容
                     toolbars: [
                         ['fullscreen', 'source', '|', 'undo', 'redo', '|',
                         'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                         'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                         'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
                         'directionalityltr', 'directionalityrtl', 'indent', '|',
                         'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                         'simpleupload', 'emotion', 'scrawl', 'map', 'pagebreak', '|',
                         'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
                         'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
                         'print', 'preview', 'searchreplace', 'drafts', 'help']
                     ],
                    autoHeightEnabled: false,
                    autoFloatEnabled: true,
                    initialContent:'请输入内容',   //初始化编辑器的内容,也可以通过textarea/script给值，看官网例子
                    autoClearinitialContent:true, //是否自动清除编辑器初始内容，注意：如果focus属性设置为true,这个也为真，那么编辑器一上来就会触发导致初始化的内容看不到了
                    initialFrameWidth: 1200,
                    initialFrameHeight: 450,
//                    BaseUrl: '',
                    UEDITOR_HOME_URL: '../../../static/ueditor/'
                },
                form: {},
                contents: [],
                bt: '',
                content: {},
                equipmentType: [],
                pId: null,
                updating: null
            }
        },
        mounted () {
            this.$nextTick(()=>{
                this.editor = UE.getEditor("editor");
            })
            this.$axios.post('/equipmentType/findAllByGsid').then((res) => {
                let data = res.data;
                this.equipmentType = data;
            });
        },
        created() {
            console.log(this.$route.params.pId);
            this.pId = this.$route.params.pId;
            if(this.$route.params.pId) {
                this.$axios.post('/equipment/findById?id=' + this.$route.params.pId).then((res) => {
                    console.log(res)
                    let data = res.data[0];
                    this.form = {
                        id: data.id,
                        gsid: data.gsid,
                        cpmc : data.cpmc,
                        cptp: data.cptp,
                        sblx: data.sblx
                    };
                    this.contents = data.cpxq;
                    console.log(this.form, this.contents)
                });
            }
        },
        computed: {

        },
        methods: {
            deleteTP() {
                this.form.cptp = '';
                this.file = {};
            },
            deleteItem(item, index) {
                this.contents.splice(index, 1);
                console.log(this.contents)
            },
            getFile(event) {
                this.file = event.target.files[0];
                this.form.file = this.file;
            },
            editItem(item, index) {
                this.updating = index;
                this.bt = item.bt;
                this.content.bt = item.bt;
                this.content.xxnr = '';
                this.content.index = index;
                window.setTimeout(() => {
                    this.editor.setContent(this.contents[index].xxnr);
                }, 50)
            },
            finishItem() {
                if(this.content.index >= 0) {   //小标题修改
                    let index = this.content.index;
                    this.contents[index].bt = this.bt;
                    this.contents[index].xxnr = this.$refs.ueditor.getUEContent();
                    this.updating = null;
                }else {    //新建小标题
                    if(this.pId) {
                        this.content.id = null;
                        this.content.cpid = null;
                    }
                    this.content.bt = this.bt;
                    this.content.xxnr = this.$refs.ueditor.getUEContent();
                    this.contents.push(this.content);
                }
                window.setTimeout(() => {
                    this.content = {};
                    this.bt = '';
                    this.editor.setContent('');
                }, 300)
            },
            prelook() {
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        let url = '/equipment/add';
                        if(this.pId) {
                            url = '/equipment/update'
                        }
                        let formData = new FormData();
                        for (let key in this.form) {
                            formData.append(key, this.form[key]);
                        }
                        formData.append('contents', JSON.stringify(this.contents));
                        let config = {
                            headers: {
                                'Content-Type': 'multipart/form-data'
                            }
                        };
                        this.$axios.post(url, formData, config).then(res => {
                            this.$refs.form.resetFields()
                            this.$router.push({name:'products', params: {sblx: this.form.sblx}});
                        })
                    }
                })
            },

        }
    }

</script>

<style scoped>
    .contents {
        margin-bottom: 20px;
        padding: 5px 0 15px;
    }
</style>
