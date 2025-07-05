<template>
    <div class="cadastro-container">
        <div class="cadastro-image"></div>
        <div class="cadastro-form">
            <BaseAlert v-model="alert.visible" :message="alert.message" :type="alert.type" />
            <h1>Criar uma Conta Empresa</h1>

            <form @submit.prevent="cadastrarEmpresa">
                <div class="input-row">
                    <div class="half">
                        <BaseInput id="nome" label="Nome" v-model="empresa.nome" placeholder="Digite o nome da empresa"
                            required />
                    </div>
                    <div class="half">
                        <BaseInput id="cnpj" label="CNPJ" v-model="empresa.cnpj" placeholder="Digite o CNPJ"
                            @input="empresa.cnpj = aplicarMascaraCnpj(empresa.cnpj)" maxlength="18" required />
                    </div>
                </div>

                <BaseInput id="email" label="E-mail" v-model="empresa.email" type="email" placeholder="Digite o e-mail"
                    required />
                <BaseInput id="senha" label="Senha" v-model="empresa.senha" type="password" placeholder="Digite a senha"
                    hint="Deve conter no mínimo 8 caracteres, incluindo letras e números." required />

                <div class="input-row">
                    <div class="half">
                        <BaseInput id="logradouro" label="Logradouro" v-model="empresa.localizacao.logradouro"
                            placeholder="Digite o logradouro" required />
                    </div>
                    <div class="half">
                        <BaseInput id="numero" label="Número" v-model.number="empresa.localizacao.numero"
                            placeholder="Digite o número" required />
                    </div>
                </div>

                <div class="input-row">
                    <div class="half">
                        <BaseInput id="bairro" label="Bairro" v-model="empresa.localizacao.bairro"
                            placeholder="Digite o bairro" required />
                    </div>
                    <div class="half">
                        <BaseInput id="cidade" label="Cidade" v-model="empresa.localizacao.cidade"
                            placeholder="Digite a cidade" required />
                    </div>
                </div>

                <div class="input-row">
                    <div class="half">
                        <BaseInput id="cep" label="CEP" v-model="empresa.localizacao.cep" placeholder="Digite o CEP"
                            @input="empresa.localizacao.cep = aplicarMascaraCep(empresa.localizacao.cep)" maxlength="9"
                            required />
                    </div>
                    <div class="half">
                        <BaseInput id="estado" label="UF" v-model="empresa.localizacao.estado" placeholder="UF"
                            maxlength="2" required />
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
import {
    aplicarMascaraCnpj,
    aplicarMascaraCep,
    isCampoPreenchido,
    isEmailValido,
    isSenhaForte,
    isCepValido,
    isCnpjValido
} from '@/utils/validation'

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

    alert = {
        message: '',
        type: 'error' as 'error' | 'success',
        visible: false
    }

    aplicarMascaraCnpj = aplicarMascaraCnpj
    aplicarMascaraCep = aplicarMascaraCep

    validarFormulario(): boolean {
        if (!isCampoPreenchido(this.empresa.nome)) {
            return this.dispararErro('Nome da empresa é obrigatório.')
        }

        if (!isCnpjValido(this.empresa.cnpj)) {
            return this.dispararErro('CNPJ inválido.')
        }

        if (!isEmailValido(this.empresa.email)) {
            return this.dispararErro('E-mail inválido.')
        }

        if (!isSenhaForte(this.empresa.senha)) {
            return this.dispararErro('A senha deve ter pelo menos 8 caracteres, incluindo letras e números.')
        }

        const loc = this.empresa.localizacao

        if (!isCampoPreenchido(loc.logradouro)) {
            return this.dispararErro('Logradouro é obrigatório.')
        }

        if (!loc.numero || loc.numero <= 0) {
            return this.dispararErro('Número do endereço deve ser maior que 0.')
        }

        if (!isCampoPreenchido(loc.bairro)) {
            return this.dispararErro('Bairro é obrigatório.')
        }

        if (!isCampoPreenchido(loc.cidade)) {
            return this.dispararErro('Cidade é obrigatória.')
        }

        if (!isCepValido(loc.cep)) {
            return this.dispararErro('CEP inválido. Use o formato 00000-000.')
        }

        if (!isCampoPreenchido(loc.estado)) {
            return this.dispararErro('UF é obrigatória.')
        }

        return true
    }

    dispararErro(msg: string): boolean {
        this.alert.message = msg
        this.alert.type = 'error'
        this.alert.visible = true
        return false
    }

    async cadastrarEmpresa() {
        if (!this.validarFormulario()) return

        try {
            await cadastrarEmpresa(this.empresa)
            this.alert.message = 'Cadastro realizado com sucesso!'
            this.alert.type = 'success'
            this.alert.visible = true
            setTimeout(() => this.$router.push('/'), 2000)
        } catch (error: any) {
            this.alert.message = error.message || 'Erro ao cadastrar empresa.'
            this.alert.type = 'error'
            this.alert.visible = true
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