<template>
  <div class="nursing-record-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-input
        v-model="params.name"
        placeholder="请输入要搜索的客户姓名"
        class="search-input"
        clearable
      >
        <template #append>
          <el-button :icon="Search" @click="search" />
        </template>
      </el-input>
    </div>
    
    <!-- 护理记录表格 -->
    <el-table :data="tableData.records" style="width: 100%" stripe border>
      <el-table-column width="80" label="编号" prop="cuid" align="center" />
      <el-table-column width="120" label="姓名" prop="name" align="center" />
      <el-table-column width="150" label="护理日期" prop="date" align="center" />
      <el-table-column label="护理内容" prop="content" align="center" />
      <el-table-column width="150" label="护理人员" prop="nursepeople" align="center" />
      <el-table-column label="操作" width="200" align="center">
        <template #default="scope">
          <el-button 
            type="primary" 
            plain 
            size="small" 
            @click="setup(scope.row.id, scope.row.executecycle, scope.row.executenub)"
          >
            修改周期
          </el-button>
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
    
    <!-- 修改弹窗 -->
    <el-dialog 
      v-model="dialog.show" 
      :title="dialog.title" 
      width="450px" 
      :close-on-click-modal="false"
    >
      <CustomSetup 
        v-if="dialog.show" 
        v-model:show="dialog.show" 
        @getTableData="getTableData"
        :id="dialog.id"
      />
    </el-dialog>
    
    <!-- 添加弹窗 -->
    <el-dialog 
      v-model="dlog.show" 
      :title="dlog.title" 
      width="450px" 
      :close-on-click-modal="false"
    >
      <Add 
        v-if="dlog.show" 
        v-model:show="dlog.show" 
        @getTableData="getTableData"
        :id="dlog.id"
      />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { Search } from '@element-plus/icons-vue';
import { get } from '@/axios/axios';
import CustomSetup from './setup.vue';
import Add from './add.vue';

// 对话框状态
const dialog = reactive({
  show: false,
  title: '',
  id: null
});

const dlog = reactive({
  show: false,
  title: '',
  id: null
});

// 表格数据
const tableData = ref({
  records: [],
  pages: 0,
  total: 0
});

// 请求参数
const params = reactive({
  pageNo: 1,
  pageSize: 10,
  name: ''
});

// 获取表格数据
function getTableData() {
  get('/record/list', params, content => {
    tableData.value = content;
  });
}

getTableData();

// 搜索
function search() {
  getTableData();
}

// 修改执行周期
function setup(id, executecycle, executenub) {
  dialog.title = '修改执行周期、执行次数';
  dialog.id = id;
  dialog.show = true;
}

// 添加记录
function add() {
  dlog.title = '添加用户';
  dlog.id = null;
  dlog.show = true;
}
</script>

<style scoped>
.nursing-record-container {
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