import { defineStore } from 'pinia'

export const useNumStore = defineStore('number', {
  state: () => ({
    num: 0, // ← 初期値は数値で指定
  }),
  actions: {
    setNum(num: number) {
      this.num = num
    },
    addNum() {
      this.num++
    },
  },
})
