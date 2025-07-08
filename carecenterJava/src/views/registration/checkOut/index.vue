<template>
  <div>
    <div class="btns">
		<el-input
		  v-model="params.customername"
		  style="max-width: 300px;"
		  placeholder="客户姓名"
		  class="input-with-select"
		>
		  <template #append>
		    <el-button :icon="Search" @click="search"/>
		  </template>
		</el-input>
		<el-button type="primary" style="margin-left: 30px;" plain @click="out">退住办理</el-button>
    </div>
<!--————————————————————————客户信息显示———————————————————————————-->
	<el-table :data="tableData.records">
	<el-table-column width="50px" label="序号" prop="id"></el-table-column>
			<el-table-column width="80px" label="客户姓名" prop="customername"></el-table-column>
			<el-table-column width="50px" label="性别" prop="customersex">
				<template #default="scope">
					<span v-if="scope.row.customersex===1">男</span>
					<span v-else>女</span>
				</template>
			</el-table-column>
			<el-table-column width="60px" label="年龄" prop="customerage"></el-table-column>
			<el-table-column width="70px" label="档案号" prop="recordid"></el-table-column>
			<el-table-column width="80px" label="入住时间" prop="checkindate"></el-table-column>
			<el-table-column width="80px" label="退住时间" prop="checkoutdate"></el-table-column>
			<el-table-column width="60px" label="退住类型" prop="checkouttype">
				<template #default="scope">
					<span v-if="scope.row.checkouttype===0">正常退住</span>
					<span v-else-if="scope.row.checkouttype===1">死亡退住</span>
					<span v-else>保留床位</span>
				</template>
			</el-table-column>
			<el-table-column width="80px" label="退住原因" prop="checkoutreason"></el-table-column>
			<el-table-column width="70px" label="状态" prop="status">
				<template #default="scope">
					<span v-if="scope.row.status===0">待审核</span>
					<span v-else-if="scope.row.status===1">通过</span>
					<span v-else-if="scope.row.status===2">不通过</span>
					<span v-else>撤销</span>
				</template>
			</el-table-column>
			<el-table-column width="80px" label="申请时间" prop="asktime"></el-table-column>
			<el-table-column width="80px" label="审核意见" prop="auditopinion"></el-table-column>
			<el-table-column width="60px" label="审核人" prop="auditperson"></el-table-column>
			<el-table-column width="80px" label="审核时间" prop="audittime"></el-table-column>
			<el-table-column width="60px" label="备注" prop="remarks"></el-table-column>
			<el-table-column width="60px" label="当前状态" prop="delflag">
				<template #default="scope">
				<el-tag  type="success" v-if="scope.row.delflag">启用</el-tag>
				<el-tag  type="danger" v-else>禁用</el-tag>
				</template>
			</el-table-column>
<!--————————————————————————客户信息操作———————————————————————————-->	
			<el-table-column width="200px" label="操作">
				<template #default="scope">
					<template v-if="scope.row.delflag">
						<el-button type="primary" plain size="small" @click="update(scope.row.id,scope.row.recordid)">修改</el-button>
						<el-button  type="danger" plain size="small" @click="del(scope.row.id,0)">删除</el-button>
						<el-button  type="success" plain size="small" @click="audit(scope.row.id)">审核</el-button>
					</template>
					<el-button v-else type="warning" plain size="small" @click="del(scope.row.id,1)">启用</el-button>
				</template>
			</el-table-column>
	</el-table>
	<el-pagination 
	style="margin-top: 10px;" 
	background
	 v-model:current-page="params.pageNo" 
	 :page-count="tableData.pages" 
	 :total="tableData.total"
	  @current-change="getTableData" />
	  

<!--————————————————————————退住信息弹窗———————————————————————————-->
	<el-dialog v-model="dialog.show" :title="dialog.title" width="450px" :close-on-click-modal="false">
		<Out v-if="dialog.show" @getTableData="getTableData" v-model:show="dialog.show" :id="dialog.id" :recordid="dialog.recordid"/>
	</el-dialog>
<!--————————————————————————审核弹窗———————————————————————————-->
	<el-dialog v-model="auditdialog.show" :title="auditdialog.title" width="450px" :close-on-click-modal="false">
		<Audit v-if="auditdialog.show" @getTableData="getTableData" v-model:show="auditdialog.show" :id="auditdialog.id"/>
	</el-dialog>	
  </div>
</template>

<script setup>
import { CirclePlus, Search } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus';
import {get,post} from'@/axios'
import {ref,reactive} from 'vue'
import Out from './out'
import Audit from'./audit'
//——————————————————————————————变量——————————————————————————————
const dialog=reactive({
	show:false,
	title:'',
	id:null,
	recordid:''
})
const auditdialog=reactive({
	show:false,
	title:'',
	id:null
})
const tableData=reactive({
	records:[],
	pages:0,
	total:0
})
const params= reactive({
	pageNo:1,
	pageSize:9,
	customername: ''
})
//———————————————————————————————功能实现——————————————————————————————
//———————————————————————————————搜索模块——————————————————————————————
function search(){
	getTableData()
}
//——————————————————————————————添加客户模块——————————————————————————————
function out(){
    dialog.title='退住办理'
	dialog.id=null
	dialog.show=true
}
//——————————————————————————————修改客户模块——————————————————————————————
function update(id,recordid){
	dialog.title='修改客户退住信息'
	dialog.id=id
	dialog.recordid=recordid
	dialog.show=true
}
//——————————————————————————————审核模块——————————————————————————————
function audit(id){
	auditdialog.title='审核'
	auditdialog.id=id
	auditdialog.show=true
}
//——————————————————————————————禁用客户模块——————————————————————————————
function del(id,delflag){
	const text=delflag ? '确定要启用该客户吗':'确定要禁用该客户吗'
	ElMessageBox.confirm(text,"警告",{
		type:'warning'
	}).then(()=>{
		post('/checkIn/del',{id,delflag},content=>{
			getTableData()
		})
	}).catch(()=>{})
}
//——————————————————————————————获取分页数据——————————————————————————————
function getTableData(){
	get('/checkIn/checkoutlist',params,content=>{
		tableData.records=content.records
		tableData.pages=content.pages
		tableData.total=content.total
		})
}
getTableData()
</script>

<style scoped lang="scss">
	.el-table {
	  font-size: 13px;
	}
</style>
