<template>
  <NavigationDrawer>
    <div class="perfil-wrapper">
      <h2>Perfil</h2>

      <div class="card">
        <h2 class="section-title">Detalhes</h2>

        <form @submit.prevent="salvarPerfil" class="grid-form">
          <BaseInput id="nome" label="Nome" v-model="empresa.nome" placeholder="Nome da empresa" />
          <BaseInput id="cnpj" label="CNPJ" v-model="empresa.cnpj" placeholder="00.000.000/0001-00" />
          <BaseInput id="logradouro" label="Logradouro" v-model="empresa.localizacao.logradouro" placeholder="Rua Exemplo" />
          <BaseInput id="numero" label="Número" v-model="empresa.localizacao.numero" placeholder="123" />
          <BaseInput id="bairro" label="Bairro" v-model="empresa.localizacao.bairro" placeholder="Centro" />
          <BaseInput id="cidade" label="Cidade" v-model="empresa.localizacao.cidade" placeholder="Caxias do Sul" />
          <BaseInput id="cep" label="CEP" v-model="empresa.localizacao.cep" placeholder="95000-000" />
          <BaseInput id="uf" label="UF" v-model="empresa.localizacao.estado" placeholder="RS" />

          <div class="form-group full-width">
            <BaseButton class="botao-salvar" type="submit">Salvar</BaseButton>
          </div>
        </form>
      </div>

      <BaseAlert v-if="mensagem" :message="mensagem" :type="sucesso ? 'success' : 'error'" />
    </div>
  </NavigationDrawer>
</template>

<script lang="ts">
import { defineComponent, reactive, ref, onMounted } from 'vue'
import NavigationDrawer from '@/components/NavigationDrawer.vue'
import BaseInput from '@/components/BaseInput.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseAlert from '@/components/BaseAlert.vue'
import { buscarEmpresa, atualizarEmpresa } from '@/services/empresaService'
import type { Empresa } from '@/interface/empresa'

export default defineComponent({
  name: 'PerfilEmpresa',
  components: {
    NavigationDrawer,
    BaseInput,
    BaseButton,
    BaseAlert
  },
  setup() {
    const empresa = reactive<Empresa>({
      nome: '',
      cnpj: '',
      email: '',
      localizacao: {
        logradouro: '',
        numero: 0,
        bairro: '',
        cidade: '',
        cep: '',
        estado: ''
      }
    })

    const mensagem = ref('')
    const sucesso = ref(false)

    onMounted(async () => {
      const email = localStorage.getItem('email')
      if (!email) {
        mensagem.value = 'Email não encontrado.'
        sucesso.value = false
        return
      }

      try {
        const dados = await buscarEmpresa(email)
        Object.assign(empresa, dados)
      } catch {
        mensagem.value = 'Erro ao carregar os dados.'
        sucesso.value = false
      }
    })

    const salvarPerfil = async () => {
      const email = localStorage.getItem('email')
      try {
        await atualizarEmpresa(email || '', empresa)
        mensagem.value = 'Salvo com sucesso!'
        sucesso.value = true
      } catch {
        mensagem.value = 'Erro ao salvar.'
        sucesso.value = false
      }
    }

    return { empresa, mensagem, sucesso, salvarPerfil }
  }
})
</script>

<style scoped>
.perfil-wrapper h2 {
  margin-left: 0;
  padding-left: 0;
  text-align: left;
}

.page-title {
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 24px;
  color: #1f2937;
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
  margin-bottom: 20px;
  color: #111827;
}

.grid-form {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.form-group.full-width {
  grid-column: 1 / -1;
}

.botao-salvar {
  width: 100%;
}

</style>