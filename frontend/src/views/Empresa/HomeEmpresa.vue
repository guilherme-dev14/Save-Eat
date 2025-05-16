<template>
  <NavigationDrawer>
    <div class="produtos-destaque">
      <h2>Produtos em Destaque</h2>
      <p v-if="erro">{{ erro }}</p>

      <div class="grid-produtos">
        <div class="produto-card" v-for="produto in produtos" :key="produto.id">
          <h3>{{ produto.nome }}</h3>
          <p>{{ produto.descricao }}</p>
          <p><s>R$ {{ produto.precoOriginal.toFixed(2) }}</s></p>
          <p><strong>R$ {{ produto.precoDesconto.toFixed(2) }}</strong></p>
          <p>Validade: {{ new Date(produto.dataValidade).toLocaleDateString() }}</p>
          <p>Categoria: {{ produto.categoria }}</p>
        </div>
      </div>
    </div>
  </NavigationDrawer>
</template>
<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue'
import NavigationDrawer from '@/components/NavigationDrawer.vue'
import { listarDestaques } from '@/services/produtoService'
import type { Produto } from '@/interface/produto'

export default defineComponent({
  components: { NavigationDrawer },
  name: 'HomeCliente',
  setup() {
    const produtos = ref<Produto[]>([])
    const erro = ref('')

    const carregarProdutos = async () => {
      try {
        produtos.value = await listarDestaques()
      } catch (e) {
        erro.value = 'Erro ao carregar produtos em destaque.'
      }
    }

    onMounted(carregarProdutos)

    return {
      produtos,
      erro
    }
  }
})
</script>

<style scoped>
.grid-produtos {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 20px;
}

.produto-card {
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}
</style>
