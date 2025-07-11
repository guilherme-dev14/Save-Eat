<template>
    <NavigationDrawer>
        <v-container class="tela-produto-detalhes">
            <div class="header">
                <h1 class="title">Produtos</h1>
            </div>

            <div class="tabs">
                <button v-for="tab in tabs" :key="tab" @click="selecionarTab(tab)"
                    :class="['tab-button', { active: selectedTab === tab }]">
                    {{ tab }}
                </button>
                <input type="text" placeholder="Pesquisar" class="search" v-model="termoBusca"
                    @input="paginaAtual = 1" />

                <v-btn :to="`/empresa/NovoProduto`" class="btn-novoProduto">
                    + Novo Produto
                </v-btn>
            </div>

            <div class="table-container">
                <table class="product-table">
                    <thead>
                        <tr>
                            <th>Nome</th>
                            <th>Descrição</th>
                            <th>Data de Validade</th>
                            <th>Preço com Desconto</th>
                            <th>Quantidade</th>
                            <th>Status</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(produto, index) in produtosPaginados" :key="index">
                            <td>
                                <div class="product-name">
                                    <div class="name-main">{{ produto.nome }}</div>
                                </div>
                            </td>
                            <td>{{ produto.descricao }}</td>
                            <td>{{ formatarData(produto.dataValidade) }}</td>
                            <td>
                                R$
                                {{ produto.precoDesconto != null ? Number(produto.precoDesconto).toFixed(2) : '0,00' }}
                            </td>
                            <td>{{ produto.quantidade }}</td>
                            <td><span class="badge">{{ produto.status }}</span></td>
                            <td>
                                <button @click="deletarProduto(produto.id)">
                                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="23" height="30"
                                        fill="rgba(29, 29, 29, 0.822)">
                                        <path
                                            d="M9,3V4H4V6H5V19A2,2 0 0,0 7,21H17A2,2 0 0,0 19,19V6H20V4H15V3H9M7,6H17V19H7V6M9,8V17H11V8H9M13,8V17H15V8H13Z" />
                                    </svg>
                                </button>
                            </td>

                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="pagination">
                <button @click="irParaPagina(paginaAtual - 1)" :disabled="paginaAtual === 1">‹ Anterior</button>
                <button v-for="pagina in paginasExibidas" :key="pagina" @click="irParaPagina(pagina)"
                    :class="{ active: paginaAtual === pagina, ellipsis: pagina === '...' }"
                    :disabled="pagina === '...'">
                    {{ pagina }}
                </button>
                <button @click="irParaPagina(paginaAtual + 1)" :disabled="paginaAtual === totalPaginas">Próxima
                    ›</button>
            </div>
        </v-container>
    </NavigationDrawer>
</template>
<script lang="ts">
import {
    listarTodosDaEmpresa,
    listarVencidosEmpresa,
    deletarProduto
} from '@/services/produtoService'
import Vue from 'vue'
import NavigationDrawer from '@/components/NavigationDrawer.vue'
import type { Produto, ProdutoEdit } from '@/interface/produto'

export default Vue.extend({
    components: {
        NavigationDrawer,
    },
    data() {
        return {
            selectedTab: 'Todos',
            tabs: ['Todos', 'Vencidos'],
            paginaAtual: 1,
            produtos: [] as ProdutoEdit[],
            termoBusca: '',
            carregando: false
        }
    },
    computed: {
        produtosFiltrados(): ProdutoEdit[] {
            if (!this.termoBusca) return this.produtos
            const termo = this.termoBusca.toLowerCase()
            return this.produtos.filter(p =>
                p.nome?.toLowerCase().includes(termo)
            )
        },
        produtosPaginados(): ProdutoEdit[] {
            const inicio = (this.paginaAtual - 1) * 10
            return this.produtosFiltrados.slice(inicio, inicio + 10)
        },
        totalPaginas(): number {
            return Math.ceil(this.produtosFiltrados.length / 10)
        },
        paginasExibidas(): (number | string)[] {
            const total = this.totalPaginas
            const atual = this.paginaAtual
            const range: (number | string)[] = []

            if (total <= 7) {
                for (let i = 1; i <= total; i++) range.push(i)
            } else {
                if (atual <= 4) {
                    range.push(1, 2, 3, 4, 5, '...', total)
                } else if (atual >= total - 3) {
                    range.push(1, '...', total - 4, total - 3, total - 2, total - 1, total)
                } else {
                    range.push(1, '...', atual - 1, atual, atual + 1, '...', total)
                }
            }

            return range
        }
    },
    methods: {
        async selecionarTab(tab: string) {
            this.selectedTab = tab
            this.paginaAtual = 1
            this.carregando = true
            const idEmpresa = Number(localStorage.getItem('idEmpresa'));
            if (!idEmpresa) {
                console.error('idEmpresa não encontrado no localStorage');
                return;
            }

            try {
                if (tab === 'Todos') {
                    this.produtos = await listarTodosDaEmpresa(idEmpresa);
                } else if (tab === 'Vencidos') {
                    this.produtos = await listarVencidosEmpresa(idEmpresa);
                }
            } catch (error) {
                console.error('Erro ao buscar produtos:', error)
            } finally {
                this.carregando = false
            }
        },
        irParaPagina(pagina: number) {
            if (typeof pagina === 'number' && pagina >= 1 && pagina <= this.totalPaginas) {
                this.paginaAtual = pagina
            }
        },
        formatarData(dataStr: string): string {
            if (!dataStr) return ''
            const data = new Date(dataStr)
            if (isNaN(data.getTime())) return dataStr
            return data.toLocaleDateString('pt-BR')
        },
        async deletarProduto(id: number) {
            if (!id) {
                console.error('Produto não encontrado');
                return;
            }
            await deletarProduto(id);
            this.produtos = this.produtos.filter(produto => produto.id !== id);
        }
    },
    async created() {
        await this.selecionarTab(this.selectedTab)
    }
})
</script>



<style scoped>
.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 24px;
}

.title {
    font-size: 28px;
    font-weight: bold;
}

.tabs {
    display: flex;
    gap: 12px;
    border-bottom: 1px solid #ddd;
    margin-bottom: 16px;
    align-items: center;
    width: 100%;
}

.search {
    padding: 8px 12px;
    border: 1px solid #ccc;
    border-radius: 2px;
    font-size: 14px;
    margin-left: auto;
}

.tab-button {
    padding: 8px 16px;
    border: none;
    background: none;
    font-size: 14px;
    cursor: pointer;
    color: #666;
    border-bottom: 2px solid transparent;
}

.tab-button.active {
    color: #007bff;
    border-color: #007bff;
    font-weight: bold;
}

.table-container {
    overflow-x: auto;
    background: white;
    border-radius: 8px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.product-table {
    width: 100%;
    border-collapse: collapse;
}

.product-table th,
.product-table td {
    padding: 12px;
    text-align: left;
    border-bottom: 1px solid #eee;
}

.product-name {
    display: flex;
    align-items: center;
    gap: 10px;
}

.name-main {
    font-weight: bold;
}

.badge {
    background-color: #f0f0f0;
    color: #333;
    font-size: 12px;
    padding: 4px 8px;
    border-radius: 12px;
}

.pagination {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    gap: 6px;
    font-size: 14px;
    align-items: center;
}

.pagination button {
    padding: 6px 12px;
    border: none;
    background: transparent;
    cursor: pointer;
    color: #007bff;
    border-radius: 4px;
}

.pagination button.active {
    background-color: #cce0ff;
    font-weight: bold;
    color: #000;
}

.pagination button:disabled {
    color: #999;
    cursor: not-allowed;
}

.pagination button.ellipsis {
    cursor: default;
    color: #555;
    background: none;
}

::v-deep main.content {
    padding: 0 !important;
    background-color: #f5f5f5 !important;
}

.tela-produto-detalhes {
    background-color: rgba(212, 199, 199, 0);
    min-height: 100vh;
    padding-top: 32px;
    padding: 30px;
}

.btn-novoProduto {
    background-color: #007bff !important;
    color: white !important;
    font-size: 12px;

}
</style>
