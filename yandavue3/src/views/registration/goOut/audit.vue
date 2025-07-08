<template>
  <div style="margin-right: 30px"> 
   <el-form>
		  <el-form-item  label="审批状态" prop="gooutstatus">
			  <el-radio-group v-model="auditform.gooutstatus">
				  <el-radio :value="1">通过</el-radio>
				  <el-radio :value="2">不通过</el-radio>
				  <el-radio :value="3">撤销</el-radio>
			  </el-radio-group>
		  </el-form-item>
		  <el-form-item label="审批人"prop="gooutauditperson">
		  			  <el-input v-model="auditform.gooutauditperson"></el-input>
		  </el-form-item>
		  <el-form-item label="审批时间"prop="gooutaudittime">
		  			  <el-date-picker v-model="auditform.gooutaudittime" type="date" value-format="YYYY-MM-DD"></el-date-picker>
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
	gooutstatus:null, 
	gooutauditperson:'',
	gooutaudittime:'',
	remarks:''
})
const rules=reactive({
	gooutstatus:[
		{required:true,message:'请选择审批状态',trigger:'blur'}
	],
	gooutauditperson:[
		{required:true,message:'请输入审批人',trigger:'blur'}
	],
	gooutaudittime:[
		{required:true,message:'请选择审批时间',trigger:'blur'}
	]
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
	})
}
if(props.id){
	auditform.id=props.id
	getById()
}
</script>

<style scoped lang="scss"></style>
