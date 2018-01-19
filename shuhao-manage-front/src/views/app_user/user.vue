<template>

  <div class="container-wrap">
    <div class="container-box">
      <div class="container-content">
        <el-row>
          <el-col :span="12">
            <div class="grid-content bg-purple-dark">
              <el-tag style="font-size: large" color="#20A0FF">用户详情</el-tag>
            </div>
          </el-col>
        </el-row>
        <div style="margin: 20px"></div>
        <el-row>
          <el-col :span="12">
            <div style="margin: 20px;"></div>
            <div class="grid-content" style="width: 1000px">
              <el-form :inline="true" :data="user" class="demo-form-inline" label-width="100px">
                <el-form-item label="用户ID">
                  <el-input v-model="user.userId" placeholder="用户ID" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="手机号码">
                  <el-input v-model="user.phone" placeholder="手机号码" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="注册时间">
                  <el-input v-model="user.registerTime" placeholder="注册时间" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>

              </el-form>
            </div>
          </el-col>
        </el-row>
        <div style="margin: 20px"></div>
        <el-row style="margin: 20px">
          <el-col :span="24">
            <div class="grid-content bg-purple-dark">
              <el-tabs type="card">
                <el-tab-pane label="我的设备列表">
                  <el-table :data="userDevices" border stripe style="width: 100%">
                    <el-table-column prop="deviceId" label="设备ID" align="center">
                      <template slot-scope="scope">
                        <router-link :to="{ name: 'device', params:  { id: scope.row.deviceId,readOnly:false}}" style="color: white">
                          <p  style="color:blue">{{ scope.row.deviceId }}</p>
                        </router-link>
                      </template>
                    </el-table-column>
                    <el-table-column prop="deviceVer" label="设备版本号" align="center"></el-table-column>

                    <el-table-column prop="sensorCount" label="传感器模块的数量" show-overflow-tooltip align="center"></el-table-column>

                    <el-table-column prop="driverCount" label="输出模块的数量" align="center"></el-table-column>
                    <el-table-column prop="ioCfgTime" label="模块配置更新时间" align="center">
                      <template slot-scope="scope">
                        {{scope.row.ioCfgTime | date}}
                      </template>
                    </el-table-column>

                    <el-table-column prop="isOnlineDesc" label="设备在线状态" align="center"></el-table-column>

                    <el-table-column prop="onlineTime" label="设备在线时间" align="center">
                      <template slot-scope="scope">
                        {{scope.row.onlineTime | date}}
                      </template>
                    </el-table-column>

                    <el-table-column prop="sinarioCfgTime" label="场景区域配置时间" align="center">
                      <template slot-scope="scope">
                        {{scope.row.sinarioCfgTime | date}}
                      </template>
                    </el-table-column>
                  </el-table>
                  <div style="margin: 2% 42%">
                    <el-pagination
                      @current-change="handleCurrentChange"
                      :current-page="pageData.page"
                      :page-size="pageData.length"
                      layout="total, prev, pager, next, jumper"
                      :total="totalCount">
                    </el-pagination>
                  </div>
                </el-tab-pane>
                <el-tab-pane label="好友列表">
                  <el-table :data="friedns" border stripe tooltip-effect="dark" style="width: 100%">

                    <el-table-column prop="userId" label="用户id" align="center"></el-table-column>


                    <el-table-column prop="phone" label="手机号码" align="center"></el-table-column>

                    <el-table-column prop="registerTime" label="注册时间" show-overflow-tooltip align="center">
                      <template slot-scope="scope">
                        {{scope.row.registerTime | date}}
                      </template>
                    </el-table-column>

                    <el-table-column prop="userShares" label="分享给好友的设备" align="center"></el-table-column>


                    <el-table-column prop="friendShares" label="好友分享的设备" align="center"></el-table-column>


                  </el-table>
                  <div style="margin: 2% 42%">
                    <el-pagination
                      @current-change="handleCurrentLivesChange"
                      :current-page="pageFirednsData.page"
                      :page-size="pageFirednsData.length"
                      layout="total, prev, pager, next, jumper"
                      :total="totalFriendsCount">
                    </el-pagination>
                  </div>
                </el-tab-pane>
                <el-tab-pane label="好友分享的设备">

                  <el-table :data="friendDevices" border stripe tooltip-effect="dark" style="width: 100%">

                    <el-table-column prop="deviceId" label="设备ID" align="center">
                      <template slot-scope="scope">
                        <router-link :to="{ name: 'device', params:  { id: scope.row.deviceId,readOnly:false}}" style="color: white">
                          <p  style="color:blue">{{ scope.row.deviceId }}</p>
                        </router-link>
                      </template>
                    </el-table-column>

                    <el-table-column prop="deviceVer" label="设备版本号" align="center"></el-table-column>

                    <el-table-column prop="sensorCount" label="设备器模块的数量" show-overflow-tooltip align="center"></el-table-column>

                    <el-table-column prop="driverCount" label="输出模块的数量" align="center"></el-table-column>
                    <el-table-column prop="ioCfgTime" label="模块配置更新时间" align="center">
                      <template slot-scope="scope">
                        {{scope.row.ioCfgTime | date}}
                      </template>
                    </el-table-column>
                    <el-table-column prop="isOnlineDesc" label="设备在线状态" align="center"></el-table-column>
                    <el-table-column prop="onlineTime" label="设备在线时间" align="center">
                      <template slot-scope="scope">
                        {{scope.row.onlineTime | date}}
                      </template>
                    </el-table-column>
                    <el-table-column prop="sinarioCfgTime" label="场景区域配置时间" align="center">
                      <template slot-scope="scope">
                        {{scope.row.sinarioCfgTime | date}}
                      </template>
                    </el-table-column>

                  </el-table>
                  <div style="margin: 2% 42%">
                    <el-pagination
                      @current-change=" handleCurrentLivesHistoryChange"
                      :current-page="pageFriendDevicesData.page"
                      :page-size="pageFriendDevicesData.length"
                      layout="total, prev, pager, next, jumper"
                      :total="totalFriendDevicesCount">
                    </el-pagination>
                  </div>
                </el-tab-pane>
              </el-tabs>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
  import UserService from '../../service/user';
  import Util from '../../assets/lib/util';
  import ElInput from "../../../node_modules/element-ui/packages/input/src/input";

  export default {
    components: {ElInput},
    data() {
      return {
        userSharesArry:[],
        friendsSharesArry:[],
        friendListCount:0,
        userDevices:[],
        friedns:[],
        friendDevices:[],
        device: null,
        resetPassword: false,
        totalPage: 0,
        totalCount: 0,

        totalFriendsPage: 0,
        totalFriendsCount: 0,

        totalFriendDevicesPage:0,
        totalFriendDevicesCount:0,
        readOnly: true,
        user: {},
        statusList: this.GLOBAL.userStatusList,
        sexList: this.GLOBAL.userSexList,
        pageData: {
          page: 1,
          length: 10,
          userId: null
        },

        pageFirednsData: {
          page: 1,
          length: 10,
          userId: null
        },

        pageFriendDevicesData: {
          page: 1,
          length: 10,
          userId: null
        },
        reset: {
          pass: '',
          checkPass: ''
        },
      }
    },
    created () {
      this.user.id = this.$route.params.id;
      this.readOnly = this.$route.params.readOnly;
      this.getUser(this.user.id);

      this.pageData.userId = this.user.id;
      this.getUserDevices(this.pageData);

      this.pageFirednsData.userId=this.user.id;
      this.getFriends(this.pageFirednsData);

      this.pageFriendDevicesData.userId=this.user.id;
      this.getFriendDevices(this.pageFriendDevicesData);

    },

    methods: {

      getUser(id){
        let that = this;
        UserService.getUser(id)
          .then(function (response) {
            if (response.code == 0) {
              that.user = response.data;
             var date= new Date(that.user.registerTime);
              that.user.registerTime = Util.msToDate(date);
            } else {
              that.$message({type: 'error', message: response.reason});
            }
          })
      },
      handleCurrentChange (val) {
        this.pageData.page = val;
        this.getUserDevices(this.pageData);
      },
      handleCurrentLivesChange (val) {
        this.pageFirednsData.page = val;
        this.userSharesArry = [];
        this.friendsSharesArry = [];
        this.getFriends(this.pageFirednsData);

      },
      handleCurrentLivesHistoryChange (val) {
        this.pageFriendDevicesData.page = val;
      this.getFriendDevices(this.pageFriendDevicesData);

      },
      getUserDevices(data){
        var that = this;
        UserService.getUserDevices(data).then(function (response) {
            console.log("111")
            if (response.code == 0) {
              if(response.data != null){
                that.userDevices = response.data.data;
                that.totalPage = response.data.page;
                that.totalCount = response.data.total;
              };
            } else {
              that.$message({type: 'error', message: response.reason});
            }

          })
      },
      getFriends(data){
        var that = this;
        UserService.getFriends(data).then(function (response) {
          if (response.code == 0) {
            if(response.data != null){
              that.friedns = response.data.data;
              for(var i=0;i<that.friedns.length;i++){
                that.userSharesArry.push(that.friedns[i].userShares);
              }
              for(var i=0;i<that.userSharesArry.length;i++){
                  if(that.userSharesArry[i]){
                    that.friedns[i].userShares = that.userSharesArry[i].join(",");
                  }

              }

              for(var i=0;i<that.friedns.length;i++){
                that.friendsSharesArry.push(that.friedns[i].friendShares);
              }
              console.log(that.friendsSharesArry)
              for(var i=0;i<that.friendsSharesArry.length;i++){
                if(that.friendsSharesArry[i]){
                  that.friedns[i].friendShares = that.friendsSharesArry[i].join(",");
                }

              }
              that.totalFriendsPage = response.data.page;
              that.totalFriendsCount = response.data.total;
            };
          } else {
            that.$message({type: 'error', message: response.reason});
          }

        })
      },

      getFriendDevices(data){
        var that = this;
        UserService.getFriendDevices(data).then(function (response) {
          console.log("111")
          if (response.code == 0) {
            if(response.data != null){
              that.friendDevices = response.data.data;
              that.totalFriendDevicesPage = response.data.page;
              that.totalFriendDevicesCount = response.data.total;
            };
          } else {
            that.$message({type: 'error', message: response.reason});
          }

        })
      },
    }
  }
</script>
