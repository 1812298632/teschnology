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
            prop="year"
            label="年份"
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
        <!--        <el-table-column
                    prop="excelname"
                    label="来源excel名称">
                </el-table-column>-->
        <el-table-column
            label="操作"
            width="100">
          <template slot-scope="scope">
            <el-button @click="editclick(scope.row)" type="text" size="small">编辑</el-button>
            <el-button type="text" @click="delclick(scope.row)" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

    </el-container>
    <el-container style="height: 10%; margin-top: 30px">
      <el-form :inline="true" ref="form" :model="form" size="small " label-position="right">
          <el-form-item label="车辆类型">
            <el-select v-model="form.type" placeholder="选择沃尔沃或解放">
              <el-option label="全部" value=""></el-option>

              <el-option v-for="(type,index) in cartypeSelData" :label="type.value" :key="type.key"
                         :value="type.key"></el-option>

            </el-select>
          </el-form-item>
          <el-form-item label="年份">
            <el-select v-model="form.year">
              <el-option label="全部" value=""></el-option>

              <el-option v-for="(year,index) in yearselData" :label="year.value" :key="year.key"
                         :value="year.key"></el-option>

            </el-select>
          </el-form-item>
          <el-form-item label="月份">
            <el-select v-model="form.month" placeholder="月份">
              <el-option label="全部" value=""></el-option>

              <el-option v-for="(month,index) in monthselData" :label="month.value" :key="month.key"
                         :value="month.key"></el-option>

            </el-select>
          </el-form-item>

          <el-form-item label="起点">
            <el-select v-model="form.startcity" placeholder="城市">
              <el-option label="全部" value=""></el-option>

              <el-option v-for="(city,index) in startCitySelData" :label="city.value" :key="city.key"
                         :value="city.key"></el-option>

            </el-select>
          </el-form-item>
<!--          <el-form-item label="终点">
            <el-select v-model="form.endcity" placeholder="城市">
              <el-option label="全部" value=""></el-option>
              <el-option v-for="(city,index) in endCitySelData" :label="city.value" :key="city.key"
                         :value="city.key"></el-option>

            </el-select>
          </el-form-item>-->
        <font size="2">共<font color="red">{{ size }}</font>条数据</font>


      </el-form>

      <el-drawer
          :title="diatitle"
          :before-close="handleClose"
          :visible.sync="dialog"
          direction="rtl"
          custom-class="demo-drawer"
          ref="drawer"
      >
        <div>
          <el-col :span="24">
            <el-collapse accordion style="margin-left: 10px">
              <el-collapse-item>
                <template slot="title">
                  tip1：点击保存后，数据才会真正保存 <i class="header-icon el-icon-info"></i>
                </template>
              </el-collapse-item>


            </el-collapse>
          </el-col>

          <el-col :span="24">
            <el-collapse accordion style="margin-left: 10px">
              <el-collapse-item>
                <template slot="title">
                  tip2：未点击保存，数据仅在当前界面显示，刷新后将恢复 <i class="header-icon el-icon-info"></i>
                </template>

              </el-collapse-item>

            </el-collapse>
          </el-col>

          <el-col :span="24">
            <el-collapse accordion style="margin-left: 10px">
              <el-collapse-item>
                <template slot="title">
                  当前正在修改 {{ diatitle }} {{ editform.month }} 月份，编号为 {{ editform.sheetid }} 的数据
                </template>

              </el-collapse-item>

            </el-collapse>
          </el-col>

          <el-form :model="editform" style="margin-left: 15px">


            <el-col :span="24" style="margin-top: 10px">
              <el-form-item align="left" label="起点" :label-width="formLabelWidth">
                <el-input v-model="editform.startcity" style="width: 80%"></el-input>
              </el-form-item>
            </el-col>


            <el-col :span="24">
              <el-form-item align="left" label="终点" :label-width="formLabelWidth">
                <el-input v-model="editform.endcity" style="width: 80%"></el-input>
              </el-form-item>
            </el-col>


            <el-col :span="24">
              <el-form-item align="left" label="起始公里数" :label-width="formLabelWidth">
                <el-input v-model="editform.startkilo" style="width: 80%"></el-input>
              </el-form-item>
            </el-col>


            <el-col :span="24">
              <el-form-item align="left" label="结束公里数" :label-width="formLabelWidth">
                <el-input v-model="editform.endkilo" style="width: 80%"></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="24">
              <el-form-item align="left" label="行驶里程" :label-width="formLabelWidth">
                <el-input v-model="editform.kilo" style="width: 80%"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6"><label>
              <el-tooltip class="item" effect="dark" content="此条数据在生成excel时，是否算作一次发车次数" placement="top-start"><font
                  size="2">是否算做发车次数</font></el-tooltip>
            </label>
            </el-col>
            <el-col :span="5">
              <!--  :content="editform.ismonthcount"             -->
              <el-tooltip placement="top" content="此条数据在生成excel时，是否算作一次发车次数">
                <el-switch
                    v-model="editform.ismonthcount"
                    active-color="#13ce66"
                    active-value="1"
                    inactive-value="0">
                </el-switch>
              </el-tooltip>
            </el-col>


            <el-col :span="6"><label>
              <el-tooltip class="item" effect="dark" content="此条数据在生成excel时，是否算在月度总里程中" placement="top-start"><font
                  size="2">是否算做总里程</font></el-tooltip>
            </label>
            </el-col>
            <el-col :span="5">
              <!--  :content="editform.ismonthcount"             -->
              <el-tooltip placement="top" content="此条数据在生成excel时，是否算在月度总里程中">
                <el-switch
                    v-model="editform.iskilosum"
                    active-color="#13ce66"
                    active-value="1"
                    inactive-value="0">
                </el-switch>
              </el-tooltip>
            </el-col>

          </el-form>
          <div class="demo-drawer__footer">
            <el-button @click="cancelForm">取 消</el-button>
            <el-button type="primary" @click="edit" :loading="buttonloading">
              保存
            </el-button>
          </div>
        </div>

      </el-drawer>


    </el-container>
    <el-container style="height: 5%">
      <div style="margin-left: 92%;">
        <el-button type="primary" icon="el-icon-search" @click="submit()">查询</el-button>
      </div>

    </el-container>
  </div>

</template>

<script>

module.exports = {
  data() {
    return {
      diatitle: "",
      dialog: false,
      buttonloading: false,
      loading: true,
      tagsList: [],
      formLabelWidth: '90px',
      isCollapse: true,
      size: 0,
      tableData: [],
      cartypeSelData: [],
      startCitySelData: [],
      endCitySelData: [],
      monthselData: [],
      yearselData: [],
      form: {
        year: '',
        type: '',
        startcity: '',
        endcity: '',
        month: '',
      },
      editform: {
        sheetid: '',
        cartype: '',
        month: '',
        fromno: '',
        carnum: '',
        startcity: '',
        endcity: '',
        startkilo: '',
        endkilo: '',
        kilo: '',
        ismonthcount: '',
        iskilosum: ''
      }
    };
  },
  watch: {},
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
    }).then(res => res.json()).then(response => {
      this.startCitySelData = response.resList
    });

    fetch("http://localhost:9080/queryEndCitySelect", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json()).then(response => {
      this.endCitySelData = response.resList
    });


    fetch("http://localhost:9080/queryMonthSelect", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json()).then(response => {
      this.monthselData = response.resList
    });


    fetch("http://localhost:9080/queryCarTypeByDepart", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json()).then(response => {
      this.cartypeSelData = response.resList
    });

    fetch("http://localhost:9080/queryYearSelect", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json()).then(response => {
      this.yearselData = response.resList
    });

    fetch("http://localhost:9080/queryDeaprtList", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
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
    editclick(row) {
      this.dialog = true

      this.editform = row

      this.diatitle = row.cartype
    },

    delclick(row) {
      this.editform = row
      fetch("http://localhost:9080/delDepart", {
        method: 'POST', // 请求方法还可以是 put
        body: JSON.stringify(this.editform),
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      }).then(res => res.json()).then(response => {


        this.$message({
          showClose: true,
          message: response.resMessage,
          center: true,
          offset: 150,
          type: response.res
        });
        this.loading = true
        fetch("http://localhost:9080/queryDeaprtList", {
          method: 'POST', // 请求方法还可以是 put
          body: JSON.stringify(this.form),
          headers: new Headers({
            'Content-Type': 'application/json'
          })
        }).then(res1 => res1.json()).then(response1 => {
          this.tableData = response1.resList
          this.size = response1.resList.length
          this.loading = false
        });


      });


    },
    handleClose(done) {

      this.cancelForm();
    },
    cancelForm() {
      this.buttonloading = false;
      this.dialog = false;
    },
    edit() {


      this.buttonloading = true
      fetch("http://localhost:9080/updateDepart", {
        method: 'POST', // 请求方法还可以是 put
        body: JSON.stringify(this.editform),
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      }).then(res => res.json()).then(response => {

        if (response.res == 'error') {
          this.$message({
            showClose: true,
            message: response.resMessage,
            center: true,
            offset: 150,
            type: response.res
          });

          this.loading = true
          fetch("http://localhost:9080/queryDeaprtList", {
            method: 'POST', // 请求方法还可以是 put
            body: JSON.stringify(this.form),
            headers: new Headers({
              'Content-Type': 'application/json'
            })
          }).then(res1 => res1.json()).then(response1 => {
            this.tableData = response1.resList
            this.size = response1.resList.length
            this.loading = false
          });

        } else {
          this.$message({
            showClose: true,
            message: response.resMessage,
            center: true,
            offset: 150,
            type: response.res
          });
        }


        this.buttonloading = false
        this.dialog = false;
      });


    },


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


.el-table {
  overflow: visible !important;
}


.el-drawer__body {

  overflow-y: scroll;

  margin-bottom: 50px;

}

.demo-drawer__footer {

  width: 90%;

  position: absolute;

  bottom: 0;

  left: 0;

  border-top: 1px solid #e8e8e8;

  padding: 10px 16px;

  text-align: right;

  background-color: white;


}


</style>
