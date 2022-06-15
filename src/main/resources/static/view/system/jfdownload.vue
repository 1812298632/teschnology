<template>
  <div class="home" >
  <el-container style="margin-top: 3%" >

  <el-card class="box-card" style="width: 17%">
      <div slot="header" class="clearfix">
        <span>发车次数统计</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="createDepartCount()">生成
        </el-button>
      </div>
      <div v-for="list in this.countList" :key="list" class="text item">
        <font size="2">解放车 <font size="2" color="#ff291a">{{ list.key }} </font>月份发车次数为
          <font size="2" color="#ff291a">{{ list.value }}</font></font>


      </div>
      <font size="2">
        <el-link href="http://localhost:9080/page#/uploaddepart" type="primary" target="_blank">其余月份没有数据导入</el-link>
      </font>
    </el-card>


        <el-card class="box-card" style="width: 17%;margin-left: 3%">
        <div slot="header" class="clearfix">
          <span>过路费统计</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createJfGlf()">生成</el-button>
        </div>
        <div v-for="list in this.glfList" :key="list" class="text item">
          <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
          <font size="2" color="#ff291a"> 1月 {{ list.onemonth }}</font><br/>
          <font size="2" color="#ff291a"> 2月 {{ list.twomonth }}</font><br/>
          <font size="2" color="#ff291a"> 3月 {{ list.threemonth }}</font><br/>
          <font size="2" color="#ff291a"> 4月 {{ list.fourmonth }}</font><br/>
          <font size="2" color="#ff291a"> 5月 {{ list.fivemonth }}</font><br/>
          <font size="2" color="#ff291a"> 6月 {{ list.sixmonth }}</font><br/>
          <font size="2" color="#ff291a"> 7月 {{ list.sevenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 8月 {{ list.eightmonth }}</font><br/>
          <font size="2" color="#ff291a"> 9月 {{ list.ninemonth }}</font><br/>
          <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
          <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>


        </div>
      </el-card>

      <el-card class="box-card" style="width: 17%;margin-left: 3%">
        <div slot="header" class="clearfix">
          <span>燃油费统计</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createJfRy()">生成</el-button>
        </div>
        <div v-for="list in this.ryList" :key="list" class="text item">
          <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
          <font size="2" color="#ff291a"> 1月 {{ list.onemonth }}</font><br/>
          <font size="2" color="#ff291a"> 2月 {{ list.twomonth }}</font><br/>
          <font size="2" color="#ff291a"> 3月 {{ list.threemonth }}</font><br/>
          <font size="2" color="#ff291a"> 4月 {{ list.fourmonth }}</font><br/>
          <font size="2" color="#ff291a"> 5月 {{ list.fivemonth }}</font><br/>
          <font size="2" color="#ff291a"> 6月 {{ list.sixmonth }}</font><br/>
          <font size="2" color="#ff291a"> 7月 {{ list.sevenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 8月 {{ list.eightmonth }}</font><br/>
          <font size="2" color="#ff291a"> 9月 {{ list.ninemonth }}</font><br/>
          <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
          <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>


        </div>
      </el-card>


      <el-card class="box-card" style="width: 17%;margin-left: 3%">
        <div slot="header" class="clearfix">
          <span>罚款统计</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createJfFk()">生成</el-button>
        </div>
        <div v-for="list in this.fkList" :key="list" class="text item">
          <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
          <font size="2" color="#ff291a"> 1月 {{ list.onemonth }}</font><br/>
          <font size="2" color="#ff291a"> 2月 {{ list.twomonth }}</font><br/>
          <font size="2" color="#ff291a"> 3月 {{ list.threemonth }}</font><br/>
          <font size="2" color="#ff291a"> 4月 {{ list.fourmonth }}</font><br/>
          <font size="2" color="#ff291a"> 5月 {{ list.fivemonth }}</font><br/>
          <font size="2" color="#ff291a"> 6月 {{ list.sixmonth }}</font><br/>
          <font size="2" color="#ff291a"> 7月 {{ list.sevenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 8月 {{ list.eightmonth }}</font><br/>
          <font size="2" color="#ff291a"> 9月 {{ list.ninemonth }}</font><br/>
          <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
          <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>


        </div>
      </el-card>

      <el-card class="box-card" style="width: 17%;margin-left: 3%">
        <div slot="header" class="clearfix">
          <span>停车费统计</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="createJfTc()">生成</el-button>
        </div>
        <div v-for="list in this.tcList" :key="list" class="text item">
          <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
          <font size="2" color="#ff291a"> 1月 {{ list.onemonth }}</font><br/>
          <font size="2" color="#ff291a"> 2月 {{ list.twomonth }}</font><br/>
          <font size="2" color="#ff291a"> 3月 {{ list.threemonth }}</font><br/>
          <font size="2" color="#ff291a"> 4月 {{ list.fourmonth }}</font><br/>
          <font size="2" color="#ff291a"> 5月 {{ list.fivemonth }}</font><br/>
          <font size="2" color="#ff291a"> 6月 {{ list.sixmonth }}</font><br/>
          <font size="2" color="#ff291a"> 7月 {{ list.sevenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 8月 {{ list.eightmonth }}</font><br/>
          <font size="2" color="#ff291a"> 9月 {{ list.ninemonth }}</font><br/>
          <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
          <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
          <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>


        </div>
      </el-card>




  </el-container>
  <el-container style="margin-top: 3%">
    <el-card class="box-card" style="width: 17%">
      <div slot="header" class="clearfix">
        <span>轮胎费统计</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="createJfLt()">生成
        </el-button>
      </div>
      <div v-for="list in this.ltList" :key="list" class="text item">
        <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
        <font size="2" color="#ff291a"> 1月 {{ list.onemonth }}</font><br/>
        <font size="2" color="#ff291a"> 2月 {{ list.twomonth }}</font><br/>
        <font size="2" color="#ff291a"> 3月 {{ list.threemonth }}</font><br/>
        <font size="2" color="#ff291a"> 4月 {{ list.fourmonth }}</font><br/>
        <font size="2" color="#ff291a"> 5月 {{ list.fivemonth }}</font><br/>
        <font size="2" color="#ff291a"> 6月 {{ list.sixmonth }}</font><br/>
        <font size="2" color="#ff291a"> 7月 {{ list.sevenmonth }}</font><br/>
        <font size="2" color="#ff291a"> 8月 {{ list.eightmonth }}</font><br/>
        <font size="2" color="#ff291a"> 9月 {{ list.ninemonth }}</font><br/>
        <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
        <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
        <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>


      </div>

    </el-card>


    <el-card class="box-card" style="width: 17%;margin-left: 3%">
      <div slot="header" class="clearfix">
        <span>维修费统计</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="createJfWx()">生成
        </el-button>
      </div>
      <div v-for="list in this.wxList" :key="list" class="text item">
        <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
        <font size="2" color="#ff291a"> 1月 {{ list.onemonth }}</font><br/>
        <font size="2" color="#ff291a"> 2月 {{ list.twomonth }}</font><br/>
        <font size="2" color="#ff291a"> 3月 {{ list.threemonth }}</font><br/>
        <font size="2" color="#ff291a"> 4月 {{ list.fourmonth }}</font><br/>
        <font size="2" color="#ff291a"> 5月 {{ list.fivemonth }}</font><br/>
        <font size="2" color="#ff291a"> 6月 {{ list.sixmonth }}</font><br/>
        <font size="2" color="#ff291a"> 7月 {{ list.sevenmonth }}</font><br/>
        <font size="2" color="#ff291a"> 8月 {{ list.eightmonth }}</font><br/>
        <font size="2" color="#ff291a"> 9月 {{ list.ninemonth }}</font><br/>
        <font size="2" color="#ff291a"> 10月 {{ list.tenmonth }}</font><br/>
        <font size="2" color="#ff291a"> 11月 {{ list.eleventmonth }}</font><br/>
        <font size="2" color="#ff291a"> 12月 {{ list.twelvemonth }}</font>


      </div>

    </el-card>


    <el-card class="box-card" style="width: 17%;margin-left: 3%">
      <div slot="header" class="clearfix">
        <span>辅料统计</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="createDepartCount()">生成
        </el-button>
      </div>
      <div v-for="list in this.flList" :key="list" class="text item">
        <font size="2">{{ list.carid }} {{ list.columnname }}</font><br/>
        <font size="2" color="#ff291a"> 1月 {{ list.onemonth }}</font><br/>
        <font size="2" color="#ff291a"> 2月 {{ list.twomonth }}</font><br/>
        <font size="2" color="#ff291a"> 3月 {{ list.threemonth }}</font><br/>
        <font size="2" color="#ff291a"> 4月 {{ list.fourmonth }}</font><br/>
        <font size="2" color="#ff291a"> 5月 {{ list.fivemonth }}</font><br/>
        <font size="2" color="#ff291a"> 6月 {{ list.sixmonth }}</font><br/>
        <font size="2" color="#ff291a"> 7月 {{ list.sevenmonth }}</font><br/>
        <font size="2" color="#ff291a"> 8月 {{ list.eightmonth }}</font><br/>
        <font size="2" color="#ff291a"> 9月 {{ list.ninemonth }}</font><br/>
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
      tagsList: [],
      countList: [],
      glfList: [],
      ryList: [],
      fkList: [],
      tcList: [],
      ltList: [],
      wxList: [],
      flList: [],
      form: {
        type: '',
        startcity: '',
        endcity: '',
        month: '',
        cartype: '',
      },
      isCollapse: true,
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

    this.form.cartype = '解放车'
    fetch("http://localhost:9080/queryJfCount", {
      method: 'POST', // 请求方法还可以是 put
      body: JSON.stringify(this.form),
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json())
        .then(response => {
          this.countList = response.resList

          let alertmsg = ''
          response.resList.map(item => {
            alertmsg = alertmsg + item.key + ','

          })

          let message = alertmsg.substring(0, alertmsg.length - 1)
          if (response.resList.length < 12) {
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


    fetch("http://localhost:9080/queryjfmoney", {
      method: 'POST', // 请求方法还可以是 put
      body: JSON.stringify(this.form),
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json()).then(response => {
      this.glfList = response.resList.filter( item => item.columnname == '         路桥费')
      this.ryList = response.resList.filter( item => item.columnname == '         燃油费')
      this.fkList = response.resList.filter( item => item.columnname == '         罚金、滞纳金')
      this.tcList = response.resList.filter( item => item.columnname == '         停车费')
      this.ltList = response.resList.filter( item => item.columnname == '            轮胎维修')
      this.wxList = response.resList.filter( item => item.columnname == '            日常维修')
      this.flList = response.resList.filter( item => item.columnname == '         物料消耗')

    });



    this.$notify({
      title: '提示',
      message: '数据统计完成，确认数据无误后点击生成按钮即可生成对应excel',
      type: 'success'
    });

  },
  methods: {
    createDepartCount() {
      this.form.cartype = '解放车'

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

    },


    createJfGlf() {
      this.form.cartype = '解放车'
      fetch("http://localhost:9080/exportIncomeTolls", {
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
    createJfRy(){
      this.form.cartype = '解放车'
      fetch("http://localhost:9080/exportIncomeFuel", {
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
    createJfFk(){
      this.form.cartype = '解放车'
      fetch("http://localhost:9080/exportIncomeFine", {
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
    createJfTc(){
      this.form.cartype = '解放车'
      fetch("http://localhost:9080/exportIncomeParking", {
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
    createJfLt(){
      this.form.cartype = '解放车'
      fetch("http://localhost:9080/exportIncomeTire", {
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
    createJfWx(){
      this.form.cartype = '解放车'
      fetch("http://localhost:9080/exportIncomeRepair", {
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
};
</script>

<style scoped>
.home {
  font-size: 24px;
  height: 100%;
  background-color: #ffffff;
}
</style>
