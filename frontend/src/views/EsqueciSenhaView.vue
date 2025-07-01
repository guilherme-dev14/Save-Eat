<template>
  <div class="esqueci-senha-container">
    <div class="form-box">
      <h1>Esqueceu sua senha?</h1>
      <p>
        Não há motivo para preocupação, vamos te enviar uma mensagem para ajudar
        a redefinir a sua senha.
      </p>

      <BaseAlert v-model="alert.visible" :message="alert.message" :type="alert.type" />

      <form @submit.prevent="enviarSolicitacao">
        <BaseInput id="email" label="E-mail" type="email" placeholder="Digite o seu e-mail" v-model="email" />

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
import BaseAlert from "@/components/BaseAlert.vue";
import { recuperarSenha } from "@/services/authService";
import { isEmailValido } from "@/utils/validation";

@Component({
  components: {
    BaseInput,
    BaseButton,
    BaseAlert,
  },
})
export default class EsqueciSenhaView extends Vue {
  email = "";
  alert = {
    message: "",
    type: "info" as "error" | "info",
    visible: false,
  };

  async enviarSolicitacao() {
    if (!this.email.trim()) {
      return this.dispararAlerta("Por favor, informe o e-mail.", "error");
    }

    if (!isEmailValido(this.email)) {
      return this.dispararAlerta("E-mail inválido.", "error");
    }

    try {
      const resposta = await recuperarSenha(this.email);
      this.dispararAlerta(
        resposta?.mensagem ||
        "Se o e-mail estiver cadastrado, enviaremos uma mensagem.",
        "info"
      );

      setTimeout(() => {
        this.$router.push("/");
      }, 3000);
    } catch (error) {
      this.dispararAlerta("Erro ao enviar solicitação.", "error");
    }
  }

  dispararAlerta(mensagem: string, tipo: "error" | "info") {
    this.alert.message = mensagem;
    this.alert.type = tipo;
    this.alert.visible = true;
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