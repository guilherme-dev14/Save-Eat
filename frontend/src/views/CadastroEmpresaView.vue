<template>
    <div class="cadastro-container">
        <div class="cadastro-image"></div>
        <div class="cadastro-form">
        <BaseAlert v-if="alertCadastro" :message="alertCadastro" :type="alertType" />
        <h1>Criar uma Conta Empresa</h1>

        <form @submit.prevent="cadastrarEmpresa">
            <div class="input-row">
            <div class="half">
                <BaseInput id="nome" label="Nome" v-model="empresa.nome" placeholder="Digite o nome da empresa" />
            </div>
            <div class="half">
                <BaseInput id="cnpj" label="CNPJ" v-model="empresa.cnpj" placeholder="Digite o CNPJ" />
            </div>
            </div>

            <BaseInput id="email" label="E-mail" v-model="empresa.email" type="email" placeholder="Digite o e-mail" />
            <BaseInput id="senha" label="Senha" v-model="empresa.senha" type="password" placeholder="Digite a senha" />

            <div class="input-row">
            <div class="half">
                <BaseInput id="logradouro" label="Logradouro" v-model="empresa.localizacao.logradouro" placeholder="Digite o logradouro" />
            </div>
            <div class="half">
                <BaseInput id="numero" label="Número" v-model.number="empresa.localizacao.numero" placeholder="Digite o número" />
            </div>
            </div>

            <div class="input-row">
            <div class="half">
                <BaseInput id="bairro" label="Bairro" v-model="empresa.localizacao.bairro" placeholder="Digite o bairro" />
            </div>
            <div class="half">
                <BaseInput id="cidade" label="Cidade" v-model="empresa.localizacao.cidade" placeholder="Digite a cidade" />
            </div>
            </div>

            <div class="input-row">
            <div class="half">
                <BaseInput id="cep" label="CEP" v-model="empresa.localizacao.cep" placeholder="Digite o CEP" />
            </div>
            <div class="half">
                <BaseInput id="estado" label="UF" v-model="empresa.localizacao.estado" placeholder="UF" />
            </div>
            </div>

            <BaseButton type="submit">Cadastrar</BaseButton>

            <hr />
            <router-link to="/" class="link-voltar">Já tem uma conta?</router-link>
        </form>
        </div>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import BaseInput from '@/components/BaseInput.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseAlert from '@/components/BaseAlert.vue'
import { cadastrarEmpresa } from '@/services/authService'
import { EmpresaCadastro } from '@/interface/empresaCadastro'

@Component({
    components: {
        BaseInput,
        BaseButton,
        BaseAlert
    }
})
export default class CadastroEmpresaView extends Vue {
    empresa: EmpresaCadastro = {
        nome: '',
        cnpj: '',
        email: '',
        senha: '',
        localizacao: {
            logradouro: '',
            numero: 0,
            bairro: '',
            cidade: '',
            cep: '',
            estado: ''
        }
    }

    alertCadastro: string | null = null
    alertType = 'error'

    async cadastrarEmpresa() {
        try {
        await cadastrarEmpresa(this.empresa)
        this.alertType = 'success'
        this.alertCadastro = 'Cadastro realizado com sucesso!'
        setTimeout(() => this.$router.push('/'), 2000)
        } catch (error: any) {
        this.alertType = 'error'
        this.alertCadastro = error.message || 'Erro ao cadastrar empresa.'
        }
    }
}
</script>

<style scoped>
.cadastro-container {
    display: flex;
    min-height: 100vh;
    align-items: stretch;
}
.cadastro-image {
    flex: 1;
    background-image: url('@/assets/login-image.png');
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
}
.cadastro-form {
    flex: 1;
    padding: 80px;
    display: flex;
    flex-direction: column;
    justify-content: center;
}
h1 {
    margin-bottom: 30px;
    font-size: 32px;
}
form {
    display: flex;
    flex-direction: column;
}
.input-row {
    display: flex;
    gap: 16px;
}
.half {
    flex: 1;
}
hr {
    margin: 30px 0;
    border: none;
    height: 1px;
    background-color: #e5e7eb;
}
.link-voltar {
    font-size: 14px;
    color: #2ba839;
    text-decoration: none;
    align-self: flex-start;
}
</style>