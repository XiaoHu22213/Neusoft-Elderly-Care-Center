<template>
  <div style="margin-right: 30px">
    <el-form ref="formObj" :model="wyform" :rules="rules" label-width="100px">
      <el-form-item label="护理级别" prop="level">
        <el-input maxLength="20" v-model="wyform.level" placeholder="请输入护理级别"></el-input>
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
  level: '',
  status: 1,
   memo: ''
  
})
	if (props.id) {
		wyform.id = props.id
		//getById()
	}
const rules = reactive({
	level: [
		{ required: true, message: '请输入护理等级', trigger: 'blur' },
			{ validator: check, message: '该护理级别已经被使用', trigger: 'blur' }
	],
	status: [
		{ required: true, message: '请输入护理状态', trigger: 'blur' }
	]
})
const formObj = ref()


function save() {
	console.dir(wyform)
	post(props.id ? "/nurselevel/update" : "/nurselevel/add", wyform, content => {
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
		get('/nurselevel/check', { id: props.id, value, field: rule.field }, content => {
			if (content) {
				callback()
			} else {
				callback(new Error())
			}
		})
	}
</script>

<style scoped lang="scss"></style>