<template>
  <div>
    <div class="btns">
      <el-button type="primary" plain :icon="CirclePlus" @click="add">添加</el-button>
    </div>
	
	<div class="days">
	   <el-radio-group v-model="params.days" size="large" @change="search">
	     <el-radio-button label="周一" value="Monday"/>
	     <el-radio-button label="周二" value="Tuesday" />
	     <el-radio-button label="周三" value="Wednesday" />
	     <el-radio-button label="周四" value="Thursday" />
		 <el-radio-button label="周五" value="Friday" />
		 <el-radio-button label="周六" value="Saturday" />
		 <el-radio-button label="周日" value="Sunday" />
	   </el-radio-group>
	 </div>
	 
  <div class="flex-container" style="margin-top: 15px;">
	<div class="left-gutter">
		<div class="text-container">早餐</div>
	</div>
	<!-- <div> -->
		<div v-for="item in Breakfast" :key="item.id" class="flex-item">
		  <el-image style="width: 120px;height: 120px;margin-left: 20px;" fit="contain" :src="getPath(item.icon)"></el-image>
		  <div class="image-info">
		    <p><strong>类型：</strong>{{ item.type }}</p>
		    <p><strong>时间：</strong>{{ item.mealtime }}</p>
		    <p><strong>名称：</strong>{{ item.mealname }}</p>
		    <p><strong>价格：</strong>{{ item.price }}</p>
		    <p><strong>是否清真：</strong>{{ item.qingzhen === 1 ? '是' : '否' }}</p>
		
		  </div>
		  <template v-if="item.status">
		    <el-button style="margin-left: 25px; margin-top: 3px;" type="primary" plain size="small" @click="update(item.id)">修改</el-button>
		    <el-button type="danger" plain size="small" @click="del(item.id, 0)">删除</el-button>
		  </template>
		  <el-button v-else type="warning" plain size="small" @click="del(item.id, 1)">启用</el-button>
		</div>
	<!-- </div> -->
  </div>
  
  <div class="flex-container"style="margin-top: 20px;">
  	<div class="left-gutter">
  		<div class="text-container">午餐</div>
  	</div>
    <div v-for="item in Lunch" :key="item.id" class="flex-item">
      <el-image style="width: 120px;height: 120px;margin-left: 20px;" fit="contain" :src="getPath(item.icon)"></el-image>
      <div class="image-info">
        <p><strong>类型：</strong>{{ item.type }}</p>
        <p><strong>时间：</strong>{{ item.mealtime }}</p>
        <p><strong>名称：</strong>{{ item.mealname }}</p>
        <p><strong>价格：</strong>{{ item.price }}</p>
        <p><strong>是否清真：</strong>{{ item.qingzhen === 1 ? '是' : '否' }}</p>

      </div>
      <template v-if="item.status">
        <el-button style="margin-left: 25px; margin-top: 3px;" type="primary" plain size="small" @click="update(item.id)">修改</el-button>
        <el-button type="danger" plain size="small" @click="del(item.id, 0)">删除</el-button>
      </template>
      <el-button v-else type="warning" plain size="small" @click="del(item.id, 1)">启用</el-button>
    </div>
  </div>
  
  <div class="flex-container" style="margin-top: 15px;">
  	<div class="left-gutter">
  		<div class="text-container">晚餐</div>
  	</div>
    <div v-for="item in Dinner" :key="item.id" class="flex-item">
      <el-image style="width: 120px;height: 120px;margin-left: 20px;" fit="contain" :src="getPath(item.icon)"></el-image>
      <div class="image-info">
        <p><strong>类型：</strong>{{ item.type }}</p>
        <p><strong>时间：</strong>{{ item.mealtime }}</p>
        <p><strong>名称：</strong>{{ item.mealname }}</p>
        <p><strong>价格：</strong>{{ item.price }}</p>
        <p><strong>是否清真：</strong>{{ item.qingzhen === 1  ? '是' : '否' }}</p>

      </div>
      <template v-if="item.status">
        <el-button style="margin-left: 25px; margin-top: 3px;" type="primary" plain size="small" @click="update(item.id)">修改</el-button>
        <el-button type="danger" plain size="small" @click="del(item.id, 0)">删除</el-button>
      </template>
      <el-button v-else type="warning" plain size="small" style="margin-right: 300px;" @click="del(item.id, 1)">启用</el-button>
    </div>
  </div>

	 <el-pagination
	 	style="margin-top: 10px;"
	 	background
	 	v-model:current-page="params.pageNo"
	 	:page-count="tableData.pages"
	 	:total="tableData.total"
	 	@current-change="getTableData" />
	 <el-dialog
	 		v-model="dialog.show"
	 		:title="dialog.title"
	 		width="450px"
	 		:close-on-click-modal="false">
	   <Add
	 		v-if="dialog.show"
	 		@getTableData="getTableData"
			v-model:show="dialog.show"
 			:id="dialog.id"/>
	 </el-dialog>
  </div>
</template>

<script setup>
import { CirclePlus, Search } from '@element-plus/icons-vue'
import { getPath } from '@/util'
// import Guanliyuan from '@/components/icons/guanliyuan'
import { get, post } from '@/axios'
import { ref, reactive, onMounted, watch, computed } from 'vue'
import Add from './add'
import url from './util'
import { ElMessageBox } from 'element-plus'
const radio1 = ref('')
const dialog = reactive({
	show: false,
	title: '',
	id: null
})
const tableData = reactive({
	records: [],
	pages: 0,
	total: 0
})
const params = reactive({
	pageNo: 1,
	pageSize: 10,
	days: '',
	mealtime: '早餐'
})
const mealData = ref([])
getmealData()

function getmealData(){
		get('/dietarycalendar/type',null,content =>{
			mealData.value=content
			console.dir(content)
			    const iconValues = mealData.value.map(item => item.status);
			    console.log(iconValues); // 输出包含icon属性的值的数组
		})
}
const dayOfWeekMapping = {
  0: 'Sunday',
  1: 'Monday',
  2: 'Tuesday',
  3: 'Wednesday',
  4: 'Thursday',
  5: 'Friday',
  6: 'Saturday'
};
const indays = ref()
onMounted(() => {
  const today = new Date();
  const dayOfWeek = today.getDay(); // 获取今天是周几，0 是周日，1 是周一，以此类推
  params.days = dayOfWeekMapping[dayOfWeek];// 设置 params.days 为今天的星期名
  radio1.value = dayOfWeekMapping[dayOfWeek]; // 设置 radio1 的默认值为今天的星期名
  
});

// 过滤mealData以仅显示与radio1相等的days属性的值
const Breakfast = computed(() => {
  return mealData.value.filter(item => item.days === params.days && item.mealtime === '早餐' && item.status === true);
});
const  Lunch = computed(() => {
  return mealData.value.filter(item => item.days === params.days && item.mealtime === '午餐');
});
const Dinner = computed(() => {
  return mealData.value.filter(item => item.days === params.days && item.mealtime === '晚餐');
});

getTableData()
function getTableData () {
	get(url.list, params, content => {
		// console.dir(content)
		// const breakfastData = content.records.filter(item => item.mealtime === '早餐');
		const recordsArray = Array.prototype.slice.call(content.records);
		const sortedData = recordsArray.sort((a, b) => {
		    const breakfastOrder = ['早餐', '午餐', '晚餐'];
		    const aIndex = breakfastOrder.indexOf(a.mealtime);
		    const bIndex = breakfastOrder.indexOf(b.mealtime);
		    if (aIndex < bIndex) return -1;
		    if (aIndex > bIndex) return 1;
		    return 0;
		  });
		  
		// tableData.records = content.records.filter(item => item.mealtime === '早餐')
		// tableData.records = content.records.filter(item => item.mealtime === '午餐')
		// tableData.records = content.records.filter(item => item.mealtime === '晚餐')
		tableData.records = sortedData
		tableData.pages = content.pages
		tableData.total = content.total
		console.dir(sortedData)
	})
}

function add() {
	dialog.title = '添加菜品'
	dialog.id = null
  dialog.show = true
}
function update(id) {
	dialog.title = '修改菜品'
	dialog.id = id
	dialog.show = true
}
function del (id, status) {
	const text = status ? '确定要启用该菜单吗?' : '确定要禁用该菜单吗'
	ElMessageBox.confirm(text, '警告', {
		type: 'warning'
	}).then(() => {
		post(url.del, { id ,status}, content => {
			getTableData()
		})
	}).catch(() => {})
}
function search(){
	getTableData()
}
</script>

<style scoped lang="scss">
	.days{
		margin-top: 10px;
	}
	.breakfast{
		margin-top: 10px;
	}
	.lunch{
		margin-top: 10px;
	}
	.dinner{
		margin-top: 10px;
	}
	
	.flex-container {
	  display: flex;
	  flex-wrap: wrap; /* 允许子元素在一行放不下时换行 */
	  box-shadow:  0 5px 5px rgba(0, 0, 0, 0.2);
	}
	.image-info {
	  width: 200px;
	  margin-left: 30px;
	  // margin-top: 10px; /* 上边距 */
	}
	
	.image-info p {
	  margin: 2px 0; /* 段落间距 */
	}
	
	.left-gutter {
	  background-color: rgba(0, 0, 0, 0.3);
	  width: 130px;
	  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
	}
	
	.flex-item {
	  margin-left: 50px;
	  margin-top: 20px;
	  margin-bottom: 20px;
	  width: 160px; /* 占据剩余空间 */
	  box-shadow: 0 5px 5px rgba(0, 0, 0, 0.4);
	}
	
	.text-container {
	  display: flex;
	  justify-content: center; /* 水平居中 */
	  align-items: center; /* 竖直居中 */
	  height: 100%; /* 占据整个flex-item的高度 */
	  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
	}
</style>
