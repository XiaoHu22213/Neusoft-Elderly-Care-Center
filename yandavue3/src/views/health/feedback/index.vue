<template>
  <div class="feedback-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-input
        v-model="params.name"
        placeholder="搜索反馈"
        class="search-input"
        clearable
      >
        <template #append>
          <el-button :icon="Search" @click="search" />
        </template>
      </el-input>
      
      <el-button type="primary" plain @click="add" class="add-btn">
        添加投诉
      </el-button>
    </div>
    
    <!-- 反馈信息表格 -->
    <el-table :data="tableData.records" style="width: 100%" stripe border>
      <el-table-column width="60" label="序号" prop="id" align="center" />
      <el-table-column width="100" label="客户姓名" prop="name" align="center" />
      <el-table-column width="70" label="性别" prop="sex" align="center" />
      <el-table-column width="150" label="事项" prop="thing" align="center" />
      <el-table-column width="100" label="状态" align="center">
        <template #default="scope">
          <el-tag :type="scope.row.status === '已处理' ? 'success' : 'warning'">
            {{ scope.row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column width="150" label="时间" prop="ntime" align="center" />
      <el-table-column width="150" label="备注" prop="memo" align="center" />
      <el-table-column width="100" label="处理人" prop="people" align="center" />
      <el-table-column width="150" label="处理内容" prop="content" align="center" />
      <el-table-column label="操作" width="150" align="center">
        <template #default="scope">
          <el-button 
            type="primary" 
            size="small" 
            plain 
            v-if="scope.row.status === '未处理'"
            @click="setup(scope.row.id)"
          >
            处理
          </el-button>
          <el-button 
            type="danger" 
            size="small" 
            plain 
            v-if="scope.row.status === '已处理'" 
            @click="del(scope.row.id)"
            style="margin-top: 5px;"
          >
            删除
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
    
    <!-- 弹窗组件 -->
    <el-dialog v-model="showDialog" title="处理反馈" width="450px" :close-on-click-modal="false">
      <CustomSetup 
        v-if="showDialog" 
        v-model:show="showDialog" 
        v-model:id="remark.id"
        @getTableData="getTableData" 
      />
    </el-dialog>
    
    <el-dialog v-model="dialog.show" :title="dialog.title" width="450px" :close-on-click-modal="false">
      <Add 
        v-if="dialog.show" 
        v-model:show="dialog.show" 
        @getTableData="getTableData" 
        :id="dialog.id" 
      />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ElMessageBox } from 'element-plus';
import { Search } from '@element-plus/icons-vue';
import { get, post } from '@/axios/axios';
import CustomSetup from './setup.vue';
import Add from './add.vue';

// 表格数据
const tableData = ref({});

// 对话框状态
const showDialog = ref(false);
const dialog = reactive({
  show: false,
  title: '',
  id: null
});

// 备注信息
const remark = reactive({
  id: ''
});

// 请求参数
const params = reactive({
  pageNo: 1,
  pageSize: 10,
  name: ''
});

// 获取表格数据
function getTableData() {
  get('/feedback/list', params, content => {
    tableData.value = content;
  });
}

// 初始化获取数据
getTableData();

// 处理反馈
function setup(id) {
  remark.id = id;
  showDialog.value = true;
}

// 添加投诉
function add() {
  dialog.title = '添加投诉事件';
  dialog.id = null;
  dialog.show = true;
}

// 搜索功能
function search() {
  getTableData();
}

// 修改信息
function update(id) {
  dialog.title = '修改信息';
  dialog.id = id;
  dialog.show = true;
}

// 删除反馈
function del(id) {
  const text = '确定要删除该反馈吗';
  ElMessageBox.confirm(text, '警告', {
    type: 'warning'
  }).then(() => {
    post('/feedback/del', { id }, () => {
      getTableData();
    });
  }).catch(() => {});
}
</script>

<style scoped>
.feedback-container {
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

.el-tag {
  font-weight: 500;
}

.el-button + .el-button {
  margin-left: 8px;
}

.el-table .cell {
  padding: 8px 0;
}
</style>