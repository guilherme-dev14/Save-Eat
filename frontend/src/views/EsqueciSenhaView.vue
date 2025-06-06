<template>
  <div class="esqueci-senha-container">
    <div class="form-box">
      <h1>Esqueceu sua senha?</h1>
      <p>
        Não há motivo para preocupação, vamos te enviar uma mensagem para ajudar
        a redefinir a sua senha.
      </p>

      <BaseAlert v-if="mensagem" :message="mensagem" :type="alertType" />

      <form @submit.prevent="enviarSolicitacao">
        <BaseInput
          id="email"
          label="E-mail"
          type="email"
          placeholder="Digite o seu e-mail"
          v-model="email"
        />

        <BaseButton class="full-width" type="submit">
          Recuperar senha
        </BaseButton>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import BaseInput from "@/components/BaseInput.vue";
import BaseButton from "@/components/BaseButton.vue";
import { recuperarSenha } from "@/services/authService";
import BaseAlert from "@/components/BaseAlert.vue";

@Component({
  components: {
    BaseInput,
    BaseButton,
    BaseAlert,
  },
})
export default class EsqueciSenhaView extends Vue {
  email = "";
  mensagem = "";
  alertType: "error" | "info" = "info";

  async enviarSolicitacao() {
    if (!this.email.trim()) {
      this.alertType = "error";
      this.mensagem = "Por favor, informe o e-mail.";
      return;
    }

    try {
      const resposta = await recuperarSenha(this.email);
      this.alertType = "info";
      this.mensagem = resposta?.mensagem || "Se o e-mail estiver cadastrado, enviaremos uma mensagem.";

      setTimeout(() => {
        this.$router.push("/");
      }, 3000);
    } catch (error) {
      this.alertType = "error";
      this.mensagem = "Erro ao enviar solicitação.";
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
