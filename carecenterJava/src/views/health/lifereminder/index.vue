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
			<el-table-column label="联系方式" prop="phone"></el-table-column>
			<el-table-column label="事件" prop="rememberthing"></el-table-column>
			<el-table-column label="事件时间" prop="thingtime"></el-table-column>
			<el-table-column label="状态" prop="status"></el-table-column>
			<el-table-column label="提醒时间" prop="remerbertime"></el-table-column>
			<el-table-column label="操作">
				<template #default="scope">
					<!-- <el-button type="primary" size="small" @click="update(scope.row.id)">修改</el-button> -->
					<el-button type="danger" plain size="small" @click="del(scope.row.id)">删除</el-button>
					<!-- <el-button type="success" plain @click="setup(scope.row.id)">设置</el-button> -->
					<el-button type="primary" plain size="small" v-if="scope.row.status==='未提醒'"
						@click="remind(scope.row.id)">提醒</el-button>
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
		memo: '',
		id: ''
	});
	getTableData()

	function getTableData() {

		get('/lifereminder/list', params, content => {
			tableData.value = content
		})
	}
// function search() {
// 		getTableData()
// 	}

	function setup(memo, id) {
		remark.memo = memo
		remark.id = id

		showDialog.value = true
	}

	function add() {
		dialog.title = '添加提醒'
		dialog.id = null
		dialog.show = true
	}

	function search() {
		getTableData()
	}

	function update(id) {
		dialog.title = '修改提醒'
		dialog.id = id
		dialog.show = true
	}

	function del(id) {
		const text = '确定要删除该提醒吗'
		ElMessageBox.confirm(text, '警告', {
			type: 'warning'
		}).then(() => {

			post('/lifereminder/del', {
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