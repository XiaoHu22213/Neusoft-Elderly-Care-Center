<template>
  <div>
    <div class="btns">
		<el-input
		  v-model="params.name"
		  style="max-width: 300px;"
		  placeholder="请输入要搜索的人名"
		  class="input-with-select"
		>
		  <template #append>
		    <el-button :icon="Search" @click="search"/>
		  </template>
		</el-input>
		<el-button type="primary" style="margin-left: 30px;" plain @click="add">登记</el-button>
    </div>
    <el-table style="width: 100%" :data="tableData.records">
      <el-table-column prop="outtime" label="离席时间" width="180"></el-table-column>
      <el-table-column prop="intime" label="回来时间" width="180"></el-table-column>
      <el-table-column prop="outinname" label="人名" width="100"></el-table-column>
      <el-table-column prop="bednum" label="床号" width="100"></el-table-column>
      <el-table-column prop="thing" label="事由" width="100"></el-table-column>
     <!-- <el-table-column label="操作" width="180">
        <template #default="scope"> -->
  <!--        <el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button> -->
        <!--  <el-button type="danger" plain size="small" @click="del(scope.row.id)">删除</el-button> -->
      <!--  </template> -->
     <!-- </el-table-column> -->
    </el-table>
    <el-pagination style="margin-top: 10px;" background
      v-model:current-page="params.pageNo"
      :page-count="tableData.pages"
      :total="tableData.total"
      @current-change="getTableData"/>
    <el-dialog v-model="dialog.show" :title="dialog.title" width="450px">
      <Add 
        v-if="dialog.show"
        v-model:show="dialog.show"  
        @getTableData="getTableData"
        :id="dialog.id"/>
    </el-dialog>
  </div>
</template>

<script setup>
import { User } from '@element-plus/icons-vue';
import { CirclePlus, Search } from '@element-plus/icons-vue'
import { reactive, ref } from 'vue';
import { request, get } from '@/axios';
import Add from './add.vue';
import { ElMessageBox, ElMessage } from 'element-plus';

const dialog = reactive({
  show: false,
  title: '',
  id: ''
});

const tableData = reactive({
  records: [],
  pages: 0,
  total: 0
});

const params = reactive({
  pageNo: 1,
  pageSize: 20,
  name: ""
});

function update(id) {
  dialog.show = true;
  dialog.title = '修改离床记录';
  dialog.id = id;
}

function add() {
  dialog.show = true;
  dialog.title = '添加离床记录';
  dialog.id = null;
}

function getTableData() {
  get('/outin/list', params, content => {
    tableData.records = content.records;
    tableData.pages = content.pages;
    tableData.total = content.total;
    console.dir(content);
  });
}

function del(id) {
  ElMessageBox.confirm('确定要删除该记录吗？', '警告', {
    type: 'warning'
  }).then(() => {
    request(`/outin/delete/${id}`, 'delete', {}, content => {
      console.dir(content);
      getTableData(); // 重新获取数据
      ElMessage({
        type: 'success',
        message: '操作成功',
      });
    });
  }).catch(() => {
    // 用户取消删除操作
  });
}

getTableData();
function search() {
  getTableData();
}
</script>

<style scoped lang="scss">
// Your styles here
</style>
