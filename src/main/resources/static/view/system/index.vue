<template>
  <div class="home">
    <el-container style="height: 100%">
     <div class="block" style="height: 100%;width: 1000px">
        <el-timeline>
          <el-timeline-item timestamp="第一步" placement="top">
            <el-card>
              <h4 align="left">菜单：台账上传，上传台账信息</h4>
              <br/>
              <div align="left" v-for="(list1,index1) in departList"  class="text item">
                <font size="2"> {{ list1.cartype }} 已导入 <font size="2" color="#1e90ff"> {{ list1.month }} </font> 月份数据</font><br/>
              </div>
              <br/>
              <div align="left" v-for="(list2,index2) in departCountList" class="text item">
                <font size="2" >{{ list2.cartype }} <font size="2" color="green"> {{ list2.runcount }} </font></font><br/>
              </div>
              <br/>
              <div align="left" v-for="(list3,index3) in departWarringList" class="text item">
                <font size="2"> {{ list3.cartype }} <font size="2" color="red">{{ list3.tmp }} </font> 的数据可能有误 </font><br/>
              </div>
            </el-card>
          </el-timeline-item>
           <el-timeline-item timestamp="第二步" placement="top">
            <el-card>
              <h4 align="left">菜单：损益表上传，上传损益表信息</h4>
              <br/>
              <div align="left" v-for="(list4,index4) in this.incomeList"  class="text item">
                <font size="2"> {{ list4.cartype }}已导入车牌号为 <font size="2"  color="#1e90ff">{{ list4.tmp }}</font> 的数据 </font><br/>
              </div>

              <br/>
              <div align="left" v-for="(list5,index5) in this.incomeWarringList"  class="text item">
                <font size="2"> {{ list5.cartype }} <font size="2" color="red">{{ list5.carid }}  {{ list5.columnname}}项目</font> 数据为小于0,可能有误 </font><br/>
              </div>
            </el-card>
          </el-timeline-item>
          <el-timeline-item timestamp="第三步" placement="top">
            <el-card>
              <h4 align="left">菜单：数据查询，确认导入信息</h4>
              <br/>
              <p align="left">菜单中可以删除或修改有问题的数据</p>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </div>

<!--
<el-button  type="primary" icon="el-icon-search" @click="click()">点击</el-button>
-->



    </el-container>

  </div>

</template>

<script>
module.exports = {
  name :'index',
  data() {
    return {
     tagsList: [],
      departList: [],
      incomeList: [],
      departCountList: [],
      departWarringList: [],
      incomeWarringList: [],
      isCollapse: true,
    };
  },
  mounted() {


  },

  created() {
    // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
  /*  bus.$on("tags", (msg) => {
      console.log("home....bus...on");
      console.log(msg);
      let arr = [];


      for (let i = 0, len = msg.length; i < len; i++) {
        msg[i].name && arr.push(msg[i].name);
      }


      alert(JSON.stringify(arr))

      this.tagsList = arr;
    });
*/

    alert(111)


    fetch("http://localhost:9080/queryDepartIndex", {
      method: 'POST', // 请求方法还可以是 put
      body: JSON.stringify(this.form),
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json()).then(response => {
      this.departList = response.resList

    });


    fetch("http://localhost:9080/queryDepartIndex1", {
      method: 'POST', // 请求方法还可以是 put
      body: JSON.stringify(this.form),
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json()).then(response => {
      this.departCountList = response.resList

    });

    fetch("http://localhost:9080/queryDepartWarning", {
      method: 'POST', // 请求方法还可以是 put
      body: JSON.stringify(this.form),
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json()).then(response => {
      this.departWarringList = response.resList

    });


    fetch("http://localhost:9080/queryIncomeIndex", {
      method: 'POST', // 请求方法还可以是 put
      body: JSON.stringify(this.form),
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json()).then(response => {
      this.incomeList = response.resList

    });


    fetch("http://localhost:9080/queryIncomeWarning", {
      method: 'POST', // 请求方法还可以是 put
      body: JSON.stringify(this.form),
      headers: new Headers({
        'Content-Type': 'application/json'
      })
    }).then(res => res.json()).then(response => {
      this.incomeWarringList = response.resList

    });
  },
  methods: {
    click(){
      this.tagsList.push({
        title: '沃尔沃数据导出',
        path: '/wrwdownload',
        //   name: route.matched[1].components.default.name,
        name: null
      });


      console.log('12345')
      console.log(this.tagsList)
      this.$router.push(this.tagsList);
      //bus.$emit("tags", this.tagsList);
    }
  }


};
</script>

<style scoped>
.home {
  font-size: 24px;
  height: 100%;
  background-color: #ffffff;
}

.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 150px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}

</style>
