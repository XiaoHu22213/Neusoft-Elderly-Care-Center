<template>
  <div style="margin-right: 30px">
    <el-form ref="formObj" :model="wyform" :rules="rules" label-width="100px">
      <el-form-item label="角色名" prop="name">
        <el-input maxLength="20" v-model="wyform.name" placeholder="请输入角色名称"></el-input>
      </el-form-item>
      <el-form-item label="角色说明" prop="description">
        <el-input maxLength="255" v-model="wyform.description" placeholder="请输入角色说明"></el-input>
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
const emits = defineEmits(['update:show', 'getTableData'])
const props = defineProps(['id'])
const wyform = reactive({
	id: null,
  name: '',
  description: ''
})
const rules = reactive({
	name: [
		{ required: true, message: '请输入角色名称', trigger: 'blur' },
		{ validator: check, message: '该角色名已经被使用', trigger: 'blur' }
	],
	description: [
		{ required: true, message: '请输入角色说明', trigger: 'blur' }
	]
})
const formObj = ref()
if (props.id) {
	wyform.id = props.id
	getById()
}
function check(rule, value, callback) {
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
		for (const key in wyform) {
			if (Object.prototype.hasOwnProperty.call(content, key)) {
				wyform[key] = content[key]
			}
		}
		console.dir(wyform)
	})
}

</script>

<style scoped lang="scss"></style>
