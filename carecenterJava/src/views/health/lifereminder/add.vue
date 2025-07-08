<template>
  <div style="margin-right: 30px">
    <el-form ref="formObj" :model="wyform" :rules="rules" label-width="100px">
      <el-form-item label="客户姓名" prop="name">
        <el-input maxLength="20" v-model="wyform.name" placeholder="请输入客户姓名"></el-input>
      </el-form-item>
	  <el-form-item label="联系方式" prop="phone">
	    <el-input maxLength="20" v-model="wyform.phone" placeholder="请输入联系方式"></el-input>
	  </el-form-item>
	  <el-form-item label="事件" prop="rememberthing">
	    <el-input maxLength="20" v-model="wyform.rememberthing" placeholder="请输入事件"></el-input>
	  </el-form-item>
   <el-form-item label="事件时间" prop="thingtime">
     <el-date-picker
       v-model="wyform.thingtime"
       style="width: 100%"
       type="datetime"
       value-format="YYYY-MM-DD HH:mm:ss"
     ></el-date-picker>
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
  phone: '',
  rememberthing: '',
  thingtime:""
  
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
	
	rememberthing: [
		{ required: true, message: '请输入事件', trigger: 'blur' }
	],
	thingtime: [
		{ required: true, message: '请输入事件时间', trigger: 'blur' }
	],
	phone: [
		{ required: true, message: '请输入手机号码', trigger: 'blur' },
		{ pattern: /^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\d{8}$/, message: '请输入正确的中国手机号', trigger: 'blur' },
		{ validator: check, message: '该手机号已经被使用', trigger: 'blur' }
	]
})
function check (rule, value, callback) {
		get("/lifereminder/check", { id: props.id, value, field: rule.field }, content => {
			if (content) {
				callback()
			} else {
				callback(new Error())
			}
		})
	}

function save() {
	console.dir(wyform)
	post(props.id ? "/lifereminder/update" : "/lifereminder/add", wyform, content => {
		
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