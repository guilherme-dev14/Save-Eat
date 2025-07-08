<template>
    <NavigationDrawer>
        <v-container class="tela-produto-detalhes">
            <v-row class="header-linha">
                <v-col cols="9">
                    <h1 class="titulo-pagina">Mais Detalhes do Produto</h1>
                </v-col>
                <v-col cols="3" class="text-right">
                    <v-btn class="bt-voltar" @click="$router.go(-1)">
                        Voltar
                    </v-btn>
                </v-col>
            </v-row>

            <!-- Caixa com fundo branco -->
            <div class="produto-detalhes" v-if="produto">
                <h1>{{ produto.nome }}</h1>
                <p>{{ produto.descricao }}</p>
                <p>{{ produto.quantidade }} produtos em estoque</p>
                <v-row>
                    <v-col cols="6">
                        <BaseInput id="dataValidade" type="date" v-model="produto.dataValidade" label="Data de Validade"
                            :readonly="true" />
                    </v-col>
                    <v-col cols="6">
                        <BaseInput id="status" v-model="produto.status" label="Status" :readonly="true" />
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="6">
                        <BaseInput id="precoOriginal" v-model="produto.precoOriginal" label="Preço Original"
                            :readonly="true" />
                    </v-col>
                    <v-col cols="6">
                        <BaseInput id="precoDesconto" v-model="produto.precoDesconto" label="Preço com Desconto"
                            :readonly="true" />
                    </v-col>
                </v-row>
                <BaseInput id="categoria" v-model="produto.categoria" label="Categoria" :readonly="true" />
                <v-row>
                    <v-col cols="6">
                        <BaseInput id="empresa" v-model="empresa.nome" label="Empresa Responsável" :readonly="true" />
                    </v-col>
                    <v-col cols="6">
                        <BaseInput id="logradouro" v-model="empresa.localizacao.logradouro" label="Logradouro"
                            :readonly="true" />
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="6">
                        <BaseInput id="numero" v-model="empresa.localizacao.numero" label="Número" :readonly="true" />
                    </v-col>
                    <v-col cols="6">
                        <BaseInput id="bairro" v-model="empresa.localizacao.bairro" label="Bairro" :readonly="true" />
                    </v-col>
                </v-row>
                <BaseInput id="cidade" v-model="empresa.localizacao.cidade" label="Cidade" :readonly="true" />
            </div>
        </v-container>
    </NavigationDrawer>
</template>

<script lang="ts">
import NavigationDrawer from '@/components/NavigationDrawer.vue'
import BaseInput from '@/components/BaseInput.vue'
import { buscarPorId } from '@/services/produtoService'
import { buscarPorIdEmpresa } from '@/services/empresaService'
import type { Produto } from '@/interface/produto'
import type { Empresa } from '@/interface/empresa'
import { Route } from 'vue-router'
import Vue from 'vue'

export default Vue.extend({
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
        const route = this.$route as Route;
        const id = Number(route.params.id);
        try {
            const resposta = await buscarPorId(id);
            this.produto = resposta;
            const empresa = await buscarPorIdEmpresa(resposta.idEmpresa);
            this.empresa = empresa;
        } catch (e) {
            console.error('Erro ao carregar produto:', e);
        }
    },
})
</script>


<style scoped>
.text-right {
    text-align: right;
}

.tela-produto-detalhes {
    background-color: #f5f5f5;
    min-height: 100vh;
    padding-top: 32px;
    padding: 30px;
}

.header-linha {
    margin-bottom: 15px;
}

.produto-detalhes {
    background-color: #ffffff;
    padding: 32px;
    border-radius: 2px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.493);
}

.produto-detalhes h1,
.produto-detalhes p {
    margin-bottom: 16px;
    text-align: left;
}

.v-container {
    padding: 0 !important;
}

.bt-voltar {
    color: rgb(24, 119, 207);
}

.titulo-pagina {
    font-weight: bold;
    text-align: left;
}

::v-deep main.content {
    padding: 0 !important;
    background-color: #f5f5f5 !important;
}
</style>
