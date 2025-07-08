<template>
	<div style="margin-right: 30px">
	  <el-form ref="formObj" :model="vform" :rules="rules" label-width="100px">
	    
		<el-form-item label="姓名" prop="name" label-width="110px">
			<el-input maxLength="20"  v-model="vform.name" placeholder="请输入真实姓名"></el-input>
		</el-form-item>
		
	    
	    <el-form-item label="性别" prop="sex">
	      <el-radio-group v-model="vform.sex">
	        <el-radio :value="1">男</el-radio>
	        <el-radio :value="0">女</el-radio>
	      </el-radio-group>
	    </el-form-item>
		
	    <el-form-item label="生日" prop="birthday">
	      <el-date-picker
	        v-model="vform.birthday"
	        style="width: 100%"
	        type="date"
	        value-format="YYYY-MM-DD">
		  </el-date-picker>
	    </el-form-item>
		
		<!-- <el-form-item label="员工类别" prop="type">
				  <el-select v-model="vform.type" placeholder="请选择员工类别">
				    <el-option
				      v-for="item in options"
				      :key="item.value"
				      :label="item.label"
				      :value="item.value">
				    </el-option>
				  </el-select>
				</el-form-item> -->
		
		<el-form-item label="入职时间" prop="worktime">
		  <el-date-picker
		    v-model="vform.worktime"
		    style="width: 100%"
		    type="date"
		    value-format="YYYY-MM-DD">
		  </el-date-picker>
		</el-form-item>
		

		
		<el-form-item label="简介" prop="introduction">
		  <el-input maxLength="255" v-model="vform.introduction" placeholder="请输入简介"></el-input>
		</el-form-item>
		
	    <el-form-item label="手机号" prop="phone">
	      <el-input maxLength="15" v-model="vform.phone" placeholder="请输入手机号"></el-input>
	    </el-form-item>
		
	    <el-form-item label="电子信箱" prop="email">
	      <el-input maxLength="64" v-model="vform.email" placeholder="请输入电子信箱"></el-input>
	    </el-form-item>
		
		<el-form-item v-if="!props.id" label="登录密码" prop="password">
		  <el-input v-model="vform.password" placeholder="请输入登录密码" show-password></el-input>
		</el-form-item>
		
	    <el-form-item>
	      <el-button type="primary" plain @click="save">保存</el-button>
	    </el-form-item>
	  </el-form>
	</div>
</template>

<script setup>
	
	import { ref, reactive } from 'vue'
	import { get, post } from '@/axios'
	import url from './util'
	import {getPath} from '@/util'
	const emits = defineEmits(['update:show', 'getTableData'])
	const props = defineProps(['id'])
	const fileList=reactive([ ])
	const vform = reactive({
		id: null,
	  name: '',
	  sex: null,
	  birthday: '2000-01-01',
	  type:'',
	  worktime:'2000-01-01',
	  introduction:'',
	  phone: '',
	  email: '',
	  password: '',
	})
	const options = ref([
		  { label: '董事长', value: '董事长' },
		  { label: '护工', value: '护工' },
		  { label: '厨师', value: '厨师' },
		  { label: '前台', value: '前台' },
		  { label: '医生', value: '医生' }
		])
	const rules = reactive({
		name: [
			{ required: true, message: '请输入员工姓名', trigger: 'blur' }
		],
		
		sex: [
			{ required: true, message: '请选择员工性别', trigger: 'blur' }
		],
		birthday: [
			{ required: true, message: '请输入员工生日', trigger: 'blur' }
		],
		type: [
			{ required: true, message: '请输入员工类别', trigger: 'blur' }
		],
		worktime: [
			{ required: true, message: '请输入入职时间', trigger: 'blur' }
		],
		introduction: [
			{ required: true, message: '请输入员工简介', trigger: 'blur' }
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
		]
	})
	const formObj = ref()
	if (props.id) {
		vform.id = props.id
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
		post(props.id ? url.update : url.add, vform, content => {
			emits('update:show', false)
			emits('getTableData')
		}, formObj)
	}
	function getById () {
		get(url.getById, { id: props.id }, content => {
			console.dir(content)
			for (const key in vform) {
				if (Object.prototype.hasOwnProperty.call(content, key)) {
					vform[key] = content[key]
				}
			}
		})
	}
</script>

<style>
</style>