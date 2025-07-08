<template>
  <div class="checkout-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-input
        v-model="params.customername"
        placeholder="客户姓名"
        class="search-input"
        clearable
      >
        <template #append>
          <el-button :icon="Search" @click="search" />
        </template>
      </el-input>
      <el-button type="primary" plain @click="out" class="add-btn">
        <i class="fas fa-user-minus"></i> 办理退住
      </el-button>
    </div>
    
    <!-- 统计卡片 -->
    <div class="statistics">
      <div class="stat-card">
        <div class="stat-icon icon-1">
          <i class="fas fa-file-contract"></i>
        </div>
        <div class="stat-content">
          <div class="stat-title">待审核退住申请</div>
          <div class="stat-value">8</div>
        </div>
      </div>
      
      <div class="stat-card">
        <div class="stat-icon icon-2">
          <i class="fas fa-user-clock"></i>
        </div>
        <div class="stat-content">
          <div class="stat-title">本月退住人数</div>
          <div class="stat-value">12</div>
        </div>
      </div>
      
      <div class="stat-card">
        <div class="stat-icon icon-3">
          <i class="fas fa-bed"></i>
        </div>
        <div class="stat-content">
          <div class="stat-title">空出床位</div>
          <div class="stat-value">24</div>
        </div>
      </div>
      
      <div class="stat-card">
        <div class="stat-icon icon-4">
          <i class="fas fa-check-circle"></i>
        </div>
        <div class="stat-content">
          <div class="stat-title">已处理申请</div>
          <div class="stat-value">32</div>
        </div>
      </div>
    </div>
    
    <!-- 客户信息表格 -->
    <el-table :data="tableData.records" style="width: 100%" stripe border>
      <el-table-column width="60" label="序号" prop="id" align="center"></el-table-column>
      <el-table-column width="100" label="客户姓名" prop="customername" align="center"></el-table-column>
      <el-table-column width="70" label="性别" align="center">
        <template #default="scope">
          <span v-if="scope.row.customersex === 1">男</span>
          <span v-else>女</span>
        </template>
      </el-table-column>
      <el-table-column width="70" label="年龄" prop="customerage" align="center"></el-table-column>
      <el-table-column width="100" label="档案号" prop="recordid" align="center"></el-table-column>
      <el-table-column width="120" label="入住时间" prop="checkindate" align="center"></el-table-column>
      <el-table-column width="120" label="退住时间" prop="checkoutdate" align="center"></el-table-column>
      <el-table-column width="120" label="退住类型" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.checkouttype===0" type="success">正常退住</el-tag>
          <el-tag v-else-if="scope.row.checkouttype===1" type="danger">死亡退住</el-tag>
          <el-tag v-else type="warning">保留床位</el-tag>
        </template>
      </el-table-column>
      <el-table-column width="150" label="退住原因" prop="checkoutreason" align="center"></el-table-column>
      <el-table-column width="100" label="状态" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.status===0" type="warning">待审核</el-tag>
          <el-tag v-else-if="scope.row.status===1" type="success">通过</el-tag>
          <el-tag v-else-if="scope.row.status===2" type="danger">不通过</el-tag>
          <el-tag v-else type="info">撤销</el-tag>
        </template>
      </el-table-column>
      <el-table-column width="120" label="申请时间" prop="asktime" align="center"></el-table-column>
      <el-table-column width="120" label="审核意见" prop="auditopinion" align="center"></el-table-column>
      <el-table-column width="90" label="审核人" prop="auditperson" align="center"></el-table-column>
      <el-table-column width="120" label="审核时间" prop="audittime" align="center"></el-table-column>
      <el-table-column width="120" label="备注" prop="remarks" align="center"></el-table-column>
      <el-table-column width="100" label="当前状态" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.delflag" type="success">启用</el-tag>
          <el-tag v-else type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="280" align="center">
        <template #default="scope">
          <div class="action-cell">
            <template v-if="scope.row.delflag">
              <el-button type="primary" plain size="small" @click="update(scope.row.id, scope.row.recordid)">
                <i class="fas fa-edit"></i> 修改
              </el-button>
              <el-button type="danger" plain size="small" @click="del(scope.row.id, 0)">
                <i class="fas fa-ban"></i> 禁用
              </el-button>
              <el-button type="success" plain size="small" @click="audit(scope.row.id)">
                <i class="fas fa-check-circle"></i> 审核
              </el-button>
            </template>
            <el-button v-else type="warning" plain size="small" @click="del(scope.row.id, 1)">
              <i class="fas fa-check"></i> 启用
            </el-button>
          </div>
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
    
    <!-- 退住办理弹窗 -->
    <el-dialog v-model="dialog.show" :title="dialog.title" width="450px" :close-on-click-modal="false">
      <Out v-if="dialog.show" @getTableData="getTableData" v-model:show="dialog.show" :id="dialog.id" :recordid="dialog.recordid"/>
    </el-dialog>
    
    <!-- 审核弹窗 -->
    <el-dialog v-model="auditdialog.show" :title="auditdialog.title" width="450px" :close-on-click-modal="false">
      <Audit v-if="auditdialog.show" @getTableData="getTableData" v-model:show="auditdialog.show" :id="auditdialog.id"/>
    </el-dialog>	
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ElMessageBox } from 'element-plus';
import { Search } from '@element-plus/icons-vue';
import { get, post } from '@/axios';
import Out from './out.vue';
import Audit from './audit.vue';

// 对话框状态
const dialog = reactive({
  show: false,
  title: '',
  id: null,
  recordid: ''
});

const auditdialog = reactive({
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
  pageSize: 9,
  customername: ''
});

// 获取表格数据
function getTableData() {
  get('/checkIn/checkoutlist', params, content => {
    tableData.records = content.records;
    tableData.pages = content.pages;
    tableData.total = content.total;
  });
}

// 初始化时获取数据
getTableData();

// 搜索
function search() {
  getTableData();
}

// 办理退住
function out() {
  dialog.title = '退住办理';
  dialog.id = null;
  dialog.recordid = '';
  dialog.show = true;
}

// 修改退住信息
function update(id, recordid) {
  dialog.title = '修改退住信息';
  dialog.id = id;
  dialog.recordid = recordid;
  dialog.show = true;
}

// 审核
function audit(id) {
  auditdialog.title = '审核';
  auditdialog.id = id;
  auditdialog.show = true;
}

// 删除/启用
function del(id, delflag) {
  const text = delflag ? '确定要启用该客户吗' : '确定要禁用该客户吗';
  ElMessageBox.confirm(text, "警告", {
    type: 'warning'
  }).then(() => {
    post('/checkIn/del', { id, delflag }, content => {
      getTableData();
    });
  }).catch(() => {});
}
</script>

<style scoped>
.checkout-container {
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

/* 统计卡片样式 */
.statistics {
  display: flex;
  gap: 20px;
  margin-bottom: 25px;
  flex-wrap: wrap;
}

.stat-card {
  flex: 1;
  min-width: 200px;
  background: white;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  display: flex;
  align-items: center;
  gap: 15px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: white;
}

.stat-content {
  flex: 1;
}

.stat-title {
  font-size: 14px;
  color: #666;
  margin-bottom: 5px;
}

.stat-value {
  font-size: 28px;
  font-weight: 700;
  color: #0d4a9e;
}

.icon-1 { background: linear-gradient(135deg, #1a6dcc 0%, #0d4a9e 100%); }
.icon-2 { background: linear-gradient(135deg, #00c6ff 0%, #0072ff 100%); }
.icon-3 { background: linear-gradient(135deg, #5dceaf 0%, #2a9d8f 100%); }
.icon-4 { background: linear-gradient(135deg, #ff9a9e 0%, #fad0c4 100%); }

/* 美化标签样式 */
.el-tag {
  font-weight: 500;
}

/* 操作按钮间距 */
.action-cell {
  display: flex;
  gap: 8px;
  justify-content: center;
}

/* 添加Font Awesome图标 */
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css');
</style>