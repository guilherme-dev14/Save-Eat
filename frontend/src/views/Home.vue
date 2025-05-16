<template>
    <NavigationDrawer>
        <div class="produtos-destaque">
            <h2>Destaques</h2>
            <p v-if="erro">{{ erro }}</p>

       <div class="grid-produtos">
                <div class="produto-card" v-for="produto in produtos" :key="produto.id">
                    <div class="produto-content">
                        <div class="info-row">
                            <span class="info-label">Categoria:</span>
                            <span class="info-value">{{ produto.categoria }}</span>
                        </div>
                        <div class="info-row">
                            <span class="info-label">Nome do Produto:</span>
                            <span class="info-value"><strong>{{ produto.nome }}</strong></span>
                        </div>
                        <div class="info-row">
                            <span class="info-label">Descrição:</span>
                            <span class="info-value">{{ produto.descricao }}</span>
                        </div>
                        <div class="info-row">
                            <span class="info-label">Preço Original:</span>
                            <span class="info-value"><s>R$ {{ produto.precoOriginal.toFixed(2) }}</s></span>
                        </div>
                        <div class="info-row">
                            <span class="info-label">Preço Atual:</span>
                            <span class="info-value"><strong>R$ {{ produto.precoDesconto.toFixed(2) }}</strong></span>
                        </div>
                        <div class="info-row">
                            <span class="info-label">Validade:</span>
                            <span class="info-value">{{ new Date(produto.dataValidade).toLocaleDateString() }}</span>
                        </div>
                    </div>
                    <div class="botao-container">
                        <button class="botao-ver-mais">
                            Ver mais
                        </button>
                    </div>
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
    name: 'Home',
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
  display: flex;
  flex-direction: column;
  height: 100%;
}

.produto-content {
  flex-grow: 1;
}

.info-row {
  display: flex;
  margin-bottom: 12px;
  align-items: flex-start;
}

.info-label {
  font-weight: 500;
  width: 110px;
  flex-shrink: 0;
  text-align: left;
}

.info-value {
  flex-grow: 1;
  text-align: left;
  padding-left: 5px;
}

.botao-container {
  margin-top: auto;
  display: flex;
  justify-content: center;
  padding-top: 15px;
}

.produtos-destaque h2 {
  margin-left: 0;
  padding-left: 0;
  text-align: left;
}

.botao-ver-mais {
  padding: 8px 16px;
  border: 2px solid #0066ff;
  background-color: transparent;
  color: #0066ff;
  font-weight: 600;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  width: 100%;
}

.botao-ver-mais:hover {
  background-color: #0066ff;
  color: white;
}
</style>