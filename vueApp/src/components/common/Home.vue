<template>
    <div class="wrapper">
        <v-head></v-head>
        <v-sidebar></v-sidebar>
        <div class="content-box" :class="{'content-collapse':collapse}">
            <v-tags></v-tags>
            <div class="content">
                <transition name="move" mode="out-in">
                    <keep-alive :exclude="tagsList">
                        <router-view ></router-view>
                        <router-view v-if="$route.meta.keepAlive"></router-view>
                    </keep-alive>
                    <router-view v-if="!$route.meta.keepAlive"></router-view>
                </transition>
            </div>
        </div>
    </div>
</template>

<script>
    import vHead from './Header.vue';
    import vSidebar from './Sidebar.vue';
    import vTags from './Tags.vue';
    import bus from './bus';
    export default {
        data(){
            return {
                tagsList: [],
                collapse: false,
                userInfo: {}
            }
        },
        components:{
            vHead, vSidebar, vTags
        },
        created(){

            bus.$on('collapse', msg => {
                this.collapse = msg;
            });

            // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
            bus.$on('tags', msg => {
                let arr = [];
                for(let i = 0, len = msg.length; i < len; i ++){
                    msg[i].name && arr.push(msg[i].name);
                }
                this.tagsList = arr;
            });

            window.onstorage = (e) => {
                 console.log('onstorage:', e);
                let key = e.key;
                let newValue = e.newValue;
                let oldValue = e.oldValue;
                //判断登录用户信息发生改变
                if(key == 'userInfo' && (newValue != oldValue)){
                    this.$router.push('/homePage');
                    location.reload()
                }
            };

            this.getUserInfo();
        },
        methods:{
            getUserInfo(){
                let userInfo = localStorage.getItem('userInfo');
                this.userInfo = JSON.parse(userInfo);
            },
        }

    }
</script>
