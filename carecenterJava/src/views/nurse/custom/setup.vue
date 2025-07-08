<template>
  <div style="margin-right: 30px">
	<!-- <el-table :data="tableData">
	<el-table-column label="客户编号" prop="cuid"></el-table-column>
			<el-table-column label="护理内容" prop="nursecontent"></el-table-column>
			<el-table-column label="执行周期" prop="executecycle"></el-table-column>
			<el-table-column label="执行次数" prop="executenub"></el-table-column>
		</el-table> -->
    <el-form ref="formObj" :model="tableData"  label-width="100px">
		  <el-table :data="tableData">
      <el-table-column label="客户编号" prop="cuid"></el-table-column>
      		<el-table-column label="护理内容" prop="nursecontent"></el-table-column>
      		<el-table-column label="执行周期" prop="executecycle">
				<template #default="scope">
				<el-input maxLength="20" v-model="scope.row.executecycle" placeholder="请输入执行周期"></el-input>
				</template>
			</el-table-column>
      		<el-table-column label="执行次数" prop="executenub">
				<template #default="scope">
				<el-input maxLength="20" v-model="scope.row.executenub" placeholder="请输入执行次数"></el-input>
				</template>
			</el-table-column>
			
      	</el-table>
	  <el-form-item style="margin-top: 10px;">
	    <el-button type="primary" plain :icon="Save" @click="save">保存</el-button>
	  </el-form-item>
    </el-form>
  </div>
</template>

<script setup>
import Save from '@/components/icons/save'
import { ref, reactive } from 'vue'
import { get, post } from '@/axios'

import { PictureFilled } from '@element-plus/icons-vue'
const emits = defineEmits(['update:nursingContent'])
const props = defineProps(['id'])
let tableData = ref([])
const wyform = reactive({

  id:props.id,
  
})


const formObj = ref()
getTableData()
function getTableData () {
	
	get('/customcontent/list', wyform, content => {
		tableData.value = content
		//console.dir(tableData.value)
		
	})
}

function save() {
	//console.dir(tableData)
	for(let item of tableData.value )
	{
		//console.dir(item)
		post( "/customcontent/update", item, content => {
			
			}, formObj)
	}
	emits('update:show', false)
	emits('getTableData')
}

function uploadFile (file) {
	wyform.file = file.raw
}
function removeFile () {
	wyform.file = null
}

</script>

<style scoped lang="scss"></style>