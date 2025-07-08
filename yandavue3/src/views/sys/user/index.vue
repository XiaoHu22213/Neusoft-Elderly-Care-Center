<template>
  <div class="staff-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-button type="primary" plain @click="add" class="add-btn">
        添加用户
      </el-button>
    </div>
    
    <!-- 员工信息表格 -->
    <el-table :data="tableData.records" style="width: 100%" stripe border>
      <el-table-column type="expand" width="60">
        <template #default="scope">
          <el-form label-width="100px" class="detail-form">
            <el-form-item label="姓名">{{ scope.row.name }}</el-form-item>
            <el-form-item label="简介">{{ scope.row.introduction }}</el-form-item>
            <el-form-item label="手机号">{{ scope.row.phone }}</el-form-item>
            <el-form-item label="电子信箱">{{ scope.row.email }}</el-form-item>
          </el-form>
        </template>
      </el-table-column>
      
      <el-table-column label="姓名" prop="name" width="120" align="center"></el-table-column>
      
      <el-table-column label="性别" width="80" align="center">
        <template #default="scope">
          <span v-if="scope.row.sex === 1">男</span>
          <span v-else>女</span>
        </template>
      </el-table-column>
      
      <el-table-column label="生日" prop="birthday" width="120" align="center"></el-table-column>
      <el-table-column label="员工类别" prop="type" width="120" align="center"></el-table-column>
      <el-table-column label="入职时间" prop="worktime" width="120" align="center"></el-table-column>
      
      <el-table-column label="人员状态" width="100" align="center">
        <template #default="scope">
          <el-tag type="success" v-if="scope.row.status">在职</el-tag>
          <el-tag type="danger" v-else>离职</el-tag>
        </template>
      </el-table-column>
      
      <el-table-column label="简介" prop="introduction" show-overflow-tooltip></el-table-column>
      
      <el-table-column label="操作" width="200" align="center">
        <template #default="scope">
          <template v-if="scope.row.status">
            <el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
            <el-button type="danger" plain size="small" @click="del(scope.row.id, 0)">删除</el-button>
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
    <el-dialog 
      v-model="dialog.show" 
      :title="dialog.title" 
      width="450px" 
      :close-on-click-modal="false"
    >
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
import { ElMessageBox } from 'element-plus';
import { get, post } from '@/axios';
import Add from './add.vue';
import url from './util';

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
  pageSize: 9
});

// 获取表格数据
function getTableData() {
  get(url.list, params, content => {
    tableData.records = content.records;
    tableData.pages = content.pages;
    tableData.total = content.total;
  });
}

getTableData();

// 添加用户
function add() {
  dialog.title = '添加用户';
  dialog.id = null;
  dialog.show = true;
}

// 修改用户
function update(id) {
  dialog.title = '修改用户';
  dialog.id = id;
  dialog.show = true;
}

// 删除/启用用户
function del(id, status) {
  const text = status ? '确定要启用该用户吗?' : '确定要禁用该用户吗';
  ElMessageBox.confirm(text, '警告', {
    type: 'warning'
  }).then(() => {
    post(url.del, { id, status }, content => {
      getTableData();
    });
  }).catch(() => {});
}
</script>

<style scoped>
.staff-container {
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

.add-btn {
  margin-left: 15px;
}

.detail-form {
  padding: 15px;
  background: #f8f8f8;
  border-radius: 4px;
  margin: 5px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.el-table {
  margin-top: 15px;
}

/* 美化标签样式 */
.el-tag {
  font-weight: 500;
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