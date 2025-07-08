<template>
  <div style="margin-right: 30px;">
    <el-form label-width="100px" ref="formObject" :model="YlX" :rules="rules">
     <el-form-item label="床位编号" v-if="!props.id" prop="bedid">
	<!--  <el-form-item label="床位编号"  prop="bedid"> -->
        <el-input v-model="YlX.bedid" placeholder="请输入床位编号"></el-input>
      </el-form-item>
      <el-form-item  label="入住人姓名" prop="peopleid">
        <el-select
           v-model="YlX.peopleid"
           clearable
           placeholder="请选择类型"
           style="width: 240px"
           @change="handleChange"
         >
           <el-option
             v-for="item in zclData"
             :key="item.id"
             :label="item.customername"
             :value="item.id"
           > </el-option>
         </el-select>
        	  </el-form-item>
      
      <!-- <el-form-item label="状态" prop="status" > -->
      <!--  <el-radio-group v-model="YlX.status">
          <el-radio :value="'占用'">占用</el-radio> -->
         <!-- <el-radio :value="'空闲'">空闲</el-radio> -->
          <!-- <el-radio :value="'离席'">离席</el-radio>
        </el-radio-group> -->
   <!--   </el-form-item> -->
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
let zclData=ref([])
const YlX = reactive({
  id:'',
  bedid: '',
  peoplename: '',
  status: '',
  peopleid:''
});

function getzclData()
{
	get('/bedroom/effctivelist', null, content => {
		//console.dir(content)
		zclData.value = content
		//console.dir(content)
	})
}
getzclData()
const rules = reactive({
  bedid: [
    { required: true, message: '请输入床位编号', trigger: 'blur' },
    { pattern: /^[A-N](?:[1-9]|1[0-9]|2[0-9]|3[0-8])$/, message: '请输入正确的床位编号，例如A1, B2', trigger: 'blur' },
    { validator: check, message:'这床有人睡了，选择其他床吧',trigger: 'blur' }
  ],  
  peopleid:[
     
    { validator: check, message:'一个人不能睡两张床',trigger: 'blur' }
  ],
  peoplename: [
  //  { message: '请输入入住人姓名', trigger: 'blur' },
	    { required: true, message: '请输入入住人姓名', trigger: 'blur' },
		
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
});
if(props.id)
{
 	YlX.id = props.id
	getById()
	
}

function check (rule,value,callback){
	get('/bedroom/check',{id: props.id,  value,field:rule.field},content=>{
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
		post(props.id ?'/bedroom/update': '/bedroom/add',YlX,content=>{
			
			//console.dir(props.id)
			//           ElMessage({
			//             type: 'success',
			//             message: '操作成功',
			//           });
				  emits('update:show',false)
			 		  emits('getTableData')
		})
	}
})
}
function getById(){
	get('/bedroom/getById',{ id:props.id },content=>{
		for(const key in YlX){
		if(Object.prototype.hasOwnProperty.call(content,key)){
			YlX[key] = content[key]
		}
			
		}
		console.dir(content)
	})
	
}
const handleChange = (value) => {

for(let item of zclData.value )
	{
	
		if(YlX.peopleid==item.id)
		{
		YlX.peoplename=	item.customername
		}
		}
}
</script>

<style lang="scss">
</style>
