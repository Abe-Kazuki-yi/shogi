<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'

// Piece型
interface Piece {
  id: number
  name: string
  promoted: string | null
}

interface Move {
  id: number
  num: number
  beforeId: number
  beforeX: number
  beforeY: number
  targetX: number
  targetY: number
  promoted: boolean
}

// 空の10×10配列を生成（0番目は使わない）
const createEmptyBoard = (): (Piece | null)[][] => {
  return Array.from({ length: 10 }, () => Array(10).fill(null))
}

// Board型
const board = ref<{
  myFormation: (Piece | null)[][]
  opponentFormation: (Piece | null)[][]
}>({
  myFormation: createEmptyBoard(),
  opponentFormation: createEmptyBoard(),
})

const lists = ref<Move[]>([])

// 初期データ取得
const getInitial = async () => {
  try {
    const url = 'http://localhost:8080/board/first'
    const response = await axios.get(url)
    board.value.myFormation = response.data.myFormation
    board.value.opponentFormation = response.data.opponentFormation
  } catch (error) {
    console.error(error)
  }
}

const getLists = async () => {
  try {
    const url = 'http://localhost:8080/list/1'
    const response = await axios.get(url)
    lists.value = response.data
  } catch (error) {
    console.error(error)
  }
}

onMounted(getInitial)
onMounted(getLists)

// ハイライトの種類を記録する Map（キー: "x,y", 値: 'before' | 'target'）
const highlightMap = computed(() => {
  const map = new Map<string, string>()
  lists.value
    .filter((move) => move.num === 1)
    .forEach((move) => {
      map.set(`${move.beforeX},${move.beforeY}`, 'before')
      map.set(`${move.targetX},${move.targetY}`, 'target')
    })
  return map
})

const getHighlightClass = (suji: number, dan: number) => {
  const key = `${suji},${dan}`
  const type = highlightMap.value.get(key)
  if (type === 'before') return 'highlight-before'
  if (type === 'target') return 'highlight-target'
  return ''
}
</script>

<template>
  <div class="board-container">
    <h2>将棋盤</h2>
    <table class="board">
      <!-- 段: 上から下 (1〜9) -->
      <tr v-for="dan in 9" :key="dan">
        <!-- 筋: 右から左 (9→1) -->
        <td
          v-for="suji in [...Array(9).keys()].map((i) => 9 - i)"
          :key="suji"
          :class="getHighlightClass(suji, dan)"
        >
          <div class="cell">
            <!-- 相手の駒（180度回転） -->
            <div
              v-if="board.opponentFormation[suji][dan]"
              :class="[
                'piece',
                'opponent',
                {
                  'two-chars': board.opponentFormation[suji][dan]?.name.length === 2,
                },
              ]"
            >
              <span class="rotated-text">
                {{ board.opponentFormation[suji][dan]?.name }}
              </span>
            </div>

            <!-- 自分の駒 -->
            <div
              v-if="board.myFormation[suji][dan]"
              :class="[
                'piece',
                {
                  'two-chars': board.myFormation[suji][dan]?.name.length === 2,
                },
              ]"
            >
              {{ board.myFormation[suji][dan]?.name }}
            </div>
          </div>
        </td>
      </tr>
    </table>
  </div>
</template>

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
