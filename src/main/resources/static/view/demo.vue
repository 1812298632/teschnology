<template>
  <div class="home">
    <el-container style="height: 50%">
      这是第一个测试页面111



    </el-container>

  </div>

</template>

<script>
module.exports = {
  data() {
    return {
      tagsList: [],
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
  },
  methods: {
    /* isC() {
                console.log('Home....isC')
                this.isCollapse = !this.isCollapse
            } */
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
