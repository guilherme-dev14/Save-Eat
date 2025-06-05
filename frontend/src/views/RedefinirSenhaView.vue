<template>
    <div class="esqueci-senha-container">
        <div class="form-box">
            <h1>Redefinir senha</h1>
            <p>Insira uma nova senha para sua conta.</p>

            <BaseAlert v-if="mensagem" :message="mensagem" :type="alertType" />

            <form @submit.prevent="redefinirSenha">
            <BaseInput
                id="senha"
                label="Nova senha"
                type="password"
                placeholder="Digite sua nova senha"
                v-model="senha"
            />

            <BaseInput
                id="confirmacao"
                label="Confirmar senha"
                type="password"
                placeholder="Confirme sua nova senha"
                v-model="confirmacao"
            />

            <BaseButton class="full-width" type="submit">Confirmar</BaseButton>
            </form>

        </div>
    </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import BaseInput from "@/components/BaseInput.vue";
import BaseButton from "@/components/BaseButton.vue";
import BaseAlert from "@/components/BaseAlert.vue";
import { redefinirSenha } from "@/services/authService";

@Component({
    components: {
        BaseInput,
        BaseButton,
        BaseAlert,
    },
})
export default class RedefinirSenhaView extends Vue {
    senha = "";
    confirmacao = "";
    mensagem = "";
    alertType: "success" | "error" | "info" = "success";

    async redefinirSenha() {
        const token = this.$route.query.token;

        if (!this.senha || !this.confirmacao) {
            this.alertType = "error";
            this.mensagem = "Preencha todos os campos.";
            return;
        }

        if (this.senha !== this.confirmacao) {
            this.alertType = "error";
            this.mensagem = "As senhas não coincidem.";
            return;
        }

        try {
            const resposta = await redefinirSenha(token as string, this.senha);
            this.alertType = "info";
            this.mensagem = resposta?.mensagem || "Senha redefinida com sucesso!";

            setTimeout(() => {
                this.$router.push("/");
            }, 3000);
        } catch (error) {
            this.alertType = "error";
            this.mensagem = "Erro ao redefinir a senha.";
        }
    }
}
</script>

<style scoped>
.esqueci-senha-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-image: url("@/assets/login-image.png");
    background-size: cover;
    background-position: center;
    box-sizing: border-box;
    overflow: hidden;
    margin: 0;
}

.form-box {
    background: white;
    padding: 60px 40px;
    border-radius: 8px;
    width: 100%;
    max-width: 450px;
    text-align: center;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

h1 {
    font-size: 28px;
    margin-bottom: 16px;
    color: #2c3e50;
}

p {
    font-size: 16px;
    margin-bottom: 32px;
    color: #555;
}

.mensagem {
    margin-top: 20px;
    color: green;
}

.full-width {
    width: 100%;
}
</style>
