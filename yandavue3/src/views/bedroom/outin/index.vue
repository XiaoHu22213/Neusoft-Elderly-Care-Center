<template>
  <div class="outin-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-input
        v-model="params.name"
        placeholder="请输入要搜索的人名"
        class="search-input"
        clearable
      >
        <template #append>
          <el-button :icon="Search" @click="search" />
        </template>
      </el-input>
    </div>
    
    <!-- 离床记录表格 -->
    <el-table :data="tableData.records" style="width: 100%" stripe border>
      <el-table-column width="180" label="离席时间" prop="outtime" align="center" />
      <el-table-column width="180" label="回来时间" prop="intime" align="center" />
      <el-table-column width="100" label="人名" prop="outinname" align="center" />
      <el-table-column width="100" label="床号" prop="bednum" align="center" />
      <el-table-column width="100" label="事由" prop="thing" align="center" />
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
      <Add 
        v-if="dialog.show"
        @getTableData="getTableData" 
        v-model:show="dialog.show" 
        :id="dialog.id" 
      />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ElMessageBox, ElMessage } from 'element-plus';
import { Search } from '@element-plus/icons-vue';
import { request, get } from '@/axios';
import Add from './add.vue';

// 对话框状态
const dialog = reactive({
  show: false,
  title: '',
  id: null
});

// 表格数据
const tableData = reactive({
  records: [],
  pages: 0,
  total: 0
});

// 请求参数
const params = reactive({
  pageNo: 1,
  pageSize: 20,
  name: ""
});

// 获取表格数据
function getTableData() {
  get('/outin/list', params, content => {
    tableData.records = content.records;
    tableData.pages = content.pages;
    tableData.total = content.total;
  });
}

getTableData();

// 搜索
function search() {
  getTableData();
}

// 添加记录
function add() {
  dialog.title = '添加离床记录';
  dialog.id = null;
  dialog.show = true;
}

// 修改记录
function update(id) {
  dialog.title = '修改离床记录';
  dialog.id = id;
  dialog.show = true;
}

// 删除记录
function del(id) {
  ElMessageBox.confirm('确定要删除该记录吗？', '警告', {
    type: 'warning'
  }).then(() => {
    request(`/outin/delete/${id}`, 'delete', {}, content => {
      getTableData();
      ElMessage({
        type: 'success',
        message: '操作成功',
      });
    });
  }).catch(() => {});
}
</script>

<style scoped>
.outin-container {
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