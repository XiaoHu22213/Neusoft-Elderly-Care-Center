<template>
  <div>
    <div style="" class="btns">
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
		<el-button type="primary" style="margin-left: 30px;" plain @click="add">登记</el-button>
    </div>
	<div style="margin-top: 15px"class="btns">
		<el-button type="primary" plain @click="elder0">活力老人</el-button>
		<el-button type="primary" plain @click="elder1" >自理老人</el-button>
		<el-button type="primary" plain @click="elder2" >护理老人</el-button>
	</div>
<!--————————————————————————客户信息显示———————————————————————————-->
	<el-table :data="tableData.records">
	<el-table-column width="60px" label="序号" prop="id"></el-table-column>
			<el-table-column width="80px" label="客户姓名" prop="customername"></el-table-column>
			<el-table-column width="60px" label="性别" prop="customersex">
				<template #default="scope">
					<span v-if="scope.row.customersex===1">男</span>
					<span v-else>女</span>
				</template>
			</el-table-column>
			<el-table-column width="60px" label="年龄" prop="customerage"></el-table-column>
			<el-table-column width="90px" label="身份证号" prop="idcard"></el-table-column>
			<el-table-column width="60px" label="老人类型" prop="eldertype">
				<template #default="scope">
					<span v-if="scope.row.eldertype===0">活力老人</span>
					<span v-else-if="scope.row.eldertype===1">自理老人</span>
					<span v-else>护理老人</span>
				</template>
			</el-table-column>
			<el-table-column width="80px" label="房间号" prop="roomid"></el-table-column>
			<el-table-column width="80px" label="所属楼房" prop="buildingid"></el-table-column>
			<el-table-column width="70px" label="档案号" prop="recordid"></el-table-column>
			<el-table-column width="80px" label="入住时间" prop="checkindate"></el-table-column>
			<el-table-column width="80px" label="合同到期时间" prop="expirationdate"></el-table-column>
			<el-table-column width="110px" label="联系电话" prop="contacttel"></el-table-column>
			<el-table-column width="90px" label="备注" prop="remarks"></el-table-column>
			<el-table-column width="60px" label="当前状态" prop="delflag">
				<template #default="scope" label="操作">
				<el-tag  type="success" v-if="scope.row.delflag">启用</el-tag>
				<el-tag  type="danger" v-else>禁用</el-tag>
				</template>
			</el-table-column>
				<el-table-column label="护理级别" prop="nursingLevel"></el-table-column>
				<el-table-column label="护理内容">
					<template #default="scope">
						<el-button type="success" size="small" plain :disabled="isButtonDisabled(scope.row.delflag)" @click="setup(scope.row.id)">设置</el-button>
										
				</template>
						</el-table-column>
						<el-table-column label="护理记录">
							<template #default="scope">
								<el-button type="primary" size="small"   :disabled="isButtonDisabled(scope.row.delflag)" plain @click="addrecord(scope.row.id,scope.row.customername) ">添加</el-button>
												
						</template>
								</el-table-column>
<!--————————————————————————客户信息操作———————————————————————————-->	
			<el-table-column width="200px" label="操作">
				<template #default="scope">
					<template v-if="scope.row.delflag">
						<el-button type="primary" plain size="small" @click="update(scope.row.id)">修改</el-button>
						<el-button  type="danger" plain size="small" @click="del(scope.row.id,0,scope.row.eldertype)">删除</el-button>
					</template>
					<el-button v-else type="warning" plain size="small" @click="del(scope.row.id,1,scope.row.eldertype)">启用</el-button>
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
	  

<!--————————————————————————添加客户信息弹窗———————————————————————————-->
	<el-dialog v-model="dialog.show" :title="dialog.title" width="450px" :close-on-click-modal="false">
			<Add v-if="dialog.show" @getTableData="getTableData" v-model:show="dialog.show" :id="dialog.id"/>
		</el-dialog>
		<el-dialog
			v-model="dia.show"
			:title="dia.title"
			:close-on-click-modal="false"
			width="450px">
			<CustomSetup v-if="dia.show" 
			v-model:show="dia.show" 
			@getTableData="getTableData"
			 :id='dia.id'
			
			 />
			 </el-dialog>
			 <el-dialog
			 	v-model="log.show"
			 	:title="log.title"
			 	:close-on-click-modal="false"
			 	width="450px">
			 	<Record v-if="log.show" 
			 	v-model:show="log.show" 
			 	@getTableData="getTableData"
			 	 :id='log.id'
			 	 :name='log.name'
			 	 />
			 </el-dialog>
  </div>
</template>

<script setup>
import { ElMessageBox } from 'element-plus';
import { CirclePlus, Search } from '@element-plus/icons-vue'
import {get,post} from'@/axios'
import {ref,reactive} from 'vue'
import Add from './add'
import CustomSetup from './setup'
import Record from './rd'
//——————————————————————————————变量——————————————————————————————
const dialog=reactive({
	show:false,
	title:'',
	id:null
})
const dia=reactive({
	show:false,
	title:'',
	id:null
})
const log=reactive({
	show: false,
	title: '',
	id: null,
	name:''
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
const elderparams= reactive({
	pageNo:1,
	pageSize:9,
	eldertype:""
})
//———————————————————————————————功能实现——————————————————————————————
//———————————————————————————————搜索模块——————————————————————————————
function search(){
	getTableData()
}
//——————————————————————————————添加客户模块——————————————————————————————
function add(){
    dialog.title='入住登记'
	dialog.id=null
	dialog.show=true
}
//——————————————————————————————修改客户模块——————————————————————————————
function update(id){
	dialog.title='修改客户信息'
	dialog.id=id
	dialog.show=true
}
//——————————————————————————————禁用客户模块——————————————————————————————
function del(id,delflag,eldertype){
	const text=delflag ? '确定要启用该客户吗':'确定要禁用该客户吗'
	ElMessageBox.confirm(text,"警告",{
		type:'warning'
	}).then(()=>{
		post('/checkIn/del',{id,delflag},content=>{
			console.dir(eldertype)
			if(eldertype==0)
			{elder0()}
			else if(eldertype==1)
			{elder1()}
			else if(eldertype==2)
			{elder2()}
		})
	}).catch(()=>{})
}
//——————————————————————————————获取分页数据——————————————————————————————
function getTableData(){
	get('/checkIn/list',params,content=>{
		tableData.records=content.records
		tableData.pages=content.pages
		tableData.total=content.total
		})
}
getTableData()
//——————————————————————————————老人分类——————————————————————————————

function elder0(){
	elderparams.eldertype=0
	get('/checkIn/elderlist',elderparams,content=>{
		tableData.records=content.records
		tableData.pages=content.pages
		tableData.total=content.total
		})
		
}
function elder1(){
	elderparams.eldertype=1
	get('/checkIn/elderlist',elderparams,content=>{
		tableData.records=content.records
		tableData.pages=content.pages
		tableData.total=content.total
		})
}
function elder2(){
	elderparams.eldertype=2
	get('/checkIn/elderlist',elderparams,content=>{
		tableData.records=content.records
		tableData.pages=content.pages
		tableData.total=content.total
		})
}
function setup (id,executecycle,executenub) {
		
	dia.title = '修改执行周期、执行次数'
			dia.id = id
			
	dia.show = true
}
function addrecord (id,name) {
	
	log.title = '添加护理记录'
			log.id = id
			log.name=name
	log.show = true
}
function isButtonDisabled(delflag) {
  // 根据行数据中的 `isEnabled` 字段返回布尔值
  return !delflag;
}
</script>

<style scoped lang="scss">
	.el-table {
	  font-size: 13px;
	}
</style>
