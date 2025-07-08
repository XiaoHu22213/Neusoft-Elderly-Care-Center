<template>
  <div class="nursing-content-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-button type="primary" plain @click="add" class="add-btn">
        添加
      </el-button>
    </div>
    
    <!-- 护理内容表格 -->
    <el-table :data="tableData.records" style="width: 100%" stripe border>
      <el-table-column width="80" label="编号" prop="id" align="center" />
      <el-table-column label="护理内容" prop="nursecontent" align="center" />
      <el-table-column label="描述" prop="cdescribe" align="center" />
      <el-table-column width="120" label="价格" prop="price" align="center" />
      <el-table-column width="120" label="备注" align="center">
        <template #default="scope">
          <el-button 
            type="success" 
            size="small" 
            plain 
            @click="setup(scope.row.memo, scope.row.id)"
          >
            查看
          </el-button>
        </template>
      </el-table-column>
      <el-table-column width="100" label="状态" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.status === 1" type="success">启用</el-tag>
          <el-tag v-else type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template #default="scope">
          <template v-if="scope.row.status">
            <el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
            <el-button type="danger" plain size="small" @click="del(scope.row.id, 0)">禁用</el-button>
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
    
    <!-- 备注详情弹窗 -->
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
    
    <!-- 添加/修改弹窗 -->
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

// 请求参数
const params = reactive({
  pageNo: 1,
  pageSize: 10,
  name: ''
});

// 对话框状态
const dialog = reactive({
  show: false,
  title: '',
  id: null
});

const showDialog = ref(false);
const remark = reactive({
  memo: '',
  id: ''
});

// 获取表格数据
function getTableData() {
  get('/nursecontent/list', params, content => {
    tableData.value = content;
  });
}

getTableData();

// 查看备注
function setup(memo, id) {
  remark.memo = memo;
  remark.id = id;
  showDialog.value = true;
}

// 添加护理内容
function add() {
  dialog.title = '添加护理内容';
  dialog.id = null;
  dialog.show = true;
}

// 修改护理内容
function update(id) {
  dialog.title = '修改护理内容';
  dialog.id = id;
  dialog.show = true;
}

// 启用/禁用
function del(id, status) {
  const text = status ? '确定要启用该内容吗?' : '确定要禁用该内容吗';
  ElMessageBox.confirm(text, "警告", {
    type: 'warning'
  }).then(() => {
    post('/nursecontent/del', { id, status }, content => {
      getTableData();
    });
  }).catch(() => {});
}
</script>

<style scoped>
.nursing-content-container {
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