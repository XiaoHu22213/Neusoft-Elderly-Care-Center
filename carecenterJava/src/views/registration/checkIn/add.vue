<template>
  <div style="margin-right: 30px"> 
	  <el-form ref="formObj" :model="addform" :rules="rules" label-width="100px" >
		  <el-form-item label="客户姓名" prop="customername">
			  <el-input v-model="addform.customername" placeholder="请输入客户姓名"></el-input>
		  </el-form-item>
		  <el-form-item label="年龄" prop="customerage">
		  			  <el-input v-model="addform.customerage" placeholder="请输入年龄"></el-input>
		  </el-form-item>
		  <el-form-item  label="性别" prop="customersex">
			  <el-radio-group v-model="addform.customersex">
				  <el-radio :value="1">男</el-radio>
				  <el-radio :value="0">女</el-radio>
			  </el-radio-group>
		  </el-form-item>
		  <el-form-item label="身份证号"prop="idcard">
		  			  <el-input v-model="addform.idcard" placeholder="请输入身份证号"></el-input>
		  </el-form-item>
		  <el-form-item label="房间号"prop="roomid">
		  			  <el-input v-model="addform.roomid" placeholder="请输入房间号"></el-input>
		  </el-form-item>
		  <el-form-item label="所属楼房"prop="buildingid">
		  			  <el-input v-model="addform.buildingid" placeholder="请输入所属楼房"></el-input>
		  </el-form-item>
		  <el-form-item label="档案号"prop="recordid">
		  			  <el-input v-model="addform.recordid" placeholder="请输入档案号"></el-input>
		  </el-form-item>
		  <el-form-item label="老人类型"prop="eldertype">
			  <el-radio-group v-model="addform.eldertype">
				  <el-radio :value="0">活力老人</el-radio>
				   <el-radio :value="1">自理老人</el-radio> 
				  <el-radio :value="2">护理老人</el-radio> 
			  </el-radio-group>
		  </el-form-item>
		  <el-form-item label="入住时间"prop="checkindate">
		  			  <el-date-picker v-model="addform.checkindate" type="date" value-format="YYYY-MM-DD"></el-date-picker>
		  </el-form-item>
		  <el-form-item label="合同到期时间"prop="expirationdate">
		  			  <el-date-picker v-model="addform.expirationdate"type="date" value-format="YYYY-MM-DD"></el-date-picker>
		  </el-form-item>
		  <el-form-item label="联系电话"prop="contacttel">
		  			  <el-input v-model="addform.contacttel"placeholder="请输入联系电话"></el-input>
		  </el-form-item>
		  <el-form-item label="备注"prop="remarks">
		  			  <el-input v-model="addform.remarks"></el-input>
		  </el-form-item>
		  <el-form-item label="护理等级" prop='nursingLevel' >
		  		<el-select
		           v-model="addform.nursingLevel"
		           clearable
		           placeholder="请选择类型"
		           style="width: 240px"
		           @change="handleChange"
		         >
		           <el-option
		             v-for="item in tableData"
		             :key="item.id"
		             :label="item.level"
		             :value="item.id"
		           > </el-option>
		         </el-select>
		  			  </el-form-item>
		  <el-form-item >
		  			<el-button type="primary plain" @click="save">保存</el-button>  
		  </el-form-item>
		  
	  </el-form>
  </div>
</template>

<script setup>
import{ref,reactive} from 'vue'
import {get,post,request,_axios} from'@/axios'
const emits=defineEmits(['update:show','getTableData'])
const props=defineProps(['id'])
const addform=reactive({
	id:null,
	customername:'',
	customerage:'',
	customersex:null, 
	idcard:'',
	roomid:'',
	buildingid:'',
	recordid:'',
	eldertype:null,
	checkindate:'2024-08-29',
	expirationdate:'2024-08-30',
	contacttel:'',
	remarks:'',
	nursingLevel:''
})
let tableData=ref([])
getTableData()
function getTableData () {
	
	get('/nurselevel/effctivelist', null, content => {
		//console.dir(content)
		tableData.value = content
		//console.dir(content)
	})

}
const formObj=ref()
const rules=reactive({
	customername:[
		{required:true,message:'请输入客户姓名',trigger:'blur'}
	],
	customerage:[
		{required:true,message:'请输入客户年龄',trigger:'blur'}
	],
	customersex:[
		{required:true,message:'请选择客户性别',trigger:'blur'}
	],
	idcard:[
		{required:true,message:'请输入客户身份证号',trigger:'blur'}
	],
	roomid:[
		{required:true,message:'请输入客户房间号',trigger:'blur'}
	],
	buildingid:[
		{required:true,message:'请输入所属楼房',trigger:'blur'}
	],
	recordid:[
		{required:true,message:'请输入客户档案号',trigger:'blur'},
		{validator:check, message:'该档案号已存在',trigger:'blur'}
	],
	eldertype:[
		{required:true,message:'请选择老人类型',trigger:'blur'}
	],
	checkindate:[
		{required:true,message:'请输入入住时间',trigger:'blur'}
	],
	expirationdate:[
		{required:true,message:'请输入合同到期时间',trigger:'blur'}
	],
	contacttel:[
		{required:true,message:'请输入客户联系电话',trigger:'blur'},
		{pattern:/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,message:'请输入正确的手机号',trigger:'blur'}
	],
	nursingLevel:[
			{required:true,message:'请选择客户护理级别',trigger:'blur'}
	]

})
function save(){
	formObj.value.validate(valid=>{
		if(valid){
				post(props.id?'/checkIn/update':'/checkIn/add',addform,content=>{
					emits('update:show',false)
					emits('getTableData')})
		}
	})
}
function check (rule, value, callback) {
_axios.request({
	url:'/checkIn/check',
	method:'get',
	params:{value}
}).then(response=>{
	if(response.data){
		callback()
	}else{
		callback(new Error())
	}
})
}
function getById(){
	get('/checkIn/getById',{id:props.id},content=>{
		for(const key in addform){
		if(Object.prototype.hasOwnProperty.call(content,key))	
			{addform[key]=content[key]}
		}
		console.dir(content)
	})
}
if(props.id){
	addform.id=props.id
	getById()
}
</script>

<style scoped lang="scss"></style>
