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
            prop="year"
            label="年度"
            fixed="left"
            sortable
            width="80">
        </el-table-column>
        <el-table-column
            prop="columnname"
            fixed="left"
            sortable
            label="项目" width="100">
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
        </el-table-column>
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

        <el-table-column
            fixed="right"
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
      <el-form :inline="true" ref="form" :model="form" size="small ">
        <el-form-item label="车辆类型">
          <el-select v-model="form.cartype" placeholder="选择沃尔沃或解放">
            <el-option label="全部" value=""></el-option>

            <el-option v-for="(tmp,index) in cartypeSelData" :label="tmp.value" :key="tmp.key"
                       :value="tmp.key"></el-option>

          </el-select>
        </el-form-item>

        <el-form-item label="年份">
          <el-select v-model="form.year">
            <el-option label="全部" value=""></el-option>

            <el-option v-for="(year,index) in yearselData" :label="year.value" :key="year.key"
                       :value="year.key"></el-option>

          </el-select>
        </el-form-item>
        <el-form-item label="项目">
          <el-select v-model="form.columnname" placeholder="项目">
            <el-option label="全部" value=""></el-option>
            <el-option v-for="(tmp,index) in columnnameSelData" :label="tmp.value" :key="tmp.key"
                       :value="tmp.key"></el-option>

          </el-select>
        </el-form-item>


        <font size="2">共 <font color="red">{{ size }}</font> 条数据</font>

      </el-form>
    </el-container>

    <el-drawer
        :title="diatitle"
        :before-close="handleClose"
        :visible.sync="dialog"
        direction="rtl"
        custom-class="demo-drawer"
        ref="drawer"
    >
      <div>


        <el-form :model="editform" style="margin-top: 20px">

          <!--          <el-col :span="24" style="margin-top: 10px">
                      <el-form-item >
                        <el-radio-group v-model="editform.resource" size="medium">
                          <el-radio border label="线上品牌商赞助"></el-radio>
                          <el-radio border label="线下场地免费"></el-radio>
                        </el-radio-group>
                      </el-form-item>
                    </el-col>-->
          <el-col :span="24" align="left" style="margin-left: 20px">
            <el-form-item label="" prop="type">
              <el-radio-group v-model="editform.columnname">
                <el-radio label="加油升数"></el-radio>
                <el-radio label="节油升数"></el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="12" align="left" style="margin-top: 10px">
            <el-form-item label="年份" prop="cartype" :label-width="formLabelWidth">
              <el-select v-model="editform.year" allow-create filterable style="width: 80%">
                <el-option label="2020" value="2020"></el-option>
                <el-option label="2021" value="2021"></el-option>
                <el-option label="2022" value="2022"></el-option>
                <el-option label="2023" value="2023"></el-option>


              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12" align="left" style="margin-top: 10px">
            <el-form-item label="属于" prop="cartype" :label-width="formLabelWidth">
              <el-select v-model="editform.cartype" allow-create filterable style="width: 80%">
                <el-option label="沃尔沃" value="沃尔沃"></el-option>
                <el-option label="解放车" value="解放车"></el-option>

              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="12" style="margin-top: 10px">
            <el-form-item align="left" label="1月" :label-width="formLabelWidth">
              <el-input v-model="editform.onemonth" style="width: 80%"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12" style="margin-top: 10px">
            <el-form-item align="left" label="2月" :label-width="formLabelWidth">
              <el-input v-model="editform.twomonth" style="width: 80%"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item align="left" label="3月" :label-width="formLabelWidth">
              <el-input v-model="editform.threemonth" style="width: 80%"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item align="left" label="4月" :label-width="formLabelWidth">
              <el-input v-model="editform.fourmonth" style="width: 80%"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item align="left" label="5月" :label-width="formLabelWidth">
              <el-input v-model="editform.fivemonth" style="width: 80%"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item align="left" label="6月" :label-width="formLabelWidth">
              <el-input v-model="editform.sixmonth" style="width: 80%"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item align="left" label="7月" :label-width="formLabelWidth">
              <el-input v-model="editform.sevenmonth" style="width: 80%"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item align="left" label="8月" :label-width="formLabelWidth">
              <el-input v-model="editform.eightmonth" style="width: 80%"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item align="left" label="9月" :label-width="formLabelWidth">
              <el-input v-model="editform.ninemonth" style="width: 80%"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item align="left" label="10月" :label-width="formLabelWidth">
              <el-input v-model="editform.tenmonth" style="width: 80%"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item align="left" label="11月" :label-width="formLabelWidth">
              <el-input v-model="editform.eleventmonth" style="width: 80%"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item align="left" label="12月" :label-width="formLabelWidth">
              <el-input v-model="editform.twelvemonth" style="width: 80%"></el-input>
            </el-form-item>
          </el-col>
        </el-form>
        <div class="demo-drawer__footer">
          <el-button @click="cancelForm">取 消</el-button>
          <el-button type="primary" @click="edit" :loading="buttonloading" :disabled="editDis">
            修改
          </el-button>
          <el-button type="primary" @click="addFule" :loading="buttonloading" :disabled="addDis">
            保存
          </el-button>
        </div>
      </div>

    </el-drawer>
    <el-container style="height: 5%">
      <div style="margin-left: 80%;">
        <el-button type="primary" icon="el-icon-plus" @click="add('add')">新增</el-button>
      </div>

      <div style="margin-left: 2%;">
        <el-button type="primary" icon="el-icon-search" @click="submit()">查询</el-button>
      </div>

    </el-container>
  </div>

</template>

<script>

module.exports = {
  data() {
    return {
      isEdit: false,
      editDis: true,
      addDis: true,
      diatitle: "",
      dialog: false,
      loading: false,
      buttonloading: false,
      tagsList: [],
      size: 0,
      formLabelWidth: '50px',
      isCollapse: true,
      tableData: [],
      form: {
        year: '',
        carid: '',
        columnname: '',
        cartype: '',
      },
      editform: {
        year: '',
        cartype: '',
        carid: '',
        columnname: '',
        onemonth: '',
        twomonth: '',
        threemonth: '',
        fourmonth: '',
        fivemonth: '',
        sixmonth: '',
        sevenmonth: '',
        eightmonth: '',
        ninemonth: '',
        tenmonth: '',
        eleventmonth: '',
        twelvemonth: '',
      },
      cartypeSelData:[],
      yearselData:[],
      columnnameSelData:[],
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


    this.loading = true
    fetch("http://localhost:9080/queryFuleList", {
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


    fetch("http://localhost:9080/quertFuleColumns", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json())
        .then(response => {
          this.columnnameSelData = response.resList
        });


    fetch("http://localhost:9080/queryFuleType", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json())
        .then(response => {
          this.cartypeSelData = response.resList
        });


    fetch("http://localhost:9080/queryFuleYear", {
      method: 'POST', // 请求方法还可以是 put
      body: '',
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json())
        .then(response => {
          this.yearselData = response.resList
        });


  },
  methods: {
    editclick(row) {
      this.editform = {
        year: '',
        cartype: '',
        columnname: '',
        onemonth: '',
        twomonth: '',
        threemonth: '',
        fourmonth: '',
        fivemonth: '',
        sixmonth: '',
        sevenmonth: '',
        eightmonth: '',
        ninemonth: '',
        tenmonth: '',
        eleventmonth: '',
        twelvemonth: '',
      };


      this.isEdit = true;
      this.addDis = true;
      this.editDis = false;
      this.dialog = true

      console.log(row)
      this.editform = row
      this.diatitle = row.cartype
    },
    delclick(row) {
      this.editform = row
      fetch("http://localhost:9080/deleteFule", {
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
        fetch("http://localhost:9080/queryFuleList", {
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
    addFule() {

      if (this.editform.year == '') {
        this.$message({
          showClose: true,
          message: "年份不能为空",
          center: true,
          offset: 150,
          type: 'error'
        });
        return
      }

      if (this.editform.columnname == '') {
        this.$message({
          showClose: true,
          message: "请选择 加油升数 或 节油升数",
          center: true,
          offset: 150,
          type: 'error'
        });
        return
      }

      if (this.editform.cartype == '') {
        this.$message({
          showClose: true,
          message: "请选择属于 沃尔沃 或 解放车",
          center: true,
          offset: 150,
          type: 'error'
        });
        return
      }


      this.buttonloading = true
      fetch("http://localhost:9080/insertFule", {
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
          fetch("http://localhost:9080/queryFuleList", {
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

          this.loading = true
          fetch("http://localhost:9080/queryFuleList", {
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
        }


        this.buttonloading = false
        this.dialog = false;
      });


    },
    edit() {
      console.log(JSON.stringify(this.editform))
      this.buttonloading = true
      fetch("http://localhost:9080/updateFule", {
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
          fetch("http://localhost:9080/queryFuleList", {
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

    handleClose(done) {

      this.cancelForm();
    },
    cancelForm() {
      this.buttonloading = false;
      this.dialog = false;
    },
    add() {
      this.addDis = false;
      this.editDis = true;
      this.isEdit = false;
      this.editform = {
        year: '',
        cartype: '',
        carid: '',
        columnname: '',
        onemonth: '',
        twomonth: '',
        threemonth: '',
        fourmonth: '',
        fivemonth: '',
        sixmonth: '',
        sevenmonth: '',
        eightmonth: '',
        ninemonth: '',
        tenmonth: '',
        eleventmonth: '',
        twelvemonth: '',
      };

      this.dialog = true
      //this.editform = row
      this.diatitle = "数据新增";

      this.editform.type = ''
    },
    submit() {
      let formData = new FormData();
      this.loading = true
      fetch("http://localhost:9080/queryFuleList", {
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
