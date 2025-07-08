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
		  <el-form-item label="外出事由"prop="gooutreason">
		  			  <el-input v-model="addform.gooutreason" placeholder="请输入退住原因"></el-input>
		  </el-form-item>
		  <el-form-item label="外出时间"prop="goouttime">
		  			  <el-date-picker v-model="addform.goouttime" type="date" value-format="YYYY-MM-DD"></el-date-picker>
		  </el-form-item>
		  <el-form-item label="预计回院时间"prop="wantbacktime">
		  			  <el-date-picker v-model="addform.wantbacktime" type="date" value-format="YYYY-MM-DD"></el-date-picker>
		  </el-form-item>
		  <el-form-item label="陪同人"prop="companions">
		  			  <el-input v-model="addform.companions"></el-input>
		  </el-form-item>
		  <el-form-item label="与老人关系"prop="relationship">
		  			  <el-input v-model="addform.relationship"></el-input>
		  </el-form-item>
		  <el-form-item label="陪同人电话"prop="companionstel">
		  			  <el-input v-model="addform.companionstel"></el-input>
		  </el-form-item>
		  <el-form-item label="备注"prop="gooutremarks">
		  			  <el-input v-model="addform.gooutremarks"></el-input>
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
	recordid:'',
	customername:'',
	gooutreason:'',
	goouttime:'2024-08-30',
	wantbacktime:'2024-08-29',
	companions:'',
	relationship:'',
	companionstel:'',
	gooutremarks:''
})
const tableData=ref([])
const nameList=reactive([])
const formObj=ref()
const rules=reactive({
	gooutreason:[
		{required:true,message:'请输入外出事由',trigger:'blur'}
	],
	goouttime:[
		{required:true,message:'请输入外出时间',trigger:'blur'}
	],
	wantbacktime:[
		{required:true,message:'请输入预计回院时间',trigger:'blur'}
	],
	companions:[
		{required:true,message:'请输入陪同人',trigger:'blur'}
	],
	relationship:[
		{required:true,message:'请输入陪同人与老人关系',trigger:'blur'}
	],
	companionstel:[
		{required:true,message:'请输入陪同人电话',trigger:'blur'},
		{pattern:/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,message:'请输入正确的手机号',trigger:'blur'}
	]
})
function save(){
	formObj.value.validate(valid=>{
		if(valid){
				post('/checkIn/goOutbanli',addform,content=>{
					emits('update:show',false)
					emits('getTableData')
					console.dir()})
		}
	})
}
function getCanGoOut(){
	get('/checkIn/getCanGoOut',{},content=>{
      tableData.value=content	  
	})
}
getCanGoOut()
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
