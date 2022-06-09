<template>
  <div class="home">
    <el-container style="height: 10%">

    </el-container>
    <el-container style="height: 30%">
      <el-steps :active="active" finish-status="success" style="width: 90%">
        <el-step title="选择数据"></el-step>
        <el-step title="上传文件"></el-step>
        <el-step title="查看数据"></el-step>
      </el-steps>
      <el-button  type="primary" icon="el-icon-search" @click="submit()">查询</el-button>


      <!--      <el-form :inline="true" ref="form" :model="form" label-width="80px">
              <el-form-item label="导入名称">
                <el-select v-model="form.region" placeholder="请选择活动区域">
                  <el-option label="损益表" value="shanghai"></el-option>
                  <el-option label="台账" value="beijing"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="月份">
                <el-select v-model="form.month" placeholder="请选择月份">
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

            </el-form>-->
    </el-container>
    <el-container style="height: 30%">
      <el-upload
          class="upload-demo"
          drag
          action="http://localhost:9080/upload"
          multiple>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">上传excel/xlsx文件</div>
      </el-upload>
    </el-container>
  </div>
</template>

<script>
module.exports = {
  data() {
    return {
      active: 0,
      tagsList: [],
      isCollapse: true,
      form: {
        name: '',
        region: '',
        month:'',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
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
      submit() {
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
