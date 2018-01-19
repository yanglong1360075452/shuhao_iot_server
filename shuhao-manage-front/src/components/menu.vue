<template>
    <div class="menu-wrap" style="text-align:center">
      <el-menu :default-active="tabIndex" class="vertical-demo" STYLE="background-color: #37424f" @open="handleOpen" @close="handleClose">
 <!--       <el-menu-item index="1"><a href="#/users"><font color="#f0f8ff" onclick="show()">用户列表</font></a></el-menu-item>
        <el-menu-item index="2"><a href="#/devices"><font color="#f0f8ff">设备列表</font></a></el-menu-item>
        <el-menu-item index="3"><a href="#/data"><font color="#f0f8ff">设备数据</font></a></el-menu-item>
        <el-menu-item index="4"><a href="#/appver"><font color="#f0f8ff">应用管理</font></a></el-menu-item>
        <el-menu-item index="5"><a href="#/log"><font color="#f0f8ff">访问日志</font></a></el-menu-item>-->
        <el-menu-item index="1">
          <template slot-scope="scope">
            <span   v-if="user==false"><a href="#/users" @click="userf()" style="color: #dfe6ec">用户列表</a></span>
            <span   v-if="user==true"><a href="#/users" @click="userf()" style="background-color: #189ACA;color: #dfe6ec">用户列表</a></span>
          </template>
        </el-menu-item>

        <el-menu-item index="2">
          <template slot-scope="scope">
            <span  v-if="device==false"><a href="#/devices" @click="devicef()" style="color: #dfe6ec">设备列表</a></span>
            <span  v-if="device==true"><a href="#/devices" @click="devicef()" style="background-color: #189ACA;color: #dfe6ec">设备列表</a></span>
          </template>
        </el-menu-item>

        <el-menu-item index="3">
          <template slot-scope="scope">
            <span   v-if="deviceData==false"><a href="#/data" @click="deviceDataf()" style="color: #dfe6ec">设备数据</a></span>
            <span   v-if="deviceData==true"><a href="#/data" @click="deviceDataf()" style="background-color: #189ACA;color: #dfe6ec">设备数据</a></span>
          </template>
        </el-menu-item>

        <el-menu-item index="4">
          <template slot-scope="scope">
            <span   v-if="appver==false"><a href="#/appver" @click="appverf()" style="color: #dfe6ec">应用管理</a></span>
            <span   v-if="appver==true"><a href="#/appver" @click="appverf()" style="background-color: #189ACA;color: #dfe6ec">应用管理</a></span>
          </template>
        </el-menu-item>

        <el-menu-item index="5">
          <template slot-scope="scope">
            <span  v-if="log==false"><a href="#/log" @click="logf()" style="color: #dfe6ec">访问日志</a></span>
            <span    v-if="log==true"><a href="#/log" @click="logf()" style="background-color: #189ACA;color: #dfe6ec">访问日志</a></span>
          </template>
        </el-menu-item>
      </el-menu>

    </div>
</template>
<script>
  import Util from '../assets/lib/util';
    export default {
        name: 'menu',
        computed: {
            tabIndex(){
                return this.$store.state.tab_index;
            }
        },
      data () {
        return {
          user_name: '',
          sign:false,
          user:false,
          device:false,
          deviceData:false,
          appver:false,
          log:false
        };
      },
      created () {
        this.achieveUser();
      },
        methods: {
          userf(){
            this.user=true;
            this.device=false;
            this.deviceData=false;
            this.appver=false;
            this.log=false;
          },
          devicef(){
            this.device=true;
            this.user=false;
            this.deviceData=false;
            this.appver=false;
            this.log=false;
          },
          deviceDataf(){
            this.deviceData=true;
            this.device=false;
            this.user=false;
            this.appver=false;
            this.log=false;
          },
          appverf(){
            this.appver=true;
            this.device=false;
            this.user=false;
            this.deviceData=false;
            this.log=false;
          },
          logf(){
            this.log=true;
            this.device=false;
            this.user=false;
            this.deviceData=false;
            this.appver=false;
          },


          achieveUser () {
           var username= this.user_name;
            var sign= this.sign;
            username= Util.dataToSessionStorageOperate.achieve('user').username;
            if (username != "admin"){
              sign=true,
              this.sign=sign
              console.log(sign)
            }
          },
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
        }
    }
</script>
<style lang="scss">
    @import "../assets/scss/define";
    .menu-wrap{
        @extend %pa;
        top: 50px;
        left: 0;
        width: 200px;
        bottom: 0;
        background-color: #293038;
    }
    .el-menu-item{
        @extend %pr;
        a{
            @extend %pa;
            @extend %db;
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            padding: 0 45px;
            color: inherit;
        }
    }
</style>
