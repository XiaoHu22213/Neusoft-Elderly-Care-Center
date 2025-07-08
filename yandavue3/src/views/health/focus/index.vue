<template>
  <div class="nursing-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-input
        v-model="params.customername"
        placeholder="搜索客户"
        class="search-input"
        clearable
      >
        <template #append>
          <el-button :icon="Search" @click="search" />
        </template>
      </el-input>
    </div>
    
    <div class="content-wrapper">
      <!-- 左侧客户列表 -->
      <div class="customer-list">
        <el-table 
          :data="tableData.records" 
          @row-click="handleRowClick"
          stripe
          border
          style="width: 100%"
        >
          <el-table-column width="60" label="序号" prop="id" align="center" />
          <el-table-column width="100" label="客户姓名" prop="customername" align="center" />
          <el-table-column width="70" label="性别" align="center">
            <template #default="scope">
              <span v-if="scope.row.customersex === 1">男</span>
              <span v-else>女</span>
            </template>
          </el-table-column>
          <el-table-column width="70" label="年龄" prop="customerage" align="center" />
          <el-table-column width="120" label="老人类型" align="center">
            <template #default="scope">
              <span v-if="scope.row.eldertype===0">活力老人</span>
              <span v-else-if="scope.row.eldertype===1">自理老人</span>
              <span v-else>护理老人</span>
            </template>
          </el-table-column>
          <el-table-column label="护理级别" prop="nursingLevel" align="center" />
        </el-table>
        
        <el-pagination
          class="pagination"
          background
          v-model:current-page="params.pageNo"
          :page-size="params.pageSize"
          :total="tableData.total"
          layout="prev, pager, next, jumper, total"
          @current-change="getTableData"
        />
      </div>
      
      <!-- 右侧护理内容详情 -->
      <div class="nursing-details">
        <el-table :data="mxData" stripe border style="width: 100%">
          <el-table-column label="护理内容" prop="nursecontent" align="center" />
          <el-table-column label="上期剩余数量" prop="lastn" align="center" />
          <el-table-column label="购买数量" prop="buy" align="center" />
          <el-table-column label="总数量" prop="sum" align="center" />
          <el-table-column label="本期剩余数量" prop="leftn" align="center" />
          <el-table-column label="购买时间" prop="time" align="center" />
          <el-table-column label="备注" prop="memo" align="center" />
          <el-table-column label="服务状态" align="center">
            <template #default="scope">
              <el-tag v-if="scope.row.leftn < 0" type="danger">已欠费</el-tag>
              <el-tag v-else-if="scope.row.leftn < 6" type="warning">即将用完</el-tag>
              <el-tag v-else type="success">正常使用</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="200" align="center">
            <template #default="scope">
              <el-button 
                type="primary" 
                size="small" 
                plain 
                v-if="scope.row.leftn < 6"
                @click="update(scope.row.cuid, scope.row.cid)"
              >
                购买
              </el-button>
              <el-button 
                type="danger" 
                size="small" 
                plain 
                v-if="scope.row.leftn < 6"
                @click="del(scope.row.id, 0)"
              >
                立即提醒
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    
    <!-- 弹窗组件 -->
    <el-dialog v-model="dialog.show" :title="dialog.title" width="450px" :close-on-click-modal="false">
      <Add 
        v-if="dialog.show" 
        @getTableData="getTableData" 
        v-model:show="dialog.show" 
        :cuid="dialog.cuid" 
        :cid="dialog.cid" 
      />
    </el-dialog>
    
    <el-dialog v-model="dia.show" :title="dia.title" width="450px" :close-on-click-modal="false">
      <CustomSetup 
        v-if="dia.show" 
        v-model:show="dia.show" 
        @getTableData="getTableData" 
        :id="dia.id" 
      />
    </el-dialog>
    
    <el-dialog v-model="log.show" :title="log.title" width="450px" :close-on-click-modal="false">
      <Record 
        v-if="log.show" 
        v-model:show="log.show" 
        @getTableData="getTableData" 
        :id="log.id" 
        :name="log.name" 
      />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ElMessageBox } from 'element-plus';
import { Search } from '@element-plus/icons-vue';
import { get, post } from '@/axios';
import Add from './add.vue';
import CustomSetup from './setup.vue';
import Record from './rd.vue';

// 对话框状态
const dialog = reactive({
  show: false,
  title: '',
  cuid: null,
  cid: null
});

const dia = reactive({
  show: false,
  title: '',
  id: null
});

const log = reactive({
  show: false,
  title: '',
  id: null,
  name: ''
});

// 表格数据
const tableData = reactive({
  records: [],
  pages: 0,
  total: 0
});

const mxData = ref([]);

// 请求参数
const params = reactive({
  pageNo: 1,
  pageSize: 9,
  customername: ''
});

const elderparams = reactive({
  pageNo: 1,
  pageSize: 9,
  eldertype: ""
});

// 获取表格数据
function getTableData() {
  get('/checkIn/getlist', params, content => {
    tableData.records = content.records;
    tableData.pages = content.pages;
    tableData.total = content.total;
    if (tableData.records.length > 0) {
      getmxData(tableData.records[0].id);
    }
  });
}

function getmxData(id) {
  get('/customcontent/list', { id }, content => {
    mxData.value = content;
  });
}

// 初始化获取数据
getTableData();

// 搜索功能
function search() {
  getTableData();
}

// 添加客户
function add() {
  dialog.title = '入住登记';
  dialog.id = null;
  dialog.show = true;
}

// 修改客户/购买服务
function update(cuid, cid) {
  dialog.title = '购买当前该服务';
  dialog.cuid = cuid;
  dialog.cid = cid;
  dialog.show = true;
}

// 删除/启用客户
function del(id, delflag, eldertype) {
  const text = delflag ? '确定要启用该客户吗' : '确定要禁用该客户吗';
  ElMessageBox.confirm(text, "警告", {
    type: 'warning'
  }).then(() => {
    post('/checkIn/del', { id, delflag }, content => {
      if (eldertype === 0) elder0();
      else if (eldertype === 1) elder1();
      else if (eldertype === 2) elder2();
    });
  }).catch(() => {});
}

// 老人类型筛选
function elder0() {
  elderparams.eldertype = 0;
  getElderData();
}

function elder1() {
  elderparams.eldertype = 1;
  getElderData();
}

function elder2() {
  elderparams.eldertype = 2;
  getElderData();
}

function getElderData() {
  get('/checkIn/elderlist', elderparams, content => {
    tableData.records = content.records;
    tableData.pages = content.pages;
    tableData.total = content.total;
  });
}

// 设置护理内容
function setup(id) {
  dia.title = '修改执行周期、执行次数';
  dia.id = id;
  dia.show = true;
}

// 添加护理记录
function addrecord(id, name) {
  log.title = '添加护理记录';
  log.id = id;
  log.name = name;
  log.show = true;
}

// 判断按钮是否禁用
function isButtonDisabled(delflag) {
  return !delflag;
}

// 处理行点击事件
function handleRowClick(row) {
  getmxData(row.id);
}
</script>

<style scoped>
.nursing-container {
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

.content-wrapper {
  display: flex;
  gap: 20px;
}

.customer-list {
  flex: 1;
  min-width: 400px;
}

.nursing-details {
  flex: 2;
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