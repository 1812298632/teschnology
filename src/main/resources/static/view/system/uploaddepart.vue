<template>
  <div class="home">
    <el-container style="height: 5%">
      <div style="margin-left: 92%;">
        <el-button type="primary" v-show="shownextbutton" @click="submit()" :disabled="nextDisable">下一步</el-button>
        <el-button v-show="showbutton" type="primary" @click="again()" >重新导入</el-button>

      </div>


    </el-container>

    <el-container style="height: 20%">
      <el-steps :active="active" finish-status="success" style="width: 90%" align-center>
        <el-step title="选择数据" description="选择完成之后，下一步"></el-step>
        <el-step title="上传文件" description="上传文件，如果失败可以重新导入，重新选择数据"></el-step>
        <el-step title="查看数据" description="查看导入的数据,查看无误 下一步，可以再次导入新的数据"></el-step>
      </el-steps>
    </el-container>
    <el-container style="height: 75%">
      <div v-show="showone" align="center">
        <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" >
          <el-form-item label="车辆" prop="cartype">
            <el-select v-model="ruleForm.cartype">
              <el-option label="沃尔沃" value="沃尔沃"></el-option>
              <el-option label="解放车" value="解放车"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="导入名称" prop="type">
            <el-select v-model="ruleForm.type">
<!--              <el-option label="损益表" value="损益"></el-option>-->
              <el-option label="中卡物流车辆作业台账" value="台账"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="月份" prop="month">
            <el-select placeholder="请选择月份" v-model="ruleForm.month">
              <el-option label="1月" value="1"></el-option>
              <el-option label="2月" value="2"></el-option>
              <el-option label="3月" value="3"></el-option>
              <el-option label="4月" value="4"></el-option>
              <el-option label="5月" value="5"></el-option>
              <el-option label="6月" value="6"></el-option>
              <el-option label="7月" value="7"></el-option>
              <el-option label="8月" value="8"></el-option>
              <el-option label="9月" value="9"></el-option>
              <el-option label="10月" value="10"></el-option>
              <el-option label="11月" value="11"></el-option>
              <el-option label="12月" value="12"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="sheet名称" prop="sheetname">
            <el-input v-model="ruleForm.sheetname"></el-input>
          </el-form-item>

        </el-form>
      </div>
      <div v-show="showtwo" style="margin-left: 35%;">
        <el-upload
            class="upload-demo"
            drag
            accept=".xlsx,.xls"
            :on-success="uploadSuccess"
            :on-error="uploadError"
            action="http://localhost:9080/upload"
            multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">上传excel/xlsx文件</div>
        </el-upload>
      </div>
      <div v-show="showthree" height="100%"
           style="width: 100%">
        <el-table
            v-loading="loading"
            :data="tableData"
            height="100%"
            border
            stripe
            style="width: 100%">
          <el-table-column
              prop="sheetid"
              label="No"
              width="50">
          </el-table-column>
          <el-table-column
              prop="cartype"
              label="类型"
              width="60">
          </el-table-column>
          <el-table-column
              prop="month"
              label="月份" width="60">
          </el-table-column>
          <el-table-column
              prop="fromno"
              label="表单号"
              width="80">
          </el-table-column>
          <el-table-column
              prop="carnum"
              label="派单车号"
              width="180">
          </el-table-column>
          <el-table-column
              prop="startcity"
              label="起点"
              width="60">
          </el-table-column>
          <el-table-column
              prop="endcity"
              label="终点" width="60">
          </el-table-column>
          <el-table-column
              prop="startkilo"
              label="起始公里数">
          </el-table-column>
          <el-table-column
              prop="endkilo"
              label="结束公里数">
          </el-table-column>
          <el-table-column
              prop="kilo"
              label="行驶里程">
          </el-table-column>
          <el-table-column
              prop="sheet"
              label="来源sheet名称">
          </el-table-column>
          <el-table-column
              prop="excelname"
              label="来源excel名称">
          </el-table-column>

        </el-table>
      </div>
    </el-container>
  </div>
</template>

<script>
module.exports = {
  data() {
    return {
      showbutton:false,
      shownextbutton:true,
      nextDisable:false,
      loading:false,
      showone: true,
      showtwo: false,
      showthree: false,
      tableData: [],
      active: 0,
      isCollapse: true,
      ruleForm: {
        type: '',
        cartype: '',
        month: '',
        sheetname: '',
      },
      rules: {
        cartype: [
          {required: true, message: '请选择车辆', trigger: 'change'},
        ],
        type: [
          {required: true, message: '请选择导入名称', trigger: 'change'}
        ],
        month: [
          {required: true, message: '请选择月份', trigger: 'change'}
        ],
        sheetname: [
          {required: true, message: '请选择sheet名称', trigger: 'change'}
        ]
      }
    };
  },
  /* components: {
          // 将组建加入组建库
          "my-header": "url:../components/Header.vue",
        }, */
  created() {
    // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
    bus.$on("tags", (msg) => {
      console.log("home....bus...on");
      console.log(msg);
      let arr = [];
      for (let i = 0, len = msg.length; i < len; i++) {
        msg[i].name && arr.push(msg[i].name);
      }
      this.tagsList = arr;
    });
  },
  methods: {
    uploadSuccess(response, file, fileList){
      this.$message({
        showClose: true,
        message: response.resMessage,
        center: true,
        offset: 150,
        type: response.res
      });

      this.nextDisable = false
    },
    uploadError(err, file, fileList){
      this.$message({
        showClose: true,
        message: "上传失败",
        width: 200,
        height: 200,
        center: true,
        type: 'error'
      });
      this.showbutton =true;
      this.shownextbutton =false;
    }
    ,
    again(){
      this.active = 0;
      this.showtwo = false
      this.showone = true
      this.showthree = false
      this.showbutton =false;
      this.shownextbutton =true;
      this.nextDisable =false;
    },
    submit() {
      let vali = true;
      if (this.active == 0) {
        this.$refs['ruleForm'].validate((valid) => {
          vali = valid
          if (valid) {


            fetch("http://localhost:9080/setParamter", {
              method: 'POST', // 请求方法还可以是 put
              body: JSON.stringify(this.ruleForm),
              headers: new Headers({
                'Content-Type': 'application/json'
              })
            }).then(res => res.json())
                .then(response => {

                  this.$message({
                    showClose: true,
                    message: response.resMessage,
                    center: true,
                    offset: 150,
                    type: response.res
                  });
                });

          }

        });

      }

      if (!vali) {
        return;
      }

      if (this.active == 0 && vali) {
        this.showtwo = true
        this.showone = false
        this.showthree = false
      }

      if (this.active == 1) {

        this.showtwo = false
        this.showone = false
        this.showthree = true
        this.loading =true
        fetch("http://localhost:9080/queryByUploadEntity", {
          method: 'POST', // 请求方法还可以是 put
          body: '',
          headers: new Headers({
            'Content-Type': 'application/json'
          })
        }).then(res => res.json())
            .then(response => {

              this.$message({
                showClose: true,
                message: response.resMessage,
                center: true,
                offset: 150,
                type: response.res
              });

              this.tableData =response.resList
              this.loading =false

            });

      }
      if (this.active == 2) {
        this.nextDisable =true;
        this.showbutton =true;
        this.shownextbutton =false
      }
      if (this.active == 3) {
        this.showbutton =false;
        this.shownextbutton =true
        this.showtwo = false
        this.showone = true
        this.showthree = false
      }
      if (this.active++ > 2) this.active = 0;
    }


  },
};
</script>

<style scoped>
.home {
  font-size: 24px;
  height: 100%;
  background-color: #ffffff;
  /*font-weight: bold;*/
}

.el-header,
.el-footer {
  background-color: #fff;
  color: #fff;
  text-align: left;
  line-height: 60px;
  margin-bottom: 5px;
  box-shadow: 10px 5px 10px #ddd;
}

.el-main {
  background-color: #fff;
  color: #333;
  text-align: center;
  /*line-height: 160px;*/
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-menu,
.el-container:nth-child(6) .el-menu {
  line-height: 260px;
}

.el-container:nth-child(7) .el-menu {
  line-height: 320px;
}

.el-scrollbar__wrap {
  overflow-x: hidden;
}
</style>
