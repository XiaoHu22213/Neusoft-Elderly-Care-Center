<template>
	<div class="wrapper">
		<el-form :model="vform" ref="formObj" :rules="rules" class="form"><!-- 登录表单,提交方式为POST -->
			<h1>找回密码</h1>
			<el-form-item label="用户名" prop="username">
				<el-input class="input_1" v-model="vform.username" placeholder="请输入电子信箱"></el-input>
			</el-form-item>

			<el-form-item label="验证码" prop="code" style="width: 320px;">
				<el-input class="input_1" v-model="vform.code" placeholder="请输入验证码"></el-input>
			</el-form-item>

			<el-form-item>
				<el-button style="position:absolute; top:-54px; right:71px; width:70px; font-size: 13px;" plain
					type="primary" @click="sendcode">发送验证码</el-button>
			</el-form-item>

			<el-form-item label="修改密码" prop="password">
				<el-input class="input_1" v-model="vform.password" placeholder="请输入密码"></el-input>
			</el-form-item>

			<el-form-item label="确认密码" prop="password2">
				<el-input class="input_1" v-model="vform.password2" placeholder="请确认密码"></el-input>
			</el-form-item>

			<el-form-item>
				<el-button plain type="primary" class="input_3" @click="verifyCode">确认</el-button>
			</el-form-item>

			<el-form-item>
				<el-button style="position:absolute; right:80px; color:aliceblue; font-size: 17px;" plain link
					@click="login">回到登录</el-button>
			</el-form-item>
		</el-form>
	</div>

</template>

<script setup>
	import {
		ref,
		reactive
	} from 'vue'
	import {
		get,
		post
	} from '@/axios'
	import axios from 'axios';
	import url from '@/views/user/util.js'
	import router from '@/router'
	import VueCookie from 'vue-cookie'
	const props = defineProps(['id'])
	const emits = defineEmits(['getTableData'])
	const vform = reactive({
		username: '',
		code: '',
		password: '',
		password2: ''
	})
	const formObj = ref()
	const rules = reactive({
		username: [
			{required: true,message: '请输入邮箱号',trigger: 'blur'},
			{validator: check,message: '该邮箱不存在',trigger: 'blur'},
		],
		code: [
			{required: true,message: '请输入验证码',trigger: 'blur'},
		],
		password: [
			{required: true,message: '请输入密码',trigger: 'blur'},
		],
		password2: [{
				required: true,
				message: '请确认密码',
				trigger: 'blur'
			},
			{
				validator: validatePassword,
				trigger: 'blur'
			}
		]
	})


	if (props.id) {
		vform.id = props.id
		getById()
	}

	function check(rule, value, callback) {
		get(url.check, {
			id: props.id,
			value,
			field: "email"
		}, content => {
			if (content) {
				callback(new Error())
			} else {
				callback()
			}
		})
	}

	function getById() {
		get(url.getById, {
			id: props.id
		}, content => {
			console.dir(content)
			for (const key in vform) {
				if (Object.prototype.hasOwnProperty.call(content, key)) {
					vform[key] = content[key]
				}
			}
		})
	}

	function sendcode() {
		// 调用后端发送验证码的接口，传递用户名
		get('/api/sendcode', {
			username: vform.username
		}, content => {
			console.log("验证码已发送");
		})
	}

	function validatePassword(rule, value, callback) {
		if (value === vform.password) {
			callback(); // 密码匹配，校验通过
		} else {
			callback(new Error('确认密码与修改密码不匹配')); // 密码不匹配，返回错误信息
		}
	}

	function verifyCode(rule) {
		post('/api/verifycode', {username: vform.username,code: vform.code}, response => {
			if (vform.password === vform.password2) {
				VueCookie.set('token', response.token ,{expires: '7D'})
				change(rule)
			}else{
				alert("修改密码失败");
			}
		})
		
	}

	function change(rule) {
		post('/user/change', {
			email: vform.username,
			password: vform.password
		}, response => {
			alert("修改密码成功");
			router.push({path: '/'})
		})
		
	}


	const login = () => {
		router.push("/");
	}
</script>

<style scoped lang="scss">
	.wrapper {
		background-image: url("@/images/bg-all.jpg");
		background-size: 100% 100%;
		background-attachment: fixed;
		height: 100vh;
		width: 100%;
		display: flex;


		.form {
			position: absolute;
			left: 50%;
			top: 50%;
			margin-left: -260px;
			margin-top: -300px;
			width: 600px;
			height: 600px;
			background: url("@/images/b.png") no-repeat;
			background-size: 100% 100%;
			border-radius: 25px;
			color: #fff;
			text-align: center;
			padding: 65px;

			h1 {
				color: #fff;
				letter-spacing: 0.5rem;
			}

			:deep(.el-form-item__label) {
				width: 100px;
				margin-top: 25px;
				color: white;
				font-size: 18px;
			}

			.input_1 {
				outline: none;
				margin-top: 20px;
				border: 0;
				width: 300px;
				height: 40px;
				padding-left: 15px;
				font-size: 15px;
				color: #999;
			}

			.input_3 {
				outline: none;
				width: 100%;
				height: 50px;
				border: 0;
				padding: 5px 7px;
				border-radius: 20px;
				margin: 10px 80px;
				font-size: 20px;
			}
		}
	}
</style>