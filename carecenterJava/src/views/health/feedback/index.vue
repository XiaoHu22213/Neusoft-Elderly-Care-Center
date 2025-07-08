<template>
	<div>
		<div style="margin-bottom: 10px;" >
			<el-input
			  v-model="params.name"
			  style="max-width: 300px;"
			  placeholder="搜索"
			  class="input-with-select"
			>
			  <template #append>
			    <el-button :icon="Search" @click="search"/>
			  </template>
			</el-input>
			
			<el-button type="primary" style="margin-left: 50px;" plain @click="add">添加</el-button>
		</div>
	
		<el-table :data="tableData.records">
			<el-table-column label="序号" prop="id"></el-table-column>
			<el-table-column label="客户姓名" prop="name"></el-table-column>
			<el-table-column label="性别" prop="sex"></el-table-column>
			<el-table-column label="事项" prop="thing"></el-table-column>
			<el-table-column label="状态" prop="status"></el-table-column>
			<el-table-column label="时间" prop="ntime"></el-table-column>
			<el-table-column label="备注" prop="memo"></el-table-column>
			<el-table-column label="处理人" prop="people"></el-table-column>
			<el-table-column label="处理内容" prop="content"></el-table-column>
			<el-table-column label="操作">
				<template #default="scope">
					<!-- <el-button type="primary" size="small" @click="update(scope.row.id)">修改</el-button> -->
					<el-button type="danger" size="small" plain style="margin-left: 0px;" @click="del(scope.row.id)">删除</el-button>
					<!-- <el-button type="success" plain @click="setup(scope.row.id)">设置</el-button> -->
					<el-button type="primary" size="small" plain style="margin-left: 60px;" v-if="scope.row.status==='未处理'"
						@click="setup(scope.row.id)">处理</el-button>
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
	import { CirclePlus, Search } from '@element-plus/icons-vue'
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
		
		id: ''
	});
	getTableData()

	function getTableData() {

		get('/feedback/list', params, content => {
			tableData.value = content
		})
	}
// function search() {
// 		getTableData()
// 	}

	function setup( id) {
	
		remark.id = id

		showDialog.value = true
	}

	function add() {
		dialog.title = '添加投诉事件'
		dialog.id = null
		dialog.show = true
	}

	function search() {
		getTableData()
	}

	function update(id) {
		dialog.title = '修改信息'
		dialog.id = id
		dialog.show = true
	}

	function del(id) {
		const text = '确定要删除该反馈吗'
		ElMessageBox.confirm(text, '警告', {
			type: 'warning'
		}).then(() => {

			post('/feedback/del', {
				id

			}, content => {
				getTableData()
			})
		}).catch(() => {})
	}

	function remind(id) {
		post('/lifereminder/remind', {id},content=>
		{
				getTableData()
		})
	}
</script>

<style scoped lang="scss">
</style>