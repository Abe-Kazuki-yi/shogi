<script setup lang="ts">
import HomeRouter from '@/components/HomeRouter.vue'
import ShowMypage from '@/components/ShowMypage.vue'
</script>

<template>
  <div class="container">
    <HomeRouter />
    <ShowMypage />
  </div>
</template>

/*chatgpt*/
<style scoped>
.container {
  display: flex;
  flex-direction: row; /* 横並び */
  justify-content: center;
  gap: 20px; /* コンポーネント間のスペース（任意） */
}

.container > * {
  flex: 1; /* 左右均等に幅を取る（必要なら） */
}
</style>
/*chatgpt*/
