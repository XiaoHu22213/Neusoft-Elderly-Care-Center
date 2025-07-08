<template>
  <div class="service-container">
    <!-- 顶部搜索栏 -->
    <div class="operation-bar">
      <el-input
        v-model="searchName"
        placeholder="搜索管家姓名"
        class="search-input"
        clearable
      >
        <template #append>
          <el-button :icon="Search" @click="search" />
        </template>
      </el-input>
    </div>
    
    <!-- 管家服务表格 -->
    <el-table 
      :data="filteredData" 
      style="width: 100%" 
      stripe 
      border
    >
      <el-table-column label="序号" prop="id" width="80" align="center"></el-table-column>
      <el-table-column label="管家姓名" prop="name" width="120" align="center"></el-table-column>
      <el-table-column label="联系电话" prop="phone" width="150" align="center"></el-table-column>
      <el-table-column label="服务楼层" prop="floor" width="120" align="center"></el-table-column>
      <el-table-column label="备注" prop="notes" show-overflow-tooltip></el-table-column>
      <el-table-column label="操作时间" prop="time" width="180" align="center"></el-table-column>
      
      <el-table-column label="操作" width="220" align="center">
        <template #default="scope">
          <template v-if="scope.row.status">
            <el-button 
              v-if="!scope.row.Sid" 
              type="primary" 
              plain 
              size="small" 
              @click="set(scope.row.id)"
            >
              设置
            </el-button>
            <el-button 
              v-if="scope.row.Sid" 
              type="primary" 
              plain 
              size="small" 
              @click="update(scope.row.Sid)"
            >
              修改
            </el-button>
            <el-button 
              v-if="scope.row.Sid" 
              type="danger" 
              plain 
              size="small" 
              @click="del(scope.row.Sid, 0)"
            >
              删除
            </el-button>
          </template>
          <el-button 
            v-else 
            type="warning" 
            plain 
            size="small" 
            @click="del(scope.row.Sid, 1)"
          >
            启用
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
    
    <!-- 设置服务弹窗 -->
    <el-dialog
      v-model="dialog.show"
      :title="dialog.title"
      width="450px"
      :close-on-click-modal="false"
    >
      <Set
        v-if="dialog.show"
        @getTableData="getTableData"
        v-model:show="dialog.show"
        :id="dialog.id"
      />
    </el-dialog>
    
    <!-- 修改服务弹窗 -->
    <el-dialog
      v-model="dialog2.show"
      :title="dialog2.title"
      width="450px"
      :close-on-click-modal="false"
    >
      <Update
        v-if="dialog2.show"
        @getTableData="getTableData"
        v-model:show="dialog2.show"
        :id="dialog2.id"
      />
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue';
import { ElMessageBox } from 'element-plus';
import { Search } from '@element-plus/icons-vue';
import { get, post } from '@/axios';
import Set from './set.vue';
import Update from './update.vue';
import url from './util';

// 对话框状态
const dialog = reactive({
  show: false,
  title: '',
  id: null
});

const dialog2 = reactive({
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
  pageSize: 10,
});

// 服务数据
const serviceData = ref([]);
const userData = ref([]);
const searchName = ref('');
const model = ref(true);

// 获取表格数据
function getTableData() {
  get('/user/list', params, content => {
    tableData.records = content.records;
    tableData.pages = content.pages;
    tableData.total = content.total;
  });
}

// 获取服务数据
function getserviceData() {
  get('/servicetargets/type', null, content => {
    serviceData.value = content;
  });
}

// 获取用户数据
function getuserData() {
  get('/user/type', null, content => {
    userData.value = content;
  });
}

// 计算属性 - 管家数据
const Housekeep = computed(() => {
  return userData.value.filter(item => item.type === '管家');
});

// 计算属性 - 合并后的数据
const mergedData = computed(() => {
  return Housekeep.value.map(housekeepItem => {
    const serviceItem = serviceData.value.find(serviceItem => serviceItem.name === housekeepItem.name);
    if (serviceItem) {
      return {
        ...housekeepItem,
        status: serviceItem.status,
        floor: serviceItem.floor,
        notes: serviceItem.notes,
        time: serviceItem.time,
        Sid: serviceItem.id,
      };
    }
    return housekeepItem;
  });
});

// 计算属性 - 过滤后的数据
const filteredData = computed(() => {
  return mergedData.value.filter(item => 
    item.name.includes(searchName.value)
  );
});

// 搜索功能
function search() {
  model.value = !model.value;
}

// 设置服务
function set(id) {
  dialog.title = '设置服务对象';
  dialog.id = id;
  dialog.show = true;
}

// 修改服务
function update(id) {
  dialog2.title = '修改服务';
  dialog2.id = id;
  dialog2.show = true;
}

// 删除/启用服务
function del(id, status) {
  const text = status ? '确定要启用该服务吗?' : '确定要禁用该服务吗';
  ElMessageBox.confirm(text, '警告', {
    type: 'warning'
  }).then(() => {
    post(url.del, { id, status }, content => {
      getTableData();
    });
  }).catch(() => {});
}

// 初始化数据
onMounted(() => {
  getTableData();
  getserviceData();
  getuserData();
});
</script>

<style scoped>
.service-container {
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