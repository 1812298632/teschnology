<template>
  <div class="home" style="height: 100%">


    <el-container>
      <el-dropdown @command="handleType" style="margin-left: 82%;">
  <span class="el-dropdown-link">
    {{ checktype }}<i class="el-icon-arrow-down el-icon--right"></i>
  </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="总损益表">总损益表</el-dropdown-item>
          <el-dropdown-item command="沃尔沃">沃尔沃</el-dropdown-item>
          <el-dropdown-item command="解放车">解放车</el-dropdown-item>
          <el-dropdown-item command="德龙">德龙</el-dropdown-item>

        </el-dropdown-menu>
      </el-dropdown>

      <el-dropdown @command="handleYear">
  <span class="el-dropdown-link">
    {{ checkyear }}<i class="el-icon-arrow-down el-icon--right"></i>
  </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="2021">2021</el-dropdown-item>
          <el-dropdown-item command="2022">2022</el-dropdown-item>
          <el-dropdown-item command="2023">2023</el-dropdown-item>
          <el-dropdown-item command="2024">2024</el-dropdown-item>

        </el-dropdown-menu>
      </el-dropdown>
    </el-container>
    <el-container v-show="showDetail" style="margin-top: 3%">

      <el-card class="box-card" style="width: 17%">
        <div slot="header" class="clearfix">
          <span>发车</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createDepartCount()">生成
          </el-button>
        </div>
        <div v-for="(list,index) in this.countList" :key="index" class="text item">
          <font size="2"> <font size="2" color="#ff291a">{{ list.key }} </font>月份发车次数为
            <font size="2" color="#ff291a">{{ list.value }}</font></font>


        </div>
        <font size="2">
          <el-link href="http://localhost:9080/page#/uploaddepart" type="primary" target="_blank">其余月份没有数据导入</el-link>
        </font>
      </el-card>


      <el-card class="box-card" style="width: 17%;margin-left: 3%">
        <div slot="header" class="clearfix">
          <span>过路费</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createGlf()">生成</el-button>
        </div>
        <div v-for="(list,index) in this.glfList" :key="index" class="text item">
          <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
          <font size="2" color="#ff291a" > 1 月 {{ list.onemonth }}</font><br/>
          <font size="2" color="#ff291a"> 2 月 {{ list.twomonth }}</font><br/>
          <font size="2" color="#ff291a"> 3 月 {{ list.threemonth }}</font><br/>
          <font size="2" color="#ff291a"> 4 月 {{ list.fourmonth }}</font><br/>
          <font size="2" color="#ff291a"> 5 月 {{ list.fivemonth }}</font><br/>
          <font size="2" color="#ff291a"> 6 月 {{ list.sixmonth }}</font><br/>
          <font size="2" color="#ff291a"> 7 月 {{ list.sevenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 8 月 {{ list.eightmonth }}</font><br/>
          <font size="2" color="#ff291a"> 9 月 {{ list.ninemonth }}</font><br/>
          <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
          <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>


        </div>
      </el-card>

      <el-card class="box-card" style="width: 17%;margin-left: 3%">
        <div slot="header" class="clearfix">
          <span>燃油费</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createRy()">生成</el-button>
        </div>
        <div v-for="(list,index) in this.ryList" :key="index" class="text item">
          <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
          <font size="2" color="#ff291a"> 1 月{{ list.onemonth }}</font><br/>
          <font size="2" color="#ff291a"> 2 月{{ list.twomonth }}</font><br/>
          <font size="2" color="#ff291a"> 3 月{{ list.threemonth }}</font><br/>
          <font size="2" color="#ff291a"> 4 月{{ list.fourmonth }}</font><br/>
          <font size="2" color="#ff291a"> 5 月{{ list.fivemonth }}</font><br/>
          <font size="2" color="#ff291a"> 6 月{{ list.sixmonth }}</font><br/>
          <font size="2" color="#ff291a"> 7 月{{ list.sevenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 8 月{{ list.eightmonth }}</font><br/>
          <font size="2" color="#ff291a"> 9 月{{ list.ninemonth }}</font><br/>
          <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
          <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>


        </div>
      </el-card>


      <el-card class="box-card" style="width: 17%;margin-left: 3%">
        <div slot="header" class="clearfix">
          <span>罚款</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createFk()">生成</el-button>
        </div>
        <div v-for="(list,index) in this.fkList" :key="index" class="text item" >
          <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
          <font size="2" color="#ff291a" > 1 月{{ list.onemonth }}</font><br/>
          <font size="2" color="#ff291a" > 2 月{{ list.twomonth }}</font><br/>
          <font size="2" color="#ff291a" > 3 月{{ list.threemonth }}</font><br/>
          <font size="2" color="#ff291a" > 4 月{{ list.fourmonth }}</font><br/>
          <font size="2" color="#ff291a" > 5 月{{ list.fivemonth }}</font><br/>
          <font size="2" color="#ff291a" > 6 月{{ list.sixmonth }}</font><br/>
          <font size="2" color="#ff291a" > 7 月{{ list.sevenmonth }}</font><br/>
          <font size="2" color="#ff291a" > 8 月{{ list.eightmonth }}</font><br/>
          <font size="2" color="#ff291a" > 9 月{{ list.ninemonth }}</font><br/>
          <font size="2" color="#ff291a" > 10月 {{ list.tenmonth }}</font><br/>
          <font size="2" color="#ff291a" > 11月 {{ list.eleventmonth }}</font><br/>
          <font size="2" color="#ff291a" > 12月 {{ list.twelvemonth }}</font>


        </div>
      </el-card>

      <el-card class="box-card" style="width: 17%;margin-left: 3%">
        <div slot="header" class="clearfix">
          <span>停车费</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createTc()">生成</el-button>
        </div>
        <div v-for="(list,index) in this.tcList" :key="index" class="text item">
          <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
          <font size="2" color="#ff291a"> 1 月{{ list.onemonth }}</font><br/>
          <font size="2" color="#ff291a"> 2 月{{ list.twomonth }}</font><br/>
          <font size="2" color="#ff291a"> 3 月{{ list.threemonth }}</font><br/>
          <font size="2" color="#ff291a"> 4 月{{ list.fourmonth }}</font><br/>
          <font size="2" color="#ff291a"> 5 月{{ list.fivemonth }}</font><br/>
          <font size="2" color="#ff291a"> 6 月{{ list.sixmonth }}</font><br/>
          <font size="2" color="#ff291a"> 7 月{{ list.sevenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 8 月{{ list.eightmonth }}</font><br/>
          <font size="2" color="#ff291a"> 9 月{{ list.ninemonth }}</font><br/>
          <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
          <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>


        </div>
      </el-card>


    </el-container>
    <el-container v-show="showDetail" style="margin-top: 3%">
      <el-card class="box-card" style="width: 17%">
        <div slot="header" class="clearfix">
          <span>轮胎费</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createLt()">生成
          </el-button>
        </div>
        <div v-for="(list,index) in this.ltList" :key="index" class="text item">
          <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
          <font size="2" color="#ff291a"> 1 月{{ list.onemonth }}</font><br/>
          <font size="2" color="#ff291a"> 2 月{{ list.twomonth }}</font><br/>
          <font size="2" color="#ff291a"> 3 月{{ list.threemonth }}</font><br/>
          <font size="2" color="#ff291a"> 4 月{{ list.fourmonth }}</font><br/>
          <font size="2" color="#ff291a"> 5 月{{ list.fivemonth }}</font><br/>
          <font size="2" color="#ff291a"> 6 月{{ list.sixmonth }}</font><br/>
          <font size="2" color="#ff291a"> 7 月{{ list.sevenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 8 月{{ list.eightmonth }}</font><br/>
          <font size="2" color="#ff291a"> 9 月{{ list.ninemonth }}</font><br/>
          <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
          <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>


        </div>

      </el-card>


      <el-card class="box-card" style="width: 17%;margin-left: 3%">
        <div slot="header" class="clearfix">
          <span>维修费</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createWx()">生成
          </el-button>
        </div>
        <div v-for="(list,index) in this.wxList" :key="index" class="text item">
          <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
          <font size="2" color="#ff291a"> 1 月{{ list.onemonth }}</font><br/>
          <font size="2" color="#ff291a"> 2 月{{ list.twomonth }}</font><br/>
          <font size="2" color="#ff291a"> 3 月{{ list.threemonth }}</font><br/>
          <font size="2" color="#ff291a"> 4 月{{ list.fourmonth }}</font><br/>
          <font size="2" color="#ff291a"> 5 月{{ list.fivemonth }}</font><br/>
          <font size="2" color="#ff291a"> 6 月{{ list.sixmonth }}</font><br/>
          <font size="2" color="#ff291a"> 7 月{{ list.sevenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 8 月{{ list.eightmonth }}</font><br/>
          <font size="2" color="#ff291a"> 9 月{{ list.ninemonth }}</font><br/>
          <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
          <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>


        </div>

      </el-card>


      <el-card class="box-card" style="width: 17%;margin-left: 3%">
        <div slot="header" class="clearfix">
          <span>辅料</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createfl()">生成
          </el-button>
        </div>
        <div v-for="(list,index) in this.flList" :key="index" class="text item">
          <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
          <font size="2" color="#ff291a"> 1 月{{ list.onemonth }}</font><br/>
          <font size="2" color="#ff291a"> 2 月{{ list.twomonth }}</font><br/>
          <font size="2" color="#ff291a"> 3 月{{ list.threemonth }}</font><br/>
          <font size="2" color="#ff291a"> 4 月{{ list.fourmonth }}</font><br/>
          <font size="2" color="#ff291a"> 5 月{{ list.fivemonth }}</font><br/>
          <font size="2" color="#ff291a"> 6 月{{ list.sixmonth }}</font><br/>
          <font size="2" color="#ff291a"> 7 月{{ list.sevenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 8 月{{ list.eightmonth }}</font><br/>
          <font size="2" color="#ff291a"> 9 月{{ list.ninemonth }}</font><br/>
          <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
          <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>


        </div>

      </el-card>

      <el-card style="width: 17%;margin-left: 3%">
        <div slot="header">
          <span>百公里油耗</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createyh()">生成
          </el-button>
        </div>
        <div v-for="(list,index) in this.fuleList" :key="index" class="text item">
          <font size="2">{{ list.columnname }}</font><br/>
          <font size="2" color="#ff291a"> 1 月{{ list.onemonth }}</font><br/>
          <font size="2" color="#ff291a"> 2 月{{ list.twomonth }}</font><br/>
          <font size="2" color="#ff291a"> 3 月{{ list.threemonth }}</font><br/>
          <font size="2" color="#ff291a"> 4 月{{ list.fourmonth }}</font><br/>
          <font size="2" color="#ff291a"> 5 月{{ list.fivemonth }}</font><br/>
          <font size="2" color="#ff291a"> 6 月{{ list.sixmonth }}</font><br/>
          <font size="2" color="#ff291a"> 7 月{{ list.sevenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 8 月{{ list.eightmonth }}</font><br/>
          <font size="2" color="#ff291a"> 9 月{{ list.ninemonth }}</font><br/>
          <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
          <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>
        </div>

      </el-card>

      <el-card style="width: 17%;margin-left: 3%">
        <div slot="header">
          <span>毛利</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createJfml()">生成
          </el-button>
        </div>
        <div v-for="(list,index) in this.otherList" :key="index" class="text item">
          <font size="3" color="#dda0dd">{{ list.carid }} </font><br/>
          <font size="2">{{ list.columnname }}</font><br/>
          <font size="2" color="#ff291a"> 1 月{{ list.onemonth }}</font><br/>
          <font size="2" color="#ff291a"> 2 月{{ list.twomonth }}</font><br/>
          <font size="2" color="#ff291a"> 3 月{{ list.threemonth }}</font><br/>
          <font size="2" color="#ff291a"> 4 月{{ list.fourmonth }}</font><br/>
          <font size="2" color="#ff291a"> 5 月{{ list.fivemonth }}</font><br/>
          <font size="2" color="#ff291a"> 6 月{{ list.sixmonth }}</font><br/>
          <font size="2" color="#ff291a"> 7 月{{ list.sevenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 8 月{{ list.eightmonth }}</font><br/>
          <font size="2" color="#ff291a"> 9 月{{ list.ninemonth }}</font><br/>
          <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
          <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>
        </div>

      </el-card>


    </el-container>
  </div>
</template>

<script>
module.exports = {
  data() {
    return {
      checkyear: '请选择年度',
      checktype: '请选择车辆类型',
      showDetail: false,
      tagsList: [],
      countList: [],
      glfList: [],
      ryList: [],
      fkList: [],
      tcList: [],
      ltList: [],
      wxList: [],
      flList: [],
      otherList: [],
      form: {
        year: '',
        cartype: '',
        columnname:''
      },
      isCollapse: true,
      fuleList :[]
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
    createDepartCount() {

      if (this.countList.length == 0) {
        this.$notify({
          title: '提示',
          message: '暂未有发车数据导入',
          type: 'warning'
        });

        return
      } else {
        fetch("http://localhost:9080/exportDepart", {
          method: 'POST', // 请求方法还可以是 put
          body: JSON.stringify(this.form),
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


    },
    createfl() {
      if (this.flList.length == 0) {
        this.$notify({
          title: '提示',
          message: '暂未有辅料数据导入',
          type: 'warning'
        });
      } else {
        this.form.columnname = this.flList[0].columnname
        fetch("http://localhost:9080/exportIncomeData", {
          method: 'POST', // 请求方法还可以是 put
          body: JSON.stringify(this.form),
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
        });
      }

    },
    createGlf() {
      if (this.glfList.length == 0) {
        this.$notify({
          title: '提示',
          message: '暂未有过路费数据导入',
          type: 'warning'
        });
      } else {
        this.form.columnname = this.glfList[0].columnname
        fetch("http://localhost:9080/exportIncomeData", {
          method: 'POST', // 请求方法还可以是 put
          body: JSON.stringify(this.form),
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
        });
      }
    },
    createRy() {
      if (this.ryList.length == 0) {
        this.$notify({
          title: '提示',
          message: '暂未有燃油费数据导入',
          type: 'warning'
        });
      } else {
        this.form.columnname = this.ryList[0].columnname
        fetch("http://localhost:9080/exportIncomeData", {
          method: 'POST', // 请求方法还可以是 put
          body: JSON.stringify(this.form),
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
        });
      }
    },
    createFk() {

      if (this.fkList.length == 0) {
        this.$notify({
          title: '提示',
          message: '暂未有罚款数据导入',
          type: 'warning'
        });
      } else {

        this.form.columnname = this.fkList[0].columnname
        fetch("http://localhost:9080/exportIncomeData", {
          method: 'POST', // 请求方法还可以是 put
          body: JSON.stringify(this.form),
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
        });
      }
    },
    createTc() {
      if (this.tcList.length == 0) {
        this.$notify({
          title: '提示',
          message: '暂未有停车费数据导入',
          type: 'warning'
        });
      } else {
        this.form.columnname = this.tcList[0].columnname
        fetch("http://localhost:9080/exportIncomeData", {
          method: 'POST', // 请求方法还可以是 put
          body: JSON.stringify(this.form),
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
        });
      }

    },
    createLt() {
      if (this.ltList.length == 0) {
        this.$notify({
          title: '提示',
          message: '暂未有轮胎费数据导入',
          type: 'warning'
        });
      } else {
        this.form.columnname = this.ltList[0].columnname
        fetch("http://localhost:9080/exportIncomeData", {
          method: 'POST', // 请求方法还可以是 put
          body: JSON.stringify(this.form),
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
        });
      }

    },
    createWx() {
      if (this.wxList.length == 0) {
        this.$notify({
          title: '提示',
          message: '暂未有维修费数据导入',
          type: 'warning'
        });
      } else {
        this.form.columnname = this.wxList[0].columnname
        fetch("http://localhost:9080/exportIncomeData", {
          method: 'POST', // 请求方法还可以是 put
          body: JSON.stringify(this.form),
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
        });
      }

    },

    handleType(command) {
      this.checktype = '当前选择' + command
      this.form.cartype = command;
      if (this.checkyear != '请选择年度' && this.checktype != '请选择车辆类型') {
        this.queryData();
      }
    },

    handleYear(command) {
      this.checkyear = '当前选择' + command
      this.form.year = command;
      if (this.checkyear != '请选择年度' && this.checktype != '请选择车辆类型') {
        this.queryData();
      }
    },
    async queryData() {
      await fetch("http://localhost:9080/queryRunCount", {
        method: 'POST', // 请求方法还可以是 put
        body: JSON.stringify(this.form),
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      }).then(res => res.json()).then(response => {
        this.countList = response.resList

        let alertmsg = ''
        response.resList.map(item => {
          alertmsg = alertmsg + item.key + ','

        })

        let message = alertmsg.substring(0, alertmsg.length - 1)
        if (response.resList.length < 12 && response.resList.length != 0) {
          /* this.$alert( "当前只导入了发车次数"+ message +"月的数据。如果生成过路费、燃油费、罚款、停车费、轮胎费、维修费的excel，生成后的excel将只会有"+ message +"月的数据", '注意', {
             confirmButtonText: '确定',
             callback: action => {

             }
           });*/
          this.$notify({
            title: '注意',
            message: "当前只导入了发车次数" + message + "月的数据。如果按照现有的导入数据生成过路费、燃油费、罚款、停车费、轮胎费、维修费excel，生成后的excel将只会有" + message + "月的数据---------(此提示不会自动关闭)",
            duration: 0,
            type: 'warning',
            position: 'bottom-right'
          });
        }


      });
      await fetch("http://localhost:9080/queryIncomeMoney", {
        method: 'POST', // 请求方法还可以是 put
        body: JSON.stringify(this.form),
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      }).then(res => res.json()).then(response => {
        console.log("response")
        console.log(response)
        console.log(this.checkyear)

        if(this.checkyear === '当前选择2023' || this.checkyear === '当前选择2024'){
          this.glfList = response.resList.filter(item => item.columnname == '            通行费')
          this.ryList = response.resList.filter(item => item.columnname == '            燃油费')
          this.fkList = response.resList.filter(item => item.columnname == '        20.罚款、滞纳金')
          this.tcList = response.resList.filter(item => item.columnname == '            停车费')
          this.ltList = []//response.resList.filter(item => item.columnname == '            轮胎维修')
          this.wxList = response.resList.filter(item => item.columnname == '            车辆维修费')
          this.flList =[]// response.resList.filter(item => item.columnname == '         物料消耗')
        }else{
          this.glfList = response.resList.filter(item => item.columnname == '         路桥费')
          this.ryList = response.resList.filter(item => item.columnname == '         燃油费')
          this.fkList = response.resList.filter(item => item.columnname == '         罚金、滞纳金')
          this.tcList = response.resList.filter(item => item.columnname == '         停车费')
          this.ltList = response.resList.filter(item => item.columnname == '            轮胎维修')
          this.wxList = response.resList.filter(item => item.columnname == '            日常维修')
          this.flList = response.resList.filter(item => item.columnname == '         物料消耗')
        }



      });


      await fetch("http://localhost:9080/queryOthermoney", {
        method: 'POST', // 请求方法还可以是 put
        body: JSON.stringify(this.form),
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      }).then(res => res.json()).then(response => {
        if (response.resList.length == 0) {
          this.otherList = []
        } else {
          this.otherList = response.resList

        }
      });

      await fetch("http://localhost:9080/queryFuleMoney", {
        method: 'POST', // 请求方法还可以是 put
        body: JSON.stringify(this.form),
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      }).then(res => res.json()).then(response => {
        if (response.resList.length == 0) {
          this.fuleList = []
        } else {
          this.fuleList = response.resList

        }
      });


      if (
          this.glfList.length == 0 &&
          this.ryList.length == 0 &&
          this.fkList.length == 0 &&
          this.tcList.length == 0 &&
          this.ltList.length == 0 &&
          this.wxList.length == 0 &&
          this.flList.length == 0 &&
          this.otherList.length == 0
      ) {
        this.showDetail = false;
        this.$notify({
          title: '提示',
          message: '当前年份暂未有数据导入',
          type: 'warning'
        });
      } else {
        this.showDetail = true;

        this.$notify({
          title: '提示',
          message: '数据统计完成，确认数据无误后点击生成按钮即可生成对应excel',
          type: 'success'
        });
      }







    },
    createJfml() {
      //this.form.columnname = this.flList[0].columnname
      fetch("http://localhost:9080/exportIncomeGross", {
        method: 'POST', // 请求方法还可以是 put
        body: JSON.stringify(this.form),
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
      });
    },
    createyh(){

      if (this.fuleList.length == 0) {
        this.$notify({
          title: '提示',
          message: '暂未有油耗数据导入',
          type: 'warning'
        });
      } else {
        //this.form.columnname = this.flList[0].columnname
        fetch("http://localhost:9080/exportYh", {
          method: 'POST', // 请求方法还可以是 put
          body: JSON.stringify(this.form),
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
        });
      }
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

.el-dropdown-link {
  color: #66b1ff;
  font-size: 16px;
  cursor: pointer;
}
</style>
