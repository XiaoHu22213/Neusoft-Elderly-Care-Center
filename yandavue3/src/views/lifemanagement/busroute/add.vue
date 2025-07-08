<template>
  <div style="margin-right: 30px">
    <el-form ref="formObj" :model="wyform" :rules="rules" label-width="100px">
      <el-form-item label="班车号" prop="name">
        <el-input maxLength="20" v-model="wyform.name" placeholder="请输入班车号"></el-input>
      </el-form-item>
    <el-form-item label="班车时间" prop="bustime">
      <el-time-picker
        v-model="wyform.bustime"
        placeholder="请输入班车时间"
        format=HH:mm:ss
        value-format=HH:mm:ss
      />
    </el-form-item>
	  <el-form-item label="班车路线" prop="route">
	    <el-input maxLength="20" v-model="wyform.route" placeholder="请输入班车路线"></el-input>
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
  bustime: '',
  route: ''
  
})
const formObj = ref()
if (props.id) {
		wyform.id = props.id
		//getById()
	}
const rules = reactive({
	name: [
		{ required: true, message: '请输入班车号', trigger: 'blur' }
			
	],
	bustime: [
		{ required: true, message: '请输入班车时间', trigger: 'blur' }
	],
	route: [
		{ required: true, message: '请输入班车路线', trigger: 'blur' }
	]


})


function save() {
	console.dir(wyform)
	post(props.id ? "/busroute/update" : "/busroute/add", wyform, content => {
		
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
function check(rule, value, callback) {
		get('/nursecontent/check', { id: props.id, value, field: rule.field }, content => {
			if (content) {
				callback()
			} else {
				callback(new Error())
			}
		})
	}
</script>

<style scoped lang="scss"></style>