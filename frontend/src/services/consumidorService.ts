import type { Consumidor } from '@/interface/consumidor'

const API = 'http://localhost:8080'

export async function buscarConsumidor(email: string): Promise<Consumidor> {
    const res = await fetch(`${API}/consumidores/${email}`)
    
    if (!res.ok) {
        throw new Error('Erro ao buscar consumidor.')
    }

    return await res.json()
    }

    export async function atualizarConsumidor(email: string, consumidor: Consumidor): Promise<void> {
    const res = await fetch(`${API}/consumidores/${email}`, {
        method: 'PUT',
        headers: {
        'Content-Type': 'application/json'
        },
        body: JSON.stringify(consumidor)
    })

    if (!res.ok) {
        throw new Error('Erro ao atualizar consumidor.')
    }
}