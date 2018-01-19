<template>
  <div class="container-wrap">
    <div class="container-box">
      <div class="">

      </div>

      <div class="container-content">
        <div class="block">
          <span class="demonstration">用户id:</span>
          <el-input placeholder="请输入用户id" icon="search" v-model="key_user" :on-icon-click="handleIconClickUserName" style="width: 200px">
          </el-input>

          <span class="demonstration">时间段</span>
          <el-date-picker
            v-model="value"
            type="daterange"
            align="right"
            placeholder="选择日期范围"
            :picker-options="pickerOptions2">
          </el-date-picker>

          <el-button type="primary" style="background-color: rgb(71, 179, 216);" size="small" @click="phoneAnduName()" >查询</el-button>
        </div>
        <div  style="height:10px"></div>

        <el-table :data="logDto" border stripe tooltip-effect="dark" style="width: 100%">

          <el-table-column prop="opTime" label="操作时间"  align="center">
          <template slot-scope="scope">
            {{scope.row.opTime | date}}
          </template>
          </el-table-column>


          <el-table-column prop="userId" label="用户id" align="center">

          </el-table-column>

          <el-table-column prop="ipAddr" label="客户端IP"  align="center"> </el-table-column>

          <el-table-column prop="event" label="事件"  align="center"> </el-table-column>

          <el-table-column prop="url" label="URL"  align="center"> </el-table-column>

          <el-table-column  label="事件详情" align="center">
            <template slot-scope="scope">
              <el-button @click="detail(scope.row)" class="el-button el-button el-button--small" type="info" size="small" style="background-color: rgb(71, 179, 216);">点击查看详情</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-dialog title="请求和响应信息" :visible.sync="dialogFormVisible">d8
          <el-form :model="detailLog" :inline="true" class="demo-form-inline">
            <el-form-item label="请求地址和参数">
              <el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4}"
                placeholder="请输入内容"
                style="width: 800px"
                v-model="detailLog.request">
              </el-input>
            </el-form-item>
            <br>
            <el-form-item label="响应数据">
              <el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4}"
                placeholder="请输入内容"
                style="width: 800px"
                v-model="detailLog.response">
              </el-input>
            </el-form-item>
          </el-form>

        </el-dialog>
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
  import LogService from '../../service/log';
  import types from '../../store/mutation-types';
  import Util from '../../assets/lib/util';
  export default {
    name: 'list',
    data() {
      return {
        dialogFormVisible:false,
        detailLog:{},
        currentPage: 1,
        totalPage: 0,
        pageSize: 18,
        totalCount: 0,
        key_phone: '',
        key_user:'',
        logDto: [],
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
      this.fetchData();
    },
    watch: {
      '$route': 'fetchData',
      'value' : function (newValue) {
        console.log("*****************")
        console.log(newValue)
        console.log(newValue[0])

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
      detail(row){
        this.dialogFormVisible=true;
        this.detailLog=row;

      },
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

      /**获取日志列表*/
      fetchData (route) {
        var tab = route ? route.query.tab : this.$route.query.tab;
        let that = this;
        setTimeout(() => {
          LogService.getLogList({
            tab: tab,
            page: that.currentPage,
            length: that.pageSize,
            fromTime: that.createTime,
            toTime: that.endTime,
            userId:that.key_user,
          }).then(function (response) {

            if (response.code == 0) {
              that.logDto = response.data.data;
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
