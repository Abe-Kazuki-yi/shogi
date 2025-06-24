import { defineStore } from 'pinia'
import type { Square } from '@/types/Square'

interface Piece {
  id: number
  displayName: string
}

interface Board {
  myFormation: (Piece | null)[][]
  opponentFormation: (Piece | null)[][]
  myHand: Record<string, number>
  opponentHand: Record<string, number>
  isPlay: boolean
}

export const useBoardStore = defineStore('board', {
  state: () => ({
    myFormation: [] as (Piece | null)[][],
    opponentFormation: [] as (Piece | null)[][],
    myHand: {} as Record<string, number>,
    opponentHand: {} as Record<string, number>,
    isPlay: true,
    beforeSquares: [] as Square[],
    targetSquares: [] as Square[],
    selectedSquare: null as Square | null,
    movableSquare: [] as Square[],
  }),
  actions: {
    setBoardData(data: Board) {
      this.myFormation = data.myFormation
      this.opponentFormation = data.opponentFormation
      this.myHand = data.myHand
      this.opponentHand = data.opponentHand
      this.isPlay = data.isPlay
    },
    setBeforeSquares(data: Square[]) {
      this.beforeSquares = data
    },
    setTargetSquares(data: Square[]) {
      this.targetSquares = data
    },
    setSelectedSquare(sq: Square | null) {
      this.selectedSquare = sq
    },
    setMovableSquare(sq: Square[]) {
      this.movableSquare = sq
    },
  },
})
