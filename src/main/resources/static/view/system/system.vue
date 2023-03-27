<template>
  <div class="home">
    <el-container style="height: 10%">
      <el-input
          placeholder="输入关键字进行过滤"
          v-model="filterText">
      </el-input>
    </el-container>
    <el-container style="height: 80%">


      <!--      <el-tree
                :data="data"
                show-checkbox
                node-key="id"
            >
            </el-tree>-->

      <el-tree ref="tree" default-expand-all :props="defaultProps" :data="data" @node-click="handleNodeClick"
               :filter-node-method="filterNode"></el-tree>


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

        <el-form :model="addform" style="margin-top: 20px">
          <el-col :span="20" style="margin-top: 10px">
            <el-form-item label="父级名称" :label-width="formLabelWidth">
              <el-select v-model="addform.parentid" allow-create filterable style="width: 80%" @change="selectChange">
                <el-option v-for="(system,index) in selectData" :label="system.title" :key="system.title"
                           :value="system.id"></el-option>

              </el-select>
            </el-form-item>

          </el-col>
          <el-col :span="20" style="margin-top: 10px">
            <el-form-item label="id" :label-width="formLabelWidth">
              <el-input v-model="addform.id" style="width: 80%" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="20" style="margin-top: 10px">
            <el-form-item label="num" :label-width="formLabelWidth">
              <el-input v-model="addform.num" style="width: 80%" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="20" style="margin-top: 10px">
            <el-form-item label="名称" :label-width="formLabelWidth">
              <el-input v-model="addform.title" style="width: 80%"></el-input>
            </el-form-item>
          </el-col>
        </el-form>

        <!--  <el-button @click="cancelForm">取 消</el-button>
                            <el-button type="primary" @click="edit" :loading="buttonloading" :disabled="editDis">
                              修改
                            </el-button>-->
        <div class="demo-drawer__footer">
          <el-button type="primary" @click="add1" :loading="buttonloading" :disabled="addDis">
            保存
          </el-button>

          <el-button type="primary" @click="edit" :loading="buttonloading" :disabled="editDis">
            修改
          </el-button>

          <el-button type="primary" @click="del" :loading="buttonloading" :disabled="delDis">
            删除
          </el-button>

        </div>
      </div>

    </el-drawer>

    <el-container style="height: 5%">
      <div style="margin-left: 90%;">
        <el-button type="primary" icon="el-icon-plus" @click="add('add')">新增</el-button>
      </div>

    </el-container>

  </div>

</template>

<script>

module.exports = {
  data() {
    return {
      /*data: [{
        id: 1,
        label: '一级 1',
        children: [{
          id: 4,
          label: '二级 1-1',
          children: [{
            id: 9,
            label: '三级 1-1-1'
          }, {
            id: 10,
            label: '三级 1-1-2'
          }]
        }]
      },
        {
        id: 2,
        label: '一级 2',
        children: [{
          id: 5,
          label: '二级 2-1'
        }, {
          id: 6,
          label: '二级 2-2'
        }]
      }, {
        id: 3,
        label: '一级 3',
        children: [{
          id: 7,
          label: '二级 3-1'
        }, {
          id: 8,
          label: '二级 3-2'
        }]
      }],*/
      filterText: '',
      buttonloading: false,
      addDis: false,
      editDis: false,
      delDis: false,
      diatitle: "",
      data: [],
      formLabelWidth: '70px',
      addform: {id: '', num: '', title: '', parentid: ''},
      dialog: false,
      selectData: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      }
      /*  defaultProps: {
          children: 'children',
          label: 'label'
        }*/
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


    this.search();

  },
  methods: {
    search() {
      fetch("http://localhost:9080/querySystemList", {
        method: 'POST', // 请求方法还可以是 put
        body: '',
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      }).then(res => res.json()).then(response => {
        this.data = response.resList
      });

      fetch("http://localhost:9080/querySystemSelect", {
        method: 'POST', // 请求方法还可以是 put
        body: '',
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      }).then(res => res.json()).then(response => {
        this.selectData = response.resList
      });

    },
    add() {
      this.diatitle = '新增'


      this.addform.parentid = ''
      this.addform.title = ''
      this.addform.id = ''
      this.addform.num = ''


      this.addDis = false
      this.editDis = true
      this.delDis = true

      this.dialog = true
      fetch("http://localhost:9080/queryMaxId", {
        method: 'POST', // 请求方法还可以是 put
        body: '',
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      }).then(res => res.json()).then(response => {
        this.addform.id = response.id
      });

    }
    ,
    handleClose(done) {

      this.cancelForm();
    }
    ,
    cancelForm() {
      //this.buttonloading = false;
      this.dialog = false;
    }
    ,
    selectChange() {
      console.log(this.addform.parentid)

      fetch("http://localhost:9080/queryMaxNum", {
        method: 'POST', // 请求方法还可以是 put
        body: JSON.stringify(this.addform),
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      }).then(res => res.json()).then(response => {
        this.addform.num = response.id
      });

    },
    add1() {

      fetch("http://localhost:9080/addSystem", {
        method: 'POST', // 请求方法还可以是 put
        body: JSON.stringify(this.addform),
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

        //this.loading = true


        this.search()
        this.dialog = false;

      });
    },
    edit() {
      fetch("http://localhost:9080/updateSystem", {
        method: 'POST', // 请求方法还可以是 put
        body: JSON.stringify(this.addform),
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

        //this.loading = true


        this.search()
        this.dialog = false;

      });
    },
    del() {

      fetch("http://localhost:9080/deleteSystem", {
        method: 'POST', // 请求方法还可以是 put
        body: JSON.stringify(this.addform),
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

        //this.loading = true


        this.search()
        this.dialog = false;

      });

    },
    handleNodeClick(data) {


      console.log(data)
      if (data.children !== null) {
        this.delDis = true
      } else {
        this.delDis = false
      }


      this.addform.parentid = ''
      this.addform.title = ''
      this.addform.id = ''
      this.addform.num = ''
      this.addDis = true
      this.editDis = false
      this.diatitle = '修改/删除'

      this.addform.parentid = data.parentid
      this.addform.title = data.label
      this.addform.id = data.id
      this.addform.num = data.num
      this.dialog = true
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    }
  },

  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
}
;
</script>

<style scoped>
.home {
  font-size: 24px;
  height: 100%;
  background-color: #ffffff;
}

.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
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
