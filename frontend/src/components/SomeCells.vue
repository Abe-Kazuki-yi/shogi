<template>
  <td :class="highlightClass">
    <div class="cell">
      <!-- 相手の駒 -->
      <div
        v-if="opponentPiece"
        :class="['piece', 'opponent', { 'two-chars': opponentPiece.name.length === 2 }]"
      >
        <span class="rotated-text">{{ opponentPiece.name }}</span>
      </div>

      <!-- 自分の駒 -->
      <div v-if="myPiece" :class="['piece', { 'two-chars': myPiece.name.length === 2 }]">
        {{ myPiece.name }}
      </div>
    </div>
  </td>
</template>

<script setup lang="ts">
import type { Piece } from '@/types/index'
import { computed } from 'vue'

const props = defineProps<{
  suji: number
  dan: number
  myPiece: Piece | null
  opponentPiece: Piece | null
  highlightMap: Map<string, string>
}>()

const highlightClass = computed(() => {
  const key = `${props.suji},${props.dan}`
  const type = props.highlightMap.get(key)
  if (type === 'before') return 'highlight-before'
  if (type === 'target') return 'highlight-target'
  return ''
})
</script>

<style scoped>
.board-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.board {
  border-collapse: collapse;
  margin: 10px 0;
}

td {
  width: 60px;
  height: 60px;
  border: 1px solid black;
  text-align: center;
  vertical-align: middle;
  padding: 0;
}

.cell {
  position: relative;
  width: 100%;
  height: 100%;
}

.piece {
  position: absolute;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 24px;
  writing-mode: horizontal-tb;
}

.two-chars {
  writing-mode: vertical-rl;
  font-size: 20px;
}

.rotated-text {
  display: inline-block;
  transform: rotate(180deg);
}

.highlight-before {
  background-color: rgb(236, 236, 202);
}

.highlight-target {
  background-color: rgb(218, 250, 190);
}
</style>
