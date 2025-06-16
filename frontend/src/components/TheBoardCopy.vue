<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'

// Piece型
interface Piece {
  id: number
  name: string
  promoted: string | null
}

// 空の10×10配列を生成（0番目は使わない）
const createEmptyBoard = (): (Piece | null)[][] => {
  return Array.from({ length: 10 }, () => Array(10).fill(null))
}

// Board型（0番目を使わないため10×10にしておく）
const board = ref<{
  myFormation: (Piece | null)[][]
  opponentFormation: (Piece | null)[][]
}>({
  myFormation: createEmptyBoard(),
  opponentFormation: createEmptyBoard(),
})

// 初期データ取得
const getInitial = async () => {
  try {
    const url = 'http://localhost:8080/board/first'
    const response = await axios.get(url)

    // データを受け取って再構成（1〜9番目に詰め替える）
    const formatBoard = (original: (Piece | null)[][]): (Piece | null)[][] => {
      const newBoard = createEmptyBoard()
      for (let y = 1; y <= 9; y++) {
        for (let x = 1; x <= 9; x++) {
          newBoard[x][y] = original[x]?.[y] || null
        }
      }
      return newBoard
    }

    board.value.myFormation = formatBoard(response.data.myFormation)
    board.value.opponentFormation = formatBoard(response.data.opponentFormation)
  } catch (error) {
    console.log(error)
  }
}

onMounted(getInitial)
</script>

<template>
  <div class="board-container">
    <h2>将棋盤</h2>
    <table class="board">
      <!-- 段 (上から下: 1〜9) -->
      <tr v-for="dan in 9" :key="dan">
        <!-- 筋 (右から左: 9→1) -->
        <td v-for="suji in [...Array(9).keys()].map((i) => 9 - i)" :key="suji">
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
</style>
