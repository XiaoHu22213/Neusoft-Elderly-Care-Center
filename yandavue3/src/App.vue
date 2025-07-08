<template>
  <RouterView  />
</template>


<script setup>
import { userLoadingStore } from '@/stores'
const userLoading = userLoadingStore()
</script>


<style scoped>

</style>
