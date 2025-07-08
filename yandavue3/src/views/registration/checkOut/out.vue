<template>
  <div style="margin-right: 30px"> 
	  <el-form :inline="true" ref="formObj" :model="addform" :rules="rules" label-width="100px" >
		  <el-form-item label="客户姓名" prop="customername">
			  <el-select
			          v-model="addform.recordid"
			          clearable
			          placeholder="Select"
			          style="width: 240px"
			          @change="handleChange"
			        >
			          <el-option
			            v-for="item in tableData"
			            :key="item.recordid"
			            :label="`${item.customername} (${item.recordid})`"
			            :value="item.recordid"
			          />
			        </el-select>
		  </el-form-item>

		  <el-form-item  label="退住类型" prop="checkouttype">
			  <el-radio-group v-model="addform.checkouttype">
				  <el-radio :value="0">正常退住</el-radio>
				  <el-radio :value="1">死亡退住</el-radio>
				  <el-radio :value="2">保留床位</el-radio>
			  </el-radio-group>
		  </el-form-item>
		  <el-form-item label="退住原因"prop="checkoutreason">
		  			  <el-input v-model="addform.checkoutreason" placeholder="请输入退住原因"></el-input>
		  </el-form-item>
		  <el-form-item label="退住时间"prop="checkoutdate">
		  			  <el-date-picker v-model="addform.checkoutdate" type="date" value-format="YYYY-MM-DD"></el-date-picker>
		  </el-form-item>
		  <el-form-item label="申请时间"prop="asktime">
		  			  <el-date-picker v-model="addform.asktime" type="date" value-format="YYYY-MM-DD"></el-date-picker>
		  </el-form-item>
		  <el-form-item label="备注"prop="remarks">
		  			  <el-input v-model="addform.remarks"></el-input>
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
const props=defineProps(['id','recordid'])
const addform=reactive({
	id:null,
	customername:'',
	checkouttype:null, 
	checkoutreason:'',
	recordid:'',
	checkoutdate:'2024-08-30',
	asktime:'2024-08-29',
	checkoutremarks:''
})
const tableData=ref([])
const nameList=reactive([])
const formObj=ref()
const rules=reactive({
	checkouttype:[
		{required:true,message:'请选择退住类型',trigger:'blur'}
	],
	checkoutreason:[
		{required:true,message:'请输入退住原因',trigger:'blur'}
	],
	checkoutdate:[
		{required:true,message:'请输入退住时间',trigger:'blur'}
	],
	asktime:[
		{required:true,message:'请输入申请时间',trigger:'blur'}
	]
})
function save(){
	formObj.value.validate(valid=>{
		if(valid){
				post('/checkIn/checkoutbanli',addform,content=>{
					emits('update:show',false)
					emits('getTableData')
					console.dir()})
		}
	})
}
function getCanCheckOut(){
	get('/checkIn/getCanCheckOut',{},content=>{
      tableData.value=content	  
	})
}
getCanCheckOut()
function getById(){
	get('/checkIn/getById',{id:props.id},content=>{
		for(const key in addform){
		if(Object.prototype.hasOwnProperty.call(content,key))	
			{addform[key]=content[key]}
		}
	})
}
if(props.id){
	addform.id=props.id
	getById()
}

</script>

<style scoped lang="scss"></style>
