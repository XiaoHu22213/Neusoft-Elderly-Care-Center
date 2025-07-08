<template>
  <div style="margin-right: 30px;">
	  <el-form ref="formObj" :model="zzaform" :rules="rules" label-width="80px" style="max-width: 600px" >
	  <el-form-item label="图片" prop="file">
	  	<!-- <el-input v-model="zzaform.icon"></el-input> -->
	  	<div style="width: 150px; height: 150px; overflow: hidden;">
	  		<el-upload
	  			list-type="picture-card"
	  			:auto-upload="false"
	  			action="#"
	  			:multiple="false"
	  			:limit="1"
	  			:on-change="uploadFile"
	  			:on-remove="removeFile"
	  			v-model:file-list="fileList">
	  			<el-icon><PictureFilled /></el-icon>
	  		</el-upload>
	  	</div>
	  </el-form-item>
	    <el-form-item label="时间段" label-position="right" prop="mealtime">
	      <el-radio-group v-model="zzaform.mealtime" aria-label="早中晚餐">
	        <el-radio-button value="早餐">早餐</el-radio-button>
	        <el-radio-button value="午餐">午餐</el-radio-button>
	        <el-radio-button value="晚餐">晚餐</el-radio-button>
	      </el-radio-group>
	    </el-form-item>
	    <el-form-item label="类型" label-position="right" prop="type">
	  	  <el-select
	  	          v-model="zzaform.type"
	  	          clearable
	  	          placeholder="请选择类型"
	  	          style="width: 240px"
	  	          @change="handleChange"
	  	        >
	  	          <el-option
	  	            v-for="item in typeData"
	  	            :key="item.mealtype"
	  	            :label="item.mealtype"
	  	            :value="item.mealtype"
	  	          ></el-option>
	  	        </el-select>
	    </el-form-item>
		<el-form-item label="日期" label-position="right" prop="days">
		  <el-select
		          v-model="zzaform.days"
		          clearable
		          placeholder="请选择日期"
		          style="width: 240px"
		          @change="handleChange"
		        >
		          <el-option
		            v-for="item in options2"
		            :key="item.value"
		            :label="item.label"
		            :value="item.value"
		          ></el-option>
		        </el-select>
		</el-form-item>
	  	
	    <el-form-item label="名称" prop="mealname">
	      <el-input clearable v-model="zzaform.mealname" placeholder="请输入菜品名称" />
	    </el-form-item>
	    <el-form-item label="价格" prop="price">
	      <el-input clearable v-model="zzaform.price" placeholder="请输入菜品价格" />
	    </el-form-item>
	  	<el-form-item label="是否清真" prop="qingzhen">
	  		<el-radio-group v-model="zzaform.qingzhen">
	  			<el-radio :value="1">是</el-radio>
	  			<el-radio :value="0">否</el-radio>
	  		</el-radio-group>
	  	</el-form-item>
	  	<el-form-item >
	  		<el-button plain type="primary" :icon="Save" @click="save">保存</el-button>
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
import { PictureFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
const emits = defineEmits(['update:show', 'getTableData'])
const props = defineProps(['id'])
const fileList = reactive([])
const zzaform = reactive({
	id: null,
	mealtime: '',
	type: '',
	mealname: '',
	price: '',
	qingzhen: null,
	days: '',
	file: null
})
const formObj = ref()
if (props.id) {
	zzaform.id = props.id
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
const typeData = ref([])
gettypeData()
function gettypeData(){
		get('/mealtype/type',null,content =>{
			typeData.value=content
			console.dir(content)
		})
}
const rules = reactive({
	mealtime:[
		{required:true,message:'请选择时间段',trigger:'blur'}
	],
	type:[
		{required:true,message:'请选择类型',trigger:'blur'}
	],
	mealname:[
		{required:true,message:'请输入菜品名称',trigger:'blur'}
	],
	price:[
		{required:true,message:'请输入价格',trigger:'blur'}
	],
	qingzhen:[
		{required:true,message:'请选择是否清真',trigger:'blur'}
	],
	days:[
		{required:true,message:'请选择日期',trigger:'blur'}
	],
	// file:[
	// 	{required:true,message:'请放入图片',trigger:'blur'}
	// ]
	
})
function save() {
	post(props.id ? url.update : url.add, zzaform, content => {
		emits('update:show', false)
		emits('getTableData')
	}, formObj)
}
function getById () {
	get(url.getById, { id: props.id }, content => {
		console.dir(content)
		for (const key in zzaform) {
			if (Object.prototype.hasOwnProperty.call(content, key)) {
				zzaform[key] = content[key]
			}
		}
		fileList.push({ url: getPath(content.icon) })
	})
}

const options2 = [{
		value: 'Monday',
		label: '周一',
	},
	{
		value: 'Tuesday',
	    label: '周二',
	},
	{
		value: 'Wednesday',
	    label: '周三',
	},
	{
		value: 'Thursday',
	    label: '周四',
	},
	{
		value: 'Friday',
	    label: '周五',
	},
	{
		value: 'Saturday',
	    label: '周六',
	},
	{
		value: 'Sunday',
	    label: '周日',
	}]
const handleChange = (value) => {
  if ([undefined, null].includes(value)) {
    ElMessage.info(`The clear value is: ${value}`)
  }
}

function uploadFile (file) {
	zzaform.file = file.raw
}
function removeFile () {
	zzaform.file = null
}
</script>

<style scoped lang="scss">
</style>