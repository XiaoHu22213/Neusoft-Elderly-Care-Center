<template>
  <div style="margin-right: 30px">
    <el-form ref="formObj" :model="wyform" :rules="rules" label-width="100px">
      
	  <el-form-item label="护理内容" prop="description">
	    <el-input type="textarea" :rows="5" v-model="wyform.description" placeholder="请输入护理内容"></el-input>
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

import { PictureFilled } from '@element-plus/icons-vue'

const wyform = reactive({

  name: '',
  nickyName: '',
  sex: null,
  birthday: '2000-01-01',
  phone: '',
  email: '',
  password: '',
  file: null
  
})


const formObj = ref()


function save() {
	post( "/admin/add", wyform, content => {
		
		
	}, formObj)
}

function uploadFile (file) {
	wyform.file = file.raw
}
function removeFile () {
	wyform.file = null
}

</script>

<style scoped lang="scss"></style>