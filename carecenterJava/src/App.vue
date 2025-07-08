<template>
  <RouterView v-loading.fullscreen.lock="userLoading.loading" />
</template>


<script setup>
import { userLoadingStore } from '@/stores'
const userLoading = userLoadingStore()
</script>


<style scoped>

</style>
