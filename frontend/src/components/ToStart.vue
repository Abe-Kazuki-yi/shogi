<script setup lang="ts">
import axios from 'axios'
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useTemplateStore } from '@/stores/templateStore'

const isFirst = ref('')
const isLocked = ref(false)
const router = useRouter()
const store = useTemplateStore()

interface Template {
  id: number
  name: string
}

const templates = ref<Template[]>([])

const getTemplate = async () => {
  try {
    const url = 'http://localhost:8080/start/' + 1 + '/' + isFirst.value
    const res = await axios.get(url)
    templates.value = res.data
  } catch (error) {
    console.log(error)
  }
}

const startGame = () => {
  isLocked.value = true
  store.setTemplates(templates.value)
  router.push('/game')
}
</script>

<template>
  <select v-show="!isLocked" v-model="isFirst" @change="getTemplate">
    <option value="" disabled selected>選択してください</option>
    <option value="true">先手</option>
    <option value="false">後手</option>
  </select>

  <div>
    <p>設定されているテンプレート</p>
    <ul>
      <li v-for="template in templates" :key="template.id">{{ template.name }}</li>
    </ul>
  </div>

  <button v-show="isFirst && !isLocked" @click="startGame">対局開始</button>
</template>
