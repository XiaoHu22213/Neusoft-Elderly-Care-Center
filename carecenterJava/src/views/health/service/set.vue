<template>
	<div style="margin-left: 20px; margin-right: 30px;">
		<el-form ref="formObj" :model="service" label-width="80px">
			<el-form-item label="姓名">
				<el-input v-model="service.name" style="width: 250px;"></el-input>
			</el-form-item>
			<el-form-item label="楼层:">
				<el-select
				    v-model="service.floor"
				    clearable
			        placeholder="请选择楼层"
			        style="width: 250px"
			        @change="handleChange"
				>
		        <el-option
					v-for="item in options"
					:key="item.value"
					:label="item.label"
					:value="item.value"
			    ></el-option>
			</el-select>
			</el-form-item>
			<el-form-item label="老人:">
				<el-select
				    v-model="service.toname"
				    clearable
			        placeholder="请选择服务对象"
			        style="width: 250px"
			        @change="handleChange"
				>
			    <el-option
					v-for="item in nameData"
					:key="item.customername"
					:label="item.customername"
					:value="item.customername"
			    ></el-option>
			</el-select>
			</el-form-item>
			<el-form-item label="备注:">
				<el-input v-model="service.notes" style="width: 250px;"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" plain :icon="Save" @click="save()">保存</el-button>
			</el-form-item>

		</el-form>
	</div>
</template>

<script setup>
	import Save from '@/components/icons/save'
	import { ref, reactive, onMounted, watch, computed } from 'vue'
	import { get, post } from '@/axios'
	import url from './util'
	import { getPath } from '@/util'
	import { Food, PictureFilled } from '@element-plus/icons-vue'
	const emits = defineEmits(['update:show', 'getTableData'])
	const props = defineProps(['id'])
	const zzaform = reactive({
		name: '',
		days: ''
	})
	const service = reactive({
		id: null,
		Sid: null,
		name: '',
		phone: '',
		floor: '',
		notes: '',
		time: '',
		toname: ''
	})
	const formObj = ref()
	if (props.id) {
		service.Sid = props.id
		service.time = time
		getById()
	}
	function check (rule, value, callback) {
		get(url.check, { id: props.id, value, field: rule.field }, content => {
			if (content) {
				callback()
			} else {
				callback(new Error())
			}
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
	onMounted(() => {
	  const today = new Date();
	  const dayOfWeek = today.getDay(); // 获取今天是周几，0 是周日，1 是周一，以此类推
	  // params.days = dayOfWeekMapping[dayOfWeek];// 设置 params.days 为今天的星期名
	  zzaform.days = dayOfWeekMapping[dayOfWeek]; // 设置 radio1 的默认值为今天的星期名
	  
	});
const userData = ref([])
getuserData()
function getuserData(){
		get('/user/type',null,content =>{
			userData.value=content
			console.dir(userData)
		})
}
// 过滤mealData以仅显示与radio1相等的days属性的值
const Housekeep = computed(() => {
  return userData.value.filter(item => item.type === '管家');
});

const nameData = ref([])
getnameData()
function getnameData(){
		get('/checkIn/getCanCheckOut',null,content =>{
			nameData.value=content
			console.dir(nameData)
		})
}
function save() {
	post(url.set, service, content => {
		emits('update:show', false)
		emits('getTableData')
	}, formObj)
}
function getById () {
	get('/user/getById', { id: props.id }, content => {
		console.dir(content)
		for (const key in service) {
			if (key !== 'id' && Object.prototype.hasOwnProperty.call(content, key)) {
				service[key] = content[key]
			}
		}
	})
			console.dir(service)
}


// 获取当前时间并格式化为本地时间字符串
var time = new Date().toLocaleString();

function formatTime(date) {
  var year = date.getFullYear();
  var month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份是从0开始的
  var day = date.getDate().toString().padStart(2, '0');
  var hours = date.getHours().toString().padStart(2, '0');
  var minutes = date.getMinutes().toString().padStart(2, '0');
  var seconds = date.getSeconds().toString().padStart(2, '0');
  
  return `${year}-${month}-${day}${' '}${hours}:${minutes}:${seconds}`;
}

// 获取当前时间并格式化
var time = formatTime(new Date());
// 将格式化后的时间存入service对象的time属性
service.time = time;

// 打印当前时间
console.log(time);


const options = [{
		value: '一层',
		label: '一层',
	},
	{
		value: '二层',
	    label: '二层',
	},
	{
		value: '三层',
	    label: '三层',
	},
	{
		value: '四层',
	    label: '四层',
	}]
const handleChange = (value) => {
  if ([undefined, null].includes(value)) {
    ElMessage.info(`The clear value is: ${value}`)
  }
}
</script>

<style scoped lang="scss">
</style>