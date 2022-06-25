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
            label="No"
            width="50">
        </el-table-column>
        <el-table-column
            prop="cartype"
            label="类型"
            width="89">
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
            width="100">
        </el-table-column>
        <el-table-column
            prop="endcity"
            label="终点" width="100">
        </el-table-column>
        <el-table-column
            prop="startkilo"
            label="起始公里数" width="100">
        </el-table-column>
        <el-table-column
            prop="endkilo"
            label="结束公里数" width="100">
        </el-table-column>
        <el-table-column
            prop="kilo"
            label="行驶里程" width="80">
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
    </el-container>
    <el-container style="height: 10%; margin-top: 30px">
      <el-form :inline="true" ref="form" :model="form" label-width="80px">
        <el-form-item label="起点">
          <el-select v-model="form.startcity" placeholder="城市">
            <el-option label="全部" value=""></el-option>

            <el-option v-for ="(tmp,index) in startCitySelData" :label="tmp.value"  :key="tmp.key" :value="tmp.key"></el-option>

          </el-select>
        </el-form-item>

        <el-form-item label="终点">
          <el-select v-model="form.endcity" placeholder="城市">
            <el-option label="全部" value=""></el-option>
            <el-option v-for ="(tmp,index) in endCitySelData" :label="tmp.value"  :key="tmp.key" :value="tmp.key"></el-option>

          </el-select>
        </el-form-item>

        <el-form-item label="月份">
          <el-select v-model="form.month" placeholder="月份">
            <el-option label="全部" value=""></el-option>

            <el-option v-for ="(tmp,index) in monthselData" :label="tmp.value"  :key="tmp.key" :value="tmp.key"></el-option>

          </el-select>
        </el-form-item>
        <font size="2">共 <font color="red">{{size}}</font>   条数据</font>

      </el-form>

    </el-container>
    <el-container style="height: 5%">

       <div style="margin-left: 92%;"><el-button  type="primary" icon="el-icon-search" @click="submit()">查询</el-button></div>

    </el-container>
  </div>

</template>

<script>

module.exports = {
  data() {
    return {
      loading: true,
      tagsList: [],
      size:0,
      isCollapse: true,
      tableData: [],
      startCitySelData:[],
      endCitySelData:[],
      monthselData:[],
      form: {
        type:'解放车',
        startcity: '',
        endcity: '',
        month: '',
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

    this.loading = true
    fetch("http://localhost:9080/queryCitySelect", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json())
        .then(response => {
          this.startCitySelData = response.resList
     });

     fetch("http://localhost:9080/queryEndCitySelect", {
       method: 'POST', // 请求方法还可以是 put
       body: '',
       headers: new Headers({
         'Content-Type': 'application/json'
       })
     }).then(res => res.json())
         .then(response => {
           this.endCitySelData = response.resList
     });


     fetch("http://localhost:9080/queryJFMonthSelect", {
       method: 'POST', // 请求方法还可以是 put
       body: '',
       headers: new Headers({
         'Content-Type': 'application/json'
       })
     }).then(res => res.json())
         .then(response => {
           this.monthselData = response.resList
     });


    fetch("http://localhost:9080/queryDeaprtList", {
      method: 'POST', // 请求方法还可以是 put
      body: JSON.stringify({type:'解放车'}),
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json()).then(response => {
          this.tableData = response.resList
          this.size = response.resList.length
          this.loading = false
        });

  },
  methods: {

    tableRowClassName({row, rowIndex}) {

      if (rowIndex === 1) {
        return 'warning-row';
      } else if (rowIndex === 3) {
        return 'success-row';
      }
      return '';
    }
  ,

    submit() {
      let formData = new FormData();
      this.loading = true
      fetch("http://localhost:9080/queryDeaprtList", {
        method: 'POST', // 请求方法还可以是 put
        body: JSON.stringify(this.form),
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      }).then(res => res.json())
          .then(response => {
            this.tableData = response.resList
            this.size = response.resList.length
            this.$message({
              showClose: true,
              message: response.resMessage,
              center: true,
              offset: 150,
              type: response.res
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

.el-table .warning-row {
  background: red;
}

.el-table .success-row {
  background: #f0f9eb;
}

</style>
