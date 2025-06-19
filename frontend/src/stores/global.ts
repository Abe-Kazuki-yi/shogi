// src/stores/global.ts
import { defineStore } from 'pinia'

export const useGlobalStore = defineStore('global', {
  state: () => ({
    turn: 1,
    currentUser: 'player1',
  }),
  actions: {
    nextTurn() {
      this.turn++
    },
    resetTurn() {
      this.turn = 1
    },
  },
})
