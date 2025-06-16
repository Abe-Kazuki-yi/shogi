<template>
  <div class="board-container">
    <h2>将棋盤</h2>
    <table class="board">
      <tr v-for="dan in 9" :key="dan">
        <SomeCells
          v-for="suji in [...Array(9).keys()].map((i) => 9 - i)"
          :key="suji"
          :suji="suji"
          :dan="dan"
          :myPiece="board.myFormation[suji][dan]"
          :opponentPiece="board.opponentFormation[suji][dan]"
          :highlightMap="highlightMap"
        />
      </tr>
    </table>
  </div>
</template>

<script setup lang="ts">
import { useBoard } from '@/composables/useBoard'
import SomeCells from './SomeCells.vue'

const { board, highlightMap } = useBoard()
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
