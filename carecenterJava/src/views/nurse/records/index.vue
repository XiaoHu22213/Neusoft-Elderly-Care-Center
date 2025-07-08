<template>
  <div>
		<div style="margin-bottom: 10px;">
			<el-input
			  v-model="params.name"
			  style="max-width: 300px;"
			  placeholder="请输入要搜索的客户姓名"
			  class="input-with-select"
			>
			  <template #append>
			    <el-button :icon="Search" @click="search"/>
			  </template>
			</el-input>
			<!-- <el-button type="primary" plain @click="add">添加</el-button> -->
			<!-- <el-input size="small" style="width: auto;margin: 0 10px;" placeholder="请输入要搜索的客户姓名" v-model="params.name"></el-input>
			<el-button type="primary" size="small" icon="el-icon-search" plain @click="search">搜索</el-button> -->
				<!-- <el-button type="primary" size="small" icon="el-icon-search" plain @click="search" style="float: right;margin-right: 50px;">添加临时护理项目记录</el-button> -->
		
		</div>
		<el-table :data="tableData.records">
			<el-table-column label="编号" prop="cuid"></el-table-column>
			<el-table-column label="姓名" prop="name"></el-table-column>
			
			
			<el-table-column label="护理日期" prop="date"></el-table-column>
			<el-table-column label="护理内容" prop="content"></el-table-column>
			<el-table-column label="护理人员" prop="nursepeople"></el-table-column>
		
		</el-table>
		<el-pagination
			style="margin-top: 10px;"
			background
			:page-count="tableData.pages"
			v-model:current-page="params.pageNo"
			@current-change="getTableData"
			:total="tableData.total"></el-pagination>
		<el-dialog
			v-model="dialog.show"
			:title="dialog.title"
			:close-on-click-modal="false"
			width="450px">
			<CustomSetup v-if="dialog.show" 
			v-model:show="dialog.show" 
			@getTableData="getTableData"
			 :id='dialog.id'
			
			 />
		</el-dialog>
		<el-dialog
			v-model="dlog.show"
			:title="dlog.title"
			:close-on-click-modal="false"
			width="450px">
			<Add v-if="dlog.show" 
			v-model:show="dlog.show" 
			@getTableData="getTableData"
			 :id='dialog.id'
			 />
		</el-dialog>
  </div>
</template>

<script setup>
import { CirclePlus, Search } from '@element-plus/icons-vue'
import { get } from '@/axios/axios'
import { ElMessage } from 'element-plus'
import { ref, reactive } from 'vue'
import CustomSetup from './setup'
import Add from './add'
let tableData = ref({})
let showDialog = ref(false)
const params = reactive({
	pageNo: 1,
	pageSize: 10,
	name:''
})
const dialog = reactive({
	show: false,
	title: '',
	id: null,

})
const dlog = reactive({
	show: false,
	title: '',
	id: null
})
getTableData()
function getTableData () {
	
	get('/record/list', params, content => {
		tableData.value = content
		//console.dir(tableData)
	})
}
function setup (id,executecycle,executenub) {
		
	dialog.title = '修改执行周期、执行次数'
			dialog.id = id
			
	dialog.show = true
}
function search(){
	getTableData()
}
function add () {
	dlog.title = '添加用户'
			dlog.id = null
	dlog.show = true
}
</script>

<style scoped lang="scss">
</style>