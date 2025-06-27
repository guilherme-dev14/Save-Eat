import type { EmpresaCadastro } from '@/interface/empresaCadastro'
import type { ConsumidorCadastro } from '@/interface/consumidorCadastro'

const API_URL = 'http://localhost:8080/auth'

export async function login(email: string, senha: string) {
    const response = await fetch(`${API_URL}/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, senha })
    })

    if (!response.ok) {
        throw new Error('Credenciais inválidas')
    }

    return await response.json()
}

export async function cadastrarConsumidor(payload: ConsumidorCadastro): Promise<string> {
    const response = await fetch(`${API_URL}/cadastrar-consumidor`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })

    if (!response.ok) {
        throw new Error('Erro ao cadastrar consumidor')
    }

    return await response.text()
}

export async function cadastrarEmpresa(payload: EmpresaCadastro): Promise<string> {
    const response = await fetch(`${API_URL}/cadastrar-empresa`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })

    if (!response.ok) {
        throw new Error('Erro ao cadastrar empresa')
    }

    return await response.text()
}

export async function recuperarSenha(email: string): Promise<any> {
    const response = await fetch(`${API_URL}/recuperar-senha`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email })
    })

    if (!response.ok) {
        throw new Error('Erro ao recuperar senha')
    }

    return await response.json()
}

export async function redefinirSenha(token: string, novaSenha: string): Promise<any> {
    const response = await fetch(`${API_URL}/redefinir-senha`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ token, novaSenha })
    })

    if (!response.ok) {
        throw new Error('Erro ao redefinir senha')
    }

    return await response.json()
}
