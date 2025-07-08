<template>
  <div style="margin-right: 30px"> 
   <el-form>
		  <el-form-item  label="审核状态" prop="status">
			  <el-radio-group v-model="auditform.status">
				  <el-radio :value="1">通过</el-radio>
				  <el-radio :value="2">不通过</el-radio>
				  <el-radio :value="3">撤销</el-radio>
			  </el-radio-group>
		  </el-form-item>
		  <el-form-item label="备注"prop="remarks">
		  			  <el-input v-model="auditform.remarks"></el-input>
		  </el-form-item>
		  <el-form-item >
		  			<el-button type="primary plain" @click="save">保存</el-button>  
		  </el-form-item>
	  </el-form>
  </div>
</template>

<script setup>
import{ref,reactive} from 'vue'
import {get,post,request} from'@/axios'
const emits=defineEmits(['update:show','getTableData'])
const props=defineProps(['id'])
const auditform=reactive({
	status:null, 
	remarks:''
})
const rules=reactive({
	status:[
		{required:true,message:'请选择审核状态',trigger:'blur'}
	],
})
function save(){
				post('/checkIn/update',auditform,content=>{
					emits('update:show',false)
					emits('getTableData')
					console.dir()})
}
function getById(){
	get('/checkIn/getById',{id:props.id},content=>{
		for(const key in auditform){
		if(Object.prototype.hasOwnProperty.call(content,key))	
			{auditform[key]=content[key]}
		}
		
		console.dir(content)
	})
}
if(props.id){
	auditform.id=props.id
	getById()
}
</script>

<style scoped lang="scss"></style>
