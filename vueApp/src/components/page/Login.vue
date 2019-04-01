<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">追金侠管理系统</div>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="ms-content">
                <el-form-item prop="username">
                    <el-input v-model="ruleForm.username" placeholder="用户名">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" placeholder="密码" v-model="ruleForm.password" @keyup.enter.native="submitForm('ruleForm')">
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                </div>
                <p class="login-tips">Tips : 请输入正确的用户名和密码。</p>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {
        data: function(){
            return {
                ruleForm: {
                    username: 'admin',
                    password: 'admin'
                },
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        let param = this.ruleForm;

                        this.$axios.post('/user/login', {yhm: param.username,mm: param.password}).then( (res) => {
                            if(res.resCode == 200){
                                let data = res.data;
                                localStorage.setItem('ms_username',data.mz);
//                                localStorage.setItem('gsid',data.gsid);
                                localStorage.setItem('uuid',data.id);
                                this.$router.push('/homePage');
                            }else{
                                this.$message.error(res.resMsg);
                            }
                        });
                    } else {
                        return false;
                    }
                });
            }
        }
    }
</script>

<style lang="scss" scoped>
    .login-wrap{
        position: relative;
        width:100%;
        height:100%;
        background-image: url('/static/img/login-bg.jpg');
        background-size: 100%;
    }
    .ms-title{
        width:100%;
        line-height: 50px;
        text-align: center;
        font-size:20px;
        color: #fff;
        border-bottom: 1px solid #ddd;
    }
    .ms-login{
        position: absolute;
        left:50%;
        top:50%;
        width:400px;
        margin:-190px 0 0 -175px;
        border-radius: 5px;
        background: rgba(0,0,0, 0.3);
        overflow: hidden;
    }
    .ms-content{
        padding: 40px;
    }
    .login-btn{
        text-align: center;
    }
    .login-btn button{
        width:100%;
        height:36px;
        margin-bottom: 10px;
    }
    .login-tips{
        font-size:12px;
        line-height:30px;
        color:#fff;
    }
</style>