<template>
  <div style="margin-right: 30px"> 
   <el-form>
		  <el-form-item label="实际回院时间"prop="truebacktime">
		  			  <el-date-picker v-model="auditform.truebacktime" type="date" value-format="YYYY-MM-DD"></el-date-picker>
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
	truebacktime:''
})
const rules=reactive({
	truebacktime:[
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
