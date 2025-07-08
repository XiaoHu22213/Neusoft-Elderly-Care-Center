<template>
	<div>
		<div class="btns">
			<el-button type="primary" plain :icon="Guanliyuan" @click="add">添加管理员</el-button>
		</div>
		<el-table :data="tableData.records">
			<el-table-column type="expand">
				<template #default="scope">
					<el-form label-width="100">
						<el-form-item label="姓名">{{scope.row.name}}</el-form-item>
						<el-form-item label="昵称">{{scope.row.nickyName}}</el-form-item>
						<el-form-item label="手机号">{{scope.row.phone}}</el-form-item>
						<el-form-item label="生日">{{scope.row.birthday}}</el-form-item>
						<el-form-item label="电子信箱">{{scope.row.email}}</el-form-item>
					</el-form>
				</template>
			</el-table-column>
			
			<el-table-column label="姓名">
				<template #default="scope">
					<el-popover placement="right">
						<template #reference>
							<span>{{scope.row.name}}</span>
						</template #default>
						<el-image style="width: 120px;height: 120px;" fit="contain" :src="getPath(scope.row.icon)">
						</el-image>
					</el-popover>
				</template>
			</el-table-column>

			<el-table-column label="昵称" prop="nickyName"></el-table-column>
			<el-table-column label="性别">
				<template #default="scope">
					<span v-if="scope.row.sex === 1">男</span>
					<span v-else>女</span>
				</template>
			</el-table-column>
			<el-table-column label="手机号" prop="phone"></el-table-column>
			<el-table-column label="状态">
				<template #default="scope">
					<el-tag type="success" v-if="scope.row.status">启用</el-tag>
					<el-tag type="danger" v-else>禁用</el-tag>
				</template>
			</el-table-column>
			<el-table-column width="150">
				<template #default="scope">
					<template v-if="scope.row.status">
						<el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
						<el-button type="danger" plain size="small" @click="del(scope.row.id, 0)">删除</el-button>
					</template>
					<el-button v-else type="warning" plain size="small" @click="del(scope.row.id, 1)">启用</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination style="margin-top: 10px;" background v-model:current-page="params.pageNo"
			:page-count="tableData.pages" :total="tableData.total" @current-change="getTableData" />
		<el-dialog v-model="dialog.show" :title="dialog.title" width="450px" :close-on-click-modal="false">
			<Add v-if="dialog.show" @getTableData="getTableData" v-model:show="dialog.show" :id="dialog.id" />
		</el-dialog>
	</div>
</template>

<script setup>
	import {getPath} from '@/util'
	import Guanliyuan from '@/components/icons/guanliyuan'
	import {get,post} from '@/axios'
	import {ref,reactive} from 'vue'
	import Add from './add'
	import {ElMessageBox} from 'element-plus'
	import url from './util'
	const dialog = reactive({
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
		pageSize: 9
	})
	getTableData()

	function getTableData() {
		get(url.list, params, content => {
			tableData.records = content.records
			tableData.pages = content.pages
			tableData.total = content.total
		})
	}

	function add() {
		dialog.title = '添加管理员'
		dialog.id = null
		dialog.show = true
	}

	function update(id) {
		dialog.title = '修改管理员'
		dialog.id = id
		dialog.show = true
	}

	function del(id, status) {
		const text = status ? '确定要启用该管理员吗?' : '确定要禁用该管理员吗'
		ElMessageBox.confirm(text, '警告', {
			type: 'warning'
		}).then(() => {
			post(url.del, {
				id,
				status
			}, content => {
				getTableData()
			})
		}).catch(() => {})
	}
</script>

<style scoped lang="scss"></style>