import { defineStore } from 'pinia'

interface Template {
  id: number
  name: string
  content: string
}

export const useTemplateStore = defineStore('template', {
  state: () => ({
    templates: [] as Template[],
    isFirst: '' as string,
  }),
  actions: {
    setTemplates(newTemplates: Template[]) {
      this.templates = newTemplates
    },
    setIsFirst(value: string) {
      this.isFirst = value
    },
  },
})
