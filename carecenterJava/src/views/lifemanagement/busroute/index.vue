<template>
	<div>
		<div style="margin-bottom: 10px;" @click="add">
			<el-button type="primary" plain>添加</el-button>
		</div>
		<el-table :data="tableData.records">
			<el-table-column label="编号" prop="id"></el-table-column>
			<el-table-column label="班车号" prop="name"></el-table-column>
			<el-table-column label="班车时间" prop="bustime"></el-table-column>
			<el-table-column label="班车路线" prop="route"></el-table-column>
			<el-table-column label="操作">
				<template #default="scope">
					<el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
					<el-button type="danger" plain size="small" @click="del(scope.row.id)">删除</el-button>
					<!-- <el-button type="success" plain @click="setup(scope.row.id)">设置</el-button> -->
				</template>
			</el-table-column>
		</el-table>
		<el-pagination style="margin-top: 10px;" background :page-count="tableData.pages"
			v-model:current-page="params.pageNo" @current-change="getTableData"
			:total="tableData.total"></el-pagination>
		<el-dialog v-model="showDialog" title="详细备注" :close-on-click-modal="false" width="450px">
			<CustomSetup v-if="showDialog" v-model:show="showDialog" v-model:memo='remark.memo' v-model:id='remark.id'
				@getTableData="getTableData" />
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
		ElMessage
	} from 'element-plus'
	import {
		ref,
		reactive
	} from 'vue'
	import CustomSetup from './setup'
	import Add from './add'
	let tableData = ref({})
	let showDialog = ref(false)
	let showAdd = ref(false)
	const params = reactive({
		pageNo: 1,
		pageSize: 10,
		name: ''
	})
	const dialog = reactive({
		show: false,
		title: '',
		id: null
	})
	const remark = reactive({
		memo: '',
		id: ''
	});
	getTableData()

	function getTableData() {

		get('/busroute/list', params, content => {
			tableData.value = content
		})
	}

	function setup(memo, id) {
		remark.memo = memo
		remark.id = id

		showDialog.value = true
	}

	function add() {
		dialog.title = '添加班车'
		dialog.id = null
		dialog.show = true
	}

	function search() {
		getTableData()
	}

	function update(id) {
		dialog.title = '修改班车'
		dialog.id = id
		dialog.show = true
	}

	function del(id) {
		const text =  '确定要删除该列班车吗'
		ElMessageBox.confirm(text, '警告', {
			type: 'warning'
		}).then(() => {

			post('/busroute/del', {
				id
				
			}, content => {
				getTableData()
			})
		}).catch(() => {})
	}
</script>

<style scoped lang="scss">
</style>