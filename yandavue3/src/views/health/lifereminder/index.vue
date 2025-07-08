<template>
  <div class="reminder-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-input
        v-model="params.name"
        placeholder="搜索提醒"
        class="search-input"
        clearable
      >
        <template #append>
          <el-button :icon="Search" @click="search" />
        </template>
      </el-input>
      
      <el-button type="primary" plain @click="add" class="add-btn">
        添加
      </el-button>
    </div>
    
    <!-- 提醒信息表格 -->
    <el-table :data="tableData.records" style="width: 100%" stripe border>
      <el-table-column width="60" label="序号" prop="id" align="center" />
      <el-table-column width="120" label="客户姓名" prop="name" align="center" />
      <el-table-column width="120" label="联系方式" prop="phone" align="center" />
      <el-table-column width="180" label="事件" prop="rememberthing" align="center" />
      <el-table-column width="150" label="事件时间" prop="thingtime" align="center" />
      <el-table-column width="100" label="状态" align="center">
        <template #default="scope">
          <el-tag :type="scope.row.status === '已提醒' ? 'success' : 'info'">
            {{ scope.row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column width="150" label="提醒时间" prop="remerbertime" align="center" />
      <el-table-column label="操作" width="220" align="center">
        <template #default="scope">
          <el-button 
            type="danger" 
            plain 
            size="small" 
            @click="del(scope.row.id)"
          >
            删除
          </el-button>
          <el-button 
            type="primary" 
            plain 
            size="small" 
            v-if="scope.row.status === '未提醒'"
            @click="remind(scope.row.id)"
          >
            提醒
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
    <el-dialog v-model="showDialog" title="详细备注" width="450px" :close-on-click-modal="false">
      <CustomSetup 
        v-if="showDialog" 
        v-model:show="showDialog" 
        v-model:memo="remark.memo" 
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
  memo: '',
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
  get('/lifereminder/list', params, content => {
    tableData.value = content;
  });
}

// 初始化获取数据
getTableData();

// 搜索功能
function search() {
  getTableData();
}

// 设置备注
function setup(memo, id) {
  remark.memo = memo;
  remark.id = id;
  showDialog.value = true;
}

// 添加提醒
function add() {
  dialog.title = '添加提醒';
  dialog.id = null;
  dialog.show = true;
}

// 修改提醒
function update(id) {
  dialog.title = '修改提醒';
  dialog.id = id;
  dialog.show = true;
}

// 删除提醒
function del(id) {
  const text = '确定要删除该提醒吗';
  ElMessageBox.confirm(text, '警告', {
    type: 'warning'
  }).then(() => {
    post('/lifereminder/del', { id }, () => {
      getTableData();
    });
  }).catch(() => {});
}

// 发送提醒
function remind(id) {
  post('/lifereminder/remind', { id }, () => {
    getTableData();
  });
}
</script>

<style scoped>
.reminder-container {
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