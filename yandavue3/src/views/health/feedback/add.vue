<template>
  <div style="margin-right: 30px">
    <el-form ref="formObj" :model="wyform" :rules="rules" label-width="100px">
      <el-form-item label="客户姓名" prop="name">
        <el-input maxLength="20" v-model="wyform.name" placeholder="请输入客户姓名"></el-input>
      </el-form-item>
	 <el-form-item label="性别" prop="sex">
	         <el-radio-group v-model="wyform.sex">
	           <el-radio :value="'男'">男</el-radio>
	           <el-radio :value="'女'">女</el-radio>
	         </el-radio-group>
	       </el-form-item>
	  <el-form-item label="事项" prop="thing">
	    <el-input maxLength="20" v-model="wyform.thing" placeholder="请输入事项"></el-input>
	  </el-form-item>
   <el-form-item label="事件时间" prop="ntime">
     <el-date-picker
       v-model="wyform.ntime"
       style="width: 100%"
       type="date"
       value-format="YYYY-MM-DD"
     ></el-date-picker>
   </el-form-item>
   <el-form-item label="备注" prop="memo">
     <el-input maxLength="20" v-model="wyform.memo" placeholder="请输入备注"></el-input>
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
const props = defineProps(['id'])
const wyform = reactive({
  id:null,
  name: '',
  sex: '',
  thing:"",
  ntime: '',
  memo:"",
  status:"未处理"
  
})
const formObj = ref()
if (props.id) {
		wyform.id = props.id
		//getById()
	}
const rules = reactive({
	name: [
		{ required: true, message: '请输入姓名', trigger: 'blur' }
	],
	sex: [
		{ required: true, message: '请输入性别', trigger: 'blur' }
	],
	thing: [
		{ required: true, message: '请输入事项', trigger: 'blur' }
	],
	ntime: [
		{ required: true, message: '请输入事件时间', trigger: 'blur' }
	]
})
function check (rule, value, callback) {
		get("/feedback/check", { id: props.id, value, field: rule.field }, content => {
			if (content) {
				callback()
			} else {
				callback(new Error())
			}
		})
	}

function save() {
	console.dir(wyform)
	post(props.id ? "/feedback/update" : "/feedback/add", wyform, content => {
		
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

</script>

<style scoped lang="scss"></style>