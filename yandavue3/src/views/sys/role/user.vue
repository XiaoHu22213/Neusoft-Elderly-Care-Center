<template>
	<div>
		<el-transfer :data="userList" :props="props" :title="titles" v-model="values"></el-transfer>
		<el-button style="margin-top: 10px;" type="primary" plain @click="save">保存</el-button>
	</div>
</template>

<script setup>
import { get,post } from '@/axios'
import { ref, reactive } from 'vue'
const prop = defineProps(['roleId'])
const userList = ref([])
const emits=defineEmits(['update:show'])
const props = reactive({
	label: 'name',
	key: 'id'
})
const titles=reactive(['未选用户','已选用户'])
const values=ref([])
function getUserList(){
	get('userRole/getUser',{ roleId: prop.roleId},content=>{
		userList.value = content.userList
		for (const i in content.userRoleList)
		{
			values.value.push(content.userRoleList[i].userId)
		}
	})
}
function save(){
	post('/userRole/save',{roleId: prop.roleId,userIds:values.value},content=>{
		emits('update:show',false)
	})
}
getUserList()
</script>

<style scoped lang="scss">
</style>