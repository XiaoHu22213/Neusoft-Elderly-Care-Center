<template>
  <div class="bus-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-button type="primary" plain @click="add" class="add-btn">
        添加班车
      </el-button>
    </div>
    
    <!-- 班车信息表格 -->
    <el-table :data="tableData.records" style="width: 100%" stripe border>
      <el-table-column label="编号" prop="id" width="100" align="center"></el-table-column>
      <el-table-column label="班车号" prop="name" width="120" align="center"></el-table-column>
      <el-table-column label="班车时间" prop="bustime" width="180" align="center"></el-table-column>
      <el-table-column label="班车路线" prop="route" show-overflow-tooltip></el-table-column>
      
      <el-table-column label="操作" width="220" align="center">
        <template #default="scope">
          <el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
          <el-button type="danger" plain size="small" @click="del(scope.row.id)">删除</el-button>
          <!-- <el-button type="success" plain size="small" @click="setup(scope.row.memo, scope.row.id)">设置</el-button> -->
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
    
    <!-- 备注设置弹窗 -->
    <el-dialog 
      v-model="showDialog" 
      title="详细备注" 
      width="450px"
      :close-on-click-modal="false"
    >
      <CustomSetup 
        v-if="showDialog" 
        v-model:show="showDialog" 
        v-model:memo="remark.memo" 
        v-model:id="remark.id"
        @getTableData="getTableData" 
      />
    </el-dialog>
    
    <!-- 添加/修改班车弹窗 -->
    <el-dialog 
      v-model="dialog.show" 
      :title="dialog.title" 
      width="450px"
      :close-on-click-modal="false"
    >
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
import { get, post } from '@/axios/axios';
import CustomSetup from './setup.vue';
import Add from './add.vue';

// 表格数据
const tableData = ref({
  records: [],
  pages: 0,
  total: 0
});

// 对话框状态
const dialog = reactive({
  show: false,
  title: '',
  id: null
});

// 备注设置状态
const showDialog = ref(false);
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
  get('/busroute/list', params, content => {
    tableData.value = content;
  });
}

getTableData();

// 设置备注
function setup(memo, id) {
  remark.memo = memo;
  remark.id = id;
  showDialog.value = true;
}

// 添加班车
function add() {
  dialog.title = '添加班车';
  dialog.id = null;
  dialog.show = true;
}

// 修改班车
function update(id) {
  dialog.title = '修改班车';
  dialog.id = id;
  dialog.show = true;
}

// 删除班车
function del(id) {
  ElMessageBox.confirm('确定要删除该列班车吗', '警告', {
    type: 'warning'
  }).then(() => {
    post('/busroute/del', { id }, content => {
      getTableData();
    });
  }).catch(() => {});
}
</script>

<style scoped>
.bus-container {
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

/* 操作按钮间距 */
.el-button + .el-button {
  margin-left: 8px;
}

/* 表格行样式 */
.el-table .cell {
  padding: 8px 0;
}
</style>