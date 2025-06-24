<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { useTemplateStore } from '@/stores/useTemplateStore'
import { useNumStore } from '@/stores/useNumStore'

interface Template {
  id: number
  name: string
  content: string
}

const templates = ref<Template[]>([])
const isFirst = ref()
const router = useRouter()
const templateStore = useTemplateStore()
const numStore = useNumStore()

const getTemplate = async () => {
  try {
    const url = 'http://localhost:8080/select/' + 1 + '/' + isFirst.value
    const res = await axios.get(url)
    templates.value = res.data
  } catch (error) {
    console.log(error)
  }
}

const startGame = () => {
  templateStore.setTemplates(templates.value)
  templateStore.setIsFirst(isFirst.value)
  numStore.setNum(1)
  router.push({
    path: '/game',
  })
}
</script>

<template>
  <select v-model="isFirst" @change="getTemplate">
    <option value="" disabled selected>選択してください</option>
    <option value="true">先手</option>
    <option value="false">後手</option>
  </select>

  <div>
    <p>設定されているテンプレート</p>
    <ul>
      <li v-for="template in templates" :key="template.id">
        {{ template.name }}:{{ template.content }}
      </li>
    </ul>
  </div>

  <button v-show="isFirst" @click="startGame">対局開始</button>
</template>
