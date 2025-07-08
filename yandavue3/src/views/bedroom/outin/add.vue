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
      <el-form-item label="人名" prop="outinname">
        <el-input v-model="YlX.outinname" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="床号" prop="bednum">
        <el-input v-model="YlX.bednum" placeholder="请输入床位号"></el-input>
      </el-form-item>
      <el-form-item label="事由" prop="thing">
        <el-input v-model="YlX.thing" placeholder="请输入离席事由"></el-input>
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
let props = defineProps(['id'])

const formObject = ref();

const YlX = reactive({
  id:'',
  outtime: '',
  intime: '',
  outinname: '',
  bednum: '',
  thing: ''
});

const rules = reactive({
  outinname: [{ required: true, message: '请输入您的姓名？', trigger: 'blur' }],
  intime: [{ required: true, message: '未选择回来时间', trigger: 'blur' }],
  outtime: [{ required: true, message: '未选择离开时间', trigger: 'blur' }],
  bednum: [
    { required: true, message: '请输入床位号', trigger: 'blur' },
    { pattern: /^[A-Z]\d{1,2}$/, message: '请输入正确床位号，例如A1,B2,B3', trigger: 'blur' },
    { validator: check, message: '改床位已有人，请选择其他床位', trigger: 'blur' }
  ],
  thing: [{ required: true, message: '请输入要去做什么', trigger: 'blur' }],
});

if(props.id)
{
 	YlX.id = props.id
	getById()
	
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
		post(props.id ?'/outin/update': '/outin/add',YlX,content=>{
			console.dir(content)
			          ElMessage({
			            type: 'success',
			            message: '操作成功',
			          });
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
		console.dir(content)
	})
	
}
</script>

<style lang="scss">
</style>
