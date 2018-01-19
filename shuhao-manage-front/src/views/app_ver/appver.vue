<template>

  <div class="container-wrap">
    <div class="container-box">
      <div class="container-content">
        <div style="margin: 20px"></div>
        <el-row>
          <el-col :span="12">
            <div style="margin: 20px;">
              <el-tag style="font-size: large">安卓</el-tag>
            </div>
            <div class="grid-content"  style="width: 1000px">
              <el-form :inline="true" :data="appverAnd" class="demo-form-inline" label-width="100px">
                <el-form-item label="版本">
                  <el-input v-model="appverAnd.appVer" placeholder="版本" :disabled="disabledAnd" style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="发布时间">
                 <!-- <el-input v-model="appverAnd.relTime" placeholder="发布时间" :disabled="disabledAnd" style="width: 200px"></el-input>-->
                    <el-date-picker
                      v-model="appverAnd.relTime"
                      :disabled="disabledAnd"
                      type="datetime"
                      placeholder="选择日期时间">
                    </el-date-picker>

                </el-form-item>

                <el-button type="info" @click="editAnd" style="float:right;background-color:#47b3d8">{{readOnlyAnd ? '编辑' : '保存'}}</el-button>


                <br/>

                <el-form-item label="下载链接">
                  <el-input v-model="appverAnd.url" placeholder="模块更新时间" :disabled="disabledAnd" style="width: 890px"></el-input>
                </el-form-item>

                <el-form-item label="描述信息:">
                  <textarea v-model="appverAnd.describ" :disabled="disabledAnd"  v-if="disabledAnd==true" style="background-color:#eef1f6;width:885px;height: 100px;float:left;"></textarea>
                  <textarea v-model="appverAnd.describ" :disabled="disabledAnd" v-if="disabledAnd==false" style="background-color:#ffffff;width:885px;height: 100px;float:left;"></textarea>
                </el-form-item>


              </el-form>
            </div>
          </el-col>
        </el-row>



        <el-row>
          <el-col :span="12">
            <div style="margin: 20px;">
              <el-tag style="font-size: large">IOS</el-tag>
            </div>
            <div class="grid-content"  style="width: 1000px">
              <el-form :inline="true" :data="appverIOS" class="demo-form-inline" label-width="100px">
                <el-form-item label="版本">
                  <el-input v-model="appverIOS.appVer" placeholder="版本" :disabled="disabledIOS" style="width: 200px"></el-input>
                </el-form-item>

                <el-form-item label="发布时间">
                  <el-date-picker
                    v-model="appverIOS.relTime"
                    :disabled="disabledIOS"
                    type="datetime"
                    placeholder="选择日期时间">
                  </el-date-picker>
                </el-form-item>

                <el-button type="info" @click="editIOS" style="float:right; background-color:#47b3d8">{{readOnlyIOS ? '编辑' : '保存'}}</el-button>


                <br/>

                <el-form-item label="下载链接">
                  <el-input v-model="appverIOS.url" placeholder="下载链接" :disabled="disabledIOS" style="width: 890px"></el-input>
                </el-form-item>

                <el-form-item label="描述信息:">
                  <textarea v-model="appverIOS.describ" :disabled="disabledIOS" v-if="disabledIOS==true" style="background-color:#eef1f6;width:885px;height: 100px;float:left;"></textarea>
                  <textarea v-model="appverIOS.describ" :disabled="disabledIOS" v-if="disabledIOS==false" style="background-color:#ffffff;width:885px;height: 100px;float:left;"></textarea>
                </el-form-item>



              </el-form>
            </div>
          </el-col>
        </el-row>

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
  import AppverService from '../../service/appver';
  import Util from '../../assets/lib/util';
  import ElInput from "../../../node_modules/element-ui/packages/input/src/input";

  export default {
    components: {ElInput},
    data() {
      return {
        appverIOS: {},
        appverAnd: {},
        readOnlyIOS: true,
        readOnlyAnd: true,
        disabledIOS:true,
        disabledAnd:true,
      }
    },
    created () {
      this.getAppverList();

    },
    methods: {
      getAppverList(){
        let that = this;
        AppverService.getAppverList()
          .then(function (response) {
            if (response.code == 0) {
              that.appvers = response.data;
              for (var i=0;i<that.appvers.length;i++){
                  if (that.appvers[i].platform == 1){
                    var date= new Date(that.appvers[i].relTime);
                    that.appvers[i].relTime = Util.msToDate(date);
                    that.appverIOS = that.appvers[i]
                  }else {
                    var date= new Date(that.appvers[i].relTime);
                    that.appvers[i].relTime = Util.msToDate(date);
                    that.appverAnd = that.appvers[i]
                  }

              }

            } else {
              that.$message({type: 'error', message: response.reason});
            }
          })
      },


      editIOS(){
        let that = this;
        if (that.readOnlyIOS) {
          that.readOnlyIOS = false;
          that.disabledIOS=false;
        } else {
          that.appverIOS.relTime = Util.dateToMs(that.appverIOS.relTime);
          that.appverIOS.platform =1;
          AppverService.saveAppver(that.appverIOS)
            .then(function (response) {
              if (response.code == 0) {
                that.readOnlyIOS = true;
                that.disabledIOS=true;
                that.appverIOS.relTime = Util.msToDate(that.appverIOS.relTime);
                that.$message({type: 'success', message: "操作成功"});
              } else {
                that.$message({type: 'error', message: response.reason});
              }

            })
        }
      },

      editAnd(){
        let that = this;
        if (that.readOnlyAnd) {
          that.readOnlyAnd = false;
          that.disabledAnd=false;
        } else {
          that.appverAnd.relTime = Util.dateToMs(that.appverAnd.relTime);
          that.appverAnd.platform =0;
          AppverService.saveAppver(that.appverAnd)
            .then(function (response) {
              if (response.code == 0) {
                that.readOnlyAnd = true;
                that.disabledAnd=true;
                that.appverAnd.relTime = Util.msToDate(that.appverAnd.relTime);
                that.$message({type: 'success', message: "操作成功"});
              } else {
                that.$message({type: 'error', message: response.reason});
              }

            })
        }
      },
    }
  }
</script>

