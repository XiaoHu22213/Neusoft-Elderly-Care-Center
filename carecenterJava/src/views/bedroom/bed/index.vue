<template>
  <div>
	
	  <div class="btns">
	        <el-button type="primary" plain :icon="User" @click="add">添加床位</el-button>
	  	  <el-input
	  	    v-model="params.name"
	  	    style="max-width: 300px;margin-left: 20px;"
	  	    placeholder="搜索"
	  	    class="input-with-select"
	  	  >
	  	    <template #append>
	  	      <el-button :icon="Search" @click="search"/>
	  	    </template>
	  	  </el-input>
		  </div>

    <el-table  style="width: 100%" :data ="tableData.records">
      <el-table-column prop="bedid" label="床位编号" width="180"></el-table-column>
      <el-table-column prop="peoplename" label="入住人姓名" width="180"></el-table-column>
      <el-table-column  label="状态" width="180">
		  <template #default="scope">
			  <el-tag type="primary" v-if="scope.row.status == '占用' ">占用</el-tag>
              <el-tag type="success" v-if="scope.row.status == '空闲' ">空闲</el-tag>
              <el-tag type="danger"  v-if="scope.row.status == '离席' ">离席</el-tag>
		  </template>
	  </el-table-column>
	  <el-table-column  >
		  <template #default="scope">
			  <!-- <el-button  type="primary"color="rgba(0, 0, 225, 0.5)" plain size="small" @click="update(scope.row.id)">
				  修改
			  </el-button> -->
			  <el-button  type="success" v-if="scope.row.status==='离席'" plain size="small" @click="addstatus(scope.row.id)" >
			  				 归来
			  </el-button >
				  <el-button  type="success" v-if="scope.row.status==='离席'" plain size="small" @click="delstatus(scope.row.id)" >
				  				 清空
				  </el-button >
			  <el-button  type="warning" v-if="scope.row.status==='占用'" plain size="small" @click="del(scope.row.bedid,scope.row.peoplename)" >
				  离席
			  </el-button >
				  <el-button  type="success" v-if="!scope.row.peopleid" plain size="small" @click="update(scope.row.id)" >
				  				 添加客户
				  </el-button >
				  <el-button  type="danger" v-if="!scope.row.peopleid" plain size="small" @click="beddel(scope.row.id)" >
				  				  删除床位
				  </el-button >
		  </template>
	  </el-table-column>
    </el-table>
	<el-pagination style="margin-top: 10px;" background 
	 v-model:current-page="params.pageNo" 
	 :page-count="tableData.pages"
	 :total="tableData.total"
	 @current-change="getTableData"/>
	<el-dialog v-model ="dialog.show" :title="dialog.title" width="450px" >
    <Add 
	v-if="dialog.show"
	  v-model:show="dialog.show"  
	  @getTableData= "getTableData"
	  :id = "dialog.id"/>
	</el-dialog>
	<el-dialog v-model ="dia.show" :title="dia.title" width="450px" >
	<Outin
	v-if="dia.show"
	  v-model:show="dia.show"  
	  @getTableData= "getTableData"
	  :bedid = "dia.bedid"
	  :peoplename = "dia.peoplename"
	  />
	</el-dialog>
  </div>
</template>

<script setup>
import { User } from '@element-plus/icons-vue';
import { reactive, ref,  } from 'vue';
import { request,get,post } from '@/axios';
import Add from './add.vue';
import { ElMessageBox } from 'element-plus';
import Outin from'./outin.vue'
import { CirclePlus, Search } from '@element-plus/icons-vue'
const dialog = reactive({
	show:false,
	title:'',
	id:null
})
const dia = reactive({
	show:false,
	title:'',
	bedid:null,
	peoplename:null
})
const tableData = reactive({
	records:[],
	pages:0,
	total:0
})
const params = reactive({
	pageNo:1,
	pageSize:20,
	name:null
})








function update(id)
{
	dialog.show = true
	dialog.title ='修改床位'
	dialog.id = id
}
function add(){
	dialog.show = true
	dialog.title ='添加床位'
	dialog.id = null
}
function getTableData(){
	get('/bedroom/list',params,content=>{
		tableData.records = content.records
		tableData.pages = content.pages
		tableData.total = content.pages
		console.dir(content)
	})
}
function del(bedid,peoplename) {
	console.dir(bedid)
	console.dir(peoplename)
 dia.show=true
 dia.title='离席记录'
 dia.bedid=bedid
 dia.peoplename=peoplename
}
function addstatus(id)
{

	post("/bedroom/addstatus", {id}, content => {
		
	 getTableData(); // 重新获取数据
	})
}
function delstatus(id)
{

	post("/bedroom/delstatus", {id}, content => {
		
	 getTableData(); // 重新获取数据
	})
}
function beddel(id) {
  ElMessageBox.confirm('确定要删除该床位吗？', '警告', {
    type: 'warning'
  }).then(() => {
    request(`/bedroom/deletebed/${id}`, 'delete', {}, content => {
      
      getTableData(); // 重新获取数据
	
    });
  }).catch(() => {
    // 用户取消删除操作
  });
}
//request('/bedroom/list','get',{},content=>{console.dir(content)})
getTableData()
function search(){
getTableData()
}
</script>

<style scoped lang="scss">
// Your styles here
</style>
