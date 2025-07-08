<template>
  <div>
		<div style="margin-bottom: 10px;">
			<el-button type="primary" plain @click="add">添加</el-button>
			<el-input size="small" style="width: auto;margin: 0 10px;" placeholder="请输入要搜索的客户姓名" v-model="params.name"></el-input>
			<el-button type="primary" size="small" icon="el-icon-search" plain @click="search">搜索</el-button>
		</div>
		<el-table :data="tableData.records">
			<el-table-column label="编号" prop="id"></el-table-column>
			<el-table-column label="姓名" prop="name"></el-table-column>
			
			<el-table-column label="性别">
				<template #default="scope">
					<span v-if="scope.row.sex === 1">男</span>
					<span v-else>女</span>
				</template>
			</el-table-column>
			<el-table-column label="生日" prop="birthday"></el-table-column>
			<el-table-column label="护理级别" prop="nursingLevel"></el-table-column>
		<el-table-column label="护理内容">
		<template #default="scope">
			<el-button type="success" plain @click="setup(scope.row.id)">设置</el-button>
							
	</template>
			</el-table-column>
			<el-table-column label="护理记录">
				<template #default="scope">
					<el-button type="primary" plain @click="addrecord(scope.row.id,scope.row.name)">添加</el-button>
									
			</template>
					</el-table-column>
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
			 :id='dlog.id'
			 />
		</el-dialog>
		<el-dialog
			v-model="log.show"
			:title="log.title"
			:close-on-click-modal="false"
			width="450px">
			<Record v-if="log.show" 
			v-model:show="log.show" 
			@getTableData="getTableData"
			 :id='log.id'
			 :name='log.name'
			 />
		</el-dialog>
  </div>
</template>

<script setup>
import { get } from '@/axios/axios'
import { ElMessage } from 'element-plus'
import { ref, reactive } from 'vue'
import CustomSetup from './setup'
import Add from './add'
import Record from './rd'
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
const log=reactive({
	show: false,
	title: '',
	id: null,
	name:''
})
getTableData()
function getTableData () {
	
	get('/nurse/list', params, content => {
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
function addrecord (id,name) {
	
	log.title = '添加护理记录'
			log.id = id
			log.name=name
	log.show = true
}
</script>

<style scoped lang="scss">
</style>