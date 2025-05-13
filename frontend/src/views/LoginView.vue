<template>
  <div class="login-container">
    <div class="login-image"></div>
    <div class="login-form">
      <h1>Entrar</h1>

      <form @submit.prevent="login">
        <BaseInput
          id="email"
          label="E-mail"
          v-model="email"
          type="email"
          placeholder="Digite seu e-mail"
        />

        <BaseInput
          id="password"
          label="Senha"
          v-model="password"
          type="password"
          placeholder="Digite sua senha"
          hint="Deve ser uma combinação de no mínimo 8 letras, números e símbolos."
        />

        <div class="forgot-password">
          <a href="#">Esqueceu sua senha?</a>
        </div>

        <BaseButton type="submit">Entrar</BaseButton>

        <hr />

        <div class="signup-links">
          <a href="#">É cliente e ainda não tem conta? Crie uma conta cliente</a>
          <a href="#">É empresa e ainda não tem conta? Crie uma conta empresa</a>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import BaseInput from '@/components/BaseInput.vue';
import BaseButton from '@/components/BaseButton.vue';
import { login } from '@/services/authService';

@Component({
  components: {
    BaseInput,
    BaseButton
  }
})
export default class LoginView extends Vue {
  email = '';
  password = '';

  async login() {
    try {
      const data = await login(this.email, this.password);

      //TODO: Validar tratamentos
      localStorage.setItem('token', data.token);
      localStorage.setItem('tipoUsuario', data.tipoUsuario);
      localStorage.setItem('email', data.email);

      //TODO: Redirecionar para a página correta
      this.$router.push('/about');
    } catch (error: any) {
      alert(error.message || 'Erro ao fazer login');
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  height: 100vh;
}

.login-image {
  flex: 1;
  background-image: url('@/assets/login-image.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.login-form {
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

.forgot-password {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

.forgot-password a {
  font-size: 14px;
  color: #2ba839;
  text-decoration: none;
}

hr {
  margin: 20px 0;
  border: none;
  height: 1px;
  background-color: #e5e7eb;
}

.signup-links {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.signup-links a {
  font-size: 14px;
  color: #2ba839;
  text-decoration: none;
  align-self: flex-start;
}
</style>
