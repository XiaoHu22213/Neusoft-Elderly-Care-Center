<template>
  <div class="nurse-level-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-input
        v-model="params.level"
        placeholder="请输入要搜索的等级"
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
    
    <!-- 客户信息表格 -->
    <el-table :data="tableData.records" style="width: 100%" stripe border>
      <el-table-column width="80" label="编号" prop="id" align="center" />
      <el-table-column width="120" label="护理等级" prop="level" align="center" />
      <el-table-column width="100" label="护理状态" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.status" type="success">启用</el-tag>
          <el-tag v-else type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="memo" align="center" />
      <el-table-column label="操作" width="300" align="center">
        <template #default="scope">
          <template v-if="scope.row.status">
            <el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
            <el-button type="danger" plain size="small" @click="del(scope.row.id, 0)">禁用</el-button>
            <el-button 
              type="success" 
              plain 
              size="small" 
              @click="setup(scope.row.id)"
            >
              设置护理内容
            </el-button>
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
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ElMessageBox } from 'element-plus';
import { Search } from '@element-plus/icons-vue';
import { get, post } from '@/axios/axios';
import Add from './add.vue';
import router from '@/router';

// 对话框状态
const dialog = reactive({
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
  level: ''
});

// 获取表格数据
function getTableData() {
  get('/nurselevel/list', params, content => {
    tableData.value = content;
  });
}

getTableData();

// 搜索
function search() {
  getTableData();
}

// 添加护理等级
function add() {
  dialog.title = '添加护理等级';
  dialog.id = null;
  dialog.show = true;
}

// 修改护理等级
function update(id) {
  dialog.title = '修改护理等级';
  dialog.id = id;
  dialog.show = true;
}

// 删除/启用护理等级
function del(id, status) {
  const text = status ? '确定要启用该角色吗?' : '确定要禁用该角色吗';
  ElMessageBox.confirm(text, "警告", {
    type: 'warning'
  }).then(() => {
    post('/nurselevel/del', { id, status }, content => {
      getTableData();
    });
  }).catch(() => {});
}

// 设置护理内容
function setup(id) {
  router.push({
    path: '/levelcontent',
    query: { id: id }
  });
}
</script>

<style scoped>
.nurse-level-container {
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