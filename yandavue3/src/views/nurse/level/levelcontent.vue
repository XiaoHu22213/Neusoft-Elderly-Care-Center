<template>
  <div class="nursing-level-content-container">
    <!-- 顶部操作栏 -->
    <div class="operation-bar">
      <el-button type="primary" plain @click="returnlevel" class="action-btn">
        返回
      </el-button>
      <el-button type="primary" plain @click="lcadd(params.id)" class="action-btn">
        添加
      </el-button>
    </div>
    
    <!-- 护理等级内容表格 -->
    <el-table :data="tableData.records" style="width: 100%" stripe border>
      <el-table-column width="120" label="护理内容编号" prop="cid" align="center" />
      <el-table-column label="护理内容" prop="nursecontent" align="center" />
      <el-table-column width="120" label="执行周期" prop="executecycle" align="center" />
      <el-table-column width="120" label="执行次数" prop="executenub" align="center" />
      <el-table-column width="100" label="排序号" prop="sort" align="center" />
      <el-table-column label="备注" prop="memo" align="center" />
      <el-table-column label="操作" width="200" align="center">
        <template #default="scope">
          <el-button 
            type="primary" 
            size="small" 
            @click="update(scope.row.cid, params.id)"
          >
            修改
          </el-button>
          <el-button 
            type="danger" 
            size="small" 
            @click="del(scope.row.cid, params.id)"
          >
            删除
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
    
    <!-- 添加/修改弹窗 -->
    <el-dialog
      v-model="dialog.show"
      :title="dialog.title"
      width="450px"
      :close-on-click-modal="false"
    >
      <Lcadd 
        v-if="dialog.show"
        v-model:show="dialog.show"
        @getTableData="getTableData"
        :id="dialog.lid"
        :ccid="dialog.cid"
      />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ElMessageBox } from 'element-plus';
import { get, post } from '@/axios/axios';
import { useRoute, useRouter } from 'vue-router';
import Lcadd from './lcadd.vue';

const router = useRouter();
const route = useRoute();

// 对话框状态
const dialog = reactive({
  show: false,
  title: '',
  lid: null,
  cid: null
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
  id: route.query.id
});

// 获取表格数据
function getTableData() {
  get('/lccontrast/list', params, content => {
    tableData.value = content;
  });
}

getTableData();

// 返回护理等级页面
function returnlevel() {
  router.push('/level');
}

// 添加护理等级内容
function lcadd(id) {
  dialog.title = '添加护理等级内容';
  dialog.lid = id;
  dialog.cid = null;
  dialog.show = true;
}

// 修改护理内容
function update(cid, lid) {
  dialog.title = '修改护理内容';
  dialog.lid = lid;
  dialog.cid = cid;
  dialog.show = true;
}

// 删除护理内容
function del(cid, lid) {
  const text = '确定要删除该内容吗?';
  ElMessageBox.confirm(text, "警告", {
    type: 'warning'
  }).then(() => {
    post('/lccontrast/del', { cid, lid }, content => {
      getTableData();
    });
  }).catch(() => {});
}
</script>

<style scoped>
.nursing-level-content-container {
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

.action-btn {
  margin-right: 15px;
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