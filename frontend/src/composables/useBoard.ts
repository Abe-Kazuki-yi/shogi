import { ref, onMounted, computed } from 'vue'
import axios from 'axios'

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

const createEmptyBoard = (): (Piece | null)[][] => {
  return Array.from({ length: 10 }, () => Array(10).fill(null))
}

export function useBoard() {
  const board = ref({
    myFormation: createEmptyBoard(),
    opponentFormation: createEmptyBoard(),
    myHand: new Map<Piece, number>(),
    opponentHand: new Map<Piece, number>(),
  })

  const lists = ref<Move[]>([])

  const getInitial = async () => {
    const url = 'http://localhost:8080/board/first'
    const response = await axios.get(url)
    board.value.myFormation = response.data.myFormation
    board.value.opponentFormation = response.data.opponentFormation
    board.value.myHand = response.data.myHand
    board.value.opponentHand = response.data.opponentHand
  }

  const getLists = async () => {
    const url = 'http://localhost:8080/list/1'
    const response = await axios.get(url)
    lists.value = response.data
  }

  onMounted(async () => {
    await getInitial()
    await getLists()
  })

  const getNextBoard = async () => {
    const url = 'http://localhost:8080/board/next/me'
    const response = await axios.post(
      url,
      lists.value.find((m) => m.id === 1),
    )
    board.value.myFormation = response.data.myFormation
    board.value.opponentFormation = response.data.opponentFormation
    board.value.myHand = response.data.myHand
    board.value.opponentHand = response.data.opponentHand
  }

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

  return {
    board,
    lists,
    highlightMap,
    getNextBoard,
  }
}
