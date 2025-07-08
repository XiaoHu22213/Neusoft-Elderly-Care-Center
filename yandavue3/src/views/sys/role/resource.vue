<template>
	<div>
		<el-tree 
		  ref = "vtree"
		  :data="tableData"
			:props="props"
			:default-checked-keys="keys"
			node-key="id"
			show-checkbox
			default-expand-all></el-tree>
		<el-button style="margin-top: 20px;" type="primary" plain @click="save">保存</el-button>
	</div>
</template>

<script setup>
import { get,post } from '@/axios'
import { ref, reactive } from 'vue'
const prop = defineProps(['roleId'])
const emits = defineEmits(['update:show'])
const tableData = ref([])
const props = reactive({
	label: 'name'
})
const keys = ref([])
const vtree =ref()
function getTableData () {
	get('/roleResource/getResource', {roleId: prop.roleId}, content => {
		tableData.value = content.resourcesList
		for (const i in content.roleResourceList){
			if (content.roleResourceList[i].type === 0){
				keys.value.push(content.roleResourceList[i].resourceId)
			}
		}
		console.dir(content)
	})
}
function save () {
	const checkNodes = vtree.value.getCheckedNodes(false, true)
	const menuIds = []
	const btnIds = []
	for (const i in checkNodes) {
		if (checkNodes[i].type === 1){
			menuIds.push(checkNodes[i].id)
		}
		else{
			btnIds.push(checkNodes[i].id)
		}
	}
	post('/roleResource/save', {roleId: prop.roleId, menuIds, btnIds}, content => {
		emits('update:show', false)
	})
}
getTableData ()
</script>

<style scoped lang="scss">
	
</style>