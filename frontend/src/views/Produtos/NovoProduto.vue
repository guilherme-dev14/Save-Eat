<template>
    <NavigationDrawer>
        <v-container class="tela-produto-novo">
            <v-row class="header-linha">
                <v-col cols="9">
                    <h1 class="titulo-pagina">Cadastro de Produtos</h1>
                </v-col>
                <v-col cols="3" class="text-right">
                    <v-btn class="bt-voltar" @click="$router.go(-1)">
                        Cancelar
                    </v-btn>
                </v-col>
            </v-row>
            <v-snackbar v-model="snackbar.show" :color="snackbar.color" timeout="3000">
                {{ snackbar.msg }}
            </v-snackbar>

            <v-card>
                <div>
                    <v-card-title>
                        <strong>Detalhes</strong>
                    </v-card-title>
                    <v-card-text>
                        <v-form>
                            <BaseInput label="Nome" v-model="form.nome" />

                            <v-row>
                                <v-col cols="4">
                                    <BaseInput label="Preço Original" v-model="form.precoOriginal" />
                                </v-col>
                                <v-col cols="4">
                                    <BaseInput label="Preço com Desconto" v-model="form.precoDesconto" />
                                </v-col>
                                <v-col cols="4">
                                    <BaseInput label="Quantidade" v-model="form.quantidade" />
                                </v-col>
                            </v-row>

                            <BaseInput label="Descrição" v-model="form.descricao" />

                            <v-row>
                                <v-col cols="6">
                                    <BaseInput label="Data de Validade" v-model="form.dataValidade" type="date" />
                                </v-col>
                                <v-col cols="6">
                                    <BaseInput label="Status" v-model="form.status" />
                                </v-col>
                            </v-row>

                            <BaseInput label="Categoria" v-model="form.categoria" />

                            <v-btn class="bt-salvar" :loading="loading" :disabled="loading" @click="salvarProduto">
                                Salvar
                            </v-btn>

                        </v-form>
                    </v-card-text>
                </div>
            </v-card>

        </v-container>
    </NavigationDrawer>
</template>

<script lang="ts">
import Vue from 'vue';
import NavigationDrawer from '@/components/NavigationDrawer.vue';
import BaseInput from '@/components/BaseInput.vue';
import { Produto } from '@/interface/produto';
import { adicionar } from '@/services/produtoService';   // ajuste o path
export default Vue.extend({
    name: 'ProdutoNovo',
    components: { NavigationDrawer, BaseInput },

    data() {
        return {
            loading: false,
            form: {
                nome: '',
                precoOriginal: 0,
                precoDesconto: 0,
                quantidade: 0,
                descricao: '',
                dataValidade: '',
                status: '',
                categoria: '',
                idEmpresa: Number(localStorage.getItem('idEmpresa')),
            } as Produto,
            snackbar: { show: false, msg: '', color: '' },
        };
    },

    methods: {

        isFormValid(): boolean {
            if (!this.form.nome.trim()) { this.showSnack('Nome é obrigatório', 'error'); return false; }
            if (this.form.precoOriginal < 0 || this.form.precoDesconto < 0) {
                this.showSnack('Preços não podem ser negativos', 'error'); return false;
            }
            return true;
        },

        showSnack(msg: string, type: 'success' | 'error') {
            this.snackbar = { show: true, msg, color: type === 'success' ? 'green' : 'red' };
        },


        async salvarProduto() {
            if (!this.isFormValid()) return;

            this.loading = true;
            try {
                const produtoSalvo = await adicionar(this.form);
                this.showSnack('Produto salvo com sucesso!', 'success');

                setTimeout(() => this.$router.go(-1), 1000);
                console.log('Retorno do backend:', produtoSalvo);
            } catch (e) {
                console.error(e);
                this.showSnack('Erro ao salvar produto', 'error');
            } finally {
                this.loading = false;
            }
        },
    },
});
</script>


<style scoped>
.card-NovoProduto {
    padding: 30px;
}

.text-right {
    text-align: right;
}

.tela-produto-novo {
    background-color: #f5f5f5;
    min-height: 100vh;
    padding: 30px;
}

.header-linha {
    margin-bottom: 15px;
}

.bt-voltar {
    color: #1877cf;
}

.titulo-pagina {
    font-weight: bold;
    text-align: left;
}

.v-card {
    padding: 24px;
}

.bt-salvar {
    color: white;
    background-color: #1877cf !important;
}

::v-deep main.content {
    padding: 0 !important;
    background-color: #f5f5f5 !important;
}

.v-container {
    padding: 0 !important;
}
</style>
