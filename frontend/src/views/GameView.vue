<script setup lang="ts">
import GameList from '@/components/GameList.vue'
import GameTemplate from '@/components/GameTemplate.vue'
import TheBoard from '@/components/TheBoard.vue'
</script>

<template>
  <main>
    <TheBoard />
  </main>
  <GameTemplate />
  <GameList />
</template>
