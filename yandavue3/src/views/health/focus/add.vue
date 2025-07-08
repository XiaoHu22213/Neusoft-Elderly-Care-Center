<template>
  <div style="margin-right: 30px"> 
	  <el-form ref="formObj" :model="addform" :rules="rules" label-width="100px" >
		  <el-form-item label="购买数量" prop="num">
			  <el-input type="number" v-model="addform.num" placeholder="请输入购买数量"></el-input>
		  </el-form-item>
		  <el-form-item label="备注" prop="memo">
		  			  <el-input v-model="addform.memo" placeholder="请输入备注"></el-input>
		  </el-form-item>
		  
		  <el-form-item >
		  			<el-button type="primary" plain @click="save">保存</el-button>  
		  </el-form-item>
		  
	  </el-form>
  </div>
</template>

<script setup>
import{ref,reactive} from 'vue'
import {get,post,request,_axios} from'@/axios'
const emits=defineEmits(['update:show','getTableData'])
const props=defineProps(['cuid','cid'])
const addform=reactive({
	
	cuid:props.cuid,
	cid:props.cid,
	num:0,
	memo:null
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
	num:[
		{required:true,message:'请输入购买数量',trigger:'blur'}
	],
	memo:[
		{required:true,message:'请输入备注',trigger:'blur'}
	]

})
function save(){
	console.dir(addform)
	formObj.value.validate(valid=>{
		if(valid){
				post('/customcontent/updatenub',addform,content=>{
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


</script>

<style scoped lang="scss"></style>
