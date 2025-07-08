<template>
  <div style="margin-right: 30px;">
    <el-form label-width="100px" ref="formObject" :model="YlX" :rules="rules">
      <el-form-item label="离席时间" prop="outtime">
        <el-date-picker
          v-model="YlX.outtime"
          type="datetime"
          placeholder="请选择离席时间"
          value-format="YYYY-MM-DD HH:mm:ss"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="回来时间" prop="intime">
        <el-date-picker
          v-model="YlX.intime"
          type="datetime"
          placeholder="请选择回来时间"
          value-format="YYYY-MM-DD HH:mm:ss"
        ></el-date-picker>
      </el-form-item>
      
      <el-form-item label="事由" prop="thing">
        <el-input v-model="YlX.thing" placeholder="你请假要去做什么"></el-input>
      </el-form-item>
      <el-button type="primary" @click="Save">保存</el-button>
    </el-form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';

import { get,post, request } from '@/axios';
import { ElMessage } from 'element-plus';
const emits = defineEmits(['update:show','getTableData'])
let props = defineProps(['bedid','peoplename'])

const formObject = ref();

const YlX = reactive({

  outtime: '',
  intime: '',
  outinname: props.peoplename,
  bednum:props.bedid,
  thing: ''
});

const rules = reactive({
  outtime: [
    { required: true, message: '请选择离席时间', trigger: 'blur' },
    { validator: checkOutTime, trigger: 'blur' }
  ],
  intime: [
    { required: true, message: '请选择回来时间', trigger: 'blur' },
    { validator: checkInTime, trigger: 'blur' }
  ],
  thing: [
    { required: true, message: '请输入事由', trigger: 'blur' },
  ],
});
var currentDateTime = new Date();

// 格式化日期和时间
var currentDateTime = new Date();
var formattedTime = currentDateTime.toISOString().replace('T', ' ').replace(/\..+/, '');



function checkOutTime(rule, value, callback) {
  if (value < formattedTime) {
    callback(new Error('离席时间不能早于今天'));
  } else {
    callback();
  }
}

function checkInTime(rule, value, callback) {
	console.dir(formattedTime)
	console.dir(value)
  if (value < formattedTime) {
    callback(new Error('回来时间不能早于今天'));
  } else if (value < YlX.outtime) {
    callback(new Error('回来时间不能早于离席时间'));
  } else {
    callback();
  }
}


function check (rule,value,callback){
	get('/outin/check',{id: props.id,  value,field:rule.field},content=>{
		if(content){
			callback()
		}else{
			callback(new Error())
		}
	})
}
// function Save() {	
// formObject.value.validate(vaild=>{
// 	if(vaild){
// 		post('/bedroom/add',YlX,content=>{
// 			console.dir(content)
// 		})
// 	}
// })
// }
function Save() {	
formObject.value.validate(vaild=>{
	if(vaild){
		post('/outin/add',YlX,content=>{
			console.dir(content)
					  emits('update:show',false)
					  emits('getTableData')
		})
	}
})
}
function getById(){
	get('/outin/getById',{ id:props.id },content=>{
		for(const key in YlX){
		if(Object.prototype.hasOwnProperty.call(content,key)){
			YlX[key] = content[key]
		}
			
		}
		//console.dir(content)
	})
	
}
</script>

<style lang="scss">
</style>
