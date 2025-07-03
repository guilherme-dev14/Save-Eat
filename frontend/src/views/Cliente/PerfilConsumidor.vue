<template>
  <NavigationDrawer>
    <div class="perfil-wrapper">
      <h2>Perfil</h2>

      <div class="card">
        <h2 class="section-title">Detalhes</h2>

        <form @submit.prevent="salvarPerfil" class="grid-form">
          <BaseInput id="nome" label="Nome" v-model="consumidor.nome" placeholder="Digite seu nome" />
          <BaseInput id="cpf" label="CPF" v-model="consumidor.cpf" placeholder="000.000.000-00"
            @input="consumidor.cpf = aplicarMascaraCPF(consumidor.cpf)" maxlength="14" />

          <h2 class="section-title full-width">Preferências</h2>
          <BaseInput id="bairro" label="Bairro" v-model="consumidor.bairro" placeholder="Digite seu bairro"
            class="full-width" />
          <BaseInput id="categoria" label="Categoria Favorita" v-model="consumidor.categoriaFavorita"
            placeholder="Ex: Hortifruti" class="full-width" />

          <div class="form-group full-width">
            <BaseButton class="botao-salvar" type="submit">Salvar</BaseButton>
          </div>
        </form>
      </div>

      <BaseAlert v-model="alert.visible" :message="alert.message" :type="alert.type" />
    </div>
  </NavigationDrawer>
</template>

<script lang="ts">
import { defineComponent, reactive, onMounted } from "vue";
import NavigationDrawer from "@/components/NavigationDrawer.vue";
import BaseInput from "@/components/BaseInput.vue";
import BaseButton from "@/components/BaseButton.vue";
import BaseAlert from "@/components/BaseAlert.vue";
import {
  buscarConsumidor,
  atualizarConsumidor,
} from "@/services/consumidorService";
import type { Consumidor } from "@/interface/consumidor";
import { aplicarMascaraCpf, isCpfValido } from "@/utils/validation";

export default defineComponent({
  name: "PerfilConsumidor",
  components: {
    NavigationDrawer,
    BaseInput,
    BaseButton,
    BaseAlert,
  },
  methods: {
    aplicarMascaraCPF: aplicarMascaraCpf,
  },
  setup() {
    const consumidor = reactive<Consumidor>({
      nome: "",
      cpf: "",
      bairro: "",
      categoriaFavorita: "",
      email: "",
    });

    const alert = reactive({
      message: "",
      type: "success" as "success" | "error" | "info",
      visible: false,
    });

    onMounted(async () => {
      const email = localStorage.getItem("email");
      if (!email) {
        alert.message = "Email não encontrado.";
        alert.type = "error";
        alert.visible = true;
        return;
      }

      try {
        const dados = await buscarConsumidor(email);
        Object.assign(consumidor, dados);
      } catch {
        alert.message = "Erro ao carregar os dados.";
        alert.type = "error";
        alert.visible = true;
      }
    });

    const salvarPerfil = async () => {
      if (!isCpfValido(consumidor.cpf)) {
        alert.message = "CPF inválido.";
        alert.type = "error";
        alert.visible = true;
        return;
      }

      const email = localStorage.getItem("email");
      try {
        await atualizarConsumidor(email || "", consumidor);
        alert.message = "Salvo com sucesso!";
        alert.type = "success";
        alert.visible = true;
      } catch {
        alert.message = "Erro ao salvar.";
        alert.type = "error";
        alert.visible = true;
      }
    };

    return { consumidor, alert, salvarPerfil };
  },
});
</script>

<style scoped>
.perfil-wrapper h2 {
  margin-left: 0;
  padding-left: 0;
  text-align: left;
}

.card {
  background: #fff;
  padding: 24px;
  border-radius: 8px;
  border: 1px solid #ddd;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 16px;
  margin-top: 24px;
  color: #111827;
}

.grid-form {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.full-width {
  grid-column: 1 / -1;
}

.form-group.full-width {
  grid-column: 1 / -1;
}

.botao-salvar {
  width: 100%;
}
</style>