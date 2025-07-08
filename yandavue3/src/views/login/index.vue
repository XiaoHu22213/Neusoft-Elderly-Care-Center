<template>
	<div class="background">
		<div class="box" style="margin-top: 44px">
			<!-- <el-form :model="vform" ref="formObj" :rules="rules" class="form"> -->
			<!-- 滑动盒子 -->
			<div class="pre-box" :style="boxStyle">
				<h1>欢迎登录东软颐养系统</h1>
				<p></p>
				<!--
				<div class="img-box">
					<transition name="fade">
					    <img v-if="currentImage === 1" :src="image1" key="image1" />
					    <img v-else :src="image2" key="image2" />
					</transition>
				</div>
				-->
				
				<div class="img-box" :class="{ 'rotate-animation': isRotating }">
				  <transition name="fade">
				    <img v-if="currentImage === 1" :src="image1" key="image1" />
				    <img v-else :src="image2" key="image2" />
				  </transition>
				</div>

			</div>

			<!-- 注册/忘记密码盒子 -->
			<div class="register-form">
				<el-form :model="reginform" hidden ref="regformObj" :rules="rules2" class="regform" label-width="80px"
					style="max-width: 600px; margin-left: -60px; margin-top: -140px;">
					<!-- 标题盒子 -->
					<div class="title-box">
						<h1 id="register" style="margin-left: 70px;">注册</h1>
					</div>
					<!-- 输入框盒子 -->
					<div class="input-box">
						<el-form-item prop="name">
							<el-input id="run" v-model="reginform.name" type="text" placeholder="用户名"></el-input>
						</el-form-item>
						<el-form-item prop="phone">
							<el-input class="input_1" v-model="reginform.phone" placeholder="请输入手机号"></el-input>
						</el-form-item>
						<el-form-item prop="email">
							<el-input class="input_1" v-model="reginform.email" placeholder="请输入电子信箱"></el-input>
						</el-form-item>
						<el-form-item prop="password">
							<el-input id="rpw" v-model="reginform.password" show-password
								placeholder="请输入密码"></el-input>
						</el-form-item>
						<el-form-item prop="birthday">
							<el-date-picker v-model="reginform.birthday" placeholder="请选择生日" type="date"
								style="width: 330px; height: 45px;" value-format="YYYY-MM-DD">
							</el-date-picker>
						</el-form-item>
						<el-form-item style="margin-left: -140px; margin-top: 10px; margin-bottom: 10px;" label="性别"
							prop="sex">
							<div style="margin-left: 0px;">
								<el-radio-group v-model="reginform.sex">
									<el-radio style="margin-left: 10px;" value="1">男</el-radio>
									<el-radio style="margin-left: 25px;" value="0">女</el-radio>
								</el-radio-group>
							</div>
						</el-form-item>

					</div>
					<!-- 按钮盒子 -->
					<div class="btn-box">
						<el-form-item>
							<el-button id="btnregiest" @click="save">注册</el-button>
							<!-- 绑定点击事件 -->
							<el-button plain link @click="moveBox">已有账号?去登录</el-button>
						</el-form-item>
					</div>
				</el-form>
				<!-- 忘记密码 -->
				<el-form :model="passform" hidden ref="passformObj" :rules="rules3" class="passform" label-width="80px"
					style="max-width: 600px; margin-left: -50px; margin-top: -100px;">
					<!-- 标题盒子 -->
					<div class="title-box">
						<h1 id="password" style="margin-left: 70px;">找回密码</h1>
					</div>
					<!-- 输入框盒子 -->
					<div class="input-box">
						<el-form-item label="用户名" prop="ousername">
							<el-input class="input_1" v-model="passform.ousername" placeholder="请输入电子信箱"></el-input>
						</el-form-item>

						<el-form-item label="验证码" prop="ocode" style="width: 320px; margin-left: -90px;">
							<el-input class="input_1" v-model="passform.ocode" placeholder="请输入验证码"></el-input>
						</el-form-item>

						<el-form-item>
							<el-button
								style="position:absolute; top:-63px; right: -170px; width:80px; height: 40px; font-size: 13px;"
								plain type="primary" @click="sendcode">发送验证码</el-button>
						</el-form-item>

						<el-form-item label="修改密码" prop="opassword">
							<el-input class="input_1" v-model="passform.opassword" placeholder="请输入密码"></el-input>
						</el-form-item>

						<el-form-item label="确认密码" prop="opassword2">
							<el-input class="input_1" v-model="passform.opassword2" placeholder="请确认密码"></el-input>
						</el-form-item>
					</div>
					<!-- 按钮盒子 -->
					<div class="btn-box">
						<el-form-item>
							<el-button id="btnconfirm" @click="verifyCode">确认</el-button>
							<!-- 绑定点击事件 -->
							<el-button plain link @click="moveBox" auto-insert-space class="underline-hover">已有账号?去登录</el-button>
						</el-form-item>
					</div>
				</el-form>
			</div>
			<!-- 登录盒子 -->
			<div class="login-form">
				<el-form :model="loginform" ref="logformObj" :rules="rules3" class="logform">
					<!-- 标题盒子 -->
					<div class="title-box">
						<h1 id="logon">登录</h1>
					</div>
					<!-- 输入框盒子 -->
					<div class="input-box">
						<el-form-item prop="username">
							<el-input class="username" v-model="loginform.username"
								placeholder="请输入手机号或电子信箱"></el-input>
						</el-form-item>
						<el-form-item prop="password">
							<el-input class="password" v-model="loginform.password" show-password
								placeholder="请输入密码"></el-input>
						</el-form-item>
					</div>
					<!-- 按钮盒子 -->
					<div class="btn-box">
						<el-form-item>
							<!-- 登录 -->
							<el-button class="btnlogon" plain @click="login">登录</el-button>
							<!-- 注册 -->
							<el-button class="toregiest" plain link @click="moveBox1">没有账号?去注册</el-button>
							<!-- 忘记密码 -->
							<el-button class="topassword" plain link @click="moveBox2">忘记密码?去修改</el-button>
						</el-form-item>
					</div>
				</el-form>
			</div>
			<!-- </el-form> -->

		</div>
	</div>
</template>

<script setup>
	import $ from 'jquery'
	import {
		ref,
		reactive
	} from 'vue'

	import axios from 'axios';
	import {
		get,
		post
	} from '@/axios'
	import url from '@/views/user/util.js'
	import VueCookie from 'vue-cookie'
	import router from '@/router'
	import {userMenuStore} from '@/stores'
	const menuStore = userMenuStore()
	const show1 = ref(true)
	const flag = ref(false);
	const boxStyle = ref({
		transform: 'translateX(0%)',
		backgroundColor: 'rgb(250,250, 250, 0.8)'
	});
	const emits = defineEmits(['update:show', 'getTableData'])
	const props = defineProps(['id'])
	let toreg = true
	let topass = true

	function kong(form) {
		for (const key in form) {
			if (Object.prototype.hasOwnProperty.call(form, key)) {
				form[key] = '';
			}
		}
	}

	const a = ref(0);
	const moveBox = (a) => {
		a.value = 0
		move(a)
	}
	const moveBox1 = (a) => {
		a.value = 1
		move(a)
	}
	const moveBox2 = (a) => {
		a.value = 2
		move(a)
	}
	
	// 添加这两行图片引用
	const image1 = ref(new URL('@/images/东软.png', import.meta.url).href);
	const image2 = ref(new URL('@/images/东软睿道.png', import.meta.url).href);
	const currentImage = ref(1); // 用于跟踪当前显示的图片
	
const isRotating = ref(false);

function move(a) {
  // 开始旋转
  isRotating.value = true;
  
  kong(loginform);
  kong(reginform);
  
  setTimeout(() => {
    if (a.value === 0) {
      currentImage.value = 1;
    } else {
      currentImage.value = 2;
    }
  }, 300);
  
  if (a.value === 0) {
    boxStyle.value.transition = 'transform 1.5s ease, background-color 1.5s ease';
    boxStyle.value.transform = 'translateX(0%)';
    boxStyle.value.backgroundColor = 'rgb(250, 250, 250, 0.7)';
    
    show1.value = true;
    $('.logform').fadeIn(400);
    $('.regform').fadeOut(400);
    $('.passform').fadeOut(400);
    
  } else if (a.value === 1 || a.value === 2) {
    boxStyle.value.transition = 'transform 1.5s ease, background-color 1.5s ease';
    boxStyle.value.transform = 'translateX(100%)';
    boxStyle.value.backgroundColor = 'rgb(250, 250, 250, 0.7)';
    
    if(a.value === 1) {
      $('.logform').fadeOut(400);
      $('.regform').fadeIn(400);
      $('.passform').fadeOut(400);
    } else {
      $('.logform').fadeOut(400);
      $('.regform').fadeOut(400);
      $('.passform').fadeIn(400);
    }
  }
  
  // 旋转结束后停止
  setTimeout(() => {
    isRotating.value = false;
  }, 1500); // 与移动动画时间一致
}
	
	
	const formObj = ref()
	const logformObj = ref()
	const regformObj = ref()
	const passformObj = ref()

	const loginform = reactive({
		username: '',
		password: ''
	})
	const reginform = reactive({
		id: null,
		name: '',
		phone: '',
		email: '',
		password: '',
		birthday: '',
		sex: null

	})
	const passform = reactive({
		ousername: '',
		ocode: '',
		opassword: '',
		opassword2: ''
	})

	function login() {
		post('/user/login', loginform, content => {
			VueCookie.set('token', content.token, {expires: '1D'})
			console.dir(content)
			menuStore.setMenu(content.menu)
			router.push({
				path: '/main'
			})
		}, logformObj)
		// 设置模拟token
		// VueCookie.set('token', 'mock-token-12345', {expires: '1D'})
    
		// 设置模拟菜单数据
		// 更新后的完整路由配置
		// 更新后的菜单数据结构 - 适合Element Plus菜单组件
		// const mockMenu = [
		// 	{
		// 		id: 1,
		// 		name: '系统管理',
		// 		frontUrl: '/sys',
		// 		children: [
		// 			{
		// 				id: 11,
		// 				name: '管理员管理',
		// 				frontUrl: '/admin'
		// 			},
		// 			{
		// 				id: 12,
		// 				name: '角色管理',
		// 				frontUrl: '/role'
		// 			},
		// 			{
		// 				id: 13,
		// 				name: '资源管理',
		// 				frontUrl: '/resource'
		// 			},
		// 			{
		// 				id: 14,
		// 				name: '用户管理',
		// 				frontUrl: '/user'
		// 			}
		// 		]
		// 	},
		// 	{
		// 		id: 2,
		// 		name: '膳食管理',
		// 		frontUrl: '/meal',
		// 		children: [
		// 			{
		// 				id: 21,
		// 				name: '膳食统计',
		// 				frontUrl: '/dietarystatistics'
		// 			},
		// 			{
		// 				id: 22,
		// 				name: '膳食日历',
		// 				frontUrl: '/dietarycalendar'
		// 			},
		// 			{
		// 				id: 23,
		// 				name: '膳食管理',
		// 				frontUrl: '/dietarymanagement'
		// 			}
		// 		]
		// 	},
		// 	{
		// 		id: 3,
		// 		name: '登记管理',
		// 		frontUrl: '/registration',
		// 		children: [
		// 			{
		// 				id: 31,
		// 				name: '入住登记',
		// 				frontUrl: '/checkIn'
		// 			},
		// 			{
		// 				id: 32,
		// 				name: '退房登记',
		// 				frontUrl: '/checkOut'
		// 			},
		// 			{
		// 				id: 33,
		// 				name: '外出登记',
		// 				frontUrl: '/goOut'
		// 			}
		// 		]
		// 	},
		// 	{
		// 		id: 4,
		// 		name: '护理管理',
		// 		frontUrl: '/nurse',
		// 		children: [
		// 			{
		// 				id: 41,
		// 				name: '护理记录',
		// 				frontUrl: '/records'
		// 			},
		// 			{
		// 				id: 42,
		// 				name: '护理等级',
		// 				frontUrl: '/level'
		// 			},
		// 			{
		// 				id: 43,
		// 				name: '等级内容',
		// 				frontUrl: '/levelcontent'
		// 			},
		// 			{
		// 				id: 44,
		// 				name: '护理内容',
		// 				frontUrl: '/content'
		// 			}
		// 		]
		// 	},
		// 	{
		// 		id: 5,
		// 		name: '房间管理',
		// 		frontUrl: '/bedroom',
		// 		children: [
		// 			{
		// 				id: 51,
		// 				name: '床位管理',
		// 				frontUrl: '/bed'
		// 			},
		// 			{
		// 				id: 52,
		// 				name: '出入管理',
		// 				frontUrl: '/outin'
		// 			}
		// 		]
		// 	},
		// 	{
		// 		id: 6,
		// 		name: '健康管理',
		// 		frontUrl: '/health',
		// 		children: [
		// 			{
		// 				id: 61,
		// 				name: '服务管理',
		// 				frontUrl: '/service'
		// 			},
		// 			{
		// 				id: 62,
		// 				name: '关注管理',
		// 				frontUrl: '/focus'
		// 			},
		// 			{
		// 				id: 63,
		// 				name: '生活提醒',
		// 				frontUrl: '/lifereminder'
		// 			},
		// 			{
		// 				id: 64,
		// 				name: '反馈管理',
		// 				frontUrl: '/feedback'
		// 			}
		// 		]
		// 	},
		// 	{
		// 		id: 7,
		// 		name: '生活管理',
		// 		frontUrl: '/lifemanagement',
		// 		children: [
		// 			{
		// 				id: 71,
		// 				name: '班车路线',
		// 				frontUrl: '/busroute'
		// 			},
		// 			{
		// 				id: 72,
		// 				name: '天气信息',
		// 				frontUrl: '/weather'
		// 			}
		// 		]
		// 	}
		// ]
			
				
		// menuStore.setMenu(mockMenu)
		
		// // 跳转到主页面
		// router.push({
		// 	path: '/main'
		// })
	}

	const rules = reactive({
		username: [{
			required: true,
			message: '请输入手机号/邮箱号',
			trigger: 'blur'
		}],
		password: [{
			required: true,
			message: '请输入密码',
			trigger: 'blur'
		}]
	})
	const rules2 = reactive({
		name: [{
			required: true,
			message: '请输入姓名',
			trigger: 'blur'
		}],
		phone: [{
				required: true,
				message: '请输入手机号',
				trigger: 'blur'
			},
			{
				pattern: /^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\d{8}$/,
				message: '请输入正确的中国手机号',
				trigger: 'blur'
			},
			{
				validator: check,
				message: '该手机号已经被使用',
				trigger: 'blur'
			}
		],
		email: [{
				required: true,
				message: '请输入电子信箱',
				trigger: 'blur'
			},
			{
				type: 'email',
				message: '请输入正确的电子信箱',
				trigger: 'blur'
			},
			{
				validator: check,
				message: '该电子信箱已经被使用',
				trigger: 'blur'
			}
		],
		password: [{
			required: true,
			message: '请输入密码',
			trigger: 'blur'
		}],

		birthday: [{
			required: true,
			message: '请输入管理员生日',
			trigger: 'blur'
		}],

		sex: [{
			required: true,
			message: '请选择性别',
			trigger: 'blur'
		}],
	})
	const rules3 = reactive({
		ousername: [{
				required: true,
				message: '请输入邮箱号',
				trigger: 'blur'
			},
			{
				validator: check1,
				message: '该邮箱不存在',
				trigger: 'blur'
			}
		],
		ocode: [{
			required: true,
			message: '请输入验证码',
			trigger: 'blur'
		}],
		opassword: [{
			required: true,
			message: '请输入密码',
			trigger: 'blur'
		}],
		opassword2: [{
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

	// 如果 props.id 有值，则获取数据
	if (props.id) {
		getById(reginform)
		getById(passform)
	}

	function check(rule, value, callback) {
		get(url.check, {
			id: props.id,
			value,
			field: rule.field
		}, content => {
			if (content) {
				callback()
			} else {
				callback(new Error())
			}
		})
		console.dir(rule.field)
	}

	function check1(rule, value, callback) {
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

	function save() {
		post(props.id ? url.update : url.add, reginform, content => {
			emits('update:show', false)
			emits('getTableData')
			
		}, regformObj)
	}


	function getById(form) {
		form.id = props.id
		get(url.getById, {
			id: props.id
		}, content => {
			console.dir(content)
			for (const key in form) {
				if (Object.prototype.hasOwnProperty.call(content, key)) {
					form[key] = content[key]
				}
			}
		})
	}


	function sendcode() {
		// 调用后端发送验证码的接口，传递用户名
		get('/api/sendcode', {
			username: passform.ousername
		}, content => {
			console.log("验证码已发送");
		})
	}

	function validatePassword(rule, value, callback) {
		if (value === passform.opassword) {
			callback(); // 密码匹配，校验通过
		} else {
			callback(new Error('确认密码与修改密码不匹配')); // 密码不匹配，返回错误信息
		}
	}

	function verifyCode(rule) {
		post('/api/verifycode', {
			username: passform.ousername,
			code: passform.ocode
		}, response => {
			if (passform.opassword === passform.opassword2) {
				change(rule)
			} else {
				alert("修改密码失败");
			}
		})

	}

	function change(rule) {
		post('/user/change', {
			email: passform.ousername,
			password: passform.opassword
		}, response => {
			VueCookie.set('token', response, {
				expires: '7D'
			})
			alert("修改密码成功");
			
		})

	}
	axios.get('http://127.0.0.1/api/endpoint')
	  .then(response => {
	    console.log('Data:', response.data);
	  })
	  .catch(error => {
	    console.error('Error:', error.response.status, error.response.statusText);
	  });
</script>

<style scoped lang="scss">
	* {
		/* 去除浏览器默认内外边距 */
		margin: 0;
		padding: 0;
		box-sizing: border-box;
	}

	/* 去除input的轮廓 */
	input {
		outline: none;
	}

	
	// html,
	// body {
	//     height: 100%;
	// }

	// body {
	//     /* 溢出隐藏 */
	//     overflow-x: hidden;
	//     display: flex;
	//     /* 渐变方向从左到右 */
	//     background: linear-gradient(to right, rgb(247, 209, 215), rgb(191, 227, 241));
	// }

	html,
	body,
	#app {
		height: 100%;
		margin: 0;
		padding: 0;
		overflow: hidden;
		/* Prevent scrollbars if content overflows */
	}

	.background {

		background-size: cover;
		/* 使背景图片铺满容器，保持比例 */
		background-position: center;
		/* 使背景图片在容器中居中 */
		position: fixed;
		/* Use fixed positioning to cover the whole screen */
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		/* 渐变方向从左到右 */
		// background-image: url("@/images/养老院.jpg");
		background-image: url("@/images/背景.png");
		//background: linear-gradient(to right, rgb(247, 209, 215), rgb(191, 227, 241));
		// background: linear-gradient(to right, rgb(240, 207, 227), rgb(135, 192, 202));
	}


	/* 最外层的大盒子 */
	.box {
		width: 1152px;
		height: 660px;
		margin-top: 0px;
		display: flex;
		/* 相对定位 */
		position: relative;
		z-index: 2;
		margin: auto;
		/* 设置圆角 */
		border-radius: 8px;
		/* 设置边框 */
		border: 1px solid rgba(255, 255, 255, .6);
		/* 设置盒子阴影 */
		box-shadow: 2px 1px 19px rgba(0, 0, 0, .1);
		border: none;
	}

	/* 滑动的盒子 */
	.pre-box {
		/* 宽度为大盒子的一半 */
		width: 50%;
		/* width: var(--width); */
		height: 100%;
		/* 绝对定位 */
		position: absolute;
		/* 距离大盒子左侧为0 */
		left: 0;
		/* 距离大盒子顶部为0 */
		top: 0;
		z-index: 99;
		border-radius: 4px;
		background-color: #edd4dc;
		box-shadow: 2px 1px 19px rgba(0, 0, 0, .1);
		/* 动画过渡，先加速再减速 */
		transition: 0.5s ease-in-out;
	}

	// .pre-box {
	//   transition: transform 0.3s ease; /* 添加一个平滑的过渡效果 */
	// }

	/* 滑动盒子的标题 */
	.pre-box h1 {
		margin-top: 150px;
		text-align: center;
		/* 文字间距 */
		letter-spacing: 5px;
		color: #005bab;
		/* 禁止选中 */
		user-select: none;
		/* 文字阴影 */
		text-shadow: 4px 4px 3px rgba(0, 0, 0, .1);
	}

	/* 滑动盒子的文字 */
	.pre-box p {
		height: 30px;
		line-height: 30px;
		text-align: center;
		margin: 20px 0;
		/* 禁止选中 */
		user-select: none;
		font-weight: bold;
		color: white;
		text-shadow: 4px 4px 3px rgba(0, 0, 0, .1);
	}

	
	
	/* 图片淡入淡出效果 */
	.fade-enter-active, .fade-leave-active {
	  transition: opacity 0.8s ease;
	  position: absolute;
	}
	.fade-enter-from, .fade-leave-to {
	  opacity: 0;
	}
	.fade-enter-to, .fade-leave-from {
	  opacity: 1;
	}
	
	/* 旋转动画 */
	.rotate-animation {
	  animation: rotate 1.5s ease-in-out;
	}
	
	@keyframes rotate {
	  0% {
	    transform: rotate(0deg);
	  }
	  
	  100% {
	    transform: rotate(360deg);
	  }
	}
	
	/* 调整图片盒子样式，确保旋转中心正确 */
	.img-box {
	  width: 200px;
	  height: 200px;
	  margin: 20px auto;
	  border-radius: 50%;
	  overflow: hidden;
	  box-shadow: 4px 4px 3px rgba(0, 0, 0, .1);
	  position: relative;
	  display: flex;
	  justify-content: center;
	  align-items: center;
	  transform-origin: center center; /* 确保旋转中心在正中央 */
	}
	
	.img-box img {
	  width: 100%;
	  height: 100%;
	  object-fit: cover;
	}

	/* 登录和注册盒子 */
	.login-form,
	.register-form {

		flex: 1;
		height: 100%;
		background-color: rgba(200, 200, 200, 0.8);
	}

	/* 标题盒子 */
	.title-box {
		// background-image: url("@/images/背景3.png");
		margin-top: -40px;
		height: 300px;
		line-height: 500px;
		background-color: rgba(255, 255, 255, 0);
	}

	/* 标题 */
	.title-box h1 {
		text-align: center;
		color: black;
		/* 禁止选中 */
		user-select: none;
		letter-spacing: 5px;
		text-shadow: 4px 4px 3px rgba(0, 0, 0, .1);
	}

	/* 输入框盒子 */
	.input-box {
		display: flex;
		/* 纵向布局 */
		flex-direction: column;
		/* 水平居中 */
		align-items: center;
	}

	/* 输入框 */
	.el-input {
		width: 330px;
		height: 45px;
		margin-bottom: 20px;
		text-indent: 10px;
		border: 1px solid #fff;
		background-color: rgba(255, 255, 255, 0.1);
		border-radius: 120px;
		/* 增加磨砂质感 */
		backdrop-filter: blur(10px);
	}

	.username .el-input__inner {
		border-radius: 10px;
		/* 设置圆角半径 */
	}

	input:focus {
		/* 光标颜色 */
		color: #000000;

	}

	/* 聚焦时隐藏文字 */
	input:focus::placeholder {
		opacity: 0;
	}

	/* 按钮盒子 */
	.btn-box {
		display: flex;
		justify-content: center;
	}

	/* 按钮 */
	button {
		width: 100px;
		height: 30px;
		margin: 0 7px;
		line-height: 30px;
		border: none;
		border-radius: 4px;
		background-color: #69b3f0;
		color: white;
	}

	/* 按钮悬停时 */
	button:hover {
		/* 鼠标小手 */
		cursor: pointer;
		/* 透明度 */
		opacity: .8;
	}

	/* 按钮文字 */
	.btn-box p {
		height: 30px;
		line-height: 30px;
		/* 禁止选中 */
		user-select: none;
		font-size: 14px;
		color: white;

	}

	.btn-box p:hover {
		cursor: pointer;
		border-bottom: 1px solid white;
	}

	audio {
		height: 0;
		width: 0;
	}

	.topassword {
		position: absolute;
		top: 30px;
		left: 114px;
	}

	.el-button.is-link {//已有账号去登录 没有账号去注册等  字体颜色
		background: transparent;
		border-color: transparent;
		color: black;
		height: auto;
		padding: 2px;
	}

	::v-deep .el-radio__label {//男女  那俩按钮字体
		font-size: 16px;
		color: black;

	}

	::v-deep .el-form-item__label {//用户名 密码等提示字
		width: 100px;
		color: black;
		font-size: 14px;
	}
	
	:deep(.underline-hover){
		text-decoration: underline;
	}
</style>