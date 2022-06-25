<template>
  <div class="home">
    <el-container style="height: 80%">
<!--              style="border-collapse:collapse;width:880px;"
  -->
      <el-table
          class="el-table"
          v-loading="loading"
          ref="tableData"
          :data="tableData"
          height="100%"
          style="width:880px;"
          border
          show-summary
          stripe
          >
<!--宽度固定 style="border-collapse:collapse;width:880px;"         -->
        <el-table-column
            prop="cartype"
            label="类别"
            fixed="left"
            sortable
            width="100">
        </el-table-column>
        <el-table-column
            prop="carid"
            label="车牌"
            fixed="left"
            sortable
            width="130">
        </el-table-column>
        <el-table-column
            prop="columnname"
            fixed="left"
            sortable
            label="项目" width="150">
        </el-table-column>


        <el-table-column
            prop="onemonth"
            sortable
            label="一月" width="135">
        </el-table-column>

        <el-table-column
            prop="twomonth"
            sortable
            label="二月" width="130">
        </el-table-column>

        <el-table-column
            prop="threemonth"
            sortable
            label="三月" width="135">
        </el-table-column>
        <el-table-column
            prop="fourmonth"
            sortable
            label="四月" width="135">
        </el-table-column >
        <el-table-column
            prop="fivemonth"
            sortable
            label="五月" width="135">
        </el-table-column>
        <el-table-column
            prop="sixmonth"
            sortable
            label="六月" width="135">
        </el-table-column>
        <el-table-column
            prop="sevenmonth"
            sortable
            label="七月" width="135">
        </el-table-column>
        <el-table-column
            prop="eightmonth"
            sortable
            label="八月" width="135">
        </el-table-column>
        <el-table-column
            prop="ninemonth"
            sortable
            label="九月" width="135">
        </el-table-column>
        <el-table-column
            prop="tenmonth"
            sortable
            label="十月" width="135">
        </el-table-column>
        <el-table-column
            prop="eleventmonth"
            sortable
            label="十一月" width="135">
        </el-table-column>
        <el-table-column
            prop="twelvemonth"
            sortable
            label="十二月" width="135">
        </el-table-column>

      </el-table>
    </el-container>
    <el-container style="height: 10%; margin-top: 30px">
      <el-form :inline="true" ref="form" :model="form" >
        <el-form-item label="车辆类型">
          <el-select v-model="form.cartype" placeholder="选择沃尔沃或解放">
            <el-option label="全部" value=""></el-option>

            <el-option v-for ="(tmp,index) in cartypeSelData" :label="tmp.value"  :key="tmp.key" :value="tmp.key"></el-option>

          </el-select>
        </el-form-item>
        <el-form-item label="车牌">
          <el-select v-model="form.carid" placeholder="车牌号">
            <el-option label="全部" value=""></el-option>

            <el-option v-for ="(tmp,index) in caridSelData" :label="tmp.value"  :key="tmp.key" :value="tmp.key"></el-option>

          </el-select>
        </el-form-item>

        <el-form-item label="项目">
          <el-select v-model="form.columnname" placeholder="项目">
            <el-option label="全部" value=""></el-option>
            <el-option v-for ="(tmp,index) in columnnameSelData" :label="tmp.value"  :key="tmp.key" :value="tmp.key"></el-option>

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
      loading: false,
      tagsList: [],
      size:0,
      isCollapse: true,
      tableData: [],
      caridSelData:[],
      cartypeSelData:[],
      columnnameSelData:[],
      form: {
        carid:'',
        columnname: '',
        cartype: '',
      }
    };
  },
  updated() {
    this.$nextTick(() => {
      this.$refs['tableData'].doLayout();
    })
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
          this.startCitySelData = response.resList
     });

    fetch("http://localhost:9080/querycarid", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json())
        .then(response => {
          this.caridSelData = response.resList
    });


    fetch("http://localhost:9080/querycarTypeByIncome", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json())
        .then(response => {
          this.cartypeSelData = response.resList
    });


    fetch("http://localhost:9080/querycolumnsByIncome", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json())
        .then(response => {
          this.columnnameSelData = response.resList
        });



    this.loading = true
    fetch("http://localhost:9080/queryIncomeList", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json())
        .then(response => {
          this.tableData = response.resList
          this.size = response.resList.length
        });

    this.loading = false
  },
  methods: {


    submit() {
      let formData = new FormData();
      this.loading = true
      fetch("http://localhost:9080/queryIncomeList", {
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

.el-table { overflow: visible !important; }
</style>
