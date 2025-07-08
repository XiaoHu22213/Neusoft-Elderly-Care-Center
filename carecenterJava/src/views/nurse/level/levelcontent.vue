<template>
  <div>
        <div style="margin-bottom: 10px;" >
			<el-button type="primary" plain @click="returnlevel">返回</el-button>
			  <el-button type="primary" plain  @click="lcadd(params.id)">添加</el-button>
		</div>
		<el-table :data="tableData.records">
			<el-table-column label="护理内容编号" prop="cid"></el-table-column>
			<el-table-column label="护理内容" prop="nursecontent"></el-table-column>
			<el-table-column label="执行周期" prop="executecycle"></el-table-column>
			<el-table-column label="执行次数" prop="executenub"></el-table-column>
			<el-table-column label="排序号" prop="sort"></el-table-column>
			<el-table-column label="备注"  prop="memo"></el-table-column>
			<el-table-column label="操作">
			     <template #default="scope">
					<el-button type="primary"  size="small" @click="update(scope.row.cid,params.id)">修改</el-button>
					<el-button type="danger"  size="small" @click="del(scope.row.cid, params.id)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
	<el-pagination
		style="margin-top: 10px;"
		background
		:page-count="tableData.pages"
		v-model:current-page="params.pageNo"
		@current-change="getTableData"
		:total="tableData.total"></el-pagination>
		<el-dialog
			v-model="dialog.show"
			:title="dialog.title"
			:close-on-click-modal="false"
			width="450px">
			<Lcadd v-if="dialog.show" 
			v-model:show="dialog.show" 
			@getTableData="getTableData"
			 :id='dialog.lid'
			 :ccid='dialog.cid'
			 />
		</el-dialog>
  </div>
</template>

<script setup>
import { get,post } from '@/axios/axios'
import { ElMessage } from 'element-plus'
import { ref, reactive } from 'vue'
import { useRoute } from 'vue-router';
import CustomSetup from './setup'
import Lcadd from './lcadd'
import router from '@/router'

const dialog = reactive({
	show: false,
	title: '',
	lid: null,
	cid:null
})
let tableData = ref({})
let cids = ref([])
let _cid=reactive({
	id:null
})
let showDialog = ref(false)
let showAdd = ref(false)
const route = useRoute();
const contenttest= ref([])
const params = reactive({
	pageNo: 1,
	pageSize: 10,
	id:route.query.id
})
const selectedRows =ref([])
const remark= reactive({
	memo:'',
	id:''
});
getTableData()
function getTableData () {
	cids.value=[]
	get('/lccontrast/list', params, content => {
		 tableData.value = content
		 console.dir( tableData.value)
		 // console.dir( tableData)
		// if (tableData.value.records && tableData.value.records.length > 0) {
		//     tableData.value.records.forEach(record => {
		//       const rid = record.cid; // 获取每个行的 cid
		// 	  cids.value.push(rid)
		//     });
		//   }
		//   getcontent()
	})
	
	
}
// function getcontent() {
// 	contenttest.value = [] 
//   for (let cid of cids.value) {
//     _cid.id = cid; // 设置 _cid.id 为当前的 cid 值
//     get("/lccontrast/lccontent", _cid, content => {
//     contenttest.value.push(content.nursecontent);
//     });
//   }
//   console.dir(contenttest)
// }
function search(){
	getTableData()
}


function returnlevel(){
	router.push('/level')
}	
function save()
{
	
	console.dir(selectedRows)
}
function lcadd(id)
{
	dialog.title = '添加护理等级内容'
			dialog.lid = id
			dialog.cid=null
	dialog.show = true
}
function update(cid,lid) {
	dialog.title = '修改护理内容'
	dialog.lid = lid
	dialog.cid=cid
	dialog.show = true
}
function del (cid, lid) {
		const text = '确定要删除该角色吗?' 
		ElMessageBox.confirm(text, '警告', {
			type: 'warning'
		}).then(() => {
		
			
			post('/lccontrast/del', { cid, lid }, content => {
				getTableData()
			})
		}).catch(() => {})
	}
</script>

<style scoped lang="scss">
</style>