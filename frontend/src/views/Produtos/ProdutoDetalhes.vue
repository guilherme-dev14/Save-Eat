<template>
    <v-container>
        <NavigationDrawer>
            <h1>Mais Detalhes do Produto</h1>
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

.produto-detalhes h1,
p {
    margin-bottom: 16px;
    text-align: left;
}
</style>
