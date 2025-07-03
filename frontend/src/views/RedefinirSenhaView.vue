<template>
    <div class="esqueci-senha-container">
        <div class="form-box">
            <h1>Redefinir senha</h1>
            <p>Insira uma nova senha para sua conta.</p>

            <BaseAlert v-model="alert.visible" :message="alert.message" :type="alert.type" />

            <form @submit.prevent="redefinirSenha">
                <BaseInput id="senha" label="Nova senha" type="password" placeholder="Digite sua nova senha"
                    v-model="senha" hint="Deve conter no mínimo 8 caracteres, incluindo letras e números." />

                <BaseInput id="confirmacao" label="Confirmar senha" type="password"
                    placeholder="Confirme sua nova senha" v-model="confirmacao" />

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
import { isSenhaForte } from "@/utils/validation";

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

    alert = {
        message: "",
        type: "info" as "success" | "error" | "info",
        visible: false,
    };

    async redefinirSenha() {
        const token = this.$route.query.token;

        if (!this.senha || !this.confirmacao) {
            this.alert = {
                message: "Preencha todos os campos.",
                type: "error",
                visible: true,
            };
            return;
        }

        if (!isSenhaForte(this.senha)) {
            this.alert = {
                message: "A senha deve conter no mínimo 8 caracteres, incluindo letras e números.",
                type: "error",
                visible: true,
            };
            return;
        }

        if (this.senha !== this.confirmacao) {
            this.alert = {
                message: "As senhas não coincidem.",
                type: "error",
                visible: true,
            };
            return;
        }

        try {
            const resposta = await redefinirSenha(token as string, this.senha);
            this.alert = {
                message: resposta?.mensagem || "Senha redefinida com sucesso!",
                type: "success",
                visible: true,
            };

            setTimeout(() => {
                this.$router.push("/");
            }, 3000);
        } catch (error) {
            this.alert = {
                message: "Erro ao redefinir a senha.",
                type: "error",
                visible: true,
            };
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

.full-width {
    width: 100%;
}
</style>