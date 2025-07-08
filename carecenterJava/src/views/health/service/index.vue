<template>
	<div>
		<div>
			<!-- <el-button type="primary" plain size="small" @click="xianshi()">修改</el-button> -->
			<el-input
			  v-model="searchName"
			  style="max-width: 300px"
			  placeholder="搜索"
			  class="input-with-select"
			>
			  <template #append>
			    <el-button :icon="Search" @click="search()"/>
			  </template>
			</el-input>
			
		</div>
		<div v-if="model">
			<el-table :data="Radio">
				<el-table-column label="序号" prop="id"></el-table-column>
				<el-table-column label="管家姓名" prop="name"></el-table-column>
				<el-table-column label="联系电话" prop="phone"></el-table-column>
				<el-table-column label="服务楼层" prop="floor"></el-table-column>
				<el-table-column label="备注" prop="notes"></el-table-column>
				<el-table-column label="操作时间" prop="time"></el-table-column>
				<el-table-column label="操作" width="200px">
					<template #default="scope">
						<template v-if="scope.row.status">
							<el-button type="primary" v-if="!scope.row.Sid" plain size="small" @click="set(scope.row.id)">设置</el-button>
							<el-button type="primary" v-if="scope.row.Sid" plain size="small" @click="update(scope.row.Sid)">修改</el-button>
							<el-button type="danger" v-if="scope.row.Sid" plain size="small" @click="del(scope.row.Sid, 0)">删除</el-button>
							</template>
						<el-button v-else type="warning" plain size="small" @click="del(scope.row.Sid, 1)">启用</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<div v-else>
			<el-table :data="Radio">
				<el-table-column label="序号" prop="id"></el-table-column>
				<el-table-column label="管家姓名" prop="name"></el-table-column>
				<el-table-column label="联系电话" prop="phone"></el-table-column>
				<el-table-column label="服务楼层" prop="floor"></el-table-column>
				<el-table-column label="备注" prop="notes"></el-table-column>
				<el-table-column label="操作时间" prop="time"></el-table-column>
				<el-table-column label="操作" width="200px">
					<template #default="scope">
						<template v-if="scope.row.status">
							<el-button type="primary" v-if="!scope.row.Sid" plain size="small" @click="set(scope.row.id)">设置</el-button>
							<el-button type="primary" v-if="scope.row.Sid" plain size="small" @click="update(scope.row.Sid)">修改</el-button>
							<el-button type="danger" v-if="scope.row.Sid" plain size="small" @click="del(scope.row.Sid, 0)">删除</el-button>
							</template>
						<el-button v-else type="warning" plain size="small" @click="del(scope.row.Sid, 1)">启用</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<el-pagination
			style="margin-top: 10px;"
			background
			v-model:current-page="params.pageNo"
			:page-count="tableData.pages"
			:total="tableData.total"
			@current-change="getTableData" />
		<el-dialog
				v-model="dialog.show"
				:title="dialog.title"
				width="450px"
				:close-on-click-modal="false">
		  <Set
				v-if="dialog.show"
				@getTableData="getTableData"
				v-model:show="dialog.show"
		 		:id="dialog.id"/>
		</el-dialog>
		<el-dialog
				v-model="dialog2.show"
				:title="dialog2.title"
				width="450px"
				:close-on-click-modal="false">
		  <Update
				v-if="dialog2.show"
				@getTableData="getTableData"
				v-model:show="dialog2.show"
		 		:id="dialog2.id"/>
		</el-dialog>
	</div>
</template>

<script setup lang="ts">
import { CirclePlus, Search } from '@element-plus/icons-vue'
import { getPath } from '@/util'
// import Guanliyuan from '@/components/icons/guanliyuan'
import { get, post } from '@/axios'
import { ref, reactive, onMounted, watch, computed } from 'vue'
import Set from './set'
import Update from './update'
import url from './util'
import { ElMessageBox } from 'element-plus'
const dialog = reactive({
	show: false,
	title: '',
	id: null
})
const dialog2 = reactive({
	show: false,
	title: '',
	id: null
})
const tableData = reactive({
	records: [],
	pages: 0,
	total: 0
})
const params = reactive({
	pageNo: 1,
	pageSize: 10,
})
getTableData()
function getTableData () {
	get('/user/list', params, content => {
		tableData.records = content.records
		tableData.pages = content.pages
		tableData.total = content.total
		console.dir(tableData.records)
		
	})
}
// const serviceData = reactive({
// 	records: [],
// 	pages: 0,
// 	total: 0
// })
const serviceData = ref([])
getserviceData()
function getserviceData () {
	get('/servicetargets/type', null, content => {
		serviceData.value = content
		console.dir(serviceData)
		
	})
}
const Servicekeep = computed(() => {
  return serviceData.value.filter(item => item.status === true);
  
});

function xianshi(){
	console.dir(Housekeep.value)
	console.dir(serviceData.value)
	console.dir(mergedData.value)
}

const userData = ref([])
getuserData()

function getuserData(){
		get('/user/type',null,content =>{
			userData.value=content
			console.dir(userData)
			    // const iconValues = userData.value.map(item => item.status);
			    // console.log(iconValues); // 输出包含icon属性的值的数组
		})
}
// 过滤mealData以仅显示与radio1相等的days属性的值
const Housekeep = computed(() => {
  return userData.value.filter(item => item.type === '管家');

});

const mergedData = computed(() => {
  return Housekeep.value.map(housekeepItem => {
    const serviceItem = serviceData.value.find(serviceItem => serviceItem.name === housekeepItem.name);
	if (serviceItem) {
		    console.dir(serviceItem.status)
		// housekeepItem.status = serviceItem.status;
      // 如果 Servicekeep 中有相同 name 的项，则合并数据
      return {
        ...housekeepItem, // 保留 Housekeep 中的数据
		status: serviceItem.status,
        floor: serviceItem.floor, // 添加 Servicekeep 中的 floor
        notes: serviceItem.notes, // 添加 Servicekeep 中的 notes
        time: serviceItem.time, // 添加 Servicekeep 中的 time
		Sid: serviceItem.id,
      };
    }
    // 如果 Servicekeep 中没有相同 name 的项，则返回 Housekeep 中的数据
    return housekeepItem;
  });
});

function set(id) {
	dialog.title = '设置服务对象'
	dialog.id = id
	dialog.show = true
	console.dir(id)
}
function update(id) {
	dialog2.title = '修改服务'
	dialog2.id = id
	dialog2.show = true
	console.dir(id)
	
}
function del (id, status) {
	const text = status ? '确定要启用该服务吗?' : '确定要禁用该服务吗'
	ElMessageBox.confirm(text, '警告', {
		type: 'warning'
	}).then(() => {
		post(url.del, { id ,status}, content => {
			getTableData()
			
			console.dir(mergedData.value)
		})
	}).catch(() => {})
}

const xianshishujv = ref([])
const model = ref(true); 
const searchName = ref('');
const search = () => {
	model.value = !model
	console.dir(model)
    // 更新 mergedData 的计算属性
	xianshishujv.value = mergedData.value;

	console.dir(mergedData.value)
	console.dir(Radio.value)
};
const Radio = computed(() => {
        return xianshishujv.value.filter(item => item.name.includes(searchName.value));
    });

</script>

<style scoped lang="scss">
	
</style>
