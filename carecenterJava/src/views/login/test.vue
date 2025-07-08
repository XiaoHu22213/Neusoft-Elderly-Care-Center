<template>
  <div>
    <el-button @click="moveBox">移动盒子</el-button>
    <div class="box" :style="boxStyle">移动我</div>
  </div>
</template>

<script>
import { ref } from 'vue';

export default {
  setup() {
    const boxStyle = ref({
      transform: 'translateX(0px)',
      backgroundColor: 'blue',
      width: '200px',
      height: '200px',
      position: 'absolute',
      top: '100px',
      left: '100px'
    });

    const moveBox = () => {
		// 设置一个过渡效果，持续时间为2秒
		boxStyle.value.transition = 'transform 2s ease';
		boxStyle.value.transform = 'translateX(200px)';
    };

    return {
      boxStyle,
      moveBox
    };
  }
};
</script>

<style scoped>
.box {
  border: 1px solid black;
}
</style>
