<template>
  <div class="permission-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-button 
        type="primary" 
        plain 
        :icon="Guanliyuan" 
        @click="add(0, 1)" 
        class="add-btn"
      >
        添加权限
      </el-button>
    </div>
    
    <!-- 权限信息表格 -->
    <el-table 
      :data="tableData" 
      row-key="id" 
      style="width: 100%" 
      stripe 
      border
    >
      <el-table-column 
        width="200" 
        label="权限名称" 
        prop="name" 
        align="center"
      ></el-table-column>
      
      <el-table-column 
        label="前端地址" 
        prop="frontUrl" 
        align="center"
      ></el-table-column>
      
      <el-table-column 
        show-overflow-tooltip 
        label="后端地址" 
        prop="backUrl" 
        align="center"
      ></el-table-column>
      
      <el-table-column 
        label="类型" 
        width="120" 
        align="center"
      >
        <template #default="scope">
          <el-tag type="success" v-if="scope.row.type === 1">目录</el-tag>
          <el-tag type="danger" v-else>按钮</el-tag>
        </template>
      </el-table-column>
      
      <el-table-column 
        label="操作" 
        width="220" 
        align="center"
      >
        <template #default="scope">
          <el-button 
            v-if="scope.row.type === 1" 
            plain 
            size="small" 
            @click="add(scope.row.id, scope.row.level + 1)"
          >
            添加下级
          </el-button>
          <el-button 
            type="primary" 
            plain 
            size="small" 
            @click="update(scope.row.id)"
          >
            修改
          </el-button>
          <el-button 
            type="danger" 
            plain 
            size="small" 
            @click="del(scope.row.id)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    
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
        :parentId="dialog.parentId"
        :level="dialog.level"
        :id="dialog.id"
      />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ElMessageBox } from 'element-plus';
import { get, post } from '@/axios';
import Guanliyuan from '@/components/icons/guanliyuan';
import Add from './add.vue';
import url from './util';

// 对话框状态
const dialog = reactive({
  show: false,
  title: '',
  parentId: null,
  level: null,
  id: null
});

// 表格数据
const tableData = ref([]);

// 请求参数
const params = reactive({});

// 获取表格数据
function getTableData() {
  get(url.list, params, content => {
    tableData.value = content;
  });
}

getTableData();

// 添加权限
function add(parentId, level) {
  dialog.title = '添加权限';
  dialog.id = null;
  dialog.parentId = parentId;
  dialog.level = level;
  dialog.show = true;
}

// 修改权限
function update(id) {
  dialog.title = '修改权限';
  dialog.id = id;
  dialog.parentId = null;
  dialog.level = null;
  dialog.show = true;
}

// 删除权限
function del(id) {
  ElMessageBox.confirm('删除之后无法恢复，确定要删除该权限吗？', '警告', {
    type: 'warning'
  }).then(() => {
    post(url.del, { id }, content => {
      getTableData();
    });
  }).catch(() => {});
}
</script>

<style scoped>
.permission-container {
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