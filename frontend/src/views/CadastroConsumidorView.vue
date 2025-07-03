<template>
    <div class="cadastro-container">
        <div class="cadastro-image"></div>
        <div class="cadastro-form">
            <BaseAlert v-model="alert.visible" :message="alert.message" :type="alert.type" />
            <h1>Criar uma Conta Cliente</h1>

            <form @submit.prevent="cadastrar">
                <div class="input-row">
                    <div class="half">
                        <BaseInput id="nome" label="Nome" v-model="consumidor.nome" placeholder="Digite seu nome"
                            required />
                    </div>
                    <div class="half">
                        <BaseInput id="cpf" label="CPF" v-model="consumidor.cpf" placeholder="Digite seu CPF"
                            maxlength="14" @input="consumidor.cpf = aplicarMascaraCpf(consumidor.cpf)" required />
                    </div>
                </div>

                <BaseInput id="email" label="E-mail" v-model="consumidor.email" type="email"
                    placeholder="Digite seu e-mail" required />
                <BaseInput id="senha" label="Senha" v-model="consumidor.senha" type="password"
                    placeholder="Digite sua senha" required
                    hint="Deve ser uma combinação de no mínimo 8 letras, números e símbolos." />

                <BaseButton type="submit">Cadastrar</BaseButton>

                <hr />
                <router-link to="/" class="link-voltar">Já tem uma conta?</router-link>
            </form>
        </div>
    </div>
</template>

<script lang="ts">
import { defineComponent, reactive } from 'vue'
import BaseInput from '@/components/BaseInput.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseAlert from '@/components/BaseAlert.vue'
import { cadastrarConsumidor } from '@/services/authService'
import type { ConsumidorCadastro } from '@/interface/consumidorCadastro'
import {
    aplicarMascaraCpf,
    isCampoPreenchido,
    isCpfValido,
    isEmailValido,
    isSenhaForte
} from '@/utils/validation'

export default defineComponent({
    components: {
        BaseInput,
        BaseButton,
        BaseAlert
    },
    setup() {
        const consumidor = reactive<ConsumidorCadastro>({
            nome: '',
            cpf: '',
            email: '',
            senha: ''
        })

        const alert = reactive({
            message: '',
            type: 'error' as 'error' | 'success',
            visible: false
        })

        const exibirErro = (mensagem: string) => {
            alert.message = mensagem
            alert.type = 'error'
            alert.visible = true
        }

        const validarFormulario = (): boolean => {
            if (!isCampoPreenchido(consumidor.nome)) {
                exibirErro('Nome é obrigatório.')
                return false
            }

            if (!isCpfValido(consumidor.cpf)) {
                exibirErro('CPF inválido.')
                return false
            }

            if (!isEmailValido(consumidor.email)) {
                exibirErro('E-mail inválido.')
                return false
            }

            if (!isSenhaForte(consumidor.senha)) {
                exibirErro('A senha deve conter no mínimo 8 caracteres, com letras e números.')
                return false
            }

            return true
        }

        const cadastrar = async () => {
            if (!validarFormulario()) return

            try {
                await cadastrarConsumidor(consumidor)
                alert.message = 'Cadastro realizado com sucesso!'
                alert.type = 'success'
                alert.visible = true

                setTimeout(() => {
                    window.location.href = '/'
                }, 2000)
            } catch (error: any) {
                alert.message = error.message || 'Erro ao cadastrar consumidor.'
                alert.type = 'error'
                alert.visible = true
            }
        }

        return {
            consumidor,
            alert,
            cadastrar,
            aplicarMascaraCpf
        }
    }
})
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