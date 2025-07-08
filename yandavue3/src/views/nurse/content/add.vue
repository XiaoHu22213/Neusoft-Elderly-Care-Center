<template>
  <div style="margin-right: 30px">
    <el-form ref="formObj" :model="wyform" :rules="rules" label-width="100px">
      <el-form-item label="护理内容" prop="nursecontent">
        <el-input maxLength="20" v-model="wyform.nursecontent" placeholder="请输入护理内容"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="cdescribe">
        <el-input maxLength="20" v-model="wyform.cdescribe" placeholder="请输入描述"></el-input>
      </el-form-item>
	  <el-form-item label="价格" prop="price">
	    <el-input maxLength="20" v-model="wyform.price" placeholder="请输入价格"></el-input>
	  </el-form-item>
	   <el-form-item label="状态" prop="status">
	      <el-radio-group v-model="wyform.status">
	        <el-radio :value="1">启用</el-radio>
	        <el-radio :value="0">禁用</el-radio>
	      </el-radio-group>
	    </el-form-item>
      
      <el-form-item label="备注" prop="memo">
          <el-input type="textarea" :rows="5" v-model="wyform.memo" placeholder="请输入备注"></el-input>
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
  nursecontent: '',
  cdescribe: '',
  price: '',
  memo:'',
  status:null
  
})
const formObj = ref()
if (props.id) {
		wyform.id = props.id
		//getById()
	}
const rules = reactive({
	nursecontent: [
		{ required: true, message: '请输入护理内容', trigger: 'blur' },
			{ validator: check, message: '该护理内容已经拥有', trigger: 'blur' }
	],
	cdescribe: [
		{ required: true, message: '请输入描述', trigger: 'blur' }
	],
	status: [
		{ required: true, message: '请选择护理内容状态', trigger: 'blur' }
	],
	price: [
		{ required: true, message: '请输入价格', trigger: 'blur' }
	],
	memo:[
		{ required: true, message: '请输入备注', trigger: 'blur' }
	]

})


function save() {
	post(props.id ? "/nursecontent/update" : "/nursecontent/add", wyform, content => {
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