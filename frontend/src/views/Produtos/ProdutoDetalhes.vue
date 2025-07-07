<template>
    <v-container>
        <NavigationDrawer>
            <h1>Mais Detalhes do Produto</h1>
            <div class="produto-detalhes" v-if="produto">

                <h2>{{ produto.nome }}</h2>
                <p><strong>Descrição:</strong>{{ produto.descricao }}</p>
                <p><strong>{{ produto.quantidade }} produtos em estoque</strong></p>
                <p><strong>Categorias:</strong>{{ produto.categoria }}</p>
                <v-row>
                    <v-col cols="4">
                        <BaseInput id="dataValidade" type="date" v-model="produto.dataValidade" label="Data de Validade"
                            :readonly="true" />
                    </v-col>
                    <v-col cols="4">
                        <BaseInput id="status" v-model="produto.status" label="Status do Produto" :readonly="true" />
                    </v-col>

                </v-row>

                <BaseInput id="precoOriginal" v-model="produto.precoOriginal" label="Preço Original" :readonly="true" />
                <BaseInput id="precoDesconto" v-model="produto.precoDesconto" label="Preço com Desconto"
                    :readonly="true" />
                <BaseInput id="categoria" v-model="produto.categoria" label="Categoria do Produto" :readonly="true" />
                <BaseInput id="empresa" v-model="empresa.nome" label="Empresa Responsável" :readonly="true" />
                <BaseInput id="logradouro" v-model="empresa.localizacao.logradouro" label="Logradouro"
                    :readonly="true" />
                <BaseInput id="numero" v-model="empresa.localizacao.numero" label="Número" :readonly="true" />
                <BaseInput id="bairro" v-model="empresa.localizacao.bairro" label="Bairro" :readonly="true" />
                <BaseInput id="cidade" v-model="empresa.localizacao.cidade" label="Cidade" :readonly="true" />
            </div>
            <div v-else>
                <p>Carregando detalhes do produto...</p>
            </div>
        </NavigationDrawer>
    </v-container>
</template>

<script lang="ts">
import NavigationDrawer from '@/components/NavigationDrawer.vue'
import BaseInput from '@/components/BaseInput.vue'
import { buscarPorId } from '@/services/produtoService'
import type { Produto } from '@/interface/produto'
import type { Empresa } from '@/interface/empresa'
import { buscarPorIdEmpresa } from '@/services/empresaService'
export default {
    name: 'ProdutoDetalhes',
    components: {
        NavigationDrawer,
        BaseInput,
    },
    data() {
        return {
            produto: null as Produto | null,
            empresa: null as Empresa | null,
        }
    },
    async mounted() {
        const id = Number(this.$route.params.id);
        try {
            const resposta = await buscarPorId(id);
            this.produto = resposta;
            const empresa = await buscarPorIdEmpresa(resposta.idEmpresa);
            this.empresa = empresa;
        } catch (e) {
            console.error('Erro ao carregar produto:', e);
        }

    },

}
</script>



<style scoped>
.produto-detalhes {
    padding: 20px;
}

.produto-detalhes h2 {
    margin-bottom: 16px;
}

.produto-detalhes p {
    margin: 8px 0;
}
</style>
