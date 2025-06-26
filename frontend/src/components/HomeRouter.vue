<script setup lang="ts">
import { useRouter } from 'vue-router'

const router = useRouter()

const startOne = () => {
  router.push('/select')
}
const startTwo = () => {
  alert('また未実装です')
}

const setTemplates = () => {
  alert('まだ未実装です')
}

const editTemplate = () => {
  alert('まだ未実装です')
}
</script>

<template>
  <div class="main">
    <button @click="startOne">1人プレイ</button>
    <button @click="startTwo">2人プレイ</button>
    <button @click="setTemplates">テンプレート変更</button>
    <button @click="editTemplate">テンプレート編集</button>
  </div>
</template>

<style scoped>
.class {
  display: block;
}
</style>
