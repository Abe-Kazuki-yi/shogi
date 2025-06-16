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
  })

  const lists = ref<Move[]>([])

  const getInitial = async () => {
    const url = 'http://localhost:8080/board/first'
    const response = await axios.get(url)
    board.value.myFormation = response.data.myFormation
    board.value.opponentFormation = response.data.opponentFormation
  }

  const getLists = async () => {
    const url = 'http://localhost:8080/list/1'
    const response = await axios.get(url)
    lists.value = response.data
  }

  onMounted(getInitial)
  onMounted(getLists)

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
  }
}
