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
	<el-button type="primary" style="margin-left: 30px;" plain @click="goOut">登记</el-button>
    </div>
<!--————————————————————————客户信息显示———————————————————————————-->
	<el-table :data="tableData.records">
	        <el-table-column width="50px" label="序号" prop="id"></el-table-column>
			<el-table-column width="60px" label="客户姓名" prop="customername"></el-table-column>
			<el-table-column width="70px" label="档案号" prop="recordid"></el-table-column>
			<el-table-column width="80px" label="外出事由" prop="gooutreason"></el-table-column>
			<el-table-column width="80px" label="外出时间" prop="goouttime"></el-table-column>
			<el-table-column width="80px" label="预计回院时间" prop="wantbacktime"></el-table-column>
			<el-table-column width="80px" label="实际回院时间" prop="truebacktime"></el-table-column>
			<el-table-column width="70px" label="陪同人" prop="companions"></el-table-column>
			<el-table-column width="70px" label="与老人关系" prop="relationship"></el-table-column>
			<el-table-column width="80px" label="陪同人电话" prop="companionstel"></el-table-column>
			<el-table-column width="60px" label="审批状态" prop="gooutstatus">
				<template #default="scope">
					<span v-if="scope.row.gooutstatus===0">待审批</span>
					<span v-else-if="scope.row.gooutstatus===1">通过</span>
					<span v-else-if="scope.row.gooutstatus===2">不通过</span>
					<span v-else>撤销</span>
				</template>
			</el-table-column>
			<el-table-column width="60px" label="审批人" prop="gooutauditperson"></el-table-column>
			<el-table-column width="80px" label="审批时间" prop="gooutaudittime"></el-table-column>
			<el-table-column width="70px" label="备注" prop="gooutremarks"></el-table-column>
			<el-table-column width="60px" label="当前状态" prop="delflag">
				<template #default="scope">
				<el-tag  type="success" v-if="scope.row.delflag">启用</el-tag>
				<el-tag  type="danger" v-else>禁用</el-tag>
				</template>
			</el-table-column>
<!--————————————————————————客户信息操作———————————————————————————-->	
			<el-table-column width="300px" label="操作">
				<template #default="scope">
					<template v-if="scope.row.delflag">
						<el-button type="primary" plain size="small" @click="update(scope.row.id,scope.row.recordid)">修改</el-button>
						<el-button  type="success" plain size="small" @click="back(scope.row.id)">登记回院时间</el-button>
						<el-button type="primary" plain size="small" @click="audit(scope.row.id)" >审批</el-button>
						<el-button  type="danger" plain size="small" @click="del(scope.row.id,0)">删除</el-button>
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
	  

<!--————————————————————————外出信息弹窗———————————————————————————-->
	<el-dialog v-model="dialog.show" :title="dialog.title" width="450px" :close-on-click-modal="false">
		<Go v-if="dialog.show" @getTableData="getTableData" v-model:show="dialog.show" :id="dialog.id" :recordid="dialog.recordid"/>
	</el-dialog>
<!--————————————————————————审批弹窗———————————————————————————-->
	<el-dialog v-model="auditdialog.show" :title="auditdialog.title" width="450px" :close-on-click-modal="false">
		<Audit v-if="auditdialog.show" @getTableData="getTableData" v-model:show="auditdialog.show" :id="auditdialog.id"/>
	</el-dialog>	
<!--————————————————————————登记回院时间弹窗———————————————————————————-->	
	<el-dialog v-model="backdialog.show" :title="backdialog.title" width="450px" :close-on-click-modal="false">
		<Back v-if="backdialog.show" @getTableData="getTableData" v-model:show="backdialog.show" :id="backdialog.id"/>
	</el-dialog>
  </div>
</template>

<script setup>
import { ElMessageBox } from 'element-plus';
import { CirclePlus, Search } from '@element-plus/icons-vue'
import {get,post} from'@/axios'
import {ref,reactive} from 'vue'
import Go from './go'
import Audit from'./audit'
import Back from'./back'
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
const backdialog=reactive({
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
function goOut(){
    dialog.title='外出办理'
	dialog.id=null
	dialog.show=true
}
//——————————————————————————————修改客户模块——————————————————————————————
function update(id,recordid){
	dialog.title='修改客户外出信息'
	dialog.id=id
	dialog.recordid=recordid
	dialog.show=true
}
//——————————————————————————————审核模块——————————————————————————————
function audit(id){
	auditdialog.title='审批'
	auditdialog.id=id
	auditdialog.show=true
}
function back(id){
	backdialog.title='登记回院时间'
	backdialog.id=id
	backdialog.show=true
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
	get('/checkIn/gooutlist',params,content=>{
		console.dir(content)
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
