<template>
	<div>
		<div>
			<el-input
			  v-model="params.name"
			  style="max-width: 300px"
			  placeholder="搜索"
			  class="input-with-select"
			>
			  <template #append>
			    <el-button :icon="Search" @click="search"/>
			  </template>
			</el-input>
			<el-button type="primary" style="margin-left: 50px;" plain :icon="CirclePlus" @click="add">添加</el-button>
			
		</div>
		<div>
			<el-table :data="tableData.records">
				<el-table-column label="姓名" prop="name"></el-table-column>
				<el-table-column label="性别" prop="sex">
					<template #default="scope">
						<span v-if="scope.row.sex === 1">男</span>
						<span v-else>女</span>
					</template>
				</el-table-column>
				<el-table-column label="年龄" prop="age"></el-table-column>
				<el-table-column label="平时喜好" prop="hobby"></el-table-column>
				<el-table-column label="注意事项" prop="note"></el-table-column>
				<el-table-column label="备注" prop="notes"></el-table-column>
				<el-table-column label="操作" prop="caozuo"></el-table-column>
				<el-table-column width="200">
					<template #default="scope">
						<template v-if="scope.row.status">
							<el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
							<el-button type="danger" plain size="small" @click="del(scope.row.id, 0)">删除</el-button>
							<el-button type="primary" plain size="small" @click="set(scope.row.id)">设置</el-button>
						</template>
						<el-button v-else type="warning" plain size="small" @click="del(scope.row.id, 1)">启用</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<el-pagination
			style="margin-top: 10px;"
			background
			v-model:current-page="params.pageNo"
			:page-count="tableData.pages"
			:total="tableData.total"
			@current-change="getTableData" />
		<el-dialog
				v-model="dialog.show"
				:title="dialog.title"
				width="450px"
				:close-on-click-modal="false">
		  <Add
				v-if="dialog.show"
				@getTableData="getTableData"
					v-model:show="dialog.show"
		 			:id="dialog.id"/>
		</el-dialog>
		<el-dialog
				v-model="dialog2.show"
				:title="dialog2.title"
				width="450px"
				:close-on-click-modal="false">
		  <Set
				v-if="dialog2.show"
				@getTableData="getTableData"
					v-model:show="dialog2.show"
		 			:id="dialog2.id"/>
		</el-dialog>
	</div>
</template>

<script setup lang="ts">
import { CirclePlus, Search } from '@element-plus/icons-vue'
import { getPath } from '@/util'
// import Guanliyuan from '@/components/icons/guanliyuan'
import { get, post } from '@/axios'
import { ref, reactive } from 'vue'
import Add from './add'
import Set from './set'
import url from './util'
import { ElMessageBox } from 'element-plus'
const dialog = reactive({
	show: false,
	title: '',
	id: null
})
const dialog2 = reactive({
	show: false,
	title: '',
	id: null
})
const tableData = reactive({
	records: [],
	pages: 0,
	total: 0
})
const params = reactive({
	pageNo: 1,
	pageSize: 10,
	name: ''
})
getTableData()
function getTableData () {
	get(url.list, params, content => {
		tableData.records = content.records
		tableData.pages = content.pages
		tableData.total = content.total
	})
}
function add() {
	dialog.title = '添加菜品'
		dialog.id = null
	dialog.show = true
}
function set(id) {
	dialog2.title = '添加菜单'
		dialog2.id = id
	dialog2.show = true
}
function update(id) {
	dialog.title = '修改菜品'
	dialog.id = id
	dialog.show = true
}
function del (id, status) {
	const text = status ? '确定要启用该菜品吗?' : '确定要禁用该菜品吗'
	ElMessageBox.confirm(text, '警告', {
		type: 'warning'
	}).then(() => {
		post(url.del, { id ,status}, content => {
			getTableData()
		})
	}).catch(() => {})
}

function search(){
	getTableData()
}
</script>

<style scoped lang="scss">
	
</style>
