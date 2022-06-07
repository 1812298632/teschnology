<template>
  <div class="home">
    <el-container style="height: 80%">
      <el-table
          v-loading="loading"
          :data="tableData"
          height="100%"
          border
          stripe
          style="width: 100%">
        <el-table-column
            prop="sheetid"
            label="序号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="carnum"
            label="编号"
            width="180">
        </el-table-column>
        <el-table-column
            prop="startcity"
            label="起点"
            width="180">
        </el-table-column>
        <el-table-column
            prop="endcity"
            label="终点">
        </el-table-column>
        <el-table-column
            prop="startkilo"
            label="开始里程">
        </el-table-column>
        <el-table-column
            prop="endkilo"
            label="结束里程">
        </el-table-column>
        <el-table-column
            prop="kilo"
            label="行驶里程">
        </el-table-column>
      </el-table>
    </el-container>
    <el-container style="height: 10%; margin-top: 30px">
      <el-form :inline="true" ref="form" :model="form" label-width="80px">
        <el-form-item label="活动名称">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="活动区域">
          <el-select v-model="form.region" placeholder="请选择活动区域">
            <el-option label="区域一" value="shanghai"></el-option>
            <el-option label="区域二" value="beijing"></el-option>
          </el-select>
        </el-form-item>

      </el-form>

    </el-container>
    <el-container style="height: 5%">

       <div style="margin-left: 95%;"><el-button  type="primary" icon="el-icon-search" @click="submit()">查询</el-button></div>

    </el-container>
  </div>

</template>

<script>

module.exports = {
  data() {
    return {
      loading: false,
      tagsList: [],
      isCollapse: true,
      tableData: [],
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      }
    };
  },

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
    /* isC() {
                console.log('Home....isC')
                this.isCollapse = !this.isCollapse
            } */
    submit() {
      this.loading = true
      fetch("http://localhost:9080/myquery", {
        method: 'POST', // 请求方法还可以是 put
        body: 'aaa=213123',
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      }).then(res => res.json())
          .then(response => {

            this.tableData = response
            this.$message({
              showClose: true,
              message: '查询成功',
              center: true,
              offset: 150,
              type: 'success'
            });
            this.loading = false
          });


      /*
      *  fetch("http://localhost:9080/myquery"+"?name=111", {
        method: 'GET', // 请求方法还可以是 put

        headers: new Headers({
          'Content-Type': 'application/json'
        })
      }).then(res => res.json())
          .then(response => {

            this.tableData = response
            this.$message({
              showClose: true,
              message: '查询成功',
              center: true,
              offset: 150,
              type: 'success'
            });
            this.loading = false
          });
      * */

    /*  $.ajax({
        url: "http://localhost:9080/myquery", // 请求路径
        type: "POST", //请求方式
        data: {"name": "zhangsan"},//请求参数
        dataType: "JSON", //设置接受到的响应数据的格式,还有很多格式，如:text
        //async:false,//默认是true（异步）,false（同步）
        success: function (data) {//响应成功后的回调函数
          alert(data);

          $("#div1").html(data);//数据渲染
        },
        error: function () {
          alert("出错啦...");
        },
      });*/

    }
  },
};
</script>

<style scoped>
.home {
  font-size: 24px;
  height: 100%;
  background-color: #ffffff;
}

.el-row {
  margin-bottom: 20px;

}

.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}

</style>
