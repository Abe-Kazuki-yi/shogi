<script setup lang="ts">
defineProps<{
  piece: string | null
  isHighlighted: boolean
  isRotated?: boolean
}>()
</script>

<template>
  <td :class="['square', { highlight: isHighlighted }]">
    <span :class="{ rotated: isRotated }">{{ piece }}</span>
  </td>
</template>

<style scoped>
.square {
  width: 60px;
  height: 60px;
  text-align: center;
  vertical-align: middle;
  font-size: 20px;
  font-family: 'serif';
  border: 1px solid #000;
}
.highlight {
  background-color: rgb(236, 236, 202); /* 光らせる処理 */
}
.rotated {
  display: inline-block;
  transform: rotate(180deg);
}
</style>
