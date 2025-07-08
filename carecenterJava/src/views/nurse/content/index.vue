<template>
	<div>
		<div style="margin-bottom: 10px;" @click="add">
			<el-button type="primary" plain>添加</el-button>
		</div>
		<el-table :data="tableData.records">
			<el-table-column label="编号" prop="id"></el-table-column>
			<el-table-column label="护理内容" prop="nursecontent"></el-table-column>
			<el-table-column label="描述" prop="cdescribe"></el-table-column>
			<el-table-column label="价格" prop="price"></el-table-column>
			<el-table-column label="备注" prop="memo">
				<template #default="scope">
					<div style="margin-bottom: 10px;" @click="setup(scope.row.memo,scope.row.id)">
						<el-button type="success" size="small" plain>查看</el-button>
					</div>
				</template>
			</el-table-column>
			<el-table-column label="状态">
				<template #default="scope">
					<span v-if="scope.row.status === 1">启用</span>
					<span v-else>禁用</span>
				</template>
			</el-table-column>
			<el-table-column label="操作">

				<template #default="scope">
					<template v-if="scope.row.status">
						<el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
						<el-button type="danger" plain size="small" @click="del(scope.row.id, 0)">禁用</el-button>
						<!-- <el-button type="success" plain @click="setup(scope.row.id)">设置</el-button> -->
					</template>
					<el-button v-else type="warning" plain size="small" @click="del(scope.row.id, 1)">启用</el-button>

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

		get('/nursecontent/list', params, content => {
			tableData.value = content
		})
	}

	function setup(memo, id) {
		remark.memo = memo
		remark.id = id

		showDialog.value = true
	}

	function add() {
		dialog.title = '添加护理内容'
		dialog.id = null
		dialog.show = true
	}

	function search() {
		getTableData()
	}

	function update(id) {
		dialog.title = '修改护理内容'
		dialog.id = id
		dialog.show = true
	}

	function del(id, status) {
		const text = status ? '确定要启用该角色吗?' : '确定要禁用该角色吗'
		ElMessageBox.confirm(text, '警告', {
			type: 'warning'
		}).then(() => {

			post('/nursecontent/del', {
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