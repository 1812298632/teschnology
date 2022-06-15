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
        <el-step title="上传文件" description="上传文件，如果失败可以点击重新导入，重新选择数据"></el-step>
        <el-step title="查看数据" description="查看导入的数据,查看无误 下一步，可以再次导入新的数据"></el-step>
      </el-steps>
    </el-container>
    <el-container style="height: 75%">
      <div v-show="showone" >
        <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleForm" align-center >


          <el-form-item label="导入名称" prop="type">
            <el-select v-model="ruleForm.type"  >
              <el-option label="损益表" value="损益表"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="sheet名称" prop="sheetname">
            <el-input v-model="ruleForm.sheetname"></el-input>
          </el-form-item>
          <el-form-item label="车辆属于" prop="cartype">
            <el-select v-model="ruleForm.cartype">
              <el-option label="沃尔沃" value="沃尔沃"></el-option>
              <el-option label="解放车" value="解放车"></el-option>
            </el-select>
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
            action="http://localhost:9080/incomeUpload"
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
              prop="cartype"
              label="类别"
              width="50">
          </el-table-column>
          <el-table-column
              prop="carid"
              label="车牌"
              width="60">
          </el-table-column>
          <el-table-column
              prop="columnname"
              label="项目" width="60">
          </el-table-column>
          <el-table-column
              prop="subjectcode"
              label="科目号"
              width="80">
          </el-table-column>

          <el-table-column
              prop="onemonth"
              label="一月">
          </el-table-column>

          <el-table-column
              prop="twomonth"
              label="二月">
          </el-table-column>

          <el-table-column
              prop="threemonth"
              label="三月">
          </el-table-column>
          <el-table-column
              prop="fourmonth"
              label="四月">
          </el-table-column>
          <el-table-column
              prop="fivemonth"
              label="五月">
          </el-table-column>
          <el-table-column
              prop="sixmonth"
              label="六月">
          </el-table-column>
          <el-table-column
              prop="sevenmonth"
              label="七月">
          </el-table-column>
          <el-table-column
              prop="eightmonth"
              label="八月">
          </el-table-column>
          <el-table-column
              prop="ninemonth"
              label="九月">
          </el-table-column>
          <el-table-column
              prop="tenmonth"
              label="十月">
          </el-table-column>
          <el-table-column
              prop="eleventmonth"
              label="十一月">
          </el-table-column>
          <el-table-column
              prop="twelvemonth"
              label="十二月">
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
      this.showbutton =false;
      this.shownextbutton =true;
      this.nextDisable = false
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


            fetch("http://localhost:9080/setIncomeParamter", {
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
        fetch("http://localhost:9080/queryByUploadIncomeEntity", {
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
