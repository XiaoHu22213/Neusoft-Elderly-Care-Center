<template>
	<div style="margin-left: 20px; margin-right: 30px;">
		<el-form ref="formObj" :model="zzaform" :rules="rules" label-width="80px">
			<el-form-item label="姓名" prop="name">
				<el-input maxlength="20" v-model="zzaform.name" placeholder="请输入人员姓名"></el-input>
			</el-form-item>
			<el-form-item label="性别" prop="sex">
				<el-radio-group v-model="zzaform.sex">
					<el-radio :value="1">男</el-radio>
					<el-radio :value="0">女</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="年龄" prop="age">
				<el-input v-model="zzaform.age" placeholder="请输入年龄"></el-input>
			</el-form-item>
			<el-form-item label="平时喜好" prop="hobby">
				<el-input v-model="zzaform.hobby" placeholder="请输入平时喜好"></el-input>
			</el-form-item>
			<el-form-item label="注意事项" prop="note">
				<el-input v-model="zzaform.note" placeholder="请输入注意事项"></el-input>
			</el-form-item>
			<el-form-item label="备注" prop="notes">
				<el-input v-model="zzaform.notes" placeholder="请输入备注"></el-input>
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
import { getPath } from '@/util'
import { PictureFilled } from '@element-plus/icons-vue'
const emits = defineEmits(['update:show', 'getTableData'])
const props = defineProps(['id'])
const fileList = reactive([])
const zzaform = reactive({
		name: '',
		sex: null,
		age: '',
		hobby: '',
		note: '',
		notes: '',
		icon: ''
	})
const formObj = ref()
if (props.id) {
	zzaform.id = props.id
	getById()
}
const check = (rule, value, callback) => {
	get(url.check, { id: props.id, value, field: rule.field }, content =>{
		if(content){
			callback()
		}
		else{
			callback(new Error())
		}
	})
}
const rules = reactive({
		name: [
			{
				required: true,
				message: '请输入用户姓名',
				trigger: 'blur',
			},
			{
				validator: check,
				message: '已经有该姓名了',
				trigger: 'blur'
			}]
	})
function getById () {
	get(url.getById, { id: props.id }, content => {
		console.dir(content)
		for (const key in zzaform) {
			if (Object.prototype.hasOwnProperty.call(content, key)) {
				zzaform[key] = content[key]
			}
		}
		fileList.push({ url: getPath(content.icon) })
	})
}
function save() {
	post(props.id ? url.update : url.add, zzaform, content => {
		console.dir(content)
	},formObj)
}
</script>

<style scoped lang="scss">
</style>