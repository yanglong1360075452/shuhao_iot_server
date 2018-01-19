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

          <span class="demonstration">时间段</span>
          <el-date-picker
            v-model="value"
            type="daterange"
            align="right"
            placeholder="选择日期范围"
            :picker-options="pickerOptions2">
          </el-date-picker>

          <el-button type="primary" size="small" @click="phoneAnduName()" style="background-color:#47b3d8">查询</el-button>
        </div>
        <div  style="height:10px"></div>

        <el-table :data="data" border stripe tooltip-effect="dark" style="width: 100%">

          <el-table-column prop="deviceId" label="设备ID"  align="center"></el-table-column>


          <el-table-column prop="receiveTime" label="接收时间" align="center">
          <template slot-scope="scope">
            {{scope.row.receiveTime | date}}
            </template>
          </el-table-column>

          <el-table-column prop="data" label="数据包"  align="left"> </el-table-column>

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
  import DeviceDataService from '../../service/deviceData';
  import types from '../../store/mutation-types';
  import Util from '../../assets/lib/util';
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
        data: [],
        pickerOptions2: {
          shortcuts: [
            {
              text: '最近一天',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 );
                picker.$emit('pick', [start, end]);
              }
            },{
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
        value: '',
      }
    },
    created () {
      var id = this.$route.params.id;
      this.key_user=id;

      this.fetchData();
    },
    watch: {
      '$route': 'fetchData',
      'value' : function (newValue) {
        if (newValue[0] == undefined && newValue[1] == undefined){

        }else{
          var createTime= Util.msToDate(newValue[0]).toString();
          var endTime= Util.msToDate(newValue[1]).toString();
        }


        if (createTime == undefined){
          createTime=null;
        }

        if (endTime == undefined){
          endTime=null;
        }

        this.createTime = (new Date(createTime)).getTime();
        this.endTime = (new Date(endTime)).getTime();
      }
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

      /**获取设备数据列表*/
      fetchData (route) {
        var tab = route ? route.query.tab : this.$route.query.tab;
        let that = this;
        setTimeout(() => {
          DeviceDataService.getDeviceDataList({
            tab: tab,
            page: that.currentPage,
            length: that.pageSize,
            startTime: that.createTime,
            endTime: that.endTime,
            deviceId:that.key_user,
          }).then(function (response) {

            if (response.code == 0) {
              that.data = response.data.data;
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
