// stores/templateStore.ts
import { defineStore } from 'pinia'

interface Template {
  id: number
  name: string
}

export const useTemplateStore = defineStore('template', {
  state: () => ({
    templates: [] as Template[],
  }),
  actions: {
    setTemplates(newTemplates: Template[]) {
      this.templates = newTemplates
    },
  },
})
