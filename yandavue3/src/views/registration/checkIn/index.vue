<template>
  <div class="checkin-container">
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
      <el-button type="primary" plain @click="add" class="add-btn">
        登记
      </el-button>
    </div>
    
    <!-- 老人类型筛选 -->
    <!--
	<div class="elder-type-filter">
      <el-button type="primary" plain @click="elder0">活力老人</el-button>
      <el-button type="primary" plain @click="elder1">自理老人</el-button>
      <el-button type="primary" plain @click="elder2">护理老人</el-button>
    </div>
	-->
    
    <!-- 客户信息表格 -->
    <el-table :data="tableData.records" style="width: 100%" stripe border>
      <el-table-column width="60" label="序号" prop="id" align="center" />
      <el-table-column width="100" label="客户姓名" prop="customername" align="center" />
      <el-table-column width="70" label="性别" align="center">
        <template #default="scope">
          <span v-if="scope.row.customersex === 1">男</span>
          <span v-else>女</span>
        </template>
      </el-table-column>
      <el-table-column width="70" label="年龄" prop="customerage" align="center" />
      <el-table-column width="180" label="身份证号" prop="idcard" align="center" />
      <!--
	  <el-table-column width="100" label="老人类型" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.eldertype === 0" type="success">活力老人</el-tag>
          <el-tag v-else-if="scope.row.eldertype === 1">自理老人</el-tag>
          <el-tag v-else type="warning">护理老人</el-tag>
        </template>
      </el-table-column>
	  -->
      <el-table-column width="90" label="房间号" prop="roomid" align="center" />
      <el-table-column width="100" label="所属楼房" prop="buildingid" align="center" />
      <el-table-column width="90" label="档案号" prop="recordid" align="center" />
      <el-table-column width="120" label="入住时间" prop="checkindate" align="center" />
      <el-table-column width="120" label="合同到期时间" prop="expirationdate" align="center" />
      <el-table-column width="120" label="联系电话" prop="contacttel" align="center" />
      <el-table-column width="100" label="备注" prop="remarks" align="center" />
      <el-table-column width="100" label="当前状态" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.delflag" type="success">启用</el-tag>
          <el-tag v-else type="danger">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="护理级别" prop="nursingLevel" align="center" />
      <el-table-column label="护理内容" align="center" width="120">
        <template #default="scope">
          <el-button 
            type="success" 
            size="small" 
            plain 
            :disabled="isButtonDisabled(scope.row.delflag)" 
            @click="setup(scope.row.id)"
          >
            设置
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="护理记录" align="center" width="120">
        <template #default="scope">
          <el-button 
            type="primary" 
            size="small" 
            plain 
            :disabled="isButtonDisabled(scope.row.delflag)" 
            @click="addrecord(scope.row.id, scope.row.customername)"
          >
            添加
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template #default="scope">
          <template v-if="scope.row.delflag">
            <el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
            <el-button type="danger" plain size="small" @click="del(scope.row.id, 0, scope.row.eldertype)">删除</el-button>
          </template>
          <el-button v-else type="warning" plain size="small" @click="del(scope.row.id, 1, scope.row.eldertype)">启用</el-button>
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
    
    <el-dialog v-model="dia.show" :title="dia.title" width="450px" :close-on-click-modal="false">
      <CustomSetup v-if="dia.show" v-model:show="dia.show" @getTableData="getTableData" :id="dia.id" />
    </el-dialog>
    
    <el-dialog v-model="log.show" :title="log.title" width="450px" :close-on-click-modal="false">
      <Record v-if="log.show" v-model:show="log.show" @getTableData="getTableData" :id="log.id" :name="log.name" />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ElMessageBox } from 'element-plus';
import { Search } from '@element-plus/icons-vue';
import { get, post } from '@/axios';
import Add from './add.vue';  // 确保文件扩展名正确
import CustomSetup from './setup.vue'; // 确保文件扩展名正确
import Record from './rd.vue'; // 确保文件扩展名正确

// 对话框状态
const dialog = reactive({
  show: false,
  title: '',
  id: null
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

// 请求参数
const params = reactive({
  pageNo: 1,
  pageSize: 9,
  customername: ''
});

// 老人类型筛选参数
const elderparams = reactive({
  pageNo: 1,
  pageSize: 9,
  eldertype: ""
});

// 获取表格数据
function getTableData() {
  get('/checkIn/list', params, content => {
    tableData.records = content.records;
    tableData.pages = content.pages;
    tableData.total = content.total;
  });
}

getTableData();

// 搜索
function search() {
  getTableData();
}

// 添加客户
function add() {
  dialog.title = '入住登记';
  dialog.id = null;
  dialog.show = true;
}

// 修改客户
function update(id) {
  dialog.title = '修改客户信息';
  dialog.id = id;
  dialog.show = true;
}

// 删除/启用客户
function del(id, delflag, eldertype) {
  const text = delflag ? '确定要启用该客户吗' : '确定要禁用该客户吗';
  ElMessageBox.confirm(text, "警告", {
    type: 'warning'
  }).then(() => {
    post('/checkIn/del', { id, delflag }, content => {
      // 根据老人类型重新加载数据
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
</script>

<style scoped>
.checkin-container {
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

.elder-type-filter {
  margin-bottom: 20px;
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