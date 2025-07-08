<template>
  <div style="margin-right: 30px">
	   
   <el-form ref="formObj" :model="wyform" :rules="rules" label-width="100px">
	   <el-form-item label="护理类型" prop='cid' v-if="props.ccid===null"> 
		<el-select
	            v-model="wyform.cid"
	            clearable
	            placeholder="请选择类型"
	            style="width: 240px"
	            @change="handleChange"
	          >
	            <el-option
	              v-for="item in tableData"
	              :key="item.id"
	              :label="item.nursecontent"
	              :value="item.id"
	            > </el-option>
	          </el-select>
			  </el-form-item>
	 
     <el-form-item label="执行周期" prop="executecycle">
       <el-input maxLength="20" v-model="wyform.executecycle" placeholder="请输入执行周期"></el-input>
     </el-form-item>
     <el-form-item label="执行次数" prop="executenub">
		    <el-input maxLength="20" v-model="wyform.executenub" placeholder="请输入执行次数"></el-input>
     </el-form-item>
	 <el-form-item label="排序" prop="sort">
		  <el-input maxLength="20" v-model="wyform.sort" placeholder="请输入排序"></el-input>
	 </el-form-item>
     <el-form-item label="备注" prop="memo">
		  <el-input maxLength="20" v-model="wyform.memo" placeholder="备注"></el-input>
     </el-form-item>
     <el-form-item>
       <el-button type="primary" plain :icon="Save" @click="save">保存</el-button>
     </el-form-item>
   </el-form>
  </div>
</template>

<script setup>
import Save from '@/components/icons/save'
import { ref, reactive } from 'vue'
import { get, post } from '@/axios'
import { ElMessage } from 'element-plus'
import { PictureFilled } from '@element-plus/icons-vue'
import { ArrowDown } from '@element-plus/icons-vue'
const emits = defineEmits(['update:nursingContent'])
const props = defineProps(['id','ccid'])
const params = reactive({})
const formObj = ref()
let tableData=ref([])
const wyform = reactive({
lid:props.id,
cid:null,
  level: '',
  executecycle:"",
  executenub:'',
  sort: null,
   memo: ''
  
})
getTableData()
function getTableData () {
	
	get('/nursecontent/effctivelist', params, content => {
		//console.dir(content)
		tableData.value = content
		//console.dir(content)
	})

}
if (props.ccid) {
		wyform.cid = props.ccid
		//getById()
	}
const rules = reactive({
	cid: [
		{ required: true, message: '请输入护理内容', trigger: 'blur' },
			{ validator: check, message: '该护理内容已经被使用', trigger: 'click' }
	],
	executecycle: [
		{ required: true, message: '请输入执行周期', trigger: 'blur' },
		
	],
	executenub: [
		{ required: true, message: '请输入执行次数', trigger: 'blur' }
	],
	sort: [
		{ required: true, message: '请输入排序', trigger: 'blur' }
	],
	memo: [
		{ required: true, message: '请输入备注', trigger: 'blur' }
	]
})



function save() {
	
	post(props.ccid?'/lccontrast/update':"/lccontrast/save",wyform,content => {
		//console.dir(content)
            emits('update:show', false)
			emits('getTableData')
	},formObj)
}

function uploadFile (file) {
	wyform.file = file.raw
}
function removeFile () {
	wyform.file = null
}
function check(rule, value, callback) {
	//console.dir('check')
		get('/lccontrast/check', { lid: props.id, value, field: rule.field }, content => {
			if (content) {
				callback()
			} else {
				callback(new Error())
			}
		})
	}
	const handleChange = (value) => {
	// console.dir(wyform.lid)
	//	console.dir(wyform.cid)
		}
</script>

<style scoped lang="scss"></style>