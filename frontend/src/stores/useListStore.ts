import { defineStore } from 'pinia'

interface List {
  id: number
  beforeId: number
  beforeX: number
  beforeY: number
  targetX: number
  targetY: number
  isPromoted: boolean
}

export const useListStore = defineStore('list', {
  state: () => ({
    lists: [] as List[],
  }),
  actions: {
    setList(newLists: List[]) {
      this.lists = newLists
    },
  },
})
