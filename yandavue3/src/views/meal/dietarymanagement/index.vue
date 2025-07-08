<template>
  <div class="preference-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-input
        v-model="params.name"
        placeholder="搜索"
        class="search-input"
        clearable
      >
        <template #append>
          <el-button :icon="Search" @click="search" />
        </template>
      </el-input>
      <el-button type="primary" plain @click="add" class="add-btn">
        <el-icon><CirclePlus /></el-icon>
        添加
      </el-button>
    </div>
    
    <!-- 客户信息表格 -->
    <el-table :data="tableData.records" style="width: 100%" stripe border>
      <el-table-column width="100" label="姓名" prop="name" align="center" />
      <el-table-column width="70" label="性别" align="center">
        <template #default="scope">
          <span v-if="scope.row.sex === 1">男</span>
          <span v-else>女</span>
        </template>
      </el-table-column>
      <el-table-column width="70" label="年龄" prop="age" align="center" />
      <el-table-column label="平时喜好" prop="hobby" align="center" />
      <el-table-column label="注意事项" prop="note" align="center" />
      <el-table-column label="备注" prop="notes" align="center" />
      <el-table-column label="操作" width="300" align="center">
        <template #default="scope">
          <template v-if="scope.row.status">
            <el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
            <el-button type="danger" plain size="small" @click="del(scope.row.id, 0)">删除</el-button>
            <el-button type="success" plain size="small" @click="set(scope.row.id)">设置</el-button>
          </template>
          <el-button v-else type="warning" plain size="small" @click="del(scope.row.id, 1)">启用</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 分页 -->
    <el-pagination
      class="pagination"
      background
      v-model:current-page="params.pageNo"
      :page-size="params.pageSize"
      :total="tableData.total"
      layout="prev, pager, next, jumper, total"
      @current-change="getTableData"
    />
    
    <!-- 弹窗组件 -->
    <el-dialog v-model="dialog.show" :title="dialog.title" width="450px" :close-on-click-modal="false">
      <Add v-if="dialog.show" @getTableData="getTableData" v-model:show="dialog.show" :id="dialog.id" />
    </el-dialog>
    
    <el-dialog v-model="dialog2.show" :title="dialog2.title" width="450px" :close-on-click-modal="false">
      <Set v-if="dialog2.show" @getTableData="getTableData" v-model:show="dialog2.show" :id="dialog2.id" />
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { CirclePlus, Search } from '@element-plus/icons-vue'
import { get, post } from '@/axios'
import { reactive } from 'vue'
import { ElMessageBox } from 'element-plus'
import Add from './add.vue'
import Set from './set.vue'
import url from './util'

// 对话框状态
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

// 表格数据
const tableData = reactive({
  records: [],
  pages: 0,
  total: 0
})

// 请求参数
const params = reactive({
  pageNo: 1,
  pageSize: 10,
  name: ''
})

// 获取表格数据
function getTableData() {
  get(url.list, params, content => {
    tableData.records = content.records
    tableData.pages = content.pages
    tableData.total = content.total
  })
}

getTableData()

// 搜索
function search() {
  getTableData()
}

// 添加
function add() {
  dialog.title = '添加信息'
  dialog.id = null
  dialog.show = true
}

// 设置
function set(id) {
  dialog2.title = '设置菜单'
  dialog2.id = id
  dialog2.show = true
}

// 修改
function update(id) {
  dialog.title = '修改信息'
  dialog.id = id
  dialog.show = true
}

// 删除/启用
function del(id, status) {
  const text = status ? '确定要启用该菜品吗?' : '确定要禁用该菜品吗'
  ElMessageBox.confirm(text, '警告', {
    type: 'warning'
  }).then(() => {
    post(url.del, { id, status }, content => {
      getTableData()
    })
  }).catch(() => {})
}
</script>

<style scoped>
.preference-container {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.operation-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.search-input {
  max-width: 300px;
  margin-right: 15px;
}

.add-btn {
  margin-left: 15px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.el-table {
  margin-top: 15px;
}

/* 操作按钮间距 */
.el-button + .el-button {
  margin-left: 8px;
}

/* 表格行样式 */
.el-table .cell {
  padding: 8px 0;
}
</style>