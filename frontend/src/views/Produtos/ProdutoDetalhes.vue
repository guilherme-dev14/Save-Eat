<template>
    <NavigationDrawer>
        <div class="produto-detalhes">
            <h2>{{ produto.nome }}</h2>
            <p><strong>Descrição:</strong>{{ produto.descricao }}</p>
            <p><strong>{{ produto.quantidade }} produtos em estoque</strong></p>
            <p><strong>Categorias:</strong>{{ produto.categoria }}</p>
            <p>Data de Validade</p>
            <BaseInput id="dataValidade" type="date" v-model="produto.dataValidade" label="Data de Validade"
                :readonly="true" />
            <p>Status</p>
            <BaseInput id="status" v-model="produto.status" label="Status do Produto" :readonly="true" />
            <p>Preço Original</p>
            <BaseInput id="precoOriginal" v-model="produto.precoOriginal" label="Preço Original" :readonly="true" />
            <p>Preço com Desconto</p>
            <BaseInput id="precoDesconto" v-model="produto.precoDesconto" label="Preço com Desconto" :readonly="true" />
            <p>Categoria</p>
            <BaseInput id="categoria" v-model="produto.categoria" label="Categoria do Produto" :readonly="true" />
            <p>Empresa Responsável</p>
            <BaseInput id="empresa" v-model="produto.empresa.nome" label="Empresa Responsável" :readonly="true" />
            <p>Logradouro</p>
            <BaseInput id="logradouro" v-model="produto.empresa.localizacao.logradouro" label="Logradouro"
                :readonly="true" />
            <p>Número</p>
            <BaseInput id="numero" v-model="produto.empresa.localizacao.numero" label="Número" :readonly="true" />
            <p>Bairro</p>
            <BaseInput id="bairro" v-model="produto.empresa.localizacao.bairro" label="Bairro" :readonly="true" />
            <p>Cidade</p>
            <BaseInput id="cidade" v-model="produto.empresa.localizacao.cidade" label="Cidade" :readonly="true" />
        </div>
    </NavigationDrawer>
</template>

<script>
import NavigationDrawer from '@/components/NavigationDrawer.vue'
import BaseInput from '@/components/BaseInput.vue'
import { mapState } from 'vuex'

export default {
    name: 'ProdutoDetalhes',
    components: {
        NavigationDrawer,
        BaseInput,
    },
    computed: {
        // Supondo que o produto esteja salvo no Vuex (ajuste o nome do módulo se necessário)
        ...mapState('produtos', ['produto']),
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
