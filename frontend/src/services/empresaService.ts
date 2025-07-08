import type { Empresa } from '@/interface/empresa'

const API = 'http://localhost:8080'

export async function buscarEmpresa(email: string): Promise<Empresa> {
    const res = await fetch(`${API}/empresa/${email}`)

    if (!res.ok) {
        throw new Error('Erro ao buscar dados da empresa')
    }

    const data = await res.json()
    return data as Empresa
}

export async function atualizarEmpresa(email: string, dados: Empresa): Promise<Empresa> {
    const res = await fetch(`${API}/empresa/${email}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dados)
    })

    if (!res.ok) {
        throw new Error('Erro ao atualizar dados da empresa')
    }

    const data = await res.json()
    return data as Empresa
}
export async function buscarPorIdEmpresa(id: number): Promise<Empresa> {
    const res = await fetch(`${API}/empresa/id/${id}`)

    if (!res.ok) {
        throw new Error('Erro ao buscar empresa por ID')
    }

    const data = await res.json()
    return data as Empresa
}
