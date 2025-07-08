<template>
	<el-container class="wrapper">
		
		<el-header style="display: flex; align-items: center; justify-content: space-between;">
			<img src="@/images/logo.jpg" height="50px" width="180px">
			<div style="margin-left: 500px;">
				<h1>东软颐养中心</h1>
			</div>
			<div style="flex: 1; justify-content: center;">
				<el-button style="margin-left: 560px;" @click="login">退出登录</el-button>
			</div>
			
		
		</el-header>

		<el-container>
			<el-aside width="200px">
				<ZzaMenu />
			</el-aside>
			<el-container>
				<el-main class="zhong">
					<router-view />
				</el-main>

			</el-container>
		</el-container>
	</el-container>
</template>

<script setup>
	import router from '@/router'
	import ZzaMenu from '@/components/menu';
	const login = () => {
		router.push("/");
	}

</script>

<style scoped lang="scss">
	$zzaborder: 1px solid #cccccc;

	.wrapper {
		height: 100vh;

		.el-header {
			
			background: linear-gradient(-90deg, #e9e3ca, #f2efe1);//和图片色一样，不能改
			border-bottom: $zzaborder;

			h1 {
				line-height: 60px;

				text-align: center;

			}
		}

		.el-aside {
			border-right: $zzaborder;
			background: linear-gradient(-90deg, #e9e3ca, #f2efe1);
		}

		.el-main {
			border-bottom: $zzaborder;

		}

		.el-footer {
			h3 {
				line-height: 60px;
			}
		}
	}
</style>