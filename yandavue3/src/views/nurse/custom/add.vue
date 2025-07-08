<template>
  <div style="margin-right: 30px">
    <el-form ref="formObj" :model="wyform" :rules="rules" label-width="100px">
      <el-form-item label="姓名" prop="name">
        <el-input maxLength="20" v-model="wyform.name" placeholder="请输入管理员姓名"></el-input>
      </el-form-item>
     
      <el-form-item label="性别" prop="sex">
        <el-radio-group v-model="wyform.sex">
          <el-radio :value="1">男</el-radio>
          <el-radio :value="0">女</el-radio>
        </el-radio-group>
		
      </el-form-item>
      <el-form-item label="生日" prop="birthday">
        <el-date-picker
          v-model="wyform.birthday"
          style="width: 100%"
          type="date"
          value-format="YYYY-MM-DD"
        ></el-date-picker>
      </el-form-item>
	  <el-form-item label="护理等级" prop='id' >
	  		<el-select
	           v-model="wyform.id"
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
const emits = defineEmits(['update:nursingContent'])

const wyform = reactive({

  name: '',
  sex: null,
  birthday: '2000-01-01',
 id:null
  
})
let tableData=ref([])
const rules = reactive({
	name: [
		{ required: true, message: '请输入姓名', trigger: 'blur' }
	],
	sex: [
		{ required: true, message: '请选择管理员性别', trigger: 'blur' }
	],
	birthday: [
		{ required: true, message: '请输入管理员生日', trigger: 'blur' }
	],
id: [
		{ required: true, message: '请输入护理等级', trigger: 'blur' },
			//{ validator: check, message: '该护理级别已经被使用', trigger: 'blur' }
	]
})
const formObj = ref()

getTableData()
function getTableData () {
	
	get('/nurselevel/effctivelist', null, content => {
		//console.dir(content)
		tableData.value = content
		//console.dir(content)
	})

}
function check (rule, value, callback) {
	get("/nurselevel/check", {  value, field: rule.field }, content => {
		if (content) {
			callback()
		} else {
			callback(new Error())
		}
	})
}
function save() {
	post( "/customcontent/add", wyform, content => {
		
		emits('update:show', false)
		emits('getTableData')
	}, formObj)
}

function uploadFile (file) {
	wyform.file = file.raw
}
function removeFile () {
	wyform.file = null
}
const handleChange = (value) => {
	console.dir(wyform.id)
	//	console.dir(wyform.cid)
		}
</script>

<style scoped lang="scss"></style>