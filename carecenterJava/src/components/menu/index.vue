<template>
	<el-menu router>
		<el-menu-item class="a" index="/main" style="height: 50px;">
			<template #title class="a" index="/main">主页</template>
		</el-menu-item>
		<el-menu-item class="a" index="/consult" style="height: 50px;">
			<template #title class="a" index="/consult">颐养咨询</template>
		</el-menu-item>
		<el-sub-menu class="a" v-for="item in menuStore.menu" :key="item.id" :index="item.frontUrl">
			<template #title>{{item.name}}</template>
			<el-menu-item class="a" v-for="child in item.children" :key="child.id"
				:index="child.frontUrl"  style="height: 45px;">{{child.name}}</el-menu-item>
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
		background: linear-gradient(-90deg, #e9e3ca, #f2efe1);
	}

	.el-menu-item.a {
		height: 40px;
		/* 设置高度 */

	}
</style>