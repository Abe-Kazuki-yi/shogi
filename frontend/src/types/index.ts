export interface Piece {
  id: number
  displayName: string
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
