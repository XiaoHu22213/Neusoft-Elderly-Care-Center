<template>
  <div>
    <div class="btns">
      <el-button type="primary" plain :icon="Guanliyuan" @click="add">添加角色</el-button>
    </div>
		<el-table :data="tableData.records">
			<el-table-column label="角色名称" prop="name"></el-table-column>
			<el-table-column label="角色说明" prop="description"></el-table-column>
			<el-table-column label="状态">
				<template #default="scope">
					<el-tag type="success" v-if="scope.row.status">启用</el-tag>
					<el-tag type="danger" v-else>禁用</el-tag>
				</template>
			</el-table-column>
			<el-table-column width="290">
				<template #default="scope">
					<template v-if="scope.row.status">
						<el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
						<el-button type="danger" plain size="small" @click="del(scope.row.id, 0)">删除</el-button>
						<el-button type="success" plain size="small" @click="userList(scope.row.id)">用户</el-button>
						<el-button type="success" plain size="small" @click="resourceList(scope.row.id)">分配权限</el-button>
					</template>
					<el-button v-else type="warning" plain size="small" @click="del(scope.row.id, 1)">启用</el-button>
				</template>
			</el-table-column>
		</el-table>
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
	v-model="userDialog.show"
	title="关联用户"
	width="600px"
	:close-on-click-modal="false">
	<UserComponent 
		v-if="userDialog.show" 
		:roleId="userDialog.roleId" 
		v-model:show="userDialog.show"></UserComponent>
	</el-dialog>
	<el-dialog 
	  v-model="resourceDialog.show"
		width="500px"
		title="权限列表"
		:close-on-click-modal="false">
		<ResourceComponent
		v-if="resourceDialog.show"
		v-model:show="resourceDialog.show"
		:roleId="resourceDialog.roleId"></ResourceComponent>
		</el-dialog>
  </div>
</template>

<script setup>
import Guanliyuan from '@/components/icons/guanliyuan'
import { get, post } from '@/axios'
import { ref, reactive } from 'vue'
import Add from './add'
import { ElMessageBox } from 'element-plus'
import url from './util'
import UserComponent from './user'
import ResourceComponent from './resource'
const dialog = reactive({
	show: false,
	title: '',
	id: null
})
const userDialog = reactive({
	show: false,
	roleId: null
})
const resourceDialog = reactive({
	show: false,
	roleId: null
})
const tableData = reactive({
	records: [],
	pages: 0,
	total: 0
})
const params = reactive({
	pageNo: 1,
	pageSize: 9
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
	dialog.title = '添加角色'
	dialog.id = null
  dialog.show = true
}
function update(id) {
	dialog.title = '修改角色'
	dialog.id = id
	dialog.show = true
}
function del (id, status) {
	const text = status ? '确定要启用该角色吗?' : '确定要禁用该角色吗'
	ElMessageBox.confirm(text, '警告', {
		type: 'warning'
	}).then(() => {
		post(url.del, { id, status }, content => {
			getTableData()
		})
	}).catch(() => {})
}
function userList (roleId) {
	userDialog.roleId = roleId
	userDialog.show = true
}
function resourceList (roleId) {
	resourceDialog.roleId = roleId
	resourceDialog.show = true
}
</script>

<style scoped lang="scss"></style>
