<template>

  <div class="container-wrap">
    <div class="container-box">
      <div class="container-content">
        <el-row>
          <el-col :span="12">
            <div class="grid-content bg-purple-dark">
              <el-tag style="font-size: large" color="#20A0FF">设备详情</el-tag>
            </div>
          </el-col>
        </el-row>
        <div style="margin: 20px"></div>
        <el-row>
          <el-col :span="12">
            <div style="margin: 20px;"></div>
            <div class="grid-content"  style="width: 1000px">
              <el-form :inline="true" :data="device" class="demo-form-inline" label-width="100px">
                <el-form-item label="设备ID">
                  <el-input v-model="device.deviceId" placeholder="设备ID" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="设备版本号">
                  <el-input v-model="device.deviceVer" placeholder="设备版本号" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="传感模块数量">
                  <el-input v-model="device.sensorCount" placeholder="传感模块数量" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="输出模块数量">
                  <el-input v-model="device.driverCount" placeholder="输出模块数量" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="发送周期(s)">
                  <el-input v-model="device.samplingPeriod" placeholder="发送周期(s)" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
                <el-form-item label="在线状态">
                  <el-input v-model="device.isOnlineDesc" placeholder="在线状态" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="在线状态时间">
                  <el-input v-model="device.onlineTime" placeholder="状态切换时间" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>
                <br/>

                <el-form-item label="I/O模块配置:">
                    <textarea v-model="device.ioCfg" :disabled="true" style="background-color:#eef1f6;width:800px;height: 100px;float:left;"></textarea>
                </el-form-item>
                <br/>

                <el-form-item label="模块更新时间">
                  <el-input v-model="device.ioCfgTime" placeholder="模块更新时间" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="场景区配置:">
                  <textarea v-model="device.sinarioCfg" :disabled="true" style="background-color:#eef1f6;width:800px;height: 100px;float:left;"></textarea>
                </el-form-item>
                <br/>

                <el-form-item label="场景更新时间">
                  <el-input v-model="device.sinarioCfgTime" placeholder="场景更新时间" :disabled="true" style="width: 200px"></el-input>
                </el-form-item>

              </el-form>
            </div>
          </el-col>
        </el-row>
        <div>
          <router-link :to="{ name: 'data', params:  { id: device.deviceId,readOnly:false}}" style="color: white">
            <el-button style="background-color:#47b3d8">设备数据</el-button>
          </router-link>

        </div>
        <div style="margin: 20px"></div>
        <el-row style="margin: 20px">
          <el-col :span="24">
            <div class="grid-content bg-purple-dark">

            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
  import DeviceService from '../../service/device';
  import Util from '../../assets/lib/util';
  import ElInput from "../../../node_modules/element-ui/packages/input/src/input";

  export default {
    components: {ElInput},
    data() {
      return {
        device: {},
      }
    },
    created () {
      this.device.id = this.$route.params.id;
      this.getDevice(this.device.id);


    },

    methods: {
      getDevice(id){
        let that = this;
        DeviceService.getDevice(id)
          .then(function (response) {
            if (response.code == 0) {
              that.device = response.data;
              var date= new Date(that.device.onlineTime);
              that.device.onlineTime = Util.msToDate(date);

              var date1= new Date(that.device.ioCfgTime);
              that.device.ioCfgTime = Util.msToDate(date1);

              var date2= new Date(that.device.sinarioCfgTime);
              that.device.sinarioCfgTime = Util.msToDate(date2);
            } else {
              that.$message({type: 'error', message: response.reason});
            }
          })
      },
    }
  }
</script>

