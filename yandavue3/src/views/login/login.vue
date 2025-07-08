<template>
	<div class="wrapper">
		<el-form :model="vform" ref="formObj" :rules="rules" class="form"><!-- 登录表单,提交方式为POST -->
			<h1>欢迎登录东软颐养系统</h1>
			<el-form-item label="用户名" prop="username">
				<el-input class="input_1" v-model="vform.username" placeholder="请输入手机号"></el-input>
			</el-form-item>

			<el-form-item label="密码" prop="password">
				<el-input class="input_1" v-model="vform.password" show-password placeholder="请输入密码"></el-input>
			</el-form-item>

			<el-form-item>
				<el-button plain type="primary" class="input_3" @click="login">登录</el-button>
			</el-form-item>

			<el-form-item>
				<el-button style="position:absolute; left:120px; color:aliceblue; font-size: 17px;" plain
					type="text" @click="register">注册</el-button>
			</el-form-item>

			<el-form-item>
				<el-button style="position:absolute; right:80px; top:-35px; color:aliceblue; font-size: 17px;" plain
					type="text" @click="findpwd">找回密码</el-button>
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
		post
	} from '@/axios'
	import VueCookie from 'vue-cookie'
	import router from '@/router'
	import {userMenuStore} from '@/stores'
	const menuStore = userMenuStore()
	const vform = reactive({
		username: '',
		password: ''
	})
	const formObj = ref()
	const rules = reactive({
		username: [{
			required: true,message: '请输入手机号/邮箱号',trigger: 'blur'
		}],
		password: [{
			required: true,message: '请输入密码',trigger: 'blur'
		}]
	})
	const register = () => {
		router.push("/register");
	}
	const findpwd = () => {
		router.push("/password");
	}
	function login() {
		post('/user/login', vform, content => {
			VueCookie.set('token', content.token, {expires: '1D'})
			console.dir(content)
			menuStore.setMenu(content.menu)
			router.push({
				path: '/main'
			})
		}, formObj)
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
			margin-top: -220px;
			width: 600px;
			height: 450px;
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
				margin-top: 25px;
				color: white;
				font-size: 20px;
			}

			.input_1 {
				outline: none;
				margin-top: 20px;
				border: 0;
				width: 300px;
				height: 40px;
				padding-left: 15px;
				font-size: 18px;
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