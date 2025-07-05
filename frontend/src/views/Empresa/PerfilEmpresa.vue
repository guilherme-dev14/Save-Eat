<template>
  <NavigationDrawer>
    <div class="perfil-wrapper">
      <h2>Perfil</h2>

      <div class="card">
        <h2 class="section-title">Detalhes</h2>

        <form @submit.prevent="salvarPerfil" class="grid-form">
          <BaseInput id="nome" label="Nome" v-model="empresa.nome" placeholder="Nome da empresa" />
          <BaseInput id="cnpj" label="CNPJ" v-model="empresa.cnpj" placeholder="00.000.000/0001-00"
            @input="empresa.cnpj = aplicarMascaraCnpj(empresa.cnpj)" maxlength="18" />
          <BaseInput id="logradouro" label="Logradouro" v-model="empresa.localizacao.logradouro"
            placeholder="Rua Exemplo" />
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

      <BaseAlert v-model="alert.visible" :message="alert.message" :type="alert.type" />
    </div>
  </NavigationDrawer>
</template>

<script lang="ts">
import { defineComponent, reactive, onMounted } from 'vue'
import NavigationDrawer from '@/components/NavigationDrawer.vue'
import BaseInput from '@/components/BaseInput.vue'
import BaseButton from '@/components/BaseButton.vue'
import BaseAlert from '@/components/BaseAlert.vue'
import { buscarEmpresa, atualizarEmpresa } from '@/services/empresaService'
import type { Empresa } from '@/interface/empresa'
import {
  isCampoPreenchido,
  isCnpjValido,
  isCepValido,
  aplicarMascaraCnpj
} from '@/utils/validation'

export default defineComponent({
  name: 'PerfilEmpresa',
  components: {
    NavigationDrawer,
    BaseInput,
    BaseButton,
    BaseAlert
  },
  methods: {
    aplicarMascaraCnpj
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

    const alert = reactive({
      message: '',
      type: 'success' as 'success' | 'error' | 'info',
      visible: false
    })

    onMounted(async () => {
      const email = localStorage.getItem('email')
      if (!email) {
        alert.message = 'Email não encontrado.'
        alert.type = 'error'
        alert.visible = true
        return
      }

      try {
        const dados = await buscarEmpresa(email)
        Object.assign(empresa, dados)
      } catch {
        alert.message = 'Erro ao carregar os dados.'
        alert.type = 'error'
        alert.visible = true
      }
    })

    const validarFormulario = (): boolean => {
      const loc = empresa.localizacao

      if (!isCampoPreenchido(empresa.nome)) {
        alert.message = 'Nome da empresa é obrigatório.'
        alert.type = 'error'
        alert.visible = true
        return false
      }

      if (!isCnpjValido(empresa.cnpj)) {
        alert.message = 'CNPJ inválido.'
        alert.type = 'error'
        alert.visible = true
        return false
      }

      if (!isCampoPreenchido(loc.logradouro)) {
        alert.message = 'Logradouro é obrigatório.'
        alert.type = 'error'
        alert.visible = true
        return false
      }

      if (!loc.numero || loc.numero <= 0) {
        alert.message = 'Número deve ser maior que 0.'
        alert.type = 'error'
        alert.visible = true
        return false
      }

      if (!isCampoPreenchido(loc.bairro)) {
        alert.message = 'Bairro é obrigatório.'
        alert.type = 'error'
        alert.visible = true
        return false
      }

      if (!isCampoPreenchido(loc.cidade)) {
        alert.message = 'Cidade é obrigatória.'
        alert.type = 'error'
        alert.visible = true
        return false
      }

      if (!isCepValido(loc.cep)) {
        alert.message = 'CEP inválido. Use o formato 00000-000.'
        alert.type = 'error'
        alert.visible = true
        return false
      }

      if (!isCampoPreenchido(loc.estado)) {
        alert.message = 'UF é obrigatória.'
        alert.type = 'error'
        alert.visible = true
        return false
      }

      return true
    }

    const salvarPerfil = async () => {
      if (!validarFormulario()) return

      const email = localStorage.getItem('email')
      try {
        await atualizarEmpresa(email || '', empresa)
        alert.message = 'Salvo com sucesso!'
        alert.type = 'success'
        alert.visible = true
      } catch {
        alert.message = 'Erro ao salvar.'
        alert.type = 'error'
        alert.visible = true
      }
    }

    return { empresa, alert, salvarPerfil }
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