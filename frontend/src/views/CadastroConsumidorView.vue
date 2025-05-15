<template>
    <div class="cadastro-container">
        <div class="cadastro-image"></div>
        <div class="cadastro-form">
        <BaseAlert v-if="alertCadastro" :message="alertCadastro" :type="alertType" />
        <h1>Criar uma Conta Cliente</h1>

        <form @submit.prevent="cadastrarConsumidor">
            <div class="input-row">
                <div class="half">
                    <BaseInput id="nome" label="Nome" v-model="nome" placeholder="Digite seu nome" />
                </div>
                <div class="half">
                    <BaseInput id="cpf" label="CPF" v-model="cpf" placeholder="Digite seu CPF" />
                </div>
            </div>

            <BaseInput id="email" label="E-mail" v-model="email" type="email" placeholder="Digite seu e-mail" />
            <BaseInput id="senha" label="Senha" v-model="senha" type="password" placeholder="Digite sua senha" />

            <BaseButton type="submit">Cadastrar</BaseButton>

            <hr />

            <router-link to="/" class="link-voltar">Já tem uma conta?</router-link>
        </form>
        </div>
    </div>
</template>
<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import BaseInput from '@/components/BaseInput.vue';
import BaseButton from '@/components/BaseButton.vue';
import { cadastrarConsumidor } from '@/services/authService';
import BaseAlert from '@/components/BaseAlert.vue';

@Component({
    components: {
        BaseInput,
        BaseButton,
        BaseAlert,
    }
})
    export default class CadastroConsumidorView extends Vue {
    nome = '';
    cpf = '';
    email = '';
    senha = '';
    alertCadastro: string | null = null;
    alertType = 'error';

    async cadastrarConsumidor() {
        try {
            await cadastrarConsumidor({
                nome: this.nome,
                cpf: this.cpf,
                email: this.email,
                senha: this.senha
            });

            this.alertType = 'success';
            this.alertCadastro = 'Cadastro realizado com sucesso!';

            setTimeout(() => {
                this.$router.push('/');
            }, 2000);
        } catch (error: any) {
            this.alertType = 'error';
            this.alertCadastro = error.message || 'Erro ao cadastrar consumidor.';
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

.row {
    display: flex;
    gap: 20px;
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

.input-row {
    display: flex;
    gap: 16px;
}

.half {
    flex: 1;
}

</style>