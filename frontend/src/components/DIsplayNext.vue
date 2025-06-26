<script setup lang="ts">
import { useTemplateStore } from '@/stores/useTemplateStore'
import { useGlobalStore } from '@/stores/global'
import { useBoard } from '@/composables/useBoard'

const store = useTemplateStore()
const global = useGlobalStore()
const { getNextBoard } = useBoard()
</script>

<template>
  <div>
    <h3>ゲーム画面のテンプレート</h3>
    <ul>
      <li v-for="template in store.templates" :key="template.id">{{ template.name }}</li>
    </ul>
  </div>
  <p>現在の手数: {{ global.turn }}</p>
  <button @click="getNextBoard">次の手へ</button>
</template>
