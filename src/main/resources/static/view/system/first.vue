<template>
  <div class="home">
    <el-container style="height: 80%">
      <el-table
          v-loading="loading"
          :data="tableData"
          height="100%"
          style="width: 100%"
          border
          stripe
          >
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
        <el-form-item label="活动区域">
          <el-select v-model="form.city" placeholder="城市">
            <el-option v-for ="city in selectData" :label="city.value"  :key="city.key" :value="city.key"></el-option>

          </el-select>
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
      selectData:[],
      form: {
        name: '',
        region: '',
        city: '',
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


    fetch("http://localhost:9080/queryCitySelect", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json())
        .then(response => {
          this.selectData = response.resList
        });

  },
  methods: {
    /* isC() {
                console.log('Home....isC')
                this.isCollapse = !this.isCollapse
            } */
    submit() {
      //将"key1=value1&key2=valu2" 形式封装整FromData形式
      let formData = new FormData();
//new URLSearchParams([["foo", 1],["bar", 2]]).toString()
      this.loading = true
      fetch("http://localhost:9080/myquery", {
        method: 'POST', // 请求方法还可以是 put
        body: JSON.stringify({username:'111',password:'222'}),
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
