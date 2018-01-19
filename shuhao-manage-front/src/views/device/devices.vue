<template>
  <div class="container-wrap">
    <div class="container-box">
      <div class="">

      </div>

      <div class="container-content">
        <div class="block">
          <span class="demonstration">设备ID:</span>
          <el-input placeholder="请输入设备ID" icon="search" v-model="key_user" :on-icon-click="handleIconClickUserName" style="width: 200px">
          </el-input>

          <span class="demonstration">手机号码:</span>
          <el-input placeholder="请输入手机号码" icon="search" v-model="key_phone" :on-icon-click="handleIconClick" style="width: 200px">
          </el-input>

          <el-button type="primary" size="small" @click="phoneAnduName()" style="background-color:#47b3d8">查询</el-button>
        </div>
        <div  style="height:10px"></div>

        <el-table :data="devices" border stripe tooltip-effect="dark" style="width: 100%">

          <el-table-column prop="deviceId" label="设备ID"  align="center">
           <template slot-scope="scope">
              <router-link :to="{ name: 'device', params:  { id: scope.row.deviceId,readOnly:false}}" style="color: white">
                <p  style="color:blue">{{ scope.row.deviceId }}</p>
              </router-link>
            </template>
          </el-table-column>

          <el-table-column prop="deviceVer" label="设备版本号"  align="center"> </el-table-column>

          <el-table-column prop="userPhone" label="手机号码"  align="center">
            <template slot-scope="scope">
              <router-link :to="{ name: 'user', params:  { id: scope.row.userId,readOnly:false}}" style="color: white">
                <p  style="color:blue">{{ scope.row.userPhone }}</p>
              </router-link>
            </template>
          </el-table-column>

          <el-table-column prop="sensorCount" label="传感器模块的数量"  align="center"> </el-table-column>
          <el-table-column prop="driverCount" label="输出模块的数量"  align="center"> </el-table-column>
          <el-table-column prop="samplingPeriod" label="发送周期"  align="center"> </el-table-column>
          <el-table-column prop="ioCfgTime" label="模块配置更新时间"  align="center">
            <template slot-scope="scope">
              {{scope.row.ioCfgTime | date}}
            </template>
          </el-table-column>

          <el-table-column prop="isOnlineDesc" label="设备在线状态"  align="center"> </el-table-column>

          <el-table-column prop="onlineTime" label="设备在线时间"  align="center">
            <template slot-scope="scope">
              {{scope.row.onlineTime | date}}
            </template>
          </el-table-column>

          <el-table-column prop="sinarioCfgTime" label="场景区配置时间"  align="center">
            <template slot-scope="scope">
              {{scope.row.sinarioCfgTime | date}}
            </template>
          </el-table-column>


        </el-table>
        <div class="newpage-wrap">
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-size="pageSize"
            layout="total, prev, pager, next, jumper"
            :total="totalCount">
          </el-pagination>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import DeviceService from '../../service/device';
  import types from '../../store/mutation-types';
  export default {
    name: 'list',
    data() {
      return {
        currentPage: 1,
        totalPage: 0,
        pageSize: 18,
        totalCount: 0,
        key_phone: '',
        key_user:'',
        devices: []
      }
    },
    created () {
      this.fetchData();
    },
    watch: {
      '$route': 'fetchData'
    },
    computed: {
      keyWordUsers () {
        var arr = [];
        if (!this.key_phone)
          return this.users;
        else {
          this.users.forEach((item) => {
            if (item.username.indexOf(this.key_phone) > -1)
              arr.push(item);
          });
          return arr;
        }
      }
    },
    methods: {
      handleIconClick () {
        if (!this.key_phone) {
          this.$message({type: 'info', message: '请输入关键字'});
          return;
        }
        this.fetchData();
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.fetchData();
      },

      handleIconClickUserName () {
        if (!this.key_user) {
          this.$message({type: 'info', message: '请输入关键字'});
          return;
        }
        this.fetchData();
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.fetchData();
      },

      phoneAnduName:function () {
        this.fetchData();
      },
      handleCurrentChange (val) {
        this.currentPage = val;
        this.fetchData();
      },

      /**获取设备列表数据*/
      fetchData (route) {
        var tab = route ? route.query.tab : this.$route.query.tab;
        let that = this;
        setTimeout(() => {
          DeviceService.getDeviceList({
            tab: tab,
            page: that.currentPage,
            length: that.pageSize,
            phone: that.key_phone,
            deviceId:that.key_user,
          }).then(function (response) {
            if (response.code == 0) {
              that.devices = response.data.data;
              that.totalPage = response.data.page;
              that.totalCount = response.data.total;
            }
            else that.$message({type: 'error', message: response.reason});
          });
        }, 300);
      },
    }
  }
</script>
<style lang="scss">
  @import "../../assets/scss/define";

  .container-content {
    @extend %pa;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 30px 20px;
    overflow-x: hidden;
    overflow-y: auto;
  }

  .search-box {
    @extend %pa;
    top: 20px;
    right: 40px;
    z-index: 1;
    width: 360px;
  }

  .container-box {
    @extend %h100;
  }

  .page-wrap {
    @extend %pa;
    @extend %tac;
    background-color: #fff;
    z-index: 1;
    left: 0;
    right: 0;
    padding: 10px 0 20px;
    bottom: 0;
  }
  .newpage-wrap{
    @extend %pa;
    @extend %tac;
    left: 0;
    right: 0;
    padding: 10px 0 20px;
  }
</style>
