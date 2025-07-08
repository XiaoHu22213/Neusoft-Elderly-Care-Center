<template>
  <div style="margin-right: 30px">
    <el-form ref="formObj" :model="wyform"  label-width="100px">
		<el-form-item label="处理人员" prop='people' >
				<el-select
		         v-model="wyform.people"
		         clearable
		         placeholder="请选择处理人员"
		         style="width: 240px"
		         @change="handleChange"
		       >
		         <el-option
		           v-for="item in mxData"
		           :key="item.name"
		           :label="item.name"
		           :value="item.name"
		         > </el-option>
		       </el-select>
					  </el-form-item>
	  <el-form-item label="处理内容" prop="content">
	    <el-input type="textarea" :rows="5" v-model="wyform.content" placeholder="请输入备注"></el-input>
	  </el-form-item>
      <el-form-item>
        <el-button type="primary" plain :icon="Save" @click="save">修改</el-button>
      </el-form-item>
	  
    </el-form>
  </div>
</template>

<script setup>
import Save from '@/components/icons/save'
import { ref, reactive } from 'vue'
import { get, post } from '@/axios'
 import { defineProps,defineEmits } from 'vue';
import { PictureFilled } from '@element-plus/icons-vue'
let mxData=ref([])
const formObj = ref()
const props = defineProps({
  id: {
    type: Number,
    required: true
  }
})
const emits= defineEmits(['update:memo','update:id','getTableData'])

const wyform = reactive({

  content:'',
  id:props.id,
  people:''
})
function getMxData(){
	get('/customcontent/getall',null,content =>{
		mxData.value=content
		console.dir(content)
	})
}
getMxData()
function save() {
	//emits('update:memo',wyform.description ) 
	console.dir(wyform)
	post( "/feedback/updatememo", wyform, content => {
		
	}, formObj)
	emits('getTableData')
}

function uploadFile (file) {
	wyform.file = file.raw
}
function removeFile () {
	wyform.file = null
}
const handleChange = (value) => {
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