<template>
  <div style="margin-right: 30px">
    <el-form ref="formObj" :model="vform" :rules="rules" label-width="100px">
      <el-form-item label="权限名称" prop="name">
        <el-input maxLength="20" v-model="vform.name" placeholder="请输入权限名称"></el-input>
      </el-form-item>
      
	  <el-form-item label="前端地址" prop="frontUrl">
	    <el-input v-model="vform.frontUrl" placeholder="请输入前端地址"></el-input>
	  </el-form-item>
	  <el-form-item label="后端地址" prop="backUrl">
	    <el-input v-model="vform.backUrl" placeholder="请输入后端地址"></el-input>
	  </el-form-item>
	  
      <el-form-item label="类型" prop="type">
        <el-radio-group v-model="vform.type">
          <el-radio :value="1">目录</el-radio>
          <el-radio :value="0">按钮</el-radio>
        </el-radio-group>
      </el-form-item>
	  <el-form-item label="权限说明" prop="description">
	    <el-input type="textarea" :rows="5" v-model="vform.description" placeholder="请输入权限说明"></el-input>
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
import url from './util'
const emits = defineEmits(['update:show', 'getTableData'])
const props = defineProps(['id','parentId','level'])
const vform = reactive({
	id: null,
  name: '',
  description: '',
  parentId: null,
  frontUrl: '',
  backUrl: '',
  level: null,
  type: null
})
const rules = reactive({
})
const formObj = ref()
if (props.id) {//修改
	vform.id = props.id
	getById()
}else{//添加
	vform.parentId = props.parentId
	vform.level = props.level
}
function check (rule, value, callback) {
	get(url.check, { id: props.id, value, field: rule.field }, content => {
		if (content) {
			callback()
		} else {
			callback(new Error())
		}
	})
}
function save() {
	post(props.id ? url.update : url.add, vform, content => {
		emits('update:show', false)
		emits('getTableData')
	}, formObj)
}
function getById () {
	get(url.getById, { id: props.id }, content => {
		for (const key in vform) {
			if (Object.prototype.hasOwnProperty.call(content, key)) {
				vform[key] = content[key]
			}
		}
	})
}
</script>

<style scoped lang="scss"></style>
