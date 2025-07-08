<template>
	<div class="wrapper">
		<el-form :model="vform" ref="formObj" :rules="rules" class="form"><!-- 登录表单,提交方式为POST -->
			<h1>欢迎注册东软颐养系统</h1>
			
			<el-form-item label="姓名" prop="name" label-width="110px">
				<el-input class="input_1" v-model="vform.name" 
					placeholder="请输入真实姓名"></el-input>
			</el-form-item>
			
			<el-form-item label="手机号" prop="phone" label-width="110px">
				<el-input class="input_1" v-model="vform.phone" 
					placeholder="请输入手机号"></el-input>
			</el-form-item>

			<el-form-item label="电子信箱" prop="email" label-width="110px">
				<el-input class="input_1" v-model="vform.email" 
					placeholder="请输入电子信箱"></el-input>
			</el-form-item>

			<el-form-item label="密码"  prop="password" label-width="110px">
				<el-input class="input_1" v-model="vform.password" show-password 
					placeholder="请输入密码"></el-input>
			</el-form-item>
			
			
			<el-form-item label="生日" prop="birthday">
				<el-date-picker
					v-model="vform.birthday"
					style="width: 100%"
					type="date"
					value-format="YYYY-MM-DD">
				</el-date-picker>
			</el-form-item>			
			
			<el-form-item style="margin-left: 10px" label="性别" prop="sex">
			<div style="margin-left: 15px;">
				<el-radio-group v-model="vform.sex">
			    <el-radio value="1" >男</el-radio>
			    <el-radio value="0" >女</el-radio>
			  </el-radio-group>
			</div>
			  
			</el-form-item>

			<el-form-item>
				<el-button plain type="primary" class="input_3" @click="save">注册</el-button>
			</el-form-item>
			
		</el-form>
	</div>
</template>

<script setup>
	import { ref, reactive } from 'vue'
	import { get, post } from '@/axios'
	import url from '@/views/user/util.js'
	import router from '@/router'
	import VueCookie from 'vue-cookie'
	const emits = defineEmits(['update:show', 'getTableData'])
	const props = defineProps(['id'])
	
	const vform = reactive({
		id: null,
		name: '',
		phone:'',
		email:'',
		password: '',
		birthday:'',
		sex: null
	})
	const formObj = ref()
	const rules = reactive({
		name: [
			{ required: true, message: '请输入姓名', trigger: 'blur' }
		],
		phone: [
			{ required: true, message: '请输入手机号', trigger: 'blur' },
			{ pattern: /^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\d{8}$/, message: '请输入正确的中国手机号', trigger: 'blur' },
			{ validator: check, message: '该手机号已经被使用', trigger: 'blur' }
		],
		email: [
			{ required: true, message: '请输入电子信箱', trigger: 'blur' },
			{ type: 'email', message: '请输入正确的电子信箱', trigger: 'blur' },
			{ validator: check, message: '该电子信箱已经被使用', trigger: 'blur' }
		],
		password: [
			{ required: true, message: '请输入密码', trigger: 'blur' }
		],
		
		birthday: [
			{ required: true, message: '请输入管理员生日', trigger: 'blur' }
		],
		
		sex: [
			{ required: true, message: '请选择性别', trigger: 'blur' }
		],
	})
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
		console.dir(rule.field)
	}
	function save() {
		post(props.id ? url.update : url.add, vform, content => {
			VueCookie.set('token', content.token ,{expires: '7D'})
			emits('update:show', false)
			emits('getTableData')
			router.push({path: '/index'})
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

<style scoped lang="scss">
	.el-radio {
		margin-top: 9px;
		::v-deep .el-radio__label {
			font-size: 18px;
			color: #ffffff;
			
		}
	}
	
	.wrapper {
		background-image: url("@/images/bg-all.jpg");
		background-size: 100% 100%;
		background-attachment: fixed;
		height: 100vh;
		width: 100%;
	}

	.form {
		position: absolute;
		left: 50%;
		top: 50%;
		margin-left: -260px;
		margin-top: -330px;
		width: 600px;
		height: 635px;
		background: url("@/images/b.png") no-repeat;
		background-size: 100% 100%;
		border-radius: 25px;
		color: #fff;
		text-align: center;
		padding: 65px;

		h1 {
			color: #fff;
			letter-spacing: 0.5rem;
			margin-bottom: 20px;
		}

		::v-deep .el-form-item__label {
			width: 100px;
			margin-top: 9px;
			color: white;
			font-size: 18px;
		}
		

		.input_1 {
			outline: none;
			margin-top: 9px;
			border: 0;
			width: 300px;
			height: 40px;
			padding-left: 15px;
			font-size: 15px;
			color: #999;
		}

		.input_3 {
			outline: none;
			margin-bottom: 20px;
			width: 100%;
			height: 50px;
			border: 0;
			padding: 5px 7px;
			border-radius: 20px;
			
			font-size: 20px;
		}
	}
</style>