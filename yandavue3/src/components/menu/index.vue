<template>
	<el-menu router mode="horizontal" class="horizontal-menu">
		<el-menu-item class="a" index="/main" >
			<template #title class="a" index="/main">主页</template>
		</el-menu-item>
		<!--
		<el-menu-item class="a" index="/consult" >
			<template #title class="a" index="/consult">颐养咨询</template>
		</el-menu-item>
		-->
		<el-sub-menu class="a" v-for="item in menuStore.menu" :key="item.id" :index="item.frontUrl">
			<template #title>{{item.name}}</template>
			<el-menu-item class="a" v-for="child in item.children" :key="child.id"
				:index="child.frontUrl"  >{{child.name}}</el-menu-item>
		</el-sub-menu>

	</el-menu>
</template>

<script setup>
	import {
		computed,
		onMounted,
		onBeforeMount
	} from 'vue'
	import {
		userMenuStore
	} from '@/stores'
	const menuStore = userMenuStore()
</script>

<style scoped lang="scss">
	.a {
		background: linear-gradient(0deg, #ffffff);
	}

	.el-menu-item.a {
		height: 65px;
		width: 100%;
		/* 设置高度 */

	}
</style>