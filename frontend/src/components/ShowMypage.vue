<script setup lang="ts">
import axios from 'axios'
import { onMounted, ref } from 'vue'

interface Template {
  id: number
  name: string
  content: string
}

const templateFirsts = ref<Template[]>([])
const templateSeconds = ref<Template[]>([])

const getTemplates = async () => {
  const url = 'http://localhost:8080/mypage/'
  const id = 1 /*これは後から動的に変える*/
  const res1 = await axios.get(url + id + '/true')
  templateFirsts.value = res1.data
  const res2 = await axios.get(url + id + '/false')
  templateSeconds.value = res2.data
}

onMounted(() => {
  getTemplates()
})
</script>

<template>
  <div class="main">
    <p>先手でのテンプレート</p>
    <ul>
      <li v-for="templateFirst in templateFirsts" :key="templateFirst.id">
        {{ templateFirst.name }} : {{ templateFirst.content }}
      </li>
    </ul>

    <p>後手でのテンプレート</p>
    <ul>
      <li v-for="templateSecond in templateSeconds" :key="templateSecond.id">
        {{ templateSecond.name }} : {{ templateSecond.content }}
      </li>
    </ul>
  </div>
</template>

<style scoped>
.class {
  display: block;
}
</style>
