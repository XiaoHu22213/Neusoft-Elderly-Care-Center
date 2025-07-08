<template>
 <div style="margin-right: 30px">
   <el-form ref="formObj" :model="wyform" :rules="rules" label-width="100px">
    
 	  <el-form-item label="护理内容" prop='cid' >
 	  		<el-select
 	           v-model="wyform.cid"
 	           clearable
 	           placeholder="请选择类型"
 	           style="width: 240px"
 	           @change="handleChange"
 	         >
 	           <el-option
 	             v-for="item in tableData"
 	             :key="item.id"
 	             :label="item.nursecontent"
 	             :value="item.id"
 	           > </el-option>
 	         </el-select>
 	  			  </el-form-item>
				  <el-form-item label="护理人员" prop='nurseid' >
				  		<el-select
				           v-model="wyform.nurseid"
				           clearable
				           placeholder="请选择护理人员"
				           style="width: 240px"
				           @change="handleChange"
				         >
				           <el-option
				             v-for="item in mxData"
				             :key="item.id"
				             :label="item.name"
				             :value="item.id"
				           > </el-option>
				         </el-select>
				  			  </el-form-item>
     <el-form-item>
       <el-button type="primary" plain :icon="Save" @click="save">保存</el-button>
     </el-form-item>
   </el-form>
 </div>
</template>

<script setup>
import Save from '@/components/icons/save'
import { ref, reactive } from 'vue'
import { get, post } from '@/axios'

import { PictureFilled } from '@element-plus/icons-vue'
const props = defineProps(['id','name'])
const emits = defineEmits(['update:nursingContent'])
let tableData = ref([])
let mxData=ref([])
const wyform = reactive({

cuid:props.id,
  cid:null,
  name:props.name,
  content:"",
  nursepeople:"",
  nurseid:null
})
// const mxform= reactive({

//   cuid:props.id,
//   cid:null,
//   name:props.name,
//   content:""
  
  
// })

const formObj = ref()
getTableData()
getMxData()
function getTableData () {
//	console.dir(wyform.id)
	get('/customcontent/getcontent', wyform, content => {
		tableData.value = content
		//console.dir(tableData.value)
		
	})
}
function getMxData(){
	get('/customcontent/getnurse',null,content =>{
		mxData.value=content
		console.dir(content)
	})
}
function save() {
	//console.dir(tableData)

	post( "/record/add", wyform, content => {
		
		emits('update:show', false)
		emits('getTableData')
	}, formObj)
}
const handleChange = (value) => {
	for(let item of tableData.value )
	{
	
		if(wyform.cid==item.id)
		{
		wyform.content=	item.nursecontent
		}
		}
		for(let item of mxData.value )
		{
		
			if(wyform.nurseid==item.id)
			{
			wyform.nursepeople=	item.name
			}
			}
		//console.dir(wyform)
}

</script>

<style scoped lang="scss"></style>