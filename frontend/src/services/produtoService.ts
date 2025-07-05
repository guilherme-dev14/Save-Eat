import type { Produto } from '@/interface/produto'

const API = 'http://localhost:8080'

export async function listarDestaques(): Promise<Produto[]> {
  const res = await fetch(`${API}/produtos/destaques`)
  
  if (!res.ok) {
    throw new Error('Erro ao buscar produtos em destaque')
  }

  const data = await res.json()
  return data as Produto[]
}