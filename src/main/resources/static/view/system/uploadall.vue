<template>
  <div class="home">
    <el-container style="height: 20%">
      <el-form :inline="true" :model="form" ref="ruleForm" label-width="120px">
        <el-col :span="8">
          <el-form-item label="年份" prop="year" placeholder="请选择导入年份">
            <el-select v-model="form.year">
              <el-option label="2021" value="2021"></el-option>
              <el-option label="2022" value="2022"></el-option>
              <el-option label="2023" value="2023"></el-option>

            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="车辆" prop="cartype" placeholder="请选择沃尔沃或解放车">
            <el-select v-model="form.cartype">
              <el-option label="沃尔沃" value="沃尔沃"></el-option>
              <el-option label="解放车" value="解放车"></el-option>
            </el-select>
          </el-form-item>

        </el-col>
        <el-col :span="8">
          <el-form-item label="月份" prop="month">
            <el-select placeholder="请选择月份" v-model="form.month" allow-create filterable>
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
        </el-col>

        <el-col :span="8">
          <el-form-item label="类型" prop="type">
            <el-select placeholder="请选择或输入" v-model="form.type" allow-create filterable>

              <el-option label="干线明细（包含燃油费及过路费）" value="1"></el-option>
              <el-option label="新挂旧挂发车次数及收入" value="2"></el-option>
              <el-option label="各分公司发车次数" value="3"></el-option>


            </el-select>
          </el-form-item>

        </el-col>

        <el-col :span="8">
          <el-form-item label="sheet名称" prop="sheetname">
            <el-select placeholder="请选择或输入" v-model="form.type" allow-create filterable>

              <el-option label="干线明细" value="干线明细"></el-option>
              <el-option label="基础数据" value="基础数据"></el-option>


            </el-select>
          </el-form-item>

        </el-col>


      </el-form>
    </el-container>
    <el-container style="height: 80%">

      <div style="width: 360px;margin:0 auto;">

        <el-upload

            drag
            :data="form"
            accept=".xlsx,.xls"
            :on-success="uploadSuccess"
            :on-error="uploadError"
            action="http://localhost:9080/uploadall"
            multiple>
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">支持上传xlsx文件</div>
        </el-upload>

      </div>


    </el-container>
  </div>
</template>

<script>
module.exports = {
  data() {
    return {
      nextDisable: false,
      loading: false,
      tableData: [],
      active: 0,
      isCollapse: true,
      form: {
        year: '',
        type: '',
        cartype: '',
        month: '',
        sheetname: '',
      },

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
    uploadSuccess(response, file, fileList) {
      this.$message({
        showClose: true,
        message: response.resMessage,
        center: true,
        offset: 150,
        type: response.res
      });


    },
    uploadError(err, file, fileList) {
      this.$message({
        showClose: true,
        message: "上传失败",
        width: 200,
        height: 200,
        center: true,
        type: 'error'
      });
    }
    ,


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
