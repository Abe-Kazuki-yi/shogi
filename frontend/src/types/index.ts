export interface Piece {
  id: number
  name: string
  promoted: string | null
}

export interface Move {
  id: number
  num: number
  beforeId: number
  beforeX: number
  beforeY: number
  targetX: number
  targetY: number
  promoted: boolean
}
