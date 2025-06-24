<script setup lang="ts">
import { useTemplateStore } from '@/stores/useTemplateStore'

const templateStore = useTemplateStore()
</script>

<template>
  <main>
    <h2>設定されているテンプレート</h2>
    <ul>
      <li v-for="template in templateStore.templates" :key="template.id">
        {{ template.name }}: {{ template.content }}
      </li>
    </ul>
  </main>
</template>
