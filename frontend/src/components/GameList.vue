<script setup lang="ts">
import { useBoardStore } from '@/stores/useBoardStore'
import { useNumStore } from '@/stores/useNumStore'
import { useTemplateStore } from '@/stores/useTemplateStore'
import axios from 'axios'
import { onMounted, ref } from 'vue'
import type { List } from '@/types/List'
import type { Square } from '@/types/Square'

const numStore = useNumStore()
const templateStore = useTemplateStore()
const boardStore = useBoardStore()
const lists = ref<List[]>([])
const templateIds = templateStore.templates.map((template) => template.id)

const getListsWithNum = async (num: number) => {
  const url = 'http://localhost:8080/list/' + num
  const res = await axios.post(url, templateIds)
  lists.value = res.data

  const beforePositions: Square[] = lists.value.map((list) => ({
    x: list.beforeX,
    y: list.beforeY,
  }))

  const targetPositions: Square[] = lists.value.map((list) => ({
    x: list.targetX,
    y: list.targetY,
  }))

  boardStore.setBeforeSquares(beforePositions)
  boardStore.setTargetSquares(targetPositions)
}

onMounted(() => getListsWithNum(numStore.num))
</script>
<template>
  <main>
    <h3>現在{{ numStore.num }}手目</h3>
    <ul>
      <li v-for="list in lists" :key="list.id">
        {{ list.targetX }}{{ list.targetY
        }}{{ boardStore.myFormation[list.beforeX]?.[list.beforeY]?.displayName
        }}{{ list.isPromoted ? '成' : '' }}
      </li>
    </ul>
  </main>
</template>
