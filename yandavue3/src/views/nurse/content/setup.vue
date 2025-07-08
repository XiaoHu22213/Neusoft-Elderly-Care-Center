<template>
  <div style="margin-right: 30px">
    <el-form ref="formObj" :model="wyform"  label-width="100px">
	  <el-form-item label="详细备注" prop="description">
	    <el-input type="textarea" :rows="5" v-model="wyform.description" placeholder="请输入备注"></el-input>
	  </el-form-item>
      <el-form-item>
        <el-button type="primary" plain :icon="Save" @click="save">修改</el-button>
      </el-form-item>
	  
    </el-form>
  </div>
</template>

<script setup>
import Save from '@/components/icons/save'
import { ref, reactive } from 'vue'
import { get, post } from '@/axios'
 import { defineProps,defineEmits } from 'vue';
import { PictureFilled } from '@element-plus/icons-vue'

const formObj = ref()
const props = defineProps({
  memo: {
    type: String,
    required: true
  },
  id: {
    type: Number,
    required: true
  }
})
const emits= defineEmits(['update:memo','update:id','getTableData'])

const wyform = reactive({

  description:props.memo,
  id:props.id
})


function save() {
	//emits('update:memo',wyform.description ) 
	console.dir(wyform)
	post( "/nursecontent/updatememo", wyform, content => {
		
	}, formObj)
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