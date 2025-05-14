<template>
  <div class="empresa-home">
    <h1 class="titulo">Produtos em Destaque</h1>
    <div class="grid">
      <div v-for="produto in produtos" :key="produto.id" class="card">
        <p class="categoria">{{ produto.categoria }}</p>
        <h2 class="nome">{{ produto.nome }}</h2>
        <p class="descricao">{{ produto.descricao }}</p>
        <p class="preco">R$ {{ produto.precoDesconto.toFixed(2) }}</p>
        <button class="vermais">Ver mais</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { listarDestaques } from '@/services/produtoService'
import type { Produto } from '@/types/produto'

const produtos = ref<Produto[]>([])

onMounted(async () => {
  try {
    const resultado = await listarDestaques()
    console.log('Produtos retornados:', resultado)
    produtos.value = resultado
  } catch (err) {
    console.error('Erro ao buscar destaques:', err)
  }
})
</script>

<style scoped>
.empresa-home {
  padding: 2rem;
}
.titulo {
  font-size: 1.8rem;
  margin-bottom: 1rem;
}
.grid {
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
}
.card {
  width: 240px;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 1rem;
  background: #fff;
}
.categoria {
  font-size: 0.8rem;
  color: #777;
}
.nome {
  font-size: 1.2rem;
  font-weight: bold;
}
.descricao {
  margin: 0.5rem 0;
  font-size: 0.9rem;
}
.preco {
  font-weight: bold;
  color: #007bff;
}
.vermais {
  margin-top: 0.5rem;
  background-color: #007bff;
  color: white;
  border: none;
  padding: 0.4rem 0.8rem;
  border-radius: 4px;
  cursor: pointer;
}
</style>
