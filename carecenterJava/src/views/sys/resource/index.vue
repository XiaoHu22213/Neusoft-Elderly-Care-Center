<template>
  <div>
    <div class="btns">
      <el-button type="primary" plain :icon="Guanliyuan" @click="add(0,1)">添加权限</el-button>
    </div>
		<el-table :data="tableData" row-key="id">
			<el-table-column width="200" label="权限名称" prop="name"></el-table-column>
			<el-table-column label="前端地址" prop="frontUrl"></el-table-column>
			<el-table-column show-overflow-tooltip label="后端地址" prop="backUrl"></el-table-column>
			<el-table-column label="类型">
				<template #default="scope">
					<el-tag type="success" v-if="scope.row.type===1">目录</el-tag>
					<el-tag type="danger" v-else>按钮</el-tag>
				</template>
			</el-table-column>
			<el-table-column width="220">
				<template #default="scope">
					<el-button v-if="scope.row.type === 1" plain size="small" @click="add(scope.row.id, scope.row.level + 1)">添加下级</el-button>
						<el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
						<el-button type="danger" plain size="small" @click="del(scope.row.id, id)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		
    <el-dialog
			v-model="dialog.show"
			:title="dialog.title"
			width="450px"
			:close-on-click-modal="false">
      <Add
				v-if="dialog.show"
				@getTableData="getTableData"
				v-model:show="dialog.show"
				:parentId="dialog.parentId"
				:level="dialog.level"
				:id="dialog.id"/>
    </el-dialog>
  </div>
</template>

<script setup>
import {getPath} from '@/util' 
import Guanliyuan from '@/components/icons/guanliyuan'
import { get, post } from '@/axios'
import { ref, reactive } from 'vue'
import Add from './add'
import { ElMessageBox } from 'element-plus'
import url from './util'
const dialog = reactive({
	show: false,
	title: '',
	parentId: null,
	level: null,
	id: null
})
const tableData = ref()
const params = reactive({
})
getTableData()
function getTableData () {
	get(url.list, params, content => {
		tableData.value = content
	})
}
function add(parentId,level) {
	dialog.title = '添加权限'
	dialog.id = null
	dialog.parentId = parentId
	dialog.level = level
  dialog.show = true
}
function update(id) {
	dialog.title = '修改权限'
	dialog.id = id
	dialog.parentId = null
	dialog.level = null
	dialog.show = true
}
function del (id) {
	ElMessageBox.confirm('删除之后无法恢复，确定要删除该权限吗？', '警告', {
		type: 'warning'
	}).then(() => {
		post(url.del, { id }, content => {
			getTableData()
		})
	}).catch(() => {})
}

</script>

<style scoped lang="scss"></style>
