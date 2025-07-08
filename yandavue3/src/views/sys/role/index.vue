<template>
  <div class="role-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-button type="primary" plain :icon="Guanliyuan" @click="add" class="add-btn">
        添加角色
      </el-button>
    </div>
    
    <!-- 角色信息表格 -->
    <el-table :data="tableData.records" style="width: 100%" stripe border>
      <el-table-column label="角色名称" prop="name" width="180" align="center"></el-table-column>
      <el-table-column label="角色说明" prop="description" show-overflow-tooltip></el-table-column>
      
      <el-table-column label="状态" width="100" align="center">
        <template #default="scope">
          <el-tag type="success" v-if="scope.row.status">启用</el-tag>
          <el-tag type="danger" v-else>禁用</el-tag>
        </template>
      </el-table-column>
      
      <el-table-column label="操作" width="290" align="center">
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
    
    <el-dialog
      v-model="userDialog.show"
      title="关联用户"
      width="600px"
      :close-on-click-modal="false"
    >
      <UserComponent 
        v-if="userDialog.show" 
        :roleId="userDialog.roleId" 
        v-model:show="userDialog.show"
      />
    </el-dialog>
    
    <el-dialog 
      v-model="resourceDialog.show"
      width="500px"
      title="权限列表"
      :close-on-click-modal="false"
    >
      <ResourceComponent
        v-if="resourceDialog.show"
        v-model:show="resourceDialog.show"
        :roleId="resourceDialog.roleId"
      />
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import { ElMessageBox } from 'element-plus';
import { get, post } from '@/axios';
import Guanliyuan from '@/components/icons/guanliyuan';
import Add from './add.vue';
import UserComponent from './user.vue';
import ResourceComponent from './resource.vue';
import url from './util';

// 对话框状态
const dialog = reactive({
  show: false,
  title: '',
  id: null
});

const userDialog = reactive({
  show: false,
  roleId: null
});

const resourceDialog = reactive({
  show: false,
  roleId: null
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

// 添加角色
function add() {
  dialog.title = '添加角色';
  dialog.id = null;
  dialog.show = true;
}

// 修改角色
function update(id) {
  dialog.title = '修改角色';
  dialog.id = id;
  dialog.show = true;
}

// 删除/启用角色
function del(id, status) {
  const text = status ? '确定要启用该角色吗?' : '确定要禁用该角色吗';
  ElMessageBox.confirm(text, '警告', {
    type: 'warning'
  }).then(() => {
    post(url.del, { id, status }, content => {
      getTableData();
    });
  }).catch(() => {});
}

// 查看关联用户
function userList(roleId) {
  userDialog.roleId = roleId;
  userDialog.show = true;
}

// 分配权限
function resourceList(roleId) {
  resourceDialog.roleId = roleId;
  resourceDialog.show = true;
}
</script>

<style scoped>
.role-container {
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