<script setup lang="ts">
import { useTemplateStore } from '@/stores/templateStore'

const store = useTemplateStore()
</script>

<template>
  <div>
    <h3>ゲーム画面のテンプレート</h3>
    <ul>
      <li v-for="template in store.templates" :key="template.id">{{ template.name }}</li>
    </ul>
  </div>
</template>
