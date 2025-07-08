<template>
	<div>
		<div style="margin-bottom: 10px;">
			<el-input v-model="params.level" style="max-width: 300px;" placeholder="请输入要搜索的等级"
				class="input-with-select">
				<template #append>
					<el-button :icon="Search" @click="search" />
				</template>
			</el-input>
			<el-button type="primary" style="margin-left: 30px;" plain @click="add">添加</el-button>
		</div>
		<el-table :data="tableData.records">
			<el-table-column label="编号" prop="id"></el-table-column>
			<el-table-column label="护理等级" prop="level"></el-table-column>

			<el-table-column label="护理状态">
				<template #default="scope">
					<span v-if="scope.row.status">启用</span>
					<span v-else>禁用</span>
				</template>
			</el-table-column>
			<el-table-column label="备注" prop="memo"></el-table-column>
			<el-table-column label="操作">

				<template #default="scope">
					<template v-if="scope.row.status">
						<el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
						<el-button type="danger" plain size="small" @click="del(scope.row.id, 0)">禁用</el-button>
						<el-button type="success" plain size="small" @click="setup(scope.row.id)">设置护理内容</el-button>
					</template>
					<el-button v-else type="warning" plain size="small" @click="del(scope.row.id, 1)">启用</el-button>

				</template>



			</el-table-column>
			<!-- <el-table-column label="备注" v-model:memo="memo" prop="memo">
				
				<div style="margin-bottom: 10px;" @click="setup()">
					<el-button type="success" plain>设置</el-button>
				</div>
				
			</el-table-column> -->
		</el-table>
		<el-pagination style="margin-top: 10px;" background :page-count="tableData.pages"
			v-model:current-page="params.pageNo" @current-change="getTableData"
			:total="tableData.total"></el-pagination>
		<el-dialog v-model="showDialog" title="备注" :close-on-click-modal="false" width="450px">
			<CustomSetup v-if="showDialog" v-model:show="showDialog" @getTableData="getTableData" />
		</el-dialog>
		<el-dialog v-model="dialog.show" :title="dialog.title" :close-on-click-modal="false" width="450px">
			<Add v-if="dialog.show" v-model:show="dialog.show" @getTableData="getTableData" :id='dialog.id' />
		</el-dialog>
	</div>
</template>

<script setup>
	import {
		get,
		post
	} from '@/axios/axios'
	import {
		CirclePlus,
		Search
	} from '@element-plus/icons-vue'
	import {
		ElMessage
	} from 'element-plus'
	import {
		ref,
		reactive
	} from 'vue'
	import CustomSetup from './setup'
	import Add from './add'
	import router from '@/router'
	let tableData = ref({})
	let showDialog = ref(false)

	const params = reactive({
		pageNo: 1,
		pageSize: 10,
		level: ''
	})
	const dialog = reactive({
		show: false,
		title: '',
		id: null
	})
	getTableData()

	function getTableData() {

		get('/nurselevel/list', params, content => {
			//console.dir(content)
			tableData.value = content
		})
	}

	function setup(id) {
		//showDialog.value = true
		router.push({
			path: '/levelcontent',
			query: {
				id: id,
			}
		})
	}

	function search() {
		getTableData()
	}

	function add() {
		dialog.title = '添加护理等级'
		dialog.id = null
		dialog.show = true
	}

	function update(id) {
		dialog.title = '修改护理等级'
		dialog.id = id
		dialog.show = true
	}

	function del(id, status) {
		const text = status ? '确定要启用该角色吗?' : '确定要禁用该角色吗'
		ElMessageBox.confirm(text, '警告', {
			type: 'warning'
		}).then(() => {
			// console.dir(id)
			// console.dir("-----------------------")
			// console.dir(status)
			post('/nurselevel/del', {
				id,
				status
			}, content => {
				getTableData()
			})
		}).catch(() => {})
	}
</script>

<style scoped lang="scss">
</style>