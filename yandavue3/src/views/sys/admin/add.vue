<template>
  <div style="margin-right: 30px">
    <el-form ref="formObj" :model="wyform" :rules="rules" label-width="100px">
      <el-form-item label="姓名" prop="name">
        <el-input maxLength="20" v-model="wyform.name" placeholder="请输入管理员姓名"></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickyName">
        <el-input maxLength="20" v-model="wyform.nickyName" placeholder="请输入管理员昵称"></el-input>
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
      <el-form-item label="手机号" prop="phone">
        <el-input maxLength="15" v-model="wyform.phone" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item label="电子信箱" prop="email">
        <el-input maxLength="64" v-model="wyform.email" placeholder="请输入电子信箱"></el-input>
      </el-form-item>
      <el-form-item label="头像" prop="file">
				<div style="width: 150px; height: 150px; overflow: hidden;">
					<el-upload
						list-type="picture-card"
						:auto-upload="false"
						action="#"
						:limit="1"
						:on-change="uploadFile"
						:on-remove="removeFile"
						v-model:file-list="fileList">
						<el-icon><PictureFilled /></el-icon>
					</el-upload>
				</div>
      </el-form-item>

      <el-form-item v-if="!props.id" label="登录密码" prop="password">
        <el-input v-model="wyform.password" placeholder="请输入登录密码" show-password></el-input>
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
import url from './util'
import {getPath} from '@/util'
import { PictureFilled } from '@element-plus/icons-vue'
const emits = defineEmits(['update:show', 'getTableData'])
const props = defineProps(['id'])
const fileList=reactive([ ])
const wyform = reactive({
	id: null,
  name: '',
  nickyName: '',
  sex: null,
  birthday: '2000-01-01',
  phone: '',
  email: '',
  password: '',
  file: null
})
const rules = reactive({
	name: [
		{ required: true, message: '请输入管理员姓名', trigger: 'blur' }
	],
	nickyName: [
		{ required: true, message: '请输入管理员昵称', trigger: 'blur' }
	],
	sex: [
		{ required: true, message: '请选择管理员性别', trigger: 'blur' }
	],
	birthday: [
		{ required: true, message: '请输入管理员生日', trigger: 'blur' }
	],
	phone: [
		{ required: true, message: '请输入手机号码', trigger: 'blur' },
		{ pattern: /^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\d{8}$/, message: '请输入正确的中国手机号', trigger: 'blur' },
		{ validator: check, message: '该手机号已经被使用', trigger: 'blur' }
	],
	email: [
		{ required: true, message: '请输入电子信箱', trigger: 'blur' },
		{ type: 'email', message: '请输入正确的电子信箱', trigger: 'blur' },
		{ validator: check, message: '该电子信箱已经被使用', trigger: 'blur' }
	],
	password: [
		{ required: true, message: '请输入登录密码', trigger: 'blur' }
	],
	file: [
		{ required: props.id===null, message: '请上传管理员头像', trigger: 'blur' }
	]
})
const formObj = ref()
if (props.id) {
	wyform.id = props.id
	getById()
}
function check (rule, value, callback) {
	get(url.check, { id: props.id, value, field: rule.field }, content => {
		if (content) {
			callback()
		} else {
			callback(new Error())
		}
	})
}
function save() {
	post(props.id ? url.update : url.add, wyform, content => {
		emits('update:show', false)
		emits('getTableData')
	}, formObj)
}
function getById () {
	get(url.getById, { id: props.id }, content => {
		console.dir(content)
		for (const key in wyform) {
			if (Object.prototype.hasOwnProperty.call(content, key)) {
				wyform[key] = content[key]
			}
		}
		fileList.push({url:getPath(content.icon)})
	})
}
function uploadFile (file) {
	wyform.file = file.raw
}
function removeFile () {
	wyform.file = null
}
</script>

<style scoped lang="scss"></style>
