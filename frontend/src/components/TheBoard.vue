<script setup lang="ts">
import { useBoardStore } from '@/stores/useBoardStore'
import { useNumStore } from '@/stores/useNumStore'
import axios from 'axios'
import { ref, onMounted, computed, onBeforeUnmount } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const isFirst = route.query.isFirst === 'true'
const boardStore = useBoardStore()
const numStore = useNumStore()
const boardRef = ref<HTMLElement | null>(null)
const promotionPending = ref(false)
const inputLocked = ref(false)
const promoteTarget = ref<{ x: number; y: number } | null>(null)

/*初期盤面を作成します。onMountedです*/
const getInitialBoard = async () => {
  const url = 'http://localhost:8080/board/initial/' + isFirst
  const res = await axios.get(url)
  boardStore.setBoardData(res.data)
}

/*持ち駒を表示するときに必要です。chatGPT産*/
const filteredMyHand = computed(() => {
  const hand = boardStore.myHand
  if (!Array.isArray(hand)) return []
  const result = []
  for (const item of hand) {
    if (item.count > 0) {
      result.push(item)
    }
  }
  return result
})

const filteredOpponentHand = computed(() => {
  const hand = boardStore.opponentHand
  if (!Array.isArray(hand)) return []
  const result = []
  for (const item of hand) {
    if (item.count > 0) {
      result.push(item)
    }
  }
  return result
})

/*GameList.vueで受け取ったテンプレートの手を読み込み、その場所に色を変えるために使います。ChatGPT産*/
const isBeforeSquare = (x: number, y: number): boolean =>
  boardStore.beforeSquares.some((sq) => sq.x === x && sq.y === y)

const isTargetSquare = (x: number, y: number): boolean =>
  boardStore.targetSquares.some((sq) => sq.x === x && sq.y === y)

/*chatGPT産*/
const handlePromotion = async (promote: boolean) => {
  try {
    if (promote && promoteTarget.value) {
      const res = await axios.post('http://localhost:8080/board/promote', promoteTarget.value)
      boardStore.setBoardData(res.data)
    }
  } catch (err) {
    console.error('Promotion failed', err)
  } finally {
    promotionPending.value = false
    inputLocked.value = false
    numStore.addNum()
  }
}

/*マス目をクリックしたとき①初回なら選択状態にする。②どこかが選択済みなら移動可能か調べて、動かすor選択を解除 半分ChatGPT産*/
const handleClickCell = async (x: number, y: number) => {
  if (inputLocked.value) return

  /*chatGPT産*/
  if (boardStore.selectedHandPiece) {
    const res = await axios.get('http://localhost:8080/board/dropable')
    boardStore.setMovableSquare(res.data)
    // 盤面に自分の駒があるかチェック

    try {
      inputLocked.value = true
      const res = await axios.post('http://localhost:8080/board/drop', {
        piece: {
          id: boardStore.selectedHandPiece?.id,
          displayName: boardStore.selectedHandPiece?.displayName,
        },
        square: { x, y },
      })

      if (res.data.myFormation) {
        boardStore.setBoardData(res.data)
        numStore.addNum()
        boardStore.setSelectedHandPiece(null) // 持ち駒選択解除
        boardStore.setSelectedSquare(null)
        boardStore.setMovableSquare([])
      } else {
        alert('ここには置けません。')
      }
    } catch (err) {
      console.error(err)
      alert('駒の配置に失敗しました。')
    } finally {
      inputLocked.value = false
    }

    return
  }

  if (!boardStore.selectedSquare) {
    const url = 'http://localhost:8080/board/select/' + x + '/' + y
    const res1 = await axios.get<boolean>(url)
    const isSelectable = res1.data
    boardStore.setSelectedSquare(isSelectable ? { x, y } : null)

    const from = boardStore.selectedSquare
    if (from) {
      const res2 = await axios.post('http://localhost:8080/board/movable', from)
      boardStore.setMovableSquare(res2.data)
    }
  } else {
    const isMovable = boardStore.movableSquare.some((square) => square.x === x && square.y === y)

    if (isMovable) {
      const from = [boardStore.selectedSquare.x, boardStore.selectedSquare.y]
      const to = [x, y]
      const res = await axios.post('http://localhost:8080/board/move', {
        from,
        to,
      })

      boardStore.setBoardData(res.data.boardDTO)

      if (res.data.promotable) {
        promotionPending.value = true
        inputLocked.value = true
        promoteTarget.value = { x, y }
        boardStore.setSelectedSquare(null)
        boardStore.setMovableSquare([{ x, y }])
      } else {
        boardStore.setSelectedSquare(null)
        boardStore.setMovableSquare([])
        numStore.addNum()
      }
    } else {
      boardStore.setSelectedSquare(null)
      boardStore.setMovableSquare([])
      const url = 'http://localhost:8080/board/select/' + x + '/' + y
      const res1 = await axios.get<boolean>(url)
      const isSelectable = res1.data
      boardStore.setSelectedSquare(isSelectable ? { x, y } : null)

      const from = boardStore.selectedSquare
      if (from) {
        const res2 = await axios.post('http://localhost:8080/board/movable', from)
        boardStore.setMovableSquare(res2.data)
      }
    }
  }
}

/*ゲーム盤の外をクリックしたときに駒の選択を消します。ChatGPT産*/
const handleClickOutside = (event: MouseEvent) => {
  if (boardRef.value && !boardRef.value.contains(event.target as Node)) {
    boardStore.setSelectedSquare(null)
    boardStore.setMovableSquare([])
  }
}

function getFontClass(piece?: { displayName?: string }) {
  const text = piece?.displayName ?? ''
  if (text.length === 1) return 'one-char'
  if (text.length === 2) return 'two-char'
  return 'three-char'
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
  getInitialBoard()
})

onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside)
})
</script>

<template>
  <div class="hand opponent-hand">
    <span v-for="item in filteredOpponentHand" :key="item.piece.id">
      <span class="rotated">{{ item.piece.displayName }} ×{{ item.count }}</span>
    </span>
  </div>

  <h1>将棋盤</h1>
  <table ref="boardRef" border="1" cellspacing="0" cellpadding="10">
    <tr v-for="y in 9" :key="y">
      <td
        v-for="x in [9, 8, 7, 6, 5, 4, 3, 2, 1]"
        :key="x"
        class="cell"
        :class="{
          'highlight-before': isBeforeSquare(x, y),
          'highlight-target': isTargetSquare(x, y),
          'selected-border':
            boardStore.selectedSquare?.x === x && boardStore.selectedSquare?.y === y,
          'movable-border': boardStore.movableSquare?.some(
            (square) => square.x === x && square.y === y,
          ),
        }"
        @click="handleClickCell(x, y)"
      >
        <template v-if="boardStore.opponentFormation[x]?.[y]">
          <span class="piece rotated" :class="getFontClass(boardStore.opponentFormation[x][y])">
            {{ boardStore.opponentFormation[x][y].displayName }}
          </span>
        </template>
        <template v-else-if="boardStore.myFormation[x]?.[y]">
          <span class="piece" :class="getFontClass(boardStore.myFormation[x][y])">
            {{ boardStore.myFormation[x][y].displayName }}
          </span>
        </template>

        <div
          v-if="promotionPending && promoteTarget?.x === x && promoteTarget?.y === y"
          class="promotion-buttons"
          @click.stop
        >
          <button @click="handlePromotion(true)">成る</button>
          <button @click="handlePromotion(false)">成らない</button>
        </div>
      </td>
    </tr>
  </table>

  <div class="hand my-hand">
    <span
      v-for="item in filteredMyHand"
      :key="item.piece.id"
      :class="{ selected: boardStore.selectedHandPiece === item.piece }"
      @click="
        () => {
          boardStore.setSelectedHandPiece(
            item.piece === boardStore.selectedHandPiece ? null : item.piece,
          )
          boardStore.setSelectedSquare(null)
          boardStore.setMovableSquare([])
        }
      "
    >
      {{ item.piece.displayName }} ×{{ item.count }}
    </span>
  </div>
</template>
<style scoped>
table {
  border-collapse: collapse;
  margin: 20px auto;
}

.cell {
  width: 60px; /* 幅を拡大 */
  height: 60px; /* 高さも拡大 */
  text-align: center;
  vertical-align: middle;
  font-size: 20px; /* フォントサイズも大きく */
  font-family: 'serif';
  border: 1px solid #000;
}

.hand {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin: 10px 0;
  font-size: 18px;
  font-family: 'serif';
}

.opponent-hand {
  margin-bottom: 20px;
}

.my-hand {
  margin-top: 20px;
}

.rotated {
  display: inline-block;
  transform: rotate(180deg);
}

.highlight-before {
  background-color: rgb(236, 236, 202);
}

.highlight-target {
  background-color: rgb(218, 250, 190);
}
.selected-border {
  border: 3px solid #ffa500; /* オレンジなど目立つ色に */
}
.movable-border {
  border: 3px solid #8f18f8; /* 紫など目立つ色に */
}
.piece {
  display: inline-block;
  width: 24px;
  height: 24px;
  text-align: center;
  line-height: 24px;
  font-size: 20px;
  font-family: 'serif';
  white-space: nowrap;
  overflow: hidden;
}

.one-char {
  font-size: 24px;
}

.two-char {
  font-size: 12px;
  writing-mode: vertical-rl;
  text-orientation: upright;
}

.three-char {
  font-size: 8px;
  writing-mode: vertical-rl;
  text-orientation: upright;
}

td {
  position: relative;
}

.promotion-buttons {
  writing-mode: horizontal-tb;
  position: absolute;
  top: 5px; /* 少しだけ上に */
  left: 100%; /* マスの右側に配置 */
  margin-left: 5px; /* 右に少し余白 */
  background-color: #fff;
  border: 1px solid #aaa;
  padding: 4px;
  border-radius: 4px;
  z-index: 10;
  display: flex;
  flex-direction: column;
  gap: 4px;
  font-size: 12px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}
.my-hand span.selected {
  background-color: #ffc; /* ハイライト色 */
  border: 1px solid #aaa;
  border-radius: 4px;
  padding: 2px 4px;
}
</style>
