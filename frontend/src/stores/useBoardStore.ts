import { defineStore } from 'pinia'
import type { Square } from '@/types/Square'

interface Piece {
  id: number
  displayName: string
}

interface HandPiece {
  piece: Piece
  count: number
}

interface Board {
  myFormation: (Piece | null)[][]
  opponentFormation: (Piece | null)[][]
  myHand: HandPiece[]
  opponentHand: HandPiece[]
  isPlay: boolean
}

export const useBoardStore = defineStore('board', {
  state: () => ({
    myFormation: [] as (Piece | null)[][],
    opponentFormation: [] as (Piece | null)[][],
    myHand: [] as HandPiece[],
    opponentHand: [] as HandPiece[],
    isPlay: true,
    beforeSquares: [] as Square[],
    targetSquares: [] as Square[],
    selectedSquare: null as Square | null,
    movableSquare: [] as Square[],
    selectedHandPiece: null as Piece | null,
  }),
  actions: {
    setBoardData(data: Board) {
      this.myFormation = data.myFormation
      this.opponentFormation = data.opponentFormation
      this.myHand = data.myHand // ← サーバーから配列で受け取る前提
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
    setSelectedHandPiece(piece: Piece | null) {
      this.selectedHandPiece = piece
    },
  },
})
